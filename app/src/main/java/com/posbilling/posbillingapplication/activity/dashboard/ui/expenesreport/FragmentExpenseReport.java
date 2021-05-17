package com.posbilling.posbillingapplication.activity.dashboard.ui.expenesreport;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.activityparentreport.ActivityParentReport;
import com.posbilling.posbillingapplication.activity.dashboard.ActivityDashboard;
import com.posbilling.posbillingapplication.activity.filter.ActivityFilter;
import com.posbilling.posbillingapplication.activity.homeactivitytwo.HomeActivityTwo;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.lib.BaseFragment;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.model.realmcontoller.RealmController;
import com.posbilling.posbillingapplication.model.realmmodel.CustomerListRealm;

import com.posbilling.posbillingapplication.model.request.ExpenseReportPdfRequest;
import com.posbilling.posbillingapplication.model.response.ExpenseResponse;
import com.posbilling.posbillingapplication.model.response.PdfResponse;
import com.posbilling.posbillingapplication.retrofit.CustomRetroRequest;
import com.posbilling.posbillingapplication.utility.Utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;

import static com.posbilling.posbillingapplication.utility.Constants.ASCENDINGDESCENDING;
import static com.posbilling.posbillingapplication.utility.Constants.DISTRICT;
import static com.posbilling.posbillingapplication.utility.Constants.FILTERACTVITYREQUESTCODE;
import static com.posbilling.posbillingapplication.utility.Constants.FROMDATE;
import static com.posbilling.posbillingapplication.utility.Constants.GALLERY_PERMISSION_REQUEST_CODE;
import static com.posbilling.posbillingapplication.utility.Constants.MONTH;
import static com.posbilling.posbillingapplication.utility.Constants.SEEALL;
import static com.posbilling.posbillingapplication.utility.Constants.TALUKA;
import static com.posbilling.posbillingapplication.utility.Constants.TODATE;
import static com.posbilling.posbillingapplication.utility.Constants.VILLAGE;
import static com.posbilling.posbillingapplication.utility.Constants.YEAR;
import static com.posbilling.posbillingapplication.utility.Constants.langaugeCodeEnglish;
import static com.posbilling.posbillingapplication.utility.Constants.languageCodeMarathi;

public class FragmentExpenseReport extends BaseFragment implements OnRecyclerviewClick, ExpenseReportContractor.View {
    private Context mContext;
    private Activity activity;
    private Realm realm;
    private ExpenseReportAdapter adapter;
    private OnRecyclerviewClick onRecyclerviewClick = this;
    private ExpenseReportContractor.Presenter mPresenter;
    private ArrayList<ExpenseResponse.Data> expensereportsList = new ArrayList<>();
    private ArrayList<ExpenseResponse.Data> expensereportsListPdf = new ArrayList<>();
    private ArrayList<ExpenseResponse.Data> filterArraylist = new ArrayList<>();
    private String languageCode = "";
    private String fileN = null;
    private String filternamePdf = "-";
    private String nameTest = "";

    @BindView(R.id.recyclerview_expense_report)
    RecyclerView recyclerview_expense_report;
    @BindView(R.id.edittext_search)
    EditText edittext_search;
    @BindView(R.id.imageviewMic)
    ImageView imageviewMic;
    @BindView(R.id.textviewTotal)
    TextView textviewTotal;

    @OnClick(R.id.imageviewMic)
    void imageviewMic() {
        speechToTextFunc(mContext, edittext_search, 1, imageviewMic);
    }

    @OnClick(R.id.imagviewFilter)
    void imagviewFilter() {
        Intent intent = new Intent(mContext, ActivityFilter.class);
        startActivityForResult(intent, FILTERACTVITYREQUESTCODE);
    }

    @OnClick(R.id.fabshare)
    void fabshare() {
        if (Utility.getInstance().isOnline(mContext)) {
            getPdf();
        } else {
            Toast.makeText(mContext, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.whatsapp_floatingaction_button)
    void whatsapp_floatingaction_button(){
        if (Utility.getInstance().isOnline(mContext)) {
            getPdf();
        } else {
            Toast.makeText(mContext, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    public void methodFragment() {
        if (Utility.getInstance().isOnline(mContext)) {
            getPdf();
        } else {
            Toast.makeText(mContext, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }





    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void setPresenter() {
        mPresenter = new ExpesneReportPresenter(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_exoense_reports;
    }

    @Override
    public void onMakeServerCalls() {
        if (isAdded() || isVisible()) {
            ((ActivityParentReport) getContext()).setTitleOfScreen(getString(R.string.menu_expense_reports));
            ((ActivityParentReport) getContext()).setVisibilityofRamumunimjiVisible(true,2);
            ((ActivityParentReport) getContext()).castFragment(this,2);
            activity = getActivity();
            //          setSearchFilter();
            realm = RealmController.with(this).getRealm();
            setRecyclerview();
            setSearchFilter();
            getExpenseDetails();
        }
    }


    private void getPdf() {
        ExpenseReportPdfRequest expenseReportPdfRequest = new ExpenseReportPdfRequest();
        String currentDateForName = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(mContext);
        String firstnameForPdf = sharedPreferences.getString("FirstName", "");
        nameTest = getString(R.string.menu_expense_reports) + currentDateForName;
        expenseReportPdfRequest.setPDFName(nameTest);
        expenseReportPdfRequest.setRegId(Utility.getInstance().getclientRegId(mContext));
        languageCode = Utility.getInstance().getLanguage(mContext);
        if (languageCode.matches("") || languageCode.matches(langaugeCodeEnglish)) {
            expenseReportPdfRequest.setLangID("0");
        } else if (languageCode.matches(languageCodeMarathi)) {
            expenseReportPdfRequest.setLangID("1");
        } else {
            expenseReportPdfRequest.setLangID("1");
        }

        ArrayList<ExpenseReportPdfRequest.RecordList> arrayListRecordList = new ArrayList<>();
        int i = 1;
        for (ExpenseResponse.Data item : expensereportsListPdf) {
            ExpenseReportPdfRequest.RecordList recordList = new ExpenseReportPdfRequest().new RecordList();
            recordList.setSrno(String.valueOf(i));
            i++;
            recordList.setBusinessname(sharedPreferences.getString("ShopName", ""));
            recordList.setClientVillage(sharedPreferences.getString("Village", ""));
            recordList.setContactno(sharedPreferences.getString("ContactNumber", ""));
            recordList.setPeriod(filternamePdf);
            String date = item.getExpendDate();
            String subDate = date.substring(0, 10);
            String yyyy = subDate.substring(0, 4);
            String mm = subDate.substring(5, 7);
            String dd = subDate.substring(8, 10);


            recordList.setDate(dd + "/" + mm + "/" + yyyy);

            recordList.setExpType(item.getExpenseType());
            recordList.setExpDetail(item.getPurpose());
            recordList.setExpAmount(removeContainsDot(item.getAmount()));
            recordList.setRefernceid(item.getTransactionId());
            arrayListRecordList.add(recordList);

        }

        expenseReportPdfRequest.setRecordListArrayList(arrayListRecordList);

        Utility.getInstance().showProgressDialogue(mContext);
        mPresenter.getPDFData(expenseReportPdfRequest);
    }


    private void getExpenseDetails() {

        if (Utility.getInstance().isOnline(mContext)) {
            Utility.getInstance().showProgressDialogue(mContext);
            mPresenter.getExpnsesList(Utility.getInstance().getclientRegId(mContext));
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }



    //textchangelistner on list
    private void setSearchFilter() {
        edittext_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }


    private void setRecyclerview() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        adapter = new ExpenseReportAdapter(mContext, expensereportsList, onRecyclerviewClick, realm);
        recyclerview_expense_report.setLayoutManager(linearLayoutManager);
        recyclerview_expense_report.setAdapter(adapter);
    }


    private void filter(String text) {
        filterArraylist.clear();
        for (ExpenseResponse.Data name : expensereportsList) {
            if (name.getExpenseType().contains(text.toLowerCase())) {
                filterArraylist.add(name);
            }
        }
        adapter.filterList(filterArraylist);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mContext == null) {
            mContext = ((ActivityParentReport) getContext());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == FILTERACTVITYREQUESTCODE) {
            filterListFunctions(data);
        }
    }


    private void filterListFunctions(Intent data) {
        filternamePdf = "";
        Utility.getInstance().showProgressDialogue(mContext);
        Intent intent = data;

        ArrayList<ExpenseResponse.Data> expenseListRealmModels = new ArrayList<>();
        expenseListRealmModels.addAll(expensereportsList);

        ArrayList<CustomerListRealm> finalFilterList = new ArrayList<>();
        ArrayList<ExpenseResponse.Data> villageFilterList = new ArrayList<>();
//        ApplicableFilterList.addAll(customerList);


        String villageName = intent.getStringExtra(VILLAGE);

        //Village filter is added here on list
        if (intent.getStringExtra(VILLAGE) != null && !TextUtils.isEmpty(intent.getStringExtra(VILLAGE))) {
            villageFilterList.addAll(expenseListRealmModels);

        } else {
            villageFilterList.addAll(expenseListRealmModels);
        }

        ///Taluka filter is added here
        ArrayList<ExpenseResponse.Data> talukaFilterList = new ArrayList<>();
        if (intent.getStringExtra(TALUKA) != null && !TextUtils.isEmpty(intent.getStringExtra(TALUKA))) {
            talukaFilterList.addAll(villageFilterList);
        } else {
            talukaFilterList.addAll(villageFilterList);
        }

        ///District filter is added here
        ArrayList<ExpenseResponse.Data> districtFilterList = new ArrayList<>();
        if (intent.getStringExtra(DISTRICT) != null && !TextUtils.isEmpty(intent.getStringExtra(DISTRICT))) {
            districtFilterList.addAll(talukaFilterList);
        } else {
            districtFilterList.addAll(talukaFilterList);
        }

        ///fromDatefilter
        ArrayList<ExpenseResponse.Data> fromDateFilter = new ArrayList<>();
        if (intent.getStringExtra(FROMDATE) != null && !TextUtils.isEmpty(intent.getStringExtra(FROMDATE))) {
            filternamePdf = filternamePdf+intent.getStringExtra(FROMDATE);
            for (ExpenseResponse.Data item : districtFilterList) {
                SimpleDateFormat sdformat = new SimpleDateFormat("dd/M/yyyy", Locale.ENGLISH);
                SimpleDateFormat sdformatSecond = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
                try {
                    String fromdate = intent.getStringExtra(FROMDATE);
                    Calendar d1 = Calendar.getInstance();
                    d1.setTime(sdformat.parse(fromdate));
                    String date = item.getExpendDate();
                    String subDate = date.substring(0, 10);
                    Calendar d2 = Calendar.getInstance();
                    String year = subDate.substring(0, 4);
                    String month = subDate.substring(5, 7);
                    String dateString = subDate.substring(8, 10);

                    d2.setTime(sdformat.parse(dateString + "/" + month + "/" + year));
                    int i = d1.compareTo(d2);
                    if (d2.after(d1)) {
                        fromDateFilter.add(item);
                    }

                    if (d2.equals(d1)) {
                        fromDateFilter.add(item);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            fromDateFilter.addAll(districtFilterList);
        }

        ///toDatefilter
        ArrayList<ExpenseResponse.Data> toDateFilter = new ArrayList<>();
        if (intent.getStringExtra(TODATE) != null && !TextUtils.isEmpty(intent.getStringExtra(TODATE))) {
            filternamePdf = filternamePdf+"-"+intent.getStringExtra(TODATE);
            for (ExpenseResponse.Data item : fromDateFilter) {
                SimpleDateFormat sdformat = new SimpleDateFormat("dd/M/yyyy", Locale.ENGLISH);
                SimpleDateFormat sdformatSecond = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
                try {
                    Calendar d1 = Calendar.getInstance();
                    String dateTo = intent.getStringExtra(TODATE);
                    d1.setTime(sdformat.parse(intent.getStringExtra(TODATE)));
                    String date = item.getExpendDate();
                    String subDate = date.substring(0, 10);
                    Calendar d2 = Calendar.getInstance();
                    String year = subDate.substring(0, 4);
                    String month = subDate.substring(5, 7);
                    String dateString = subDate.substring(8, 10);
                    d2.setTime(sdformat.parse(dateString + "/" + month + "/" + year));
                    if (d2.before(d1)) {
                        toDateFilter.add(item);
                    }

                    if (d1.equals(d2)) {
                        toDateFilter.add(item);
                    }

                    /* else {
                        toDateFilter.add(item);
                    }*/
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            toDateFilter.addAll(fromDateFilter);
        }

        ////Selected month filter
        ArrayList<ExpenseResponse.Data> monthFilter = new ArrayList<>();
        if (intent.getStringExtra(MONTH) != null && !TextUtils.isEmpty(intent.getStringExtra(MONTH))) {
            filternamePdf = filternamePdf+"-"+intent.getStringExtra("monthStringForPdf");
            for (ExpenseResponse.Data item : toDateFilter) {
                SimpleDateFormat sdformatSecond = new SimpleDateFormat("yyyy-mm-dd");
                try {
                    String date = item.getExpendDate();
                    String subDate = date.substring(0, 10);
                    String yyyy = subDate.substring(0, 4);
                    String mm = subDate.substring(5, 7);
                    String dd = subDate.substring(8, 10);
                    subDate = dd + "/" + mm + "/" + yyyy;
                    Calendar d2 = Calendar.getInstance();
                    d2.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(subDate));
                    String monthSelected = intent.getStringExtra(MONTH);
                    int monthNumber = d2.get(Calendar.MONTH);
                    if ((monthNumber + 1) == Integer.valueOf(intent.getStringExtra(MONTH))) {
                        monthFilter.add(item);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            monthFilter.addAll(toDateFilter);
        }


        ////Selected year filter
        ArrayList<ExpenseResponse.Data> yearFilter = new ArrayList<>();
        if (intent.getStringExtra(YEAR) != null && !TextUtils.isEmpty(intent.getStringExtra(YEAR))) {
            filternamePdf = filternamePdf+"-"+intent.getStringExtra(YEAR);
            for (ExpenseResponse.Data item : monthFilter) {
                SimpleDateFormat sdformatSecond = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    String date = item.getExpendDate();
                    String subDate = date.substring(0, 10);
                    String yyyy = subDate.substring(0, 4);
                    String mm = subDate.substring(5, 7);
                    String dd = subDate.substring(8, 10);
                    subDate = dd + "/" + mm + "/" + yyyy;
                    Calendar d2 = Calendar.getInstance();
                    d2.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(subDate));
                    int yearNumber = d2.get(Calendar.YEAR);
                    if (yearNumber == Integer.valueOf(intent.getStringExtra(YEAR))) {
                        yearFilter.add(item);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            yearFilter.addAll(monthFilter);
        }

        //setlist to ascending or descendintg
        ArrayList<ExpenseResponse.Data> ascendingDescendingFilter = new ArrayList<>();
        ascendingDescendingFilter.addAll(yearFilter);


        // setSizeandAddition();
        String ascendingdescding = intent.getStringExtra(ASCENDINGDESCENDING);
        if (intent.getStringExtra(ASCENDINGDESCENDING).matches("Ascending")) {
            for (int i = 0; i < ascendingDescendingFilter.size(); i++) {
                for (int j = i + 1; j < ascendingDescendingFilter.size(); j++) {
                    if (Integer.parseInt(removeContainsDot(ascendingDescendingFilter.get(i).getAmount())) > Integer.parseInt(removeContainsDot(ascendingDescendingFilter.get(j).getAmount()))) {
                        ExpenseResponse.Data temp = ascendingDescendingFilter.get(i);
                        ascendingDescendingFilter.set(i, ascendingDescendingFilter.get(j));
                        ascendingDescendingFilter.set(j, temp);
                    }
                }
            }
        } else if (intent.getStringExtra(ASCENDINGDESCENDING).matches("Desecnding")) {
            for (int i = 0; i < ascendingDescendingFilter.size(); i++) {
                for (int j = i + 1; j < ascendingDescendingFilter.size(); j++) {
                    if (Integer.parseInt(removeContainsDot(ascendingDescendingFilter.get(i).getAmount())) > Integer.parseInt(removeContainsDot(ascendingDescendingFilter.get(j).getAmount()))) {
                        ExpenseResponse.Data temp = ascendingDescendingFilter.get(i);
                        ascendingDescendingFilter.set(i, ascendingDescendingFilter.get(j));
                        ascendingDescendingFilter.set(j, temp);
                    }
                }
            }
            Collections.reverse(ascendingDescendingFilter);
        }

//see all filter
        ////Selected year filter
        ArrayList<ExpenseResponse.Data> seeAllFilter = new ArrayList<>();
        if (intent.getStringExtra(SEEALL) != null && !TextUtils.isEmpty(intent.getStringExtra(SEEALL))) {
            for (ExpenseResponse.Data item : ascendingDescendingFilter) {
                if (item.getAmount() != null) {
                    String value1 = item.getAmount();
                    String value2 = intent.getStringExtra(SEEALL);
                    String value3 = intent.getStringExtra("seeall");
                    if (Integer.parseInt(intent.getStringExtra(SEEALL)) == 0) {
                        seeAllFilter.add(item);
                    } else if (Integer.parseInt(removeContainsDot(item.getAmount().toString())) <= Integer.parseInt(intent.getStringExtra(SEEALL))) {
                        seeAllFilter.add(item);
                    }
                } else if (Integer.parseInt(intent.getStringExtra(SEEALL)) == 0) {
                    seeAllFilter.add(item);
                }
            }
        } else {
            seeAllFilter.addAll(ascendingDescendingFilter);
        }

        filterArraylist.clear();
        filterArraylist.addAll(seeAllFilter);
        adapter.filterList(filterArraylist);
        adapter.notifyDataSetChanged();
        expensereportsListPdf.clear();
        expensereportsListPdf.addAll(filterArraylist);
        Utility.getInstance().dismissProgress();
    }

    private String removeContainsDot(String amount) {
        if (amount != null && !TextUtils.isEmpty(amount)) {
            if (amount.contains(".")) {
                String result = amount.substring(0, amount.indexOf("."));
                return result;
            } else {
                return amount;
            }
        }
        return "0";
    }


    @Override
    public void onAttach(@NonNull Context context) {
        this.mContext = context;
        super.onAttach(context);
    }


    @Override
    public void onReclerViewClick(View view, int position) {

    }

    @Override
    public void expenseListSuccess(ExpenseResponse body) {
        expensereportsList.clear();
        expensereportsList.addAll(body.getDataArrayList());
        filterArraylist.clear();
        filterArraylist.addAll(expensereportsList);
        expensereportsListPdf.clear();
        expensereportsListPdf.addAll(expensereportsList);
        adapter.notifyDataSetChanged();
        setSizeandAddition();
        Utility.getInstance().dismissProgress();
    }

    private void setSizeandAddition() {
        int total = 0;
        for (ExpenseResponse.Data listItem : expensereportsList) {
            if (listItem.getAmount() != null) {
                total = total + Integer.parseInt(removeContainsDot(listItem.getAmount()));
            }
        }
        textviewTotal.setText(getString(R.string.Rs)+" "+String.valueOf(total));
    }


    @Override
    public void expenseListFailure(String message) {

    }

    @Override
    public void getPdfResponse(PdfResponse body) {
        Utility.getInstance().dismissProgress();
        if (body.getStatus().matches("1")) {
            donwloadPDF(body);
        } else {
            Toast.makeText(mContext, getString(R.string.something_went_wrong_please_try_again), Toast.LENGTH_SHORT).show();
        }
    }

    private void donwloadPDF(PdfResponse body) {
//        int value = checkTwoPermission(ActivityOutstandingList.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, GALLERY_PERMISSION_REQUEST_CODE);
        int valueGallery = checkPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE, GALLERY_PERMISSION_REQUEST_CODE);
        if (valueGallery == 1) {
            checkFolder();
            Toast.makeText(mContext, getString(R.string.Downloading), Toast.LENGTH_SHORT).show();
            String url = body.getPdfpath();
            url = url.substring(1);
            newDownload(new CustomRetroRequest().pdfURL + url);
        } else {
            //       displayNeverAskAgainDialog(ActivityOutstandingList.this, getString(R.string.We_need_permission));
        }
    }

    public void checkFolder() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Ramumunimji/";
        File dir = new File(path);
        boolean isDirectoryCreated = dir.exists();
        if (!isDirectoryCreated) {
            isDirectoryCreated = dir.mkdir();
        }
        if (isDirectoryCreated) {
            // do something\
            Log.d("Folder", "Already Created");
        }
    }

    private void newDownload(String url) {
        final DownloadTask downloadTask = new DownloadTask(mContext);
        downloadTask.execute(url);
    }


    @Override
    public void getPdfFailure(String s) {
        Utility.getInstance().dismissProgress();
        Toast.makeText(mContext, getString(R.string.something_went_wrong_please_try_again), Toast.LENGTH_SHORT).show();
    }

    private void sharedPdf() {
        File file = new File(Environment.getExternalStorageDirectory() + "/Ramumunimji/" + nameTest + ".pdf");

        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
        File fileWithinMyDir = new File(file.getPath());

        if (fileWithinMyDir.exists()) {
            intentShareFile.setType("application/pdf");
            intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file.getPath()));

            intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
                    "Sharing File...");
            intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");

            startActivity(Intent.createChooser(intentShareFile, "Share File"));
        }
    }

    public void methodSetFilter() {
        Intent intent = new Intent(mContext, ActivityFilter.class);
        startActivityForResult(intent, FILTERACTVITYREQUESTCODE);
    }


    // DownloadTask for downloding video from URL
    public class DownloadTask extends AsyncTask<String, Integer, String> {
        private Context context;

        //  private PowerManager.WakeLock mWakeLock;
        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                java.net.URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                int fileLength = connection.getContentLength();

                input = connection.getInputStream();

                fileN = nameTest + ".pdf";
                File filename = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/Ramumunimji/", fileN);
                output = new FileOutputStream(filename);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    if (fileLength > 0)
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utility.getInstance().showProgressDialogue(mContext);
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // bnp.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            //    mWakeLock.release();
            //   downloadDialog.dismiss();
            Utility.getInstance().dismissProgress();
            if (result != null) {
               // Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(context, getString(R.string.File_downloaded), Toast.LENGTH_SHORT).show();
            }
            MediaScannerConnection.scanFile(mContext,
                    new String[]{Environment.getExternalStorageDirectory().getAbsolutePath() +
                            "Ramumunimji" + fileN}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String newpath, Uri newuri) {
                            Log.i("ExternalStorage", "Scanned " + newpath + ":");
                            Log.i("ExternalStorage", "-> uri=" + newuri);
                        }
                    });

            sharedPdf();

        }
    }


}
