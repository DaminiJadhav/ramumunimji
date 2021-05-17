package com.posbilling.posbillingapplication.activity.dashboard.ui.salesreport;

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
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.posbilling.posbillingapplication.activity.outstandinglist.ActivityOutstandingList;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.lib.BaseFragment;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.model.MonthYearModel;
import com.posbilling.posbillingapplication.model.realmcontoller.RealmController;
import com.posbilling.posbillingapplication.model.request.SalesReportPdfRequest;
import com.posbilling.posbillingapplication.model.response.PdfResponse;
import com.posbilling.posbillingapplication.model.response.TransactionListResponse;
import com.posbilling.posbillingapplication.retrofit.CustomRetroRequest;
import com.posbilling.posbillingapplication.utility.Utility;
import com.whiteelephant.monthpicker.MonthPickerDialog;

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
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;
import io.realm.Realm;

import static com.posbilling.posbillingapplication.utility.Constants.FILTERACTVITYREQUESTCODE;
import static com.posbilling.posbillingapplication.utility.Constants.GALLERY_PERMISSION_REQUEST_CODE;
import static com.posbilling.posbillingapplication.utility.Constants.langaugeCodeEnglish;
import static com.posbilling.posbillingapplication.utility.Constants.languageCodeMarathi;
import static com.posbilling.posbillingapplication.utility.MonthData.monthName;

public class FragmentSaleReports extends BaseFragment implements OnRecyclerviewClick, SalesReportController.View {
    private Context mContext;
    private Activity activity;
    private Realm realm;
    private SalesReportAdapter adapter;
    private OnRecyclerviewClick onRecyclerviewClick = this;
    private ArrayList<MonthYearModel> arraylistMonthYear = new ArrayList<>();
    private ArrayList<MonthYearModel> arraylistMonthYearPdf = new ArrayList<>();
    private int fromyear = 0;
    private int frommonth = 0;
    private int tomonth = 0;
    private int toyear = 0;
    private String languageCode = "";
    private String fileN = null;
    private SalesReportController.Presenter mPresenter;
    private String nameTest = "";

    @BindView(R.id.recyclerview_list)
    RecyclerView recyclerview_list;
    @BindView(R.id.ll_titleHeader)
    LinearLayout ll_titleHeader;
    @BindView(R.id.textviewHeader_Entry)
    TextView textviewHeader_Entry;
    @BindView(R.id.textviewHeader_Liter)
    TextView textviewHeader_Liter;
    @BindView(R.id.textviewHeader_Debit)
    TextView textviewHeader_Debit;
    @BindView(R.id.ediitextDateFrom)
    EditText ediitextDateFrom;
    @BindView(R.id.edittext_To)
    EditText edittext_To;

    @BindView(R.id.textviewAmountTotal)
    TextView textviewAmountTotal;

    @OnClick(R.id.fabshare)
    void fabshare() {
        if (Utility.getInstance().isOnline(mContext)) {
            getPdf();
        } else {
            Toast.makeText(mContext, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    @Optional
    @OnClick(R.id.imagviewFilter)
    void imagviewFilter() {
        Intent intent = new Intent(mContext, ActivityFilter.class);
        startActivityForResult(intent, FILTERACTVITYREQUESTCODE);
    }


    @Override
    public void onStart() {
        super.onStart();
        ((ActivityParentReport) getContext()).castFragment(this, 1);
    }


    /*    @OnClick(R.id.imageviewWhatsaapshare)
    void imageviewWhatsaapshare(){
        if (Utility.getInstance().isOnline(mContext)) {
            getPdf();
        } else {
            Toast.makeText(mContext, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }*/


    public void methodFragment() {
        if (Utility.getInstance().isOnline(mContext)) {
            getPdf();
        } else {
            Toast.makeText(mContext, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.whatsapp_floatingaction_button)
    void whatsapp_floatingaction_button() {
        if (Utility.getInstance().isOnline(mContext)) {
            getPdf();
        } else {
            Toast.makeText(mContext, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.ediitextDateFrom)
    void ediitextDateFrom() {
        monthandyearpicker(1);
    }

    @OnClick(R.id.edittext_To)
    void edittext_To() {
        monthandyearpicker(2);
    }


    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void setPresenter() {
        mPresenter = new SalesReportPresenter(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_sales_report;
    }

    @Override
    public void onMakeServerCalls() {
        if (isAdded() || isVisible()) {
            ((ActivityParentReport) getContext()).setTitleOfScreen(getString(R.string.menu_sales_reports));
            ((ActivityParentReport) getContext()).setVisibilityofRamumunimjiVisible(true, 3);
            activity = getActivity();
            realm = RealmController.with(this).getRealm();
            setDynamicView();
            setRecyclerview();
            /*setCalendar();*/
            getTransactionDetails();
        }
    }

    void monthandyearpicker(int fromOrTo) {
        //Calendar calendar = Calendar.getInstance();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int activiatedmonth = Calendar.getInstance().get(Calendar.MONTH);
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(mContext, new MonthPickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(int selectedMonth, int selectedYear) {
                if (fromOrTo == 1) {
                    fromyear = selectedYear;
                    frommonth = selectedMonth + 1;
                    ediitextDateFrom.setText(String.valueOf(selectedMonth + 1) + "/" + String.valueOf(selectedYear));
                } else {
                    toyear = selectedYear;
                    tomonth = selectedMonth + 1;
                    edittext_To.setText(String.valueOf(selectedMonth + 1) + "/" + String.valueOf(selectedYear));
                }

                filterfunctionality();

            }
        }, /* activated number in year */ 3, 5);

        builder.setActivatedMonth(activiatedmonth)
                .setMinYear(1990)
                .setActivatedYear(year)
                .setMaxYear(year)
                .setTitle("Select month and year")
                .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                    @Override
                    public void onMonthChanged(int selectedMonth) { // on month selected
                    }
                })
                .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                    @Override
                    public void onYearChanged(int selectedYear) { // on year selected
                    }
                })
                .build()
                .show();

    }


    private void getPdf() {
        SalesReportPdfRequest salesReportPdfRequest = new SalesReportPdfRequest();
        String currentDateForName = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(mContext);
        String firstnameForPdf = sharedPreferences.getString("FirstName", "");
        nameTest = getString(R.string.menu_sales_reports) + currentDateForName;
        salesReportPdfRequest.setPDFName(nameTest);
        salesReportPdfRequest.setRegId(Utility.getInstance().getclientRegId(mContext));
        languageCode = Utility.getInstance().getLanguage(mContext);
        if (languageCode.matches("") || languageCode.matches(langaugeCodeEnglish)) {
            salesReportPdfRequest.setLangID("0");
        } else if (languageCode.matches(languageCodeMarathi)) {
            salesReportPdfRequest.setLangID("1");
        } else {
            salesReportPdfRequest.setLangID("1");
        }


        ArrayList<SalesReportPdfRequest.RecordList> recordListArray = new ArrayList<>();

        int i = 1;
        for (MonthYearModel item : arraylistMonthYearPdf) {
            SalesReportPdfRequest.RecordList recordList = new SalesReportPdfRequest().new RecordList();
            recordList.setSrno(String.valueOf(i));
            i++;
            recordList.setBusinessname(sharedPreferences.getString("ShopName", ""));
            recordList.setClientVillage(sharedPreferences.getString("Village", ""));
            recordList.setContactno(sharedPreferences.getString("ContactNumber", ""));
            recordList.setPeriod(String.valueOf(ediitextDateFrom.getText().toString()) + "-" + String.valueOf(edittext_To.getText().toString()));
            recordList.setTotalsales(textviewAmountTotal.getText().toString());
            recordList.setMonthtotalsales(String.valueOf(item.getTotal()));
            recordList.setMonth(item.getMonth());
            recordListArray.add(recordList);
        }


        salesReportPdfRequest.setRecordListArrayList(recordListArray);


        Utility.getInstance().showProgressDialogue(mContext);
        mPresenter.getPDFData(salesReportPdfRequest);
    }


    private void filterfunctionality() {
        ArrayList<MonthYearModel> filterListfrom = new ArrayList<>();
        if (!TextUtils.isEmpty(ediitextDateFrom.getText().toString())) {
            for (MonthYearModel item : arraylistMonthYear) {
                if (Integer.parseInt(item.getYear()) >= fromyear && item.getPositionofMonth() >= frommonth) {
                    filterListfrom.add(item);
                }
            }
        } else {
            filterListfrom.addAll(arraylistMonthYear);
        }

        ArrayList<MonthYearModel> filterListTo = new ArrayList<>();
        if (!TextUtils.isEmpty(edittext_To.getText().toString())) {
            for (MonthYearModel item : filterListfrom) {
                if (Integer.parseInt(item.getYear()) <= toyear && item.getPositionofMonth() <= tomonth) {
                    filterListTo.add(item);
                }
            }
        } else {
            filterListTo.addAll(filterListfrom);
        }
        adapter.filterFunc(filterListTo);
        setGrandTotal(filterListTo);
        arraylistMonthYearPdf.clear();
        arraylistMonthYearPdf.addAll(filterListTo);
    }

    private void setGrandTotal(ArrayList<MonthYearModel> filterListTo) {
        int total = 0;
        for (MonthYearModel item : filterListTo) {
            total = total + item.getTotal();
        }

        textviewAmountTotal.setText(getString(R.string.Rs) + " " + String.valueOf(total));

    }


    private void setRecyclerview() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        adapter = new SalesReportAdapter(mContext, arraylistMonthYear, onRecyclerviewClick, realm);
        recyclerview_list.setLayoutManager(linearLayoutManager);
        recyclerview_list.setAdapter(adapter);
        setGrandTotal(arraylistMonthYear);
    }


    private void setDynamicView() {
        SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(mContext);
        if (true) {
            ll_titleHeader.setWeightSum(1f);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_titleHeader.getLayoutParams();
            LinearLayout.LayoutParams layoutParams_two = (LinearLayout.LayoutParams) ll_titleHeader.getLayoutParams();
            LinearLayout.LayoutParams layoutParams_three = (LinearLayout.LayoutParams) ll_titleHeader.getLayoutParams();

            layoutParams.weight = 0.6f;
            layoutParams_two.weight = 0.4f;
            //  layoutParams_three.weight = 0.3f;
            int padding_top = getResources().getDimensionPixelSize(R.dimen._4sdp);
            int padding_bottom = getResources().getDimensionPixelSize(R.dimen._4sdp);
            int padding_left = getResources().getDimensionPixelSize(R.dimen._5sdp);
            int padding_right = getResources().getDimensionPixelSize(R.dimen._5sdp);

            ll_titleHeader.setPadding(padding_left, padding_top, padding_right, padding_bottom);
            textviewHeader_Entry.setLayoutParams(layoutParams);
            textviewHeader_Debit.setLayoutParams(layoutParams_two);

            int padding_text = getResources().getDimensionPixelSize(R.dimen._25sdp);
            textviewHeader_Debit.setPadding(padding_text, 0, 0, 0);
            textviewHeader_Debit.setGravity(Gravity.LEFT);

            textviewHeader_Liter.setVisibility(View.GONE);
        }
    }


    private void getTransactionDetails() {
        if (Utility.getInstance().isOnline(mContext)) {
            Utility.getInstance().showProgressDialogue(mContext);
            mPresenter.getTransactionList(Utility.getInstance().getclientRegId(mContext), "");
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mContext == null) {
            mContext = ((ActivityDashboard) getContext());
        }
    }

    @Override
    public void onReclerViewClick(View view, int position) {

    }

    @Override
    public void getTraansactionListSuccess(TransactionListResponse response) {
        ArrayList<TransactionListResponse.TransactionList> transactionListRealms = new ArrayList<>();
        transactionListRealms.addAll(response.getDataList());

        //trasnsactionArrayList.addAll(transactionListRealms);
        for (TransactionListResponse.TransactionList item : transactionListRealms) {
            String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
            SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
            String date = item.getBillDate();
            String subDate = date.substring(0, 10);
            String yyyy = subDate.substring(0, 4);
            String mm = subDate.substring(5, 7);
            String dd = subDate.substring(8, 10);
            subDate = dd + "/" + mm + "/" + yyyy;

            Calendar d2 = Calendar.getInstance();
            try {
                d2.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(subDate));
                //  d2 = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(subDate + " " + currentTime);
                int getDate = d2.get(Calendar.MONTH);
                int getYear = d2.get(Calendar.YEAR);
                MonthYearModel monthYearModel = new MonthYearModel();
                monthYearModel.setPositionofMonth(getDate + 1);
                monthYearModel.setMonth(monthName[getDate]);
                monthYearModel.setYear(String.valueOf(getYear));
                if (arraylistMonthYear.contains(monthYearModel)) {
                    int indexof = arraylistMonthYear.indexOf(monthYearModel);
                    if (item.getDrAmt() != null && !TextUtils.isEmpty(item.getDrAmt().toString())) {
                        String debitAmt = item.getDrAmt();
                        int totalPrice = 0;
                        if (debitAmt != null) {
                            if (debitAmt.contains(".")) {
                                String result = debitAmt.substring(0, debitAmt.indexOf("."));
                                totalPrice = Integer.valueOf(result);
                            } else {
                                totalPrice = Integer.valueOf(item.getDrAmt());
                            }
                        }
                        totalPrice = totalPrice + arraylistMonthYear.get(indexof).getTotal();
                        monthYearModel.setTotal(totalPrice);
                        int totalLiter = arraylistMonthYear.get(indexof).getLiter();
                        if (item.getLiter() != null) {
                            totalLiter = Integer.valueOf(item.getLiter()) + totalLiter;
                        }
                        monthYearModel.setLiter(totalLiter);
                        arraylistMonthYear.set(indexof, monthYearModel);
                    }
                } else {
                    String debitAmt = item.getDrAmt();
                    int totalPrice = 0;
                    if (debitAmt != null) {
                        if (debitAmt.contains(".")) {
                            String result = debitAmt.substring(0, debitAmt.indexOf("."));
                            totalPrice = Integer.valueOf(result);
                        } else {
                            totalPrice = Integer.valueOf(item.getDrAmt());
                        }
                    }
                    monthYearModel.setTotal(totalPrice);
                    if (item.getLiter() != null) {
                        monthYearModel.setLiter(Integer.valueOf(item.getLiter()));
                    } else {
                        monthYearModel.setLiter(0);
                    }
                    arraylistMonthYear.add(monthYearModel);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        adapter.notifyDataSetChanged();
        setGrandTotal(arraylistMonthYear);
        arraylistMonthYearPdf.clear();
        arraylistMonthYearPdf.addAll(arraylistMonthYear);
        Utility.getInstance().dismissProgress();
    }

    @Override
    public void getTraansactionListFailure(String message) {
        Toast.makeText(mContext, getString(R.string.something_went_wrong_please_try_again), Toast.LENGTH_SHORT).show();
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

    private void newDownload(String url) {
        final DownloadTask downloadTask = new DownloadTask(mContext);
        downloadTask.execute(url);
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

                File saveFile;
                File firstTime = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/Ramumunimji/");
                if (!firstTime.exists()) {
                    firstTime.mkdir();
                    saveFile = new File(firstTime,  fileN);
                    output = new FileOutputStream(saveFile);

                }


                File filename = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/Ramumunimji/", fileN);
                saveFile = new File(firstTime,  fileN);
                //    output = new FileOutputStream(filename);
                output = new FileOutputStream(saveFile);

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
            } else {
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

//    public void methodSetFilter() {
//        Intent intent = new Intent(mContext, ActivityFilter.class);
//        startActivityForResult(intent, FILTERACTVITYREQUESTCODE);
//    }

}
