package com.posbilling.posbillingapplication.activity.filter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.lib.BaseActivity;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

import static com.posbilling.posbillingapplication.utility.Constants.ASCENDINGDESCENDING;
import static com.posbilling.posbillingapplication.utility.Constants.DISTRICT;
import static com.posbilling.posbillingapplication.utility.Constants.FROMDATE;
import static com.posbilling.posbillingapplication.utility.Constants.MONTH;
import static com.posbilling.posbillingapplication.utility.Constants.SEEALL;
import static com.posbilling.posbillingapplication.utility.Constants.TALUKA;
import static com.posbilling.posbillingapplication.utility.Constants.TODATE;
import static com.posbilling.posbillingapplication.utility.Constants.VILLAGE;
import static com.posbilling.posbillingapplication.utility.Constants.YEAR;

public class ActivityFilter extends BaseActivity implements OnRecyclerviewClick {

    final Calendar dob_currentDate = Calendar.getInstance();
    private boolean showdialogue = false;
    private Dialog dialogMonth;
    private ArrayList<String> monthList = new ArrayList<>();
    private ArrayList<String> seeAllList = new ArrayList<>();
    private OnRecyclerviewClick onRecyclerviewClick = this;
    private MonthListAdapter monthListAdapter;
    private String selectedMonth = "";
    private boolean monthDialgueSelected = false;
    private String value = "0";
    private String monthStringForPdf= "";

    @BindView(R.id.ediitextDateFrom)
    EditText ediitextDateFrom_edit;
    @BindView(R.id.edittext_To)
    EditText edittext_To_edit;
    @BindView(R.id.edittext_Month)
    EditText edittext_Month;
    @BindView(R.id.edittext_enterVillageName)
    EditText edittext_enterVillageName;
    @BindView(R.id.edittext_enterTalukaName)
    EditText edittext_enterTalukaName;
    @BindView(R.id.edittext_District)
    EditText edittext_District;
    @BindView(R.id.edittext_Year)
    EditText edittext_Year;
    @BindView(R.id.edittextSeeAll)
    EditText edittextSeeAll;
    @BindView(R.id.radioAscending)
    RadioButton radioAscending;
    @BindView(R.id.radioDesecnding)
    RadioButton radioDesecnding;
    @BindView(R.id.imageview_mic_enter_village)
    ImageView imageview_mic_enter_village;
    @BindView(R.id.imageview_mic_enter_taluka)
    ImageView imageview_mic_enter_taluka;
    @BindView(R.id.imageview_mic_enter_district)
    ImageView imageview_mic_enter_district;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.imageviewFilter)
    ImageView imageviewFilter;


    @OnClick(R.id.edittext_Year)
    void edittext_Year() {
        monthandyearpicker(2);
    }

    @OnClick(R.id.imageview_mic_enter_village)
    void imageview_mic_enter_village() {
        edittext_enterVillageName.requestFocus();
        imageview_mic_enter_village.setColorFilter(getResources().getColor(android.R.color.holo_green_dark), PorterDuff.Mode.SRC_IN);
        speechToTextFunc(this, edittext_enterVillageName, 1, imageview_mic_enter_village);
    }


    @OnClick(R.id.imageview_mic_enter_taluka)
    void imageview_mic_enter_taluka() {
        edittext_enterTalukaName.requestFocus();
        imageview_mic_enter_taluka.setColorFilter(getResources().getColor(android.R.color.holo_green_dark), PorterDuff.Mode.SRC_IN);
        speechToTextFunc(this, edittext_enterTalukaName, 1, imageview_mic_enter_taluka);
    }


    @OnClick(R.id.imageview_mic_enter_district)
    void imageview_mic_enter_district() {
        edittext_District.requestFocus();
        imageview_mic_enter_district.setColorFilter(getResources().getColor(android.R.color.holo_green_dark), PorterDuff.Mode.SRC_IN);
        speechToTextFunc(this, edittext_District, 1, imageview_mic_enter_district);
    }


/*
    @OnClick(R.id.edittext_Month)
    void edittext_Month() {
        monthandyearpicker(1);
    }
*/


    @OnClick(R.id.relativeSubmit)
    void relativeSubmit() {
        Intent intent = new Intent();
        intent.putExtra(VILLAGE, edittext_enterVillageName.getText().toString());
        intent.putExtra(TALUKA, edittext_enterTalukaName.getText().toString());
        intent.putExtra(DISTRICT, edittext_District.getText().toString());
        intent.putExtra(FROMDATE, ediitextDateFrom_edit.getText().toString());
        intent.putExtra(TODATE, edittext_To_edit.getText().toString());
        intent.putExtra(MONTH, selectedMonth);
        intent.putExtra("monthStringForPdf",monthStringForPdf);

        intent.putExtra(YEAR, edittext_Year.getText().toString());

        intent.putExtra(SEEALL, value);
        intent.putExtra("seeall",value);
        if (radioAscending.isChecked() || radioAscending.isSelected()) {
            intent.putExtra(ASCENDINGDESCENDING,"Ascending" );
        }else if(radioDesecnding.isChecked() || radioDesecnding.isSelected()){
            intent.putExtra(ASCENDINGDESCENDING, "Desecnding");
        }else {
            intent.putExtra(ASCENDINGDESCENDING, "");
        }

        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick(R.id.imageviewCalendarFrom)
    void imageviewCalendarFrom() {
        setCalendar(ediitextDateFrom_edit);
    }

    @OnClick(R.id.imageviewCalendarTo)
    void imageviewCalendarTo() {
        setCalendar(edittext_To_edit);
    }

    @OnClick(R.id.edittext_To)
    void edittext_To() {
        setCalendar(edittext_To_edit);
    }

    @OnClick(R.id.ediitextDateFrom)
    void ediitextDateFrom() {
        setCalendar(ediitextDateFrom_edit);
    }


    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_filter;
    }

    @Override
    protected void setPresenter() {
    }


    void monthandyearpicker(int month) {
        //Calendar calendar = Calendar.getInstance();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int activiatedmonth = Calendar.getInstance().get(Calendar.MONTH);
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(ActivityFilter.this, new MonthPickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(int selectedMonth, int selectedYear) {
                if (month == 1) {
                    edittext_Month.setText(String.valueOf(selectedMonth));
                } else {
                    edittext_Year.setText(String.valueOf(selectedYear));
                }
            }
        }, /* activated number in year */ 3, 5);

        if (month == 1) {
            builder.showMonthOnly()
                    .setActivatedMonth(activiatedmonth)
                    .build()
                    .show();
        } else {
            builder.showYearOnly()
                    .setActivatedYear(year)
                    .setYearRange(year,year+10)
                    .build()
                    .show();
        }

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //radioAscending.setSelected(true);
        toolbar_title.setText(R.string.Filter_screen);
        imageviewFilter.setVisibility(View.GONE);
        initMonthDialogue();
        initSeeAllDialogue();
    }

    private void initSeeAllDialogue() {
        monthDialgueSelected = false;
        String tag = "";
        edittextSeeAll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    showdialogue = true;
                    dialogMonth = new Dialog(ActivityFilter.this);
                    if (!dialogMonth.isShowing()) {
                        seeAllList.clear();
                        seeAllList.add(getString(R.string.SeeAll));
                        seeAllList.add(getString(R.string.upto500));
                        seeAllList.add(getString(R.string.upto1000));
                        seeAllList.add(getString(R.string.upto5000));
                        seeAllList.add(getString(R.string.upto10000));
                        dialogMonth.getWindow().addFlags(Window.FEATURE_NO_TITLE);
                        dialogMonth.setCancelable(true);
                        dialogMonth.setContentView(R.layout.custom_state_dialogue);
                        EditText edt_search_state = dialogMonth.findViewById(R.id.edt_search_state);
                        edt_search_state.setVisibility(View.GONE);
                        RecyclerView recyclerView = dialogMonth.findViewById(R.id.recyclerview_contact);
                        TextView textview_header = dialogMonth.findViewById(R.id.textview_header);
                        textview_header.setText(getString(R.string.selectamount));
                        monthListAdapter = new MonthListAdapter(ActivityFilter.this, seeAllList, onRecyclerviewClick);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityFilter.this);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(monthListAdapter);
                        dialogMonth.show();
                    }
                    return true;
                }
                return false;
            }
        });

    }


    private void initMonthDialogue() {

        String tag = "";
        edittext_Month.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                monthDialgueSelected = true;
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    showdialogue = true;
                    dialogMonth = new Dialog(ActivityFilter.this);
                    if (!dialogMonth.isShowing()) {
                        monthList.clear();
                        monthList.add("January");
                        monthList.add("February");
                        monthList.add("March");
                        monthList.add("April");
                        monthList.add("May");
                        monthList.add("June");
                        monthList.add("July");
                        monthList.add("August");
                        monthList.add("September");
                        monthList.add("October");
                        monthList.add("November");
                        monthList.add("December");
                        dialogMonth.getWindow().addFlags(Window.FEATURE_NO_TITLE);
                        dialogMonth.setCancelable(true);
                        dialogMonth.setContentView(R.layout.custom_state_dialogue);
                        EditText edt_search_state = dialogMonth.findViewById(R.id.edt_search_state);
                        edt_search_state.setVisibility(View.GONE);
                        RecyclerView recyclerView = dialogMonth.findViewById(R.id.recyclerview_contact);
                        TextView textview_header = dialogMonth.findViewById(R.id.textview_header);
                        textview_header.setText("Select Month");
                        monthListAdapter = new MonthListAdapter(ActivityFilter.this, monthList, onRecyclerviewClick);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityFilter.this);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(monthListAdapter);
                        dialogMonth.show();
                    }
                    return true;

                }
                return false;
            }
        });
    }


    private void setCalendar(EditText edittext_enterDate) {
        edittext_enterDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityFilter.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        edittext_enterDate.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                }, dob_currentDate.get(dob_currentDate.YEAR), dob_currentDate.get(Calendar.MONTH), dob_currentDate.get(Calendar.DAY_OF_MONTH));
//                datePickerDialog.setTitle("Select Date");
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void onReclerViewClick(View view, int position) {
       if (monthDialgueSelected) {
           monthDialgueSelected = false;
           edittext_Month.setText(monthList.get(position));
           monthStringForPdf = monthList.get(position);
           selectedMonth = String.valueOf(++position);
       }else {

           edittextSeeAll.setText(seeAllList.get(position));
           if (position == 0) {
               value = "0";
           }else if(position == 1){
               value= "500";
           }else if(position == 2){
               value = "1000";
           }else if(position ==3){
               value = "5000";
           }else {
               value = "10000";
           }
           //selectedMonth = String.valueOf(++position);

       }
        dialogMonth.dismiss();
    }
}
