package com.posbilling.posbillingapplication.activity.cropregistration;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.cropdetail.ActivityCropDetail;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.lib.BaseActivity;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.model.request.RequestAddCrop;
import com.posbilling.posbillingapplication.model.response.ResponseAddCrop;
import com.posbilling.posbillingapplication.model.response.ResponseCropDropDown;
import com.posbilling.posbillingapplication.utility.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

import static com.posbilling.posbillingapplication.activity.cropdetail.ActivityCropDetail.ADDCROPACTIVITY;
import static com.posbilling.posbillingapplication.utility.Constants.CLIENTREGID;
import static com.posbilling.posbillingapplication.utility.Constants.CUSTOMERID;

public class ActivityCropRegistration extends BaseActivity implements CropRegistrationContractor.View, OnRecyclerviewClick {
    private String customerId = "";
    private String customerName = "";
    private CropRegistrationContractor.Presenter mPresenter;
    private String customerRegId = "";
    private ArrayList<ResponseCropDropDown.Data> arrayListCropSugegstions = new ArrayList<ResponseCropDropDown.Data>();
    private boolean showdialogue = false;
    private Dialog dialogSuggestion;
    private CropSuggstionListAdapter cropSuggstionListAdapter;
    private OnRecyclerviewClick onRecyclerviewClick = this;
    private EditText edt_search_state;
    private ArrayList<ResponseCropDropDown.Data> filterarrayList = new ArrayList<>();
    private ResponseCropDropDown.Data recyclerviewSelectObject;
    final Calendar dob_currentDate = Calendar.getInstance();

    @BindView(R.id.edittext_enterId)
    EditText edittext_enterId;
    @BindView(R.id.edittext_enterCustomerName)
    EditText edittext_enterCustomerName;
    @BindView(R.id.edittext_cropName)
    EditText edittext_cropName;
    @BindView(R.id.edittext_enterDate)
    EditText edittext_enterDate;
    @BindView(R.id.edittext_enterAcres)
    EditText edittext_enterAcres;
    @BindView(R.id.edittext_enterPeriod)
    EditText edittext_enterPeriod;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @OnClick(R.id.relativeSubmit)
    void relativeSubmit() {
        if (Utility.getInstance().isOnline(ActivityCropRegistration.this)) {
            boolean value = validate();
            if (value) {
                Utility.getInstance().showProgressDialogue(ActivityCropRegistration.this);
                RequestAddCrop requestAddCrop = new RequestAddCrop();

                SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(ActivityCropRegistration.this);
                String clientId = sharedPreferences.getString(CLIENTREGID,"");
                requestAddCrop.setRegId(clientId);
                ArrayList<RequestAddCrop.AgroCustomerObject> agroCustomerObjectArrayList = new ArrayList<>();
                RequestAddCrop.AgroCustomerObject agroCustomerObject = new RequestAddCrop().new AgroCustomerObject();
                agroCustomerObject.setID(customerRegId);
                agroCustomerObject.setCustomerId(customerId);
                /*String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                Date date = null;
                try {
                    date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(currentDate + " " + currentTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/

                String currentDate = edittext_enterDate.getText().toString();
                String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                Date date = null;
                try {
                    date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(currentDate + " " + currentTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                agroCustomerObject.setCreatedOn(date);
                agroCustomerObject.setCreatedBy(customerRegId);
                agroCustomerObject.setName(customerName);
                agroCustomerObject.setUpdatedBy("");
                agroCustomerObject.setUpdatedOn("");
                ArrayList<RequestAddCrop.AgroCustomerObject.AddCropListObject> cropListObkectArraylist = new ArrayList();
                RequestAddCrop.AgroCustomerObject.AddCropListObject addCropListObject = new RequestAddCrop().new AgroCustomerObject().new AddCropListObject();
                addCropListObject.setCropId(recyclerviewSelectObject.getId());
                addCropListObject.setAcres(edittext_enterAcres.getText().toString());
                addCropListObject.setTransactionId("");
                addCropListObject.setCropPeriod(edittext_enterPeriod.getText().toString());
                addCropListObject.setCreatedOn(date);
                addCropListObject.setUpdatedOn(date);
                addCropListObject.setCreatedBy(customerId);
                addCropListObject.setUpdatedBy(customerId);
                cropListObkectArraylist.add(addCropListObject);
                agroCustomerObject.setAddCropList(cropListObkectArraylist);
                agroCustomerObjectArrayList.add(agroCustomerObject);
                requestAddCrop.setAgroCustomer(agroCustomerObjectArrayList);

                mPresenter.postyCropDetails(requestAddCrop);

            }
        } else {
            View view = getWindow().getDecorView().getRootView();
            Utility.getInstance().showSnackbar(view, getString(R.string.you_are_offline));
        }
    }

    private boolean validate() {
        if (TextUtils.isEmpty(edittext_enterId.getText())) {
            edittext_enterId.setError(getString(R.string.enter_id));
            edittext_enterId.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edittext_enterCustomerName.getText())) {
            edittext_enterCustomerName.setError(getString(R.string.please_enter_customer_name));
            edittext_enterCustomerName.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edittext_cropName.getText())) {
            edittext_cropName.setError(getString(R.string.select_crop));
            edittext_cropName.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edittext_enterDate.getText())) {
            edittext_enterDate.setError(getString(R.string.select_Date));
            edittext_enterDate.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edittext_enterAcres.getText())) {
            edittext_enterAcres.setError(getString(R.string.no_of_acre));
            edittext_enterAcres.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edittext_enterPeriod.getText())) {
            edittext_enterPeriod.setError(getString(R.string.enter_period));
            edittext_enterPeriod.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(ActivityCropRegistration.this, ActivityCropDetail.class);
        intent.putExtra("MESSAGE","1");
        setResult(RESULT_OK,intent);
        finish();
        super.onBackPressed();
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_crop_registration;
    }

    @Override
    protected void setPresenter() {
        mPresenter = new CropRegistrationPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar_title.setText(getString(R.string.add_crop));
        initUI();
        getIntentData();
        getCropSuggestionData();
        initCropSuggestionDialogue();
    }

    private void initUI() {
        int currentMonth = dob_currentDate.get(Calendar.MONTH) + 1;
        edittext_enterDate.setText(dob_currentDate.get(Calendar.DAY_OF_MONTH) + "/" + currentMonth + "/" + dob_currentDate.get(dob_currentDate.YEAR));

        edittext_enterDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edittext_enterDate.setError(null);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityCropRegistration.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        edittext_enterDate.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                }, dob_currentDate.get(dob_currentDate.YEAR), dob_currentDate.get(Calendar.MONTH), dob_currentDate.get(Calendar.DAY_OF_MONTH));
               // datePickerDialog.setTitle("Select Date");

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
    }

    private void initCropSuggestionDialogue() {
        String tag = "";
        edittext_cropName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    showdialogue = true;
                    dialogSuggestion = new Dialog(ActivityCropRegistration.this);
                    if (!dialogSuggestion.isShowing()) {
                        /*arrayListCropSugegstions.clear();

                        for (int i = 0; i < stateId.length; i++) {
                            StateModel stateModel = new StateModel();
                            stateModel.setStateNameEnglish(stateNamesEnglish[i]);
                            stateModel.setStateNameMarathi(stateNamesMarathi[i]);
                            stateModel.setStateId(stateId[i]);
                            stateList.add(stateModel);
                        }*/
                        dialogSuggestion.getWindow().addFlags(Window.FEATURE_NO_TITLE);
                        dialogSuggestion.setCancelable(true);
                        dialogSuggestion.setContentView(R.layout.custom_state_dialogue);
                        TextView textview_header = dialogSuggestion.findViewById(R.id.textview_header);
                        textview_header.setText(getString(R.string.select_crop));
                        edt_search_state = dialogSuggestion.findViewById(R.id.edt_search_state);

                        RecyclerView recyclerView = dialogSuggestion.findViewById(R.id.recyclerview_contact);
                        cropSuggstionListAdapter = new CropSuggstionListAdapter(ActivityCropRegistration.this, arrayListCropSugegstions, onRecyclerviewClick);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityCropRegistration.this);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(cropSuggstionListAdapter);
                        setSearchFilter();
                        dialogSuggestion.show();
                    }
                    return true;

                }
                return false;
            }
        });
    }

    //textchangelistner on list
    private void setSearchFilter() {
        edt_search_state.addTextChangedListener(new TextWatcher() {
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

    private void filter(String text) {
        filterarrayList.clear();
        for (ResponseCropDropDown.Data item : arrayListCropSugegstions) {
            if (item.getCropname().trim().toLowerCase().contains(text.toLowerCase())) {
                filterarrayList.add(item);
            }
        }
        cropSuggstionListAdapter.filterList(filterarrayList);
    }

    private void getCropSuggestionData() {
        Utility.getInstance().showProgressDialogue(ActivityCropRegistration.this);
        SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(this);
        mPresenter.getCropDropDown(sharedPreferences.getString(CLIENTREGID,""));
    }

    private void getIntentData() {
        if (getIntent() != null) {
            customerId = getIntent().getStringExtra(CUSTOMERID);
            customerRegId = getIntent().getStringExtra("CUDTOMERREGID");
            customerName = getIntent().getStringExtra("customerName");
            edittext_enterCustomerName.setText(customerName.toString());
            edittext_enterId.setText(customerId);
        }
    }

    @Override
    public void getCropDropDownList(ResponseCropDropDown response) {
        Utility.getInstance().dismissProgress();
        arrayListCropSugegstions.clear();
        filterarrayList.clear();
        filterarrayList.addAll(response.getDataArrayList());
        arrayListCropSugegstions.addAll(response.getDataArrayList());
    }

    @Override
    public void getCropDropDownFailure(String s) {
        Utility.getInstance().dismissProgress();
        Toast.makeText(this, getString(R.string.crop_master_Data_is_not_allowed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postCropFailure(String message) {
        Utility.getInstance().dismissProgress();
        Utility.getInstance().dismissProgress();
        Toast.makeText(this, getString(R.string.something_went_wrong_please_try_again), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postCropSuccess(ResponseAddCrop body) {
        Utility.getInstance().dismissProgress();
        Utility.getInstance().dismissProgress();
        edittext_cropName.setText("");
        recyclerviewSelectObject = null;
        edittext_enterAcres.setText("");
        edittext_enterPeriod.setText("");
        Toast.makeText(this, getString(R.string.crop_added), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReclerViewClick(View view, int position) {
        dialogSuggestion.dismiss();
        this.recyclerviewSelectObject = filterarrayList.get(position);
        edittext_cropName.setText(recyclerviewSelectObject.getCropname());
    }
}
