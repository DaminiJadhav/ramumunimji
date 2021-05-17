package com.posbilling.posbillingapplication.activity.addgarage;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.dashboard.ui.addcustomer.StateListAdapter;
import com.posbilling.posbillingapplication.activity.dashboard.ui.voice.MultipleCustomerAdapter;
import com.posbilling.posbillingapplication.activity.filter.MonthListAdapter;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.interfaceclick.OnStateListClick;
import com.posbilling.posbillingapplication.lib.BaseActivity;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.model.StateModel;
import com.posbilling.posbillingapplication.model.realmcontoller.RealmController;
import com.posbilling.posbillingapplication.model.realmmodel.CustomerListRealm;
import com.posbilling.posbillingapplication.model.request.AddVehicleRequest;
import com.posbilling.posbillingapplication.model.response.AddGarageResponse;
import com.posbilling.posbillingapplication.model.response.GetVehicleTypeResponse;
import com.posbilling.posbillingapplication.utility.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.posbilling.posbillingapplication.utility.Constants.languageCodeMarathi;

public class ActivityAddGarage extends BaseActivity implements AddGarageContractor.View, OnStateListClick, OnRecyclerviewClick {

    private AddGarageContractor.Presenter mPresenter;
    private boolean showdialogue = false;
    private OnRecyclerviewClick onRecyclerviewClick = this;
    private ArrayList<CustomerListRealm> filterMultipleCustomerList = new ArrayList<>();
    private ArrayList<CustomerListRealm> filterMultipleCustomerListTwo = new ArrayList<>();
    private Dialog dialogSuggestion;
    private EditText edt_search_state;
    private ArrayList<CustomerListRealm> sameUserList = new ArrayList<>();
    private OnStateListClick onStateListClick = this;
    private Realm realm;
    boolean isnextServiceDialogueshowing = false;
    private String customerPrimaryId = "";
    final Calendar dob_currentDate = Calendar.getInstance();
    private MultipleCustomerAdapter multipleCustomerAdapter;
    private Dialog dialogState;
    private Dialog dialogModelVehicle;
    private ArrayList<StateModel> stateList = new ArrayList<>();
    private ArrayList<StateModel> monthList = new ArrayList<>();
    private ArrayList<StateModel> vehicleTypeList = new ArrayList<>();
    private String languageCode = "";
    private boolean isdialguevehicletype = false;
    private StateListAdapter stateListAdapter;
    private ArrayList<String> vehicleModelArrayList = new ArrayList<>();
    private ArrayList<String> twowheelerlist = new ArrayList<>();
    private ArrayList<String> threewheelerList = new ArrayList<>();
    private ArrayList<String> fourwheelerList = new ArrayList<>();
    private int vehicleModel = 0;

    @BindView(R.id.edittext_enterCustomerName)
    EditText edittext_enterCustomerName;
    @BindView(R.id.edittext_VehilcleModel)
    EditText edittext_VehilcleModel;
    @BindView(R.id.edittext_enterId)
    EditText edittext_enterId;
    @BindView(R.id.edittext_VehilcleType)
    EditText edittext_VehilcleType;
    @BindView(R.id.edittext_vehicleNumber)
    EditText edittext_vehicleNumber;
    @BindView(R.id.edittext_enterServiceDate)
    EditText edittext_enterServiceDate;
    @BindView(R.id.edittext_enterCurrentMilage)
    EditText edittext_enterCurrentMilage;
    @BindView(R.id.edittext_nextServiceDate)
    EditText edittext_nextServiceDate;
    @BindView(R.id.edittext_comment)
    EditText edittext_comment;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.imageview_mic_vehicleModel)
    ImageView imageview_mic_vehicleModel;
    @BindView(R.id.imageview_mic_vehicle_number)
    ImageView imageview_mic_vehicle_number;
    @BindView(R.id.imageview_mic_Milage)
    ImageView imageview_mic_Milage;
    @BindView(R.id.imageview_mic_comment)
    ImageView imageview_mic_comment;

    @OnClick(R.id.imageview_mic_comment)
    void imageview_mic_comment(){
        edittext_comment.requestFocus();
        imageview_mic_comment.setColorFilter(getResources().getColor(android.R.color.holo_green_dark), PorterDuff.Mode.SRC_IN);
        speechToTextFunc(this, edittext_comment, 1, imageview_mic_comment);
    }


    @OnClick(R.id.imageview_mic_Milage)
    void imageview_mic_Milage(){
        edittext_enterCurrentMilage.requestFocus();
        imageview_mic_Milage.setColorFilter(getResources().getColor(android.R.color.holo_green_dark), PorterDuff.Mode.SRC_IN);
        speechToTextFunc(this, edittext_enterCurrentMilage, 1, imageview_mic_Milage);
    }


    @OnClick(R.id.imageview_mic_vehicleModel)
    void imageview_mic_vehicleModel(){
        edittext_VehilcleModel.requestFocus();
        imageview_mic_vehicleModel.setColorFilter(getResources().getColor(android.R.color.holo_green_dark), PorterDuff.Mode.SRC_IN);
        speechToTextFunc(this, edittext_VehilcleModel, 1, imageview_mic_vehicleModel);
    }

    @OnClick(R.id.imageview_mic_vehicle_number)
    void imageview_mic_vehicle_number(){
        edittext_vehicleNumber.requestFocus();
        imageview_mic_vehicle_number.setColorFilter(getResources().getColor(android.R.color.holo_green_dark), PorterDuff.Mode.SRC_IN);
        speechToTextFunc(this, edittext_vehicleNumber, 1, imageview_mic_vehicle_number);
    }



    @OnClick(R.id.relativeSubmit)
    void relativeSubmit() {
        if (Utility.getInstance().isOnline(ActivityAddGarage.this)) {
            boolean value = validate();
            if (value) {
                AddVehicleRequest addVehicleRequest = new AddVehicleRequest();
                addVehicleRequest.setRegId(Utility.getInstance().getclientRegId(ActivityAddGarage.this));

                ArrayList<AddVehicleRequest.GarageCustomer> garageCustomerList = new ArrayList<>();
                AddVehicleRequest.GarageCustomer garageCustomer = new AddVehicleRequest().new GarageCustomer();
                garageCustomer.setID(customerPrimaryId);
                garageCustomer.setCustomerId(edittext_enterId.getText().toString().trim());
                garageCustomer.setName(edittext_enterCustomerName.getText().toString().trim());
                String currentDate = edittext_enterServiceDate.getText().toString();
                String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                Date date = null;
                try {
                    date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(currentDate + " " + currentTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                garageCustomer.setCreatedOn(date);
                garageCustomer.setCreatedBy(Utility.getInstance().getclientRegId(ActivityAddGarage.this));
                garageCustomer.setUpdatedBy(Utility.getInstance().getclientRegId(ActivityAddGarage.this));
                garageCustomer.setUpdatedOn(date);
                ArrayList<AddVehicleRequest.GarageCustomer.AddGarageList> addGarageLists = new ArrayList<>();
                AddVehicleRequest.GarageCustomer.AddGarageList addGarage = new AddVehicleRequest().new GarageCustomer().new AddGarageList();
                addGarage.setVehicleNo(edittext_vehicleNumber.getText().toString().trim());
                addGarage.setVehicleType(edittext_VehilcleType.getText().toString().trim());
                addGarage.setVehicleModel(edittext_VehilcleModel.getText().toString().trim());
                addGarage.setServieDate(date);

                String nextServiceDate = edittext_nextServiceDate.getText().toString();
                // String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                Date nextdate = null;
                try {
                    nextdate = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(nextServiceDate + " " + currentTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                addGarage.setDueDate(nextdate);
                addGarage.setCurrentKM(edittext_enterCurrentMilage.getText().toString());
                addGarage.setComment(edittext_comment.getText().toString());
                addGarage.setCreatedOn(date);
                addGarage.setUpdatedOn(date);
                addGarage.setCreatedBy(Utility.getInstance().getclientRegId(this));
                addGarage.setUpdatedBy(Utility.getInstance().getclientRegId(this));
                addGarageLists.add(addGarage);
                garageCustomer.setAddGarageLists(addGarageLists);
                garageCustomerList.add(garageCustomer);
                addVehicleRequest.setGarageCustomerList(garageCustomerList);
                Utility.getInstance().showProgressDialogue(ActivityAddGarage.this);
                mPresenter.postGaragedata(addVehicleRequest);
            }
        } else {
            Toast.makeText(this, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validate() {
        if (TextUtils.isEmpty(edittext_enterCustomerName.getText().toString())) {
            edittext_enterCustomerName.setError(getString(R.string.select_customer));
            edittext_enterCustomerName.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edittext_VehilcleType.getText().toString())) {
            edittext_VehilcleType.setError(getString(R.string.vehicle_type));
            edittext_VehilcleType.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edittext_vehicleNumber.getText().toString())) {
            edittext_vehicleNumber.setError(getString(R.string.vehicle_number));
            edittext_vehicleNumber.requestFocus();
            return false;
        }

/*        if (TextUtils.isEmpty(edittext_enterCurrentMilage.getText().toString())) {
            edittext_enterCurrentMilage.setError(getString(R.string.current_km_Milage));
            edittext_enterCurrentMilage.requestFocus();
            return false;
        }*/

        if (TextUtils.isEmpty(edittext_nextServiceDate.getText().toString())) {
            edittext_nextServiceDate.setError(getString(R.string.next_service_date));
            edittext_nextServiceDate.requestFocus();
            return false;
        }

  /*      if (TextUtils.isEmpty(edittext_comment.getText().toString())) {
            edittext_comment.setError(getString(R.string.comments));
            edittext_comment.requestFocus();
            return false;
        }*/
        return true;
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_add_garage;
    }

    @Override
    protected void setPresenter() {
        mPresenter = new AddGaragePresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar_title.setText(getString(R.string.add_service_header));
        languageCode = Utility.getInstance().getLanguage(ActivityAddGarage.this);
        realm = RealmController.with(this).getRealm();
        getVehicleModelData();
        initCustomerDialogue();
        setCalendar();
    }

    private void getVehicleModelData() {
        if (Utility.getInstance().isOnline(ActivityAddGarage.this)) {
        /*    Utility.getInstance().showProgressDialogue(ActivityAddGarage.this);
            mPresenter.getVehicleModelData();*/
        } else {
            Toast.makeText(this, "" + getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private void setCalendar() {
        int currentMonth = dob_currentDate.get(Calendar.MONTH) + 1;
        String currentmonthString = String.valueOf(currentMonth);
        if(currentmonthString.length() == 1){
            currentmonthString = "0"+currentmonthString;
        }
        String currentdateString = String.valueOf(dob_currentDate.get(Calendar.DAY_OF_MONTH));
        if(currentdateString.length() == 1){
            currentdateString = "0"+currentdateString;
        }
        edittext_enterServiceDate.setText( currentdateString+ "/" + currentmonthString + "/" + dob_currentDate.get(dob_currentDate.YEAR));
        edittext_enterServiceDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityAddGarage.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String monthString = String.valueOf(month);
                        if(monthString.length() == 1){
                            monthString = "0"+monthString;
                        }

                        String dayOfMonthString = String.valueOf(dayOfMonth);
                        if(dayOfMonthString.length() == 1){
                            dayOfMonthString = "0"+dayOfMonthString;
                        }

                        edittext_enterServiceDate.setText(String.valueOf(dayOfMonthString) + "/" + monthString + "/" + String.valueOf(year));
                    }
                }, dob_currentDate.get(dob_currentDate.YEAR), dob_currentDate.get(Calendar.MONTH), dob_currentDate.get(Calendar.DAY_OF_MONTH));
//                datePickerDialog.setTitle("Select Date");
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });


        edittext_nextServiceDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    showdialogue = true;
                    isnextServiceDialogueshowing = true;
                    dialogState = new Dialog(ActivityAddGarage.this);
                    if (!dialogState.isShowing()) {
                        monthList.clear();

                        StateModel stateModel = new StateModel();
                        stateModel.setStateNameEnglish("3 months");
                        stateModel.setStateNameMarathi("3 महिने");
                        stateModel.setStateId("3");
                        monthList.add(stateModel);
                        StateModel stateModel2 = new StateModel();
                        stateModel2.setStateNameEnglish("6 months");
                        stateModel2.setStateNameMarathi("6 महिने");
                        stateModel2.setStateId("6");
                        monthList.add(stateModel2);
                        StateModel stateModel3 = new StateModel();
                        stateModel3.setStateNameEnglish("1 year");
                        stateModel3.setStateNameMarathi("1 वर्ष");
                        stateModel3.setStateId("12");
                        monthList.add(stateModel3);


                        dialogState.getWindow().addFlags(Window.FEATURE_NO_TITLE);
                        dialogState.setCancelable(true);
                        dialogState.setContentView(R.layout.custom_state_dialogue);

                        EditText edt_search_state = dialogState.findViewById(R.id.edt_search_state);
                        edt_search_state.setVisibility(View.GONE);
                        TextView textview_header = dialogState.findViewById(R.id.textview_header);
                        textview_header.setText(getString(R.string.next_service_date));
                        RecyclerView recyclerView = dialogState.findViewById(R.id.recyclerview_contact);
                        stateListAdapter = new StateListAdapter(ActivityAddGarage.this, monthList, onStateListClick, languageCode);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityAddGarage.this);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(stateListAdapter);
                        dialogState.show();
                    }
                    return true;

                }
                return false;
            }
        });


        edittext_VehilcleType.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    showdialogue = true;
                    isdialguevehicletype = true;
                    dialogState = new Dialog(ActivityAddGarage.this);
                    if (!dialogState.isShowing()) {
                        vehicleTypeList.clear();
                        StateModel stateModel = new StateModel();
                        stateModel.setStateNameEnglish("Two wheeler");
                        stateModel.setStateNameMarathi("दुचाकी");
                        stateModel.setStateId("1");
                        vehicleTypeList.add(stateModel);
                        StateModel stateModel2 = new StateModel();
                        stateModel2.setStateNameEnglish("Three wheeler");
                        stateModel2.setStateNameMarathi("थ्री व्हीलर");
                        stateModel2.setStateId("2");
                        vehicleTypeList.add(stateModel2);
                        StateModel stateModel3 = new StateModel();
                        stateModel3.setStateNameEnglish("Four wheeler");
                        stateModel3.setStateNameMarathi("चार चाकी");
                        stateModel3.setStateId("3");
                        vehicleTypeList.add(stateModel3);

                        StateModel stateModel5 = new StateModel();
                        stateModel5.setStateNameEnglish("Tractor");
                        stateModel5.setStateNameMarathi("ट्रॅक्टर");
                        stateModel5.setStateId("5");
                        vehicleTypeList.add(stateModel5);


                        StateModel stateModel4 = new StateModel();
                        stateModel4.setStateNameEnglish("Other");
                        stateModel4.setStateNameMarathi("इतर");
                        stateModel4.setStateId("5");
                        vehicleTypeList.add( stateModel4);


                        dialogState.getWindow().addFlags(Window.FEATURE_NO_TITLE);
                        dialogState.setCancelable(true);
                        dialogState.setContentView(R.layout.custom_state_dialogue);

                        EditText edt_search_state = dialogState.findViewById(R.id.edt_search_state);
                        edt_search_state.setVisibility(View.GONE);
                        TextView textview_header = dialogState.findViewById(R.id.textview_header);
                        textview_header.setText(getString(R.string.vehicle_Type));
                        RecyclerView recyclerView = dialogState.findViewById(R.id.recyclerview_contact);
                        stateListAdapter = new StateListAdapter(ActivityAddGarage.this, vehicleTypeList, onStateListClick, languageCode);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityAddGarage.this);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(stateListAdapter);
                        dialogState.show();
                    }
                    return true;

                }
                return false;
            }
        });

        /*edittext_VehilcleModel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!TextUtils.isEmpty(edittext_VehilcleType.getText().toString())) {
                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        showdialogue = true;
                        isnextServiceDialogueshowing = true;
                        dialogModelVehicle = new Dialog(ActivityAddGarage.this);
                        if (!dialogModelVehicle.isShowing()) {
                            vehicleModelArrayList.clear();

                            if (vehicleModel == 0) {
                                vehicleModelArrayList.addAll(twowheelerlist);
                            } else if (vehicleModel == 1) {
                                vehicleModelArrayList.addAll(threewheelerList);
                            } else if (vehicleModel == 2) {
                                vehicleModelArrayList.addAll(fourwheelerList);
                            }

                            dialogModelVehicle.getWindow().addFlags(Window.FEATURE_NO_TITLE);
                            dialogModelVehicle.setCancelable(true);
                            dialogModelVehicle.setContentView(R.layout.custom_state_dialogue);

                            EditText edt_search_state = dialogModelVehicle.findViewById(R.id.edt_search_state);
                            edt_search_state.setVisibility(View.GONE);
                            TextView textview_header = dialogModelVehicle.findViewById(R.id.textview_header);
                            textview_header.setText(getString(R.string.next_service_date));
                            RecyclerView recyclerView = dialogModelVehicle.findViewById(R.id.recyclerview_contact);
                            //stateListAdapter = new StateListAdapter(ActivityAddGarage.this, monthList, onStateListClick, languageCode);
                            MonthListAdapter monthListAdapter = new MonthListAdapter(ActivityAddGarage.this, vehicleModelArrayList, onRecyclerviewClick);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityAddGarage.this);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(monthListAdapter);
                            dialogModelVehicle.show();
                        }
                    }
                    return true;
                }
                return false;
            }
        });*/


    }

    private void initCustomerDialogue() {
        String tag = "";
        edittext_enterCustomerName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    showdialogue = true;
                    isnextServiceDialogueshowing = false;
                    dialogSuggestion = new Dialog(ActivityAddGarage.this);
                    if (!dialogSuggestion.isShowing()) {
                        sameUserList.clear();
                        RealmResults<CustomerListRealm> customerListRealmModels = realm.where(CustomerListRealm.class).equalTo("isCustomerOnlineSaved", true)
                                .findAll();
                        sameUserList.addAll(customerListRealmModels);
                        filterMultipleCustomerList.addAll(sameUserList);
                        filterMultipleCustomerListTwo.addAll(sameUserList);
                        dialogSuggestion.getWindow().addFlags(Window.FEATURE_NO_TITLE);
                        dialogSuggestion.setCancelable(true);
                        dialogSuggestion.setContentView(R.layout.custom_state_dialogue);
                        TextView textview_header = dialogSuggestion.findViewById(R.id.textview_header);
                        textview_header.setText(getString(R.string.select_customer));
                        edt_search_state = dialogSuggestion.findViewById(R.id.edt_search_state);

                        RecyclerView recyclerView = dialogSuggestion.findViewById(R.id.recyclerview_contact);
                        multipleCustomerAdapter = new MultipleCustomerAdapter(ActivityAddGarage.this, sameUserList, onStateListClick);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityAddGarage.this);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(multipleCustomerAdapter);
                        setSearchFilter();
                        dialogSuggestion.show();
                    }
                    return true;

                }
                return false;
            }
        });
    }

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
        filterMultipleCustomerList.clear();
        for (CustomerListRealm item : sameUserList) {
            if (item != null) {
                String firstname = "";
                String middlename = "";
                String lastname = "";
                if (item.getCLastname() != null) {
                    lastname = item.getCLastname();
                }
                if (item.getCMiddleName() != null) {
                    middlename = item.getCMiddleName();
                }
                if (item.getCFirstname() != null) {
                    firstname = item.getCFirstname();
                }
                String finalname = "";
                if (TextUtils.isEmpty(middlename) && TextUtils.isEmpty(lastname)) {
                    finalname = firstname;
                } else if (TextUtils.isEmpty(middlename)) {
                    finalname = firstname + " " + lastname;
                } else {
                    finalname = firstname + " " + middlename + " " + lastname;
                }
                if (finalname.toLowerCase().contains(text.toLowerCase())) {
                    filterMultipleCustomerList.add(item);
                }
            }
        }
        if (text.trim().equals("")) {
            filterMultipleCustomerList.clear();
            filterMultipleCustomerList.addAll(sameUserList);
        }
        filterMultipleCustomerListTwo = filterMultipleCustomerList;
        multipleCustomerAdapter.filter(filterMultipleCustomerList);
    }


    @Override
    public void onStateListClick(View view, int position) {
        if (isdialguevehicletype) {
            isdialguevehicletype = false;
            dialogState.dismiss();
            vehicleModel = position;
            if (languageCode.equalsIgnoreCase(languageCodeMarathi)) {
                edittext_VehilcleType.setText(vehicleTypeList.get(position).getStateNameMarathi());
            } else {
                edittext_VehilcleType.setText(vehicleTypeList.get(position).getStateNameEnglish());
            }
        } else if (isnextServiceDialogueshowing) {
            dialogState.dismiss();
            isnextServiceDialogueshowing = false;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                String datestring = edittext_enterServiceDate.getText().toString();
                String dd = datestring.substring(0, 2);
                String mm = datestring.substring(3, 5);
                String yyyy = datestring.substring(6, 10);
                String dateString = dd + "/" + mm + "/" + yyyy;

                /*if(mm.length()==1){
                    mm= "0"+mm;
                }*/

                DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate  d1 = LocalDate.parse(dateString, df);

                LocalDate returnvalue
                        = d1.plusMonths(Integer.parseInt(monthList.get(position).getStateId()));

                String returnDate = String.valueOf(returnvalue).substring(8,10);
                String returnMonth = String.valueOf(returnvalue).substring(5,7);
                String retunrYear = String.valueOf(returnvalue).substring(0,4);
                edittext_nextServiceDate.setText(returnDate + "/" + returnMonth + "/" + retunrYear);
            } else {
                String datestring = edittext_enterServiceDate.getText().toString();
                String dd = datestring.substring(0, 2);
                String mm = datestring.substring(3, 5);
                String yyyy = datestring.substring(6, 10);
                String subDate = dd + "/" + mm + "/" + yyyy;


                Calendar d2 = Calendar.getInstance();
                try {
                    d2.setTime(new SimpleDateFormat("dd/M/yyyy").parse(subDate));
                    d2.add(Calendar.MONTH, Integer.parseInt(monthList.get(position).getStateId()));
                    edittext_nextServiceDate.setText(getDate(d2));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            dialogSuggestion.dismiss();
            String firstname = "";
            String middlename = "";
            String lastname = "";
            if (filterMultipleCustomerListTwo.get(position).getCLastname() != null) {
                lastname = filterMultipleCustomerListTwo.get(position).getCLastname();
            }
            if (filterMultipleCustomerListTwo.get(position).getCMiddleName() != null) {
                middlename = filterMultipleCustomerListTwo.get(position).getCMiddleName();
            }
            if (filterMultipleCustomerListTwo.get(position).getCFirstname() != null) {
                firstname = filterMultipleCustomerListTwo.get(position).getCFirstname();
            }
            String finalname = "";
            if (TextUtils.isEmpty(middlename) && TextUtils.isEmpty(lastname)) {
                finalname = firstname;
            } else if (TextUtils.isEmpty(middlename)) {
                finalname = firstname + " " + lastname;
            } else {
                finalname = firstname + " " + middlename + " " + lastname;
            }
            edittext_enterCustomerName.setText(finalname);
            customerPrimaryId = filterMultipleCustomerList.get(position).getID();
            edittext_enterId.setText(filterMultipleCustomerListTwo.get(position).getCustomerId());
        }
    }


    public static String getDate(Calendar cal) {
        return "" + cal.get(Calendar.DATE) + "/" +
                (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
        super.onBackPressed();
    }

    @Override
    public void addGarageFailure(String response_fails) {
        Utility.getInstance().dismissProgress();
        Utility.getInstance().dismissProgress();
        Utility.getInstance().dismissProgress();
        Toast.makeText(this, getString(R.string.something_went_wrong_please_try_again), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void addGarageSuccess(AddGarageResponse body) {
        Utility.getInstance().dismissProgress();
        Utility.getInstance().dismissProgress();
        Utility.getInstance().dismissProgress();
        edittext_enterCustomerName.setText("");
        edittext_enterId.setText("");
        edittext_VehilcleType.setText("");
        edittext_vehicleNumber.setText("");
        edittext_VehilcleModel.setText("");
        int currentMonth = dob_currentDate.get(Calendar.MONTH) + 1;
        edittext_enterServiceDate.setText(dob_currentDate.get(Calendar.DAY_OF_MONTH) + "/" + currentMonth + "/" + dob_currentDate.get(dob_currentDate.YEAR));
        edittext_enterCurrentMilage.setText("");
        edittext_nextServiceDate.setText("");
        edittext_comment.setText("");
        edittext_nextServiceDate.setText("");
        Toast.makeText(this, ""+getString(R.string.record_saved), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getVehicleSuccess(GetVehicleTypeResponse body) {
        Utility.getInstance().dismissProgress();
        for (GetVehicleTypeResponse.Data.Two_Wheeler item : body.getData().getTwoWheelerArrayList()) {
            twowheelerlist.add(item.getBrand() + " " + item.getType());
        }

        for (GetVehicleTypeResponse.Data.Three_Wheeler item : body.getData().getThree_wheelerArrayList()) {
            threewheelerList.add(item.getBrand() + " " + item.getType());
        }

        for (GetVehicleTypeResponse.Data.Four_Wheeler item : body.getData().getFour_wheelerArrayList()) {
            fourwheelerList.add(item.getBrand() + " " + item.getType());
        }
    }

    @Override
    public void getVehicleFailure(String something_went_wrong) {
        Utility.getInstance().dismissProgress();
        Toast.makeText(this, getString(R.string.something_went_wrong_please_try_again), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReclerViewClick(View view, int position) {
        dialogModelVehicle.dismiss();
        edittext_VehilcleModel.setText(vehicleModelArrayList.get(position));
    }
}
