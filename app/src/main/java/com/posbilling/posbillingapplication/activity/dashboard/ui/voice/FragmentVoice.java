package com.posbilling.posbillingapplication.activity.dashboard.ui.voice;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.posbilling.posbillingapplication.BuildConfig;
import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.dashboard.ui.home.HomeContractor;
import com.posbilling.posbillingapplication.activity.dashboard.ui.home.HomePresenter;
import com.posbilling.posbillingapplication.activity.homeactivitytwo.HomeActivityTwo;
import com.posbilling.posbillingapplication.interfaceclick.OnMultipleSupplierListener;
import com.posbilling.posbillingapplication.interfaceclick.OnStateListClick;
import com.posbilling.posbillingapplication.lib.BaseFragment;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.model.realmcontoller.RealmController;
import com.posbilling.posbillingapplication.model.realmmodel.CustomerListRealm;

import com.posbilling.posbillingapplication.model.realmmodel.SupplierListRealm;
import com.posbilling.posbillingapplication.model.request.ExpensesRequest;
import com.posbilling.posbillingapplication.model.request.PurchaseTransactionRequest;
import com.posbilling.posbillingapplication.model.request.TransactionRequest;
import com.posbilling.posbillingapplication.model.response.ExpenseResponse;
import com.posbilling.posbillingapplication.model.response.PostPurchaseResponse;
import com.posbilling.posbillingapplication.model.response.TransactionResponse;
import com.posbilling.posbillingapplication.retrofit.CustomRetroRequest;
import com.posbilling.posbillingapplication.utility.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.posbilling.posbillingapplication.utility.Constants.LANGUAGEAVAILABILITYFIRSTTIME;
import static com.posbilling.posbillingapplication.utility.Constants.LASTCUSTOMERID;
import static com.posbilling.posbillingapplication.utility.Constants.LOGPOS;
import static com.posbilling.posbillingapplication.utility.Constants.RECORD_AUDIO_PERMISSION_REQUEST_CODE;
import static com.posbilling.posbillingapplication.utility.Constants.TTSDIALOGUECOUNT;
import static com.posbilling.posbillingapplication.utility.Constants.langaugeCodeEnglish;
import static com.posbilling.posbillingapplication.utility.Constants.languageCodeMarathi;


public class FragmentVoice extends BaseFragment implements HomeContractor.View, OnStateListClick, OnMultipleSupplierListener {
    private Context mContext;
    private Activity activity;
    private Dialog dialog;
    private OnStateListClick onStateListClick = this;
    private OnMultipleSupplierListener onMultipleSupplierListener = this;
    private HomeContractor.Presenter mPresenter;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    private TextToSpeech textToSpeech;
    private String language = "en";
    private Dialog dialogMultipleUser;
    private EditText edittextoustandingcustomerName;
    private ImageView imageviewMicOustanding;
    private Realm realm;
    private boolean SpeakOutsatnding = false;
    private boolean SpeakSupplierData = false;
    private ImageView imageviewMicSaveCancel;
    private EditText edt_search_state;
    private MultipleCustomerAdapter multipleCustomerAdapter;
    private MultipleSupplierAdapter multipleSupplierAdapter;
    private TextView textviewOustandingId;
    private TextView textviewOustandingName;
    private TextWatcher textWatcherDebitCutomerName;
    private TextWatcher textWatcherPurchaseSupplierName;
    private TextView textviewOustandingAmount;
    private EditText edittextCreditDebitDate;
    private CircleImageView imageviewSOutsnadingProfile;
    private TextView textviewSOustandingId;
    private TextView textviewSOustandingName;
    private TextView textviewSOustandingAmount;
    private RelativeLayout relativelayoutSOustandingProfile;
    private RelativeLayout relativelayoutOustandingProfile;
    private CircleImageView imageviewOutsnadingProfile;
    private TextView textview_notFound;
    private EditText edittextDebitcustomerName;
    private EditText edittextdebitAmount;
    private EditText edittextdebitPurpose;
    private EditText edittextdebitLitre;
    private TextView textviewsremark_enter_Litre;
    private int selectedFlow = 0;
    private ImageView imageviewMicExpeseName;
    private ImageView imageviewMicExpenseAmount;
    private ImageView imageviewExpenseDate;
    private ImageView imageviewMicExpensePurpose;
    private ImageView imageviewMicCustomerDebit;
    final Calendar dob_currentDate = Calendar.getInstance();
    private ArrayList<CustomerListRealm> sameUserList = new ArrayList<>();
    private ArrayList<SupplierListRealm> sameSupplierList = new ArrayList<SupplierListRealm>();
    private ArrayList<SupplierListRealm> filterMultipleSupplierList = new ArrayList<SupplierListRealm>();
    private ArrayList<SupplierListRealm> filterMultipleSupplierListTwo = new ArrayList<SupplierListRealm>();
    private ArrayList<CustomerListRealm> filterMultipleCustomerList = new ArrayList<>();
    private ArrayList<CustomerListRealm> filterMultipleCustomerListTwo = new ArrayList<>();
    private EditText edittextExpenseName;
    private EditText edittextExpesneAmount;
    private EditText edittextExpensePurpose;
    private int flowSelected = 0;
    private boolean showdialogue = false;
    private EditText edittextExpenseDate;
    private String transactionIdToast = "";
    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;

    private EditText editextPurchaseSupplierName;
    private EditText edittextPurchaseAmount;
    private EditText edittextPurchasePurpose;
    private EditText edittextPuchaseDate;
    private int flowpurchaseReceivedOrPaid = 1;


    @BindView(R.id.imageviewOustMic)
    ImageView imageviewOustMic;
    @BindView(R.id.imageviewDevitMic)
    ImageView imageviewDevitMic;
    @BindView(R.id.imageviewMicCredit)
    ImageView imageviewMicCredit;
    @BindView(R.id.imageviewMicExpense)
    ImageView imageviewMicExpense;


    @OnClick(R.id.ll_outstanding)
    void ll_outstanding() {
        if (Utility.getInstance().isOnline(mContext)) {
            int value = checkPermission(mContext, Manifest.permission.RECORD_AUDIO, RECORD_AUDIO_PERMISSION_REQUEST_CODE);
            if (value == 1) {
                imageviewOustMic.setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_IN);
                showPopuopForOustanding();
                speak(getString(R.string.please_tell_customer_name), "");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        edittextoustandingcustomerName.requestFocus();
                        speechToTextFuncVoice(mContext, edittextoustandingcustomerName, 1, imageviewMicOustanding, 1);
                    }
                }, 3000);

            } else if (value == 2) {
                displayNeverAskAgainDialog(mContext, getString(R.string.We_need_permission_Audio));
            }
        } else {
            View rootView = activity.getWindow().getDecorView().getRootView();
            Utility.getInstance().showSnackbar(rootView, getString(R.string.you_are_offline));
        }
    }

    @OnClick(R.id.ll_Debit)
    void ll_Debit() {
        if (Utility.getInstance().isOnline(mContext)) {
            int value = checkPermission(mContext, Manifest.permission.RECORD_AUDIO, RECORD_AUDIO_PERMISSION_REQUEST_CODE);
            if (value == 1) {
                imageviewDevitMic.setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_IN);
                showPopuopForDebitAndCredit(2);
                /*speak(getString(R.string.please_tell_customer_name), "");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        edittextDebitcustomerName.requestFocus();
                        speechToTextFuncVoice(mContext, edittextDebitcustomerName, 1, imageviewMicCustomerDebit, 2);
                    }
                }, 3000);*/
            } else if (value == 2) {
                displayNeverAskAgainDialog(mContext, getString(R.string.We_need_permission_Audio));
            }
        } else {
            View rootView = activity.getWindow().getDecorView().getRootView();
            Utility.getInstance().showSnackbar(rootView, getString(R.string.you_are_offline));
        }
    }


    @OnClick(R.id.ll_purchase)
    void ll_purchase() {
        if (Utility.getInstance().isOnline(mContext)) {
            int value = checkPermission(mContext, Manifest.permission.RECORD_AUDIO, RECORD_AUDIO_PERMISSION_REQUEST_CODE);
            if (value == 1) {
                showPopuopForPurchaseAndPurchasePaid(1);
            } else if (value == 2) {
                displayNeverAskAgainDialog(mContext, getString(R.string.We_need_permission_Audio));
            }
        } else {
            View rootView = activity.getWindow().getDecorView().getRootView();
            Utility.getInstance().showSnackbar(rootView, getString(R.string.you_are_offline));
        }
    }

    @OnClick(R.id.ll_purchasepaid)
    void ll_purchasepaid() {
        if (Utility.getInstance().isOnline(mContext)) {
            int value = checkPermission(mContext, Manifest.permission.RECORD_AUDIO, RECORD_AUDIO_PERMISSION_REQUEST_CODE);
            if (value == 1) {
                showPopuopForPurchaseAndPurchasePaid(2);
            } else if (value == 2) {
                displayNeverAskAgainDialog(mContext, getString(R.string.We_need_permission_Audio));
            }
        } else {
            View rootView = activity.getWindow().getDecorView().getRootView();
            Utility.getInstance().showSnackbar(rootView, getString(R.string.you_are_offline));
        }
    }

    @OnClick(R.id.ll_Credit)
    void ll_Credit() {
        if (Utility.getInstance().isOnline(mContext)) {
            int value = checkPermission(mContext, Manifest.permission.RECORD_AUDIO, RECORD_AUDIO_PERMISSION_REQUEST_CODE);
            if (value == 1) {
                imageviewMicCredit.setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_IN);
                showPopuopForDebitAndCredit(1);
                /*speak(getString(R.string.please_tell_customer_name), "");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        edittextDebitcustomerName.requestFocus();
                        speechToTextFuncVoice(mContext, edittextDebitcustomerName, 1, imageviewMicCustomerDebit, 2);
                    }
                }, 3000);*/
            } else if (value == 2) {
                displayNeverAskAgainDialog(mContext, getString(R.string.We_need_permission_Audio));
            }
        } else {
            View rootView = activity.getWindow().getDecorView().getRootView();
            Utility.getInstance().showSnackbar(rootView, getString(R.string.you_are_offline));
        }
    }


    @OnClick(R.id.ll_Expense)
    void ll_Expense() {
        if (Utility.getInstance().isOnline(mContext)) {
            int value = checkPermission(mContext, Manifest.permission.RECORD_AUDIO, RECORD_AUDIO_PERMISSION_REQUEST_CODE);
            if (value == 1) {
                imageviewMicExpense.setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_IN);
                showPopuopForExpense();
                /*speak(getString(R.string.please_tell_expense), "");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        edittextExpenseName.requestFocus();
                        speechToTextFuncVoice(mContext, edittextExpenseName, 1, imageviewMicExpeseName, 3);
                    }
                }, 2000);*/
            } else if (value == 2) {
                displayNeverAskAgainDialog(mContext, getString(R.string.We_need_permission_Audio));
            }
        } else {
            View rootView = activity.getWindow().getDecorView().getRootView();
            Utility.getInstance().showSnackbar(rootView, getString(R.string.you_are_offline));
        }
    }

    /**
     * popup for expense
     */
    private void showPopuopForExpense() {
        String languageCode = Utility.getInstance().getLanguage(getActivity());
        Utility.getInstance().setLanguage(getActivity(), languageCode);
        Utility.getInstance().localisation(getActivity(), languageCode);
        dialog = new Dialog(mContext);
        if (!dialog.isShowing()) {
            dialog.setCancelable(false);
            dialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            dialog.setContentView(R.layout.popup_expense);
            RelativeLayout relativeParentExpensePopup = dialog.findViewById(R.id.relativeParentExpensePopup);
            ImageView imageviewCross = dialog.findViewById(R.id.imageviewCross);
            imageviewMicExpeseName = dialog.findViewById(R.id.imageviewMicExpeseName);
            imageviewMicExpenseAmount = dialog.findViewById(R.id.imageviewMicExpenseAmount);
            imageviewExpenseDate = dialog.findViewById(R.id.imageviewExpenseDate);
            imageviewMicExpensePurpose = dialog.findViewById(R.id.imageviewMicExpensePurpose);
            edittextExpenseName = dialog.findViewById(R.id.edittextExpenseName);
            edittextExpesneAmount = dialog.findViewById(R.id.edittextExpesneAmount);
            edittextExpensePurpose = dialog.findViewById(R.id.edittextExpensePurpose);
            edittextExpenseDate = dialog.findViewById(R.id.edittextExpenseDate);
            CardView cardviewCreditDebitOk = dialog.findViewById(R.id.cardviewCreditDebitOk);
            CardView cardviewExpenseCancel = dialog.findViewById(R.id.cardviewExpenseCancel);
            imageviewMicSaveCancel = dialog.findViewById(R.id.imageviewMicSaveCancel);
            imageviewMicSaveCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean valueStatus = validateExpense();
                    if (valueStatus) {
                        speak(getString(R.string.please_say_save_or_cancel), "");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                speechToTextFuncVoice(mContext, null, 5, imageviewMicSaveCancel, 3);
                            }
                        }, 3000);
                    }
                }
            });

            imageviewCross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageviewMicExpense.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                    dialog.dismiss();
                    dialog.dismiss();
                    dialog.dismiss();
                    if (textToSpeech != null) {
                        textToSpeech.stop();
                    }
                }
            });

            edittextExpenseName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    edittextExpenseName.setError(null);
                    edittextExpesneAmount.setError(null);
                    edittextExpensePurpose.setError(null);
                    edittextExpenseDate.setError(null);
                }
            });

            edittextExpesneAmount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    edittextExpenseName.setError(null);
                    edittextExpesneAmount.setError(null);
                    edittextExpensePurpose.setError(null);
                    edittextExpenseDate.setError(null);
                }
            });


            edittextExpensePurpose.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    edittextExpenseName.setError(null);
                    edittextExpesneAmount.setError(null);
                    edittextExpensePurpose.setError(null);
                    edittextExpenseDate.setError(null);
                }
            });

            edittextExpenseDate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    edittextExpenseName.setError(null);
                    edittextExpesneAmount.setError(null);
                    edittextExpensePurpose.setError(null);
                    edittextExpenseDate.setError(null);
                }
            });


            cardviewExpenseCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, getString(R.string.transaction_cancelled), Toast.LENGTH_SHORT).show();
                    speak(getString(R.string.transaction_cancelled), "");
                    imageviewMicExpense.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                    dialog.dismiss();
                    dialog.dismiss();
                    dialog.dismiss();
                    if (textToSpeech != null) {
                        textToSpeech.stop();
                    }
                }
            });

            imageviewMicExpeseName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edittextExpenseName.setError(null);
                    speak(getString(R.string.please_tell_expense), "");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edittextExpenseName.requestFocus();
                            speechToTextFuncVoice(mContext, edittextExpenseName, 1, imageviewMicExpeseName, 3);
                        }
                    }, 2000);
                }
            });

            imageviewMicExpenseAmount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edittextExpesneAmount.setError(null);
                    speak(getString(R.string.please_tell_amount_to_be_enter), "");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edittextExpesneAmount.requestFocus();
                            speechToTextFuncVoice(mContext, edittextExpesneAmount, 2, imageviewMicExpenseAmount, 3);
                        }
                    }, 2000);
                }
            });

            imageviewMicExpensePurpose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edittextExpensePurpose.setError(null);
                    speak(getString(R.string.please_tell_purpose), "");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edittextExpensePurpose.requestFocus();
                            speechToTextFuncVoice(mContext, edittextExpensePurpose, 1, imageviewMicExpensePurpose, 3);
                        }
                    }, 2000);
                }
            });

            int currentMonth = dob_currentDate.get(Calendar.MONTH) + 1;
            edittextExpenseDate.setText(dob_currentDate.get(Calendar.DAY_OF_MONTH) + "/" + currentMonth + "/" + dob_currentDate.get(dob_currentDate.YEAR));
            imageviewExpenseDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edittextExpenseDate.setError(null);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month = month + 1;
                            edittextExpenseDate.setText(dayOfMonth + "/" + month + "/" + year);
                        }
                    }, dob_currentDate.get(dob_currentDate.YEAR), dob_currentDate.get(Calendar.MONTH), dob_currentDate.get(Calendar.DAY_OF_MONTH));
//                    datePickerDialog.setTitle("Select Date");
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            });

            edittextExpenseDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edittextExpenseDate.setError(null);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month = month + 1;
                            edittextExpenseDate.setText(dayOfMonth + "/" + month + "/" + year);
                        }
                    }, dob_currentDate.get(dob_currentDate.YEAR), dob_currentDate.get(Calendar.MONTH), dob_currentDate.get(Calendar.DAY_OF_MONTH));
//                    datePickerDialog.setTitle("Select Date");
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            });

            cardviewCreditDebitOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean valueStatus = validateExpense();
                    if (valueStatus) {
                        createRequestExpense();
                    }
                }
            });

            if (!dialog.isShowing()) {
                String languageCode2 = Utility.getInstance().getLanguage(getActivity());
                Utility.getInstance().setLanguage(getActivity(), languageCode2);
                Utility.getInstance().localisation(getActivity(), languageCode2);
                dialog.show();
             /*   Window window = dialog.getWindow();
                window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);*/
                dialog.getWindow().setGravity(Gravity.TOP | Gravity.RIGHT);
                WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
                layoutParams.x = 100; // right margin
                layoutParams.y = 170; // top margin
                dialog.getWindow().setAttributes(layoutParams);
                // e.g. bottom + left margins:
                dialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.LEFT);
                WindowManager.LayoutParams layoutParams1 = dialog.getWindow().getAttributes();
                layoutParams1.x = 100; // left margin
                layoutParams1.y = 170; // bottom margin
                dialog.getWindow().setAttributes(layoutParams1);
            }
        }
    }

    /**
     * creating request object for the expense
     */
    private void createRequestExpense() {
        ExpensesRequest expensesRequestSuperCLass = new ExpensesRequest();
        ArrayList<ExpensesRequest.ExpenseList> expenseLists = new ArrayList<>();
        ExpensesRequest.ExpenseList expensesRequest = new ExpensesRequest().new ExpenseList();
        expensesRequest.setAmount(edittextExpesneAmount.getText().toString());
        expensesRequest.setClientId(Utility.getInstance().getclientRegId(mContext));
        String currentDate = edittextExpenseDate.getText().toString();
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(currentDate + " " + currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String expeId = "";
        expensesRequest.setExpendDate(date);
        expensesRequest.setExpenditureId(expeId);
        transactionIdToast = expeId;
        expensesRequest.setTransactionId(expeId);
        expensesRequest.setPurpose(edittextExpensePurpose.getText().toString());
        expensesRequest.setExpenseType(edittextExpenseName.getText().toString());
        expenseLists.add(expensesRequest);
        expensesRequestSuperCLass.setRegId(Utility.getInstance().getclientRegId(mContext));
        expensesRequestSuperCLass.setExpenseListArrayList(expenseLists);
        if (Utility.getInstance().isOnline(mContext)) {
            boolean valueStatus = validateExpense();
            if (valueStatus) {
                Utility.getInstance().showProgressDialogue(mContext);
                mPresenter.postExpense(expensesRequestSuperCLass, 1);
            }
            edittextExpenseName.setText("");
            edittextExpesneAmount.setText("");
            edittextExpensePurpose.setText("");
        }
    }


    private boolean validateExpense() {
        if (TextUtils.isEmpty(edittextExpenseName.getText().toString().trim())) {
            edittextExpenseName.setError(getString(R.string.enter_Expense));
            edittextExpenseName.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edittextExpesneAmount.getText().toString().trim())) {
            edittextExpesneAmount.setError(getString(R.string.please_enter_amount));
            edittextExpesneAmount.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edittextExpenseDate.getText().toString().trim())) {
            edittextExpenseDate.setError(getString(R.string.select_Date));
            edittextExpenseDate.requestFocus();
            return false;
        }


        if (Integer.parseInt(removeContainsDot(edittextExpesneAmount.getText().toString().trim())) <= 0) {
            edittextExpesneAmount.setError(getString(R.string.invalid_amount));
            edittextExpesneAmount.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * remove unwanted dots forcefully from the amount
     *
     * @param amount
     * @return
     */
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


    /**
     * Popup for purchase paid
     */
    void showPopuopForPurchaseAndPurchasePaid(int flowpurchaseReceivedOrPaid) {
        this.flowpurchaseReceivedOrPaid = flowpurchaseReceivedOrPaid;
        String languageCode = Utility.getInstance().getLanguage(getActivity());
        Utility.getInstance().setLanguage(getActivity(), languageCode);
        Utility.getInstance().localisation(getActivity(), languageCode);
        dialog = new Dialog(mContext);
        if (!dialog.isShowing()) {
            dialog.setCancelable(false);
            /*            dialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);*/
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.popup_purchase_received_paid);
            TextView textviewTitle = dialog.findViewById(R.id.textviewTitle);
            editextPurchaseSupplierName = dialog.findViewById(R.id.editextPurchaseSupplierName);
            ImageView imageviewMicSupplierName = dialog.findViewById(R.id.imageviewMicSupplierName);
            edittextPurchaseAmount = dialog.findViewById(R.id.edittextPurchaseAmount);
            ImageView imageviewMicPurchaseAmount = dialog.findViewById(R.id.imageviewMicPurchaseAmount);
            edittextPurchasePurpose = dialog.findViewById(R.id.edittextPurchasePurpose);
            ImageView imageviewMicPurchasePurpose = dialog.findViewById(R.id.imageviewMicPurchasePurpose);
            edittextPuchaseDate = dialog.findViewById(R.id.edittextPuchaseDate);
            imageviewMicSaveCancel = dialog.findViewById(R.id.imageviewMicSaveCancel);
            ImageView imageviewPurchaseDate = dialog.findViewById(R.id.imageviewPurchaseDate);
            CardView cardviewPurchaseCancel = dialog.findViewById(R.id.cardviewPurchaseCancel);
            CardView cardviewPurchaseOk = dialog.findViewById(R.id.cardviewPurchaseOk);
            ImageView imageviewCross = dialog.findViewById(R.id.imageviewCross);
            ImageView imageviewMicSaveCancel = dialog.findViewById(R.id.imageviewMicSaveCancel);
            imageviewSOutsnadingProfile = dialog.findViewById(R.id.imageviewOutsnadingProfile);
            textviewSOustandingId = dialog.findViewById(R.id.textviewOustandingId);
            textviewSOustandingName = dialog.findViewById(R.id.textviewOustandingName);
            textviewSOustandingAmount = dialog.findViewById(R.id.textviewOustandingAmount);
            relativelayoutSOustandingProfile = dialog.findViewById(R.id.relativelayoutProfile);
            textview_notFound = dialog.findViewById(R.id.textview_notFound);
            TextView textviewamount = dialog.findViewById(R.id.textviewsaleReport);

            textviewSOustandingAmount.setVisibility(View.GONE);

            setClickPuchaseElements(imageviewMicSupplierName, editextPurchaseSupplierName, getString(R.string.please_tell_supplier_name), 1);
            if (flowpurchaseReceivedOrPaid == 1) {
                setClickPuchaseElements(imageviewMicPurchaseAmount, edittextPurchaseAmount, getString(R.string.please_tell_purchase_amount), 2);
                setClickPuchaseElements(imageviewMicPurchasePurpose, edittextPurchasePurpose, getString(R.string.please_tell_purchase_purpose), 1);

            } else {
                setClickPuchaseElements(imageviewMicPurchaseAmount, edittextPurchaseAmount, getString(R.string.please_tell_paid_amount), 2);
                setClickPuchaseElements(imageviewMicPurchasePurpose, edittextPurchasePurpose, getString(R.string.please_tell_amount_paid_purpose), 1);

            }
            setClickPuchaseElements(imageviewMicSaveCancel, null, getString(R.string.please_say_save_or_cancel), 1);
            int currentMonth = dob_currentDate.get(Calendar.MONTH) + 1;
            edittextPuchaseDate.setText(dob_currentDate.get(Calendar.DAY_OF_MONTH) + "/" + currentMonth + "/" + dob_currentDate.get(dob_currentDate.YEAR));
            if (flowpurchaseReceivedOrPaid == 1) {
                textviewTitle.setText(getString(R.string.menu_purchase));
            } else {
                textviewTitle.setText(getString(R.string.menu_purchasePaid));
                textviewamount.setText(getString(R.string.menu_purchasePaid));
//                textviewSOustandingId.setVisibility(View.INVISIBLE);
            }

            textWatcherPurchaseSupplierName = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (editextPurchaseSupplierName.hasFocus()) {
                        if (dialog.isShowing()) {
                            //checkOustandingData(s.toString(), true);
                            if (Utility.getInstance().isOnline(mContext)) {
                                checkSupplierData(s.toString(), true);
                            }
                        }
                    }
                }
            };


            editextPurchaseSupplierName.addTextChangedListener(textWatcherPurchaseSupplierName);
/*            editextPurchaseSupplierName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.e(TAG, "onTextChanged: ");
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.e(TAG, "onTextChanged: ");
                    if (dialog.isShowing()) {
                        //checkOustandingData(s.toString(), true);
                        checkSupplierData(s.toString(), true);
                    }
                }
            });*/

            cardviewPurchaseCancel.setOnClickListener(v -> {
                if (dialog != null) {
                    dialog.dismiss();
                    if (textToSpeech != null) {
                        textToSpeech.stop();
                    }
                    speak(getString(R.string.transaction_cancelled), "");
                    Toast.makeText(mContext, "" + getString(R.string.transaction_cancelled), Toast.LENGTH_SHORT).show();
                }
            });

            cardviewPurchaseOk.setOnClickListener(v -> {
                boolean valueStatus = validatePuchase();
                if (valueStatus) {
                    createPurchaseRequest(flowpurchaseReceivedOrPaid);
                }
            });

            imageviewCross.setOnClickListener(v -> {
                if (dialog != null) {
                    dialog.dismiss();
                    if (textToSpeech != null) {
                        textToSpeech.stop();
                    }
                }
            });

            imageviewPurchaseDate.setOnClickListener(v -> {
                edittextPuchaseDate.setError(null);
                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        edittextPuchaseDate.setText(dayOfMonth + "/" + month + "/" + year);
                        boolean valueStatus = validatePuchase();
                        if (valueStatus) {
                            speak(getString(R.string.please_say_save_or_cancel), "");
                            new Handler().postDelayed(() -> {
                                speechToTextFuncVoiceForPurchase(mContext, null, 3, imageviewMicSaveCancel, 2);
                            }, 3000);
                        }
                    }
                }, dob_currentDate.get(dob_currentDate.YEAR), dob_currentDate.get(Calendar.MONTH), dob_currentDate.get(Calendar.DAY_OF_MONTH));
//                datePickerDialog.setTitle("Select Date");
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            });


            if (!dialog.isShowing()) {
                String languageCode2 = Utility.getInstance().getLanguage(getActivity());
                Utility.getInstance().setLanguage(getActivity(), languageCode2);
                Utility.getInstance().localisation(getActivity(), languageCode2);
                dialog.show();


/*                WindowManager.LayoutParams params = dialog.getWindow().getAttributes(); // change this to your dialog.

                params.y = -100; // Here is the param to set your dialog position. Same with params.x
                dialog.getWindow().setAttributes(params);*/

                dialog.getWindow().setGravity(Gravity.TOP | Gravity.RIGHT);
                WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
                layoutParams.x = 100; // right margin
                layoutParams.y = 170; // top margin
                dialog.getWindow().setAttributes(layoutParams);
                // e.g. bottom + left margins:
                dialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.LEFT);
                WindowManager.LayoutParams layoutParams1 = dialog.getWindow().getAttributes();
                layoutParams1.x = 100; // left margin
                layoutParams1.y = 170; // bottom margin
                dialog.getWindow().setAttributes(layoutParams1);


/*                Window window = dialog.getWindow();



                window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);*/
            }
/*            speak(getString(R.string.please_tell_supplier_name), "");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    editextPurchaseSupplierName.requestFocus();
                    speechToTextFuncVoiceForPurchase(mContext, editextPurchaseSupplierName, 1, imageviewMicSupplierName, 2);
                }
            }, 3000);*/

        }
    }

    /**
     * Check supplier data availability
     *
     * @param name
     * @param speakStatus
     */
    private void checkSupplierData(String name, boolean speakStatus) {
        try {
            SpeakSupplierData = speakStatus;
            if (!TextUtils.isEmpty(name)) {
                boolean value = false;
                String supplierId = "";
                String fullname = "";
                String imagePath = "";
                String supplierOustanding = "0";
                RealmResults<SupplierListRealm> supplierListRealmsModels = realm.where(SupplierListRealm.class).findAll();
                int sameUserCount = 0;
                sameSupplierList.clear();
                filterMultipleSupplierList.clear();
                filterMultipleSupplierListTwo.clear();
                for (SupplierListRealm object : supplierListRealmsModels) {
                    String firstname = "";
                    String middlename = "";
                    String lastname = "";
                    if (object.getSLastname() != null) {
                        lastname = object.getSLastname();
                    }
                    if (object.getSMiddleName() != null) {
                        middlename = object.getSMiddleName();
                    }
                    if (object.getSFirstname() != null) {
                        firstname = object.getSFirstname();
                    }
                    String finalname = "";
                    if (TextUtils.isEmpty(middlename) && TextUtils.isEmpty(lastname)) {
                        finalname = firstname;
                    } else if (TextUtils.isEmpty(middlename)) {
                        finalname = firstname + " " + lastname;
                    } else {
                        finalname = firstname + " " + middlename + " " + lastname;
                    }
                    if (finalname.toLowerCase().contains(name.toLowerCase())) {
                        sameUserCount++;
                        value = true;
                        supplierId = object.getSupplierId();
                        fullname = finalname;
                        if (object.getOutstanding() != null && object.getOutstanding().contains(".")) {
                            String result = object.getOutstanding();
                            supplierOustanding = result.substring(0, result.indexOf("."));
                        } else if (object.getOutstanding() != null) {
                            supplierOustanding = object.getOutstanding();
                        }
                        imagePath = object.getImagePath();

                        sameSupplierList.add(object);
                    }
                }

                filterMultipleSupplierList.addAll(sameSupplierList);
                filterMultipleSupplierListTwo.addAll(sameSupplierList);
                if (value && sameUserCount == 1) {
                    Glide.with(mContext).load(new CustomRetroRequest().imageURL + imagePath)
                            .into(imageviewSOutsnadingProfile);
                    textviewSOustandingId.setText(supplierId);
                    textviewSOustandingName.setText(fullname);
                    textviewSOustandingAmount.setText(removeContainsDot(supplierOustanding));
                    textview_notFound.setVisibility(View.GONE);
                    relativelayoutSOustandingProfile.setVisibility(View.VISIBLE);
                    try {
                        if (editextPurchaseSupplierName != null) {
                            editextPurchaseSupplierName.removeTextChangedListener(textWatcherPurchaseSupplierName);
                            editextPurchaseSupplierName.setText(fullname);
                            editextPurchaseSupplierName.addTextChangedListener(textWatcherPurchaseSupplierName);
                        }
                    } catch (Exception ae) {
                        setError(ae.getMessage());
                    }
//                    if (speakStatus) {
                    if (false) {
                        Character s = supplierId.charAt(0);

                        if (s.toString().equals("-")) {
                            String languageCode = Utility.getInstance().getLanguage(mContext);
                            if (languageCode.matches("") || languageCode.matches(langaugeCodeEnglish)) {
                                speak(fullname + " " + getString(R.string.menuoutstanding) + " " + supplierOustanding, "");
                            } else {
                                speak(fullname + " " + getString(R.string.menuoutstanding) + " " + supplierOustanding, "");
                                // speak(fullname + " " + getString(R.string.menuoutstanding) + " " + getString(R.string.minus) + " " + Oustanding, "");
                            }
                        } else {
                            speak(fullname + " " + getString(R.string.menuoutstanding) + " " + supplierOustanding, "");
                        }
/*                        if (edittextoustandingcustomerName!=null){
                            edittextoustandingcustomerName.setText(fullname);
                        }*/


                    }
                } else if (sameUserCount > 1) {
                    showMultipleSupplierPopup(sameSupplierList);
                } else {
                    textview_notFound.setVisibility(View.VISIBLE);
                    relativelayoutSOustandingProfile.setVisibility(View.GONE);
                    if (speakStatus) {
                        // speak(getString(R.string.Supplier_not_found), "");
                    }
                }
            } else {
                textview_notFound.setVisibility(View.GONE);
                relativelayoutSOustandingProfile.setVisibility(View.GONE);
            }
        } catch (Exception ae) {
            ae.printStackTrace();
            Log.d(LOGPOS, "checkOustandingData: " + ae.getMessage());
        }
    }

    /**
     * click comman function for purchase
     *
     * @param imageviewMic
     * @param editext
     * @param textForSpeech
     * @param numberOrString
     */
    private void setClickPuchaseElements(ImageView imageviewMic, EditText editext, String textForSpeech, int numberOrString) {
        imageviewMic.setOnClickListener(v -> {
            if (editext != null) {
                editext.setError(null);
            }
            speak(textForSpeech, "");
            new Handler().postDelayed(() -> {
                if (editext != null) {
                    editext.requestFocus();
                }
                speechToTextFuncVoiceForPurchase(mContext, editext, numberOrString, imageviewMic, 3);
            }, 2000);
        });
    }

    /**
     * popup for sale and amount received
     *
     * @param flowSelected
     */
    private void showPopuopForDebitAndCredit(int flowSelected) {
        String languageCode = Utility.getInstance().getLanguage(getActivity());
        Utility.getInstance().setLanguage(getActivity(), languageCode);
        Utility.getInstance().localisation(getActivity(), languageCode);
        this.flowSelected = flowSelected;
        dialog = new Dialog(mContext);
        if (!dialog.isShowing()) {
            dialog.setCancelable(false);
            dialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.popup_debit);
            RelativeLayout relativeParentPopUp = dialog.findViewById(R.id.relativeParentPopUp);
            RelativeLayout parentRelative = dialog.findViewById(R.id.parentRelative);
            ImageView imageviewCross = dialog.findViewById(R.id.imageviewCross);
            CardView cardviewCreditDebitOk = dialog.findViewById(R.id.cardviewCreditDebitOk);
            edittextDebitcustomerName = dialog.findViewById(R.id.edittextDebitcustomerName);
            edittextdebitAmount = dialog.findViewById(R.id.edittextdebitAmount);
            edittextdebitPurpose = dialog.findViewById(R.id.edittextdebitPurpose);
            edittextdebitLitre = dialog.findViewById(R.id.edittextdebitLitre);
            textviewsremark_enter_Litre = dialog.findViewById(R.id.textviewsremark_enter_Litre);
            textview_notFound = dialog.findViewById(R.id.textview_notFound);
            imageviewOutsnadingProfile = dialog.findViewById(R.id.imageviewOutsnadingProfile);
            textviewOustandingId = dialog.findViewById(R.id.textviewOustandingId);
            textviewOustandingName = dialog.findViewById(R.id.textviewOustandingName);
            textviewOustandingAmount = dialog.findViewById(R.id.textviewOustandingAmount);
            relativelayoutOustandingProfile = dialog.findViewById(R.id.relativelayoutProfile);
            edittextCreditDebitDate = dialog.findViewById(R.id.edittextCreditDebitDate);
            ImageView imageviewMicDebitLitre = dialog.findViewById(R.id.imageviewMicDebitLitre);
            imageviewMicCustomerDebit = dialog.findViewById(R.id.imageviewMicCustomerDebit);
            ImageView imageviewMicDebitAmount = dialog.findViewById(R.id.imageviewMicDebitAmount);
            ImageView imageviewMicDebitPurpose = dialog.findViewById(R.id.imageviewMicDebitPurpose);
            imageviewMicSaveCancel = dialog.findViewById(R.id.imageviewMicSaveCancel);
            CardView cardviewCreditDebitCancel = dialog.findViewById(R.id.cardviewCreditDebitCancel);
            ImageView imageviewCreditDebitDate = dialog.findViewById(R.id.imageviewCreditDebitDate);
            TextView textviewTitle = dialog.findViewById(R.id.textviewTitle);
            TextView textviewamount = dialog.findViewById(R.id.textviewsaleReport);

            SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(mContext);
            textviewOustandingAmount.setVisibility(View.GONE);

            if (flowSelected == 2) {

                textviewTitle.setText(getString(R.string.menu_sale));
                textviewamount.setText(getString(R.string.sale_amount));

                //textviewTitle.setTextColor(getResources().getColor(R.color.lightRed));
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    // relativeParentPopUp.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.rounded_corner_popup));
                    parentRelative.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.rounded_corner_popup));
                } else {
                    // relativeParentPopUp.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_corner_popup));
                    parentRelative.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_corner_popup));
                }


                // relativeParentPopUp.setBackgroundColor(getResources().getColor(R.color.lightredbackground));
                if (sharedPreferences.getString("BusinessId", "").matches("4")) {
                    //Dairytype
                    edittextdebitLitre.setVisibility(View.GONE);
                    textviewsremark_enter_Litre.setVisibility(View.GONE);
                    imageviewMicDebitLitre.setVisibility(View.GONE);
                }
            } else {
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    //   relativeParentPopUp.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.rounded_corner_green_popup));
                    parentRelative.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.rounded_corner_green_popup));
                } else {
//                    relativeParentPopUp.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_corner_green_popup));
                    parentRelative.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_corner_green_popup));
                }
                //relativeParentPopUp.setBackgroundColor(getResources().getColor(R.color.lightgreenbackground));
                textviewTitle.setText(getString(R.string.amount_received));
                textviewamount.setText(getString(R.string.amount_receive));

                //   textviewTitle.setTextColor(getResources().getColor(R.color.colorGreen));
            }

            int currentMonth = dob_currentDate.get(Calendar.MONTH) + 1;
            edittextCreditDebitDate.setText(dob_currentDate.get(Calendar.DAY_OF_MONTH) + "/" + currentMonth + "/" + dob_currentDate.get(dob_currentDate.YEAR));

            edittextCreditDebitDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edittextCreditDebitDate.setError(null);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month = month + 1;
                            edittextCreditDebitDate.setText(dayOfMonth + "/" + month + "/" + year);
                            boolean valueStatus = validateCreditDebit(flowSelected);
                            if (valueStatus) {
                                speak(getString(R.string.please_say_save_or_cancel), "");
                                new Handler().postDelayed(() -> {
                                    if (flowSelected == 1) {
                                        speechToTextFuncVoice(mContext, null, 3, imageviewMicSaveCancel, 2);
                                    } else {
                                        speechToTextFuncVoice(mContext, null, 4, imageviewMicSaveCancel, 2);
                                    }
                                }, 3000);
                            }
                        }
                    }, dob_currentDate.get(dob_currentDate.YEAR), dob_currentDate.get(Calendar.MONTH), dob_currentDate.get(Calendar.DAY_OF_MONTH));
//                    datePickerDialog.setTitle("Select Date");
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            });


            imageviewCreditDebitDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edittextCreditDebitDate.setError(null);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month = month + 1;
                            edittextCreditDebitDate.setText(dayOfMonth + "/" + month + "/" + year);
                            boolean valueStatus = validateCreditDebit(flowSelected);
                            if (valueStatus) {
                                speak(getString(R.string.please_say_save_or_cancel), "");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (flowSelected == 1) {
                                            speechToTextFuncVoice(mContext, null, 3, imageviewMicSaveCancel, 2);
                                        } else {
                                            speechToTextFuncVoice(mContext, null, 4, imageviewMicSaveCancel, 2);
                                        }
                                    }
                                }, 3000);
                            }
                        }
                    }, dob_currentDate.get(dob_currentDate.YEAR), dob_currentDate.get(Calendar.MONTH), dob_currentDate.get(Calendar.DAY_OF_MONTH));
//                    datePickerDialog.setTitle("Select Date");
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            });


            textWatcherDebitCutomerName = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (edittextDebitcustomerName.hasFocus()) {
                        if (Utility.getInstance().isOnline(mContext)) {
                            checkOustandingData(s.toString(), false);
                        }
                    }
                }
            };

            edittextDebitcustomerName.addTextChangedListener(textWatcherDebitCutomerName);


            imageviewCross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    dialog.dismiss();
                    dialog.dismiss();
                    if (textToSpeech != null) {
                        textToSpeech.stop();
                    }
                    imageviewDevitMic.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                    imageviewMicCredit.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                }
            });
            cardviewCreditDebitOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean valueStatus = validateCreditDebit(flowSelected);
                    if (valueStatus) {
                        saveCreditandDebit(flowSelected);
                    }
                }
            });

            imageviewMicSaveCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean valueStatus = validateCreditDebit(flowSelected);
                    if (valueStatus) {
                        speak(getString(R.string.please_say_save_or_cancel), "");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (flowSelected == 1) {
                                    speechToTextFuncVoice(mContext, null, 3, imageviewMicSaveCancel, 2);
                                } else {
                                    speechToTextFuncVoice(mContext, null, 4, imageviewMicSaveCancel, 2);
                                }
                            }
                        }, 3000);
                    }
                }
            });

            edittextDebitcustomerName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (edittextDebitcustomerName.hasFocus()) {
                        edittextDebitcustomerName.setError(null);
                        edittextdebitAmount.setError(null);
                        edittextdebitPurpose.setError(null);
                        edittextdebitLitre.setError(null);
                    }
                }
            });

            edittextdebitAmount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    Log.e(TAG, "beforeTextChanged: ");
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.e(TAG, "beforeTextChanged: ");
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.e(TAG, "beforeTextChanged: ");
                    edittextDebitcustomerName.setError(null);
                    edittextdebitAmount.setError(null);
                    edittextdebitPurpose.setError(null);
                    edittextdebitLitre.setError(null);
                }
            });

            edittextdebitPurpose.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    edittextDebitcustomerName.setError(null);
                    edittextdebitAmount.setError(null);
                    edittextdebitPurpose.setError(null);
                    edittextdebitLitre.setError(null);
                }
            });
            edittextdebitLitre.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    Log.e(TAG, "beforeTextChanged: ");
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.e(TAG, "onTextChanged: ");
                }

                @Override
                public void afterTextChanged(Editable s) {
                    edittextDebitcustomerName.setError(null);
                    edittextdebitAmount.setError(null);
                    edittextdebitPurpose.setError(null);
                    edittextdebitLitre.setError(null);
                }
            });

            cardviewCreditDebitCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    dialog.dismiss();
                    dialog.dismiss();
                    if (textToSpeech != null) {
                        textToSpeech.stop();
                    }
                    Toast.makeText(mContext, getString(R.string.transaction_cancelled), Toast.LENGTH_SHORT).show();
                    speak(getString(R.string.transaction_cancelled), "");
                    imageviewDevitMic.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                    imageviewMicCredit.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                }
            });

            imageviewMicDebitLitre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edittextdebitLitre.setError(null);
                    speak(getString(R.string.please_tell_liter), "");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edittextdebitLitre.requestFocus();
                            speechToTextFuncVoice(mContext, edittextdebitLitre, 2, imageviewMicDebitLitre, 2);
                        }
                    }, 2000);

                }
            });

            imageviewMicCustomerDebit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edittextDebitcustomerName.setError(null);
                    speak(getString(R.string.please_tell_customer_name), "");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edittextDebitcustomerName.requestFocus();
                            speechToTextFuncVoice(mContext, edittextDebitcustomerName, 1, imageviewMicCustomerDebit, 2);
                        }
                    }, 3000);

                }
            });

            imageviewMicDebitAmount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edittextdebitAmount.setError(null);
                    if (flowSelected == 2) {
                        speak(getString(R.string.enter_sale_amount), "");
                    } else {
                        speak(getString(R.string.enter_amount_receive), "");
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edittextdebitAmount.requestFocus();
                            speechToTextFuncVoice(mContext, edittextdebitAmount, 2, imageviewMicDebitAmount, 2);
                        }
                    }, 2000);
                }
            });

            imageviewMicDebitPurpose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edittextdebitPurpose.setError(null);
                    speak(getString(R.string.please_tell_purpose), "");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edittextdebitPurpose.requestFocus();
                            speechToTextFuncVoice(mContext, edittextdebitPurpose, 1, imageviewMicDebitPurpose, 2);
                        }
                    }, 2000);
                }
            });

            if (!dialog.isShowing()) {
                String languageCode2 = Utility.getInstance().getLanguage(getActivity());
                Utility.getInstance().setLanguage(getActivity(), languageCode2);
                Utility.getInstance().localisation(getActivity(), languageCode2);
                dialog.show();
/*                Window window = dialog.getWindow();
                window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);*/
                dialog.getWindow().setGravity(Gravity.TOP | Gravity.RIGHT);
                WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
                layoutParams.x = 100; // right margin
                layoutParams.y = 170; // top margin
                dialog.getWindow().setAttributes(layoutParams);
                // e.g. bottom + left margins:
                dialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.LEFT);
                WindowManager.LayoutParams layoutParams1 = dialog.getWindow().getAttributes();
                layoutParams1.x = 100; // left margin
                layoutParams1.y = 170; // bottom margin
                dialog.getWindow().setAttributes(layoutParams1);
            }
        }
    }

    private boolean validateCreditDebit(int flowSelected) {
        if (TextUtils.isEmpty(edittextDebitcustomerName.getText().toString())) {
            edittextDebitcustomerName.setError(getString(R.string.please_enter_customer_name));
            edittextDebitcustomerName.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edittextdebitAmount.getText().toString())) {
            if (flowSelected == 2) {
                edittextdebitAmount.setError(getString(R.string.please_enter_sale_amount));
            } else {
                edittextdebitAmount.setError(getString(R.string.please_enter_amount));
            }

            edittextdebitAmount.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edittextdebitPurpose.getText().toString())) {
            edittextdebitPurpose.setError(getString(R.string.enter_purpose));
            edittextdebitPurpose.requestFocus();
            return false;
        }


        /*if (flowSelected == 2) {
            SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(mContext);
            if (sharedPreferences.getString("BusinessId", "").matches("4")) {
                //Dairy type
                if (TextUtils.isEmpty(edittextdebitLitre.getText().toString())) {
                    *//*edittextdebitLitre.setError(getString(R.string.enter_Litre));
                    edittextdebitLitre.requestFocus();*//*
         *//*
                    return false;*//*
                }
            }
        }*/


/*        if (Integer.parseInt(removeContainsDot(edittextdebitAmount.getText().toString().trim())) <= 0) {
            edittextdebitAmount.setError(getString(R.string.invalid_amount));
            edittextdebitAmount.requestFocus();
            return false;
        }*/

        return true;
    }


    private void showPopuopForOustanding() {
        //       AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(mContext, R.style.AlertDialogCustom));
        String languageCode = Utility.getInstance().getLanguage(getActivity());
        Utility.getInstance().setLanguage(getActivity(), languageCode);
        Utility.getInstance().localisation(getActivity(), languageCode);
        dialog = new Dialog(mContext);
        if (!dialog.isShowing()) {
            dialog.setCancelable(false);
            dialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.outstanding_popup);
            textview_notFound = dialog.findViewById(R.id.textview_notFound);
            imageviewOutsnadingProfile = dialog.findViewById(R.id.imageviewOutsnadingProfile);
            textviewOustandingId = dialog.findViewById(R.id.textviewOustandingId);
            textviewOustandingName = dialog.findViewById(R.id.textviewOustandingName);
            textviewOustandingAmount = dialog.findViewById(R.id.textviewOustandingAmount);
            relativelayoutOustandingProfile = dialog.findViewById(R.id.relativelayoutProfile);
            edittextoustandingcustomerName = dialog.findViewById(R.id.edittextoustandingcustomerName);
            imageviewMicOustanding = dialog.findViewById(R.id.imageviewMicOustanding);
            ImageView imageviewCross = dialog.findViewById(R.id.imageviewCross);

            imageviewCross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    dialog.dismiss();
                    dialog.dismiss();
                    if (textToSpeech != null) {
                        textToSpeech.stop();
                    }
                    imageviewDevitMic.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                    imageviewMicCredit.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                    imageviewOustMic.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                }
            });

            imageviewMicOustanding.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edittextoustandingcustomerName.requestFocus();
                    speechToTextFuncVoice(mContext, edittextoustandingcustomerName, 1, imageviewMicOustanding, 1);
                }
            });


            CardView cardviewOk = dialog.findViewById(R.id.cardviewOk);
            cardviewOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    dialog.dismiss();
                    dialog.dismiss();
                    if (textToSpeech != null) {
                        textToSpeech.stop();
                    }
                    imageviewDevitMic.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                    imageviewMicCredit.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                    imageviewOustMic.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                }
            });

            edittextoustandingcustomerName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.e(TAG, "onTextChanged: ");
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.e(TAG, "onTextChanged: ");
                    if (dialog.isShowing()) {
                        if(Utility.getInstance().isOnline(mContext)) {
                            checkOustandingData(s.toString(), true);
                        }else {
                            Toast.makeText(mContext,getString(R.string.please_check_internet),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            if (!dialog.isShowing()) {
                String languageCode2 = Utility.getInstance().getLanguage(getActivity());
                Utility.getInstance().setLanguage(getActivity(), languageCode2);
                Utility.getInstance().localisation(getActivity(), languageCode2);
                dialog.show();
/*                Window window = dialog.getWindow();
                window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);*/
                dialog.getWindow().setGravity(Gravity.TOP | Gravity.RIGHT);
                WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
                layoutParams.x = 100; // right margin
                layoutParams.y = 170; // top margin
                dialog.getWindow().setAttributes(layoutParams);
                // e.g. bottom + left margins:
                dialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.LEFT);
                WindowManager.LayoutParams layoutParams1 = dialog.getWindow().getAttributes();
                layoutParams1.x = 100; // left margin
                layoutParams1.y = 170; // bottom margin
                dialog.getWindow().setAttributes(layoutParams1);
            }
        }
    }

    private void checkOustandingData(String name, boolean speakStatus) {
        try {

            SpeakOutsatnding = speakStatus;
            if (!TextUtils.isEmpty(name)) {
                boolean value = false;
                String customerId = "";
                String fullname = "";
                String imagePath = "";
                String Oustanding = "0";
                RealmResults<CustomerListRealm> customerListRealmModels = realm.where(CustomerListRealm.class).findAll();
                int sameUserCount = 0;
                sameUserList.clear();
                filterMultipleCustomerList.clear();
                filterMultipleCustomerListTwo.clear();
                for (CustomerListRealm object : customerListRealmModels) {
                    String firstname = "";
                    String middlename = "";
                    String lastname = "";
                    if (object.getCLastname() != null) {
                        lastname = object.getCLastname();
                    }
                    if (object.getCMiddleName() != null) {
                        middlename = object.getCMiddleName();
                    }
                    if (object.getCFirstname() != null) {
                        firstname = object.getCFirstname();
                    }
                    String finalname = "";
                    if (TextUtils.isEmpty(middlename) && TextUtils.isEmpty(lastname)) {
                        finalname = firstname;
                    } else if (TextUtils.isEmpty(middlename)) {
                        finalname = firstname + " " + lastname;
                    } else {
                        finalname = firstname + " " + middlename + " " + lastname;
                    }
                    if (finalname.toLowerCase().contains(name.toLowerCase())) {
                        sameUserCount++;
                        value = true;
                        customerId = object.getCustomerId();
                        fullname = finalname;
                        if (object.getOutstanding() != null && object.getOutstanding().contains(".")) {
                            String result = object.getOutstanding();
                            Oustanding = result.substring(0, result.indexOf("."));
                        } else if (object.getOutstanding() != null) {
                            Oustanding = object.getOutstanding();
                        }
                        imagePath = object.getImagePath();
                        sameUserList.add(object);
                    }
                }
                filterMultipleCustomerList.addAll(sameUserList);
                filterMultipleCustomerListTwo.addAll(sameUserList);
                if (value && sameUserCount == 1) {
                    Glide.with(mContext).load(new CustomRetroRequest().imageURL + imagePath)
                            .into(imageviewOutsnadingProfile);
                    textviewOustandingId.setText(customerId);
                    textviewOustandingName.setText(fullname);
                    textviewOustandingAmount.setText(removeContainsDot(Oustanding));
                    textview_notFound.setVisibility(View.GONE);
                    relativelayoutOustandingProfile.setVisibility(View.VISIBLE);
                    try {
                        if (edittextDebitcustomerName != null) {
                            edittextDebitcustomerName.removeTextChangedListener(textWatcherDebitCutomerName);
                            edittextDebitcustomerName.setText(fullname);
                            edittextDebitcustomerName.addTextChangedListener(textWatcherDebitCutomerName);
                        }
                    } catch (Exception ae) {
                        setError(ae.getMessage());
                    }
                    if (speakStatus) {
                        Character s = Oustanding.charAt(0);

                        if (s.toString().equals("-")) {
                            String languageCode = Utility.getInstance().getLanguage(mContext);
                            if (languageCode.matches("") || languageCode.matches(langaugeCodeEnglish)) {
                                speak(fullname + " " + getString(R.string.menuoutstanding) + " " + Oustanding, "");
                            } else {
                                speak(fullname + " " + getString(R.string.menuoutstanding) + " " + Oustanding, "");
                                // speak(fullname + " " + getString(R.string.menuoutstanding) + " " + getString(R.string.minus) + " " + Oustanding, "");
                            }
                        } else {
                            speak(fullname + " " + getString(R.string.menuoutstanding) + " " + Oustanding, "");
                        }
/*                        if (edittextoustandingcustomerName!=null){
                            edittextoustandingcustomerName.setText(fullname);
                        }*/


                    }
                } else if (sameUserCount > 1) {
                    showMultipleUserPopup(sameUserList);
                } else {
                    textview_notFound.setVisibility(View.VISIBLE);
                    relativelayoutOustandingProfile.setVisibility(View.GONE);
                    if (speakStatus) {
                        speak(getString(R.string.Cutomer_not_found), "");
                    }
                }
            } else {
                textview_notFound.setVisibility(View.GONE);
                relativelayoutOustandingProfile.setVisibility(View.GONE);
            }
        } catch (Exception ae) {
            ae.printStackTrace();
            Log.d(LOGPOS, "checkOustandingData: " + ae.getMessage());
        }
    }


    private void showMultipleSupplierPopup(ArrayList<SupplierListRealm> sameSupplierList) {
        showdialogue = true;
        filterMultipleSupplierList.clear();
        filterMultipleSupplierList.addAll(sameSupplierList);
        filterMultipleSupplierListTwo.clear();
        filterMultipleSupplierListTwo.addAll(sameSupplierList);
        dialogMultipleUser = new Dialog(getContext());
        if (!dialogMultipleUser.isShowing()) {
            dialogMultipleUser.getWindow().addFlags(Window.FEATURE_NO_TITLE);
            dialogMultipleUser.setCancelable(true);
            dialogMultipleUser.setContentView(R.layout.custom_state_dialogue);
            TextView textView = dialogMultipleUser.findViewById(R.id.textview_header);
            textView.setText(getString(R.string.select_customer));
            edt_search_state = dialogMultipleUser.findViewById(R.id.edt_search_state);
            RecyclerView recyclerView = dialogMultipleUser.findViewById(R.id.recyclerview_contact);
            multipleSupplierAdapter = new MultipleSupplierAdapter(mContext, sameSupplierList, onMultipleSupplierListener);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(multipleSupplierAdapter);
            setSearchFilterSupplier();
            dialogMultipleUser.show();
        }
    }


    private void showMultipleUserPopup(ArrayList<CustomerListRealm> sameUserList) {
        showdialogue = true;
        filterMultipleCustomerList.clear();
        filterMultipleCustomerList.addAll(sameUserList);
        filterMultipleCustomerListTwo.clear();
        filterMultipleCustomerListTwo.addAll(sameUserList);
        dialogMultipleUser = new Dialog(getContext());
        if (!dialogMultipleUser.isShowing()) {
            dialogMultipleUser.getWindow().addFlags(Window.FEATURE_NO_TITLE);
            dialogMultipleUser.setCancelable(true);
            dialogMultipleUser.setContentView(R.layout.custom_state_dialogue);
            TextView textView = dialogMultipleUser.findViewById(R.id.textview_header);
            textView.setText(getString(R.string.select_customer));
            edt_search_state = dialogMultipleUser.findViewById(R.id.edt_search_state);
            RecyclerView recyclerView = dialogMultipleUser.findViewById(R.id.recyclerview_contact);
            multipleCustomerAdapter = new MultipleCustomerAdapter(mContext, sameUserList, onStateListClick);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(multipleCustomerAdapter);
            setSearchFilter();
            dialogMultipleUser.show();
        }
    }


    private void setSearchFilterSupplier() {
        edt_search_state.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filterSupplier(s.toString());
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


    private void filterSupplier(String text) {
        filterMultipleSupplierList.clear();
        for (SupplierListRealm item : sameSupplierList) {
            if (item != null) {
                String firstname = "";
                String middlename = "";
                String lastname = "";
                if (item.getSLastname() != null) {
                    lastname = item.getSLastname();
                }
                if (item.getSMiddleName() != null) {
                    middlename = item.getSMiddleName();
                }
                if (item.getSFirstname() != null) {
                    firstname = item.getSFirstname();
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
                    filterMultipleSupplierList.add(item);
                }
            }
        }
        if (text.trim().equals("")) {
            filterMultipleSupplierList.clear();
            filterMultipleSupplierList.addAll(sameSupplierList);
        }
        filterMultipleSupplierListTwo = filterMultipleSupplierList;
        multipleSupplierAdapter.filter(filterMultipleSupplierList);
    }

    private void saveCreditandDebit(int selectedFlow) {
        this.selectedFlow = selectedFlow;
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setRegId(Utility.getInstance().getclientRegId(mContext));
        if (language.toLowerCase().contains(langaugeCodeEnglish.toLowerCase())) {
            transactionRequest.setLang("1");
        } else {
            transactionRequest.setLang("1");
        }
        transactionRequest.setDeviceId("");
        SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(mContext);
        transactionRequest.setBusinessType(sharedPreferences.getString("BusinessId", ""));
        ArrayList<TransactionRequest.CustomerListClass> customerListClassArrayList = new ArrayList<>();
        TransactionRequest.CustomerListClass customerListClass = new TransactionRequest().new CustomerListClass();
        RealmResults<CustomerListRealm> customerListRealmModels = realm.where(CustomerListRealm.class).findAll();
        boolean value = false;
        String id = "";
        String customerId = "";
        for (CustomerListRealm object : customerListRealmModels) {
            if ((relativelayoutOustandingProfile.getVisibility() == View.VISIBLE) && textviewOustandingId.getText().toString().toLowerCase().matches(object.getCustomerId().toLowerCase().toLowerCase())) {
                id = object.getID();
                value = true;
                customerId = object.getCustomerId();
                break;
            }
        }
        String transactionId = "";
        if (value) {
            customerListClass.setId(id);
            customerListClass.setCustomerId(customerId);
            customerListClass.setName(edittextDebitcustomerName.getText().toString());
        } else {
            SharedPreferences sharedPreferences1 = Utility.getInstance().getSharedPReference(mContext);
            String createId = "";
            if (TextUtils.isEmpty(sharedPreferences1.getString(LASTCUSTOMERID, ""))) {
                createId = "C" + Utility.getInstance().getclientRegId(mContext) + "1";
            } else {
                createId = "C" + Utility.getInstance().getclientRegId(mContext) + String.valueOf(Integer.parseInt(sharedPreferences1.getString(LASTCUSTOMERID, "")) + 1);
            }
            customerListClass.setId("");
            // customerListClass.setCustomerId(createId);
            customerListClass.setCustomerId("");
            customerListClass.setName(edittextDebitcustomerName.getText().toString());
        }
        ArrayList<TransactionRequest.CustomerListClass.TransactionsListClass> transactionsListClassArrayList = new ArrayList<>();
        TransactionRequest.CustomerListClass.TransactionsListClass transactionsListClass = new TransactionRequest().new CustomerListClass().new TransactionsListClass();
        transactionsListClass.setAmount(edittextdebitAmount.getText().toString());
        transactionsListClass.setLiter(edittextdebitLitre.getText().toString());
        transactionsListClass.setPurpose(edittextdebitPurpose.getText().toString());
        transactionsListClass.setTransactionId(transactionId);
        transactionIdToast = transactionId;
        transactionsListClass.setTransactionType(String.valueOf(selectedFlow));
        String currentDate = edittextCreditDebitDate.getText().toString();
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(currentDate + " " + currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        transactionsListClass.setDate(date);
        transactionsListClass.setTime(currentTime);
        transactionsListClassArrayList.add(transactionsListClass);
        customerListClass.setTransactionsList(transactionsListClassArrayList);
        customerListClassArrayList.add(customerListClass);
        transactionRequest.setCustomerList(customerListClassArrayList);
        if (Utility.getInstance().isOnline(mContext)) {
            boolean valueStatus = validateCreditDebit(flowSelected);
            if (valueStatus) {
                Utility.getInstance().showProgressDialogue(mContext);
                mPresenter.postTransaction(transactionRequest, 1);
            }
            edittextDebitcustomerName.setText("");
            edittextdebitAmount.setText("");
            edittextdebitPurpose.setText("");
        } else {
            Toast.makeText(mContext, getString(R.string.you_are_offline), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void setPresenter() {
        mPresenter = new HomePresenter(this);
    }

    @Override
    protected int getLayout() {
        /*        return R.layout.activity_voice_change;*/
        return R.layout.activity_voice_two;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onStart() {
        super.onStart();
        String languageCode = Utility.getInstance().getLanguage(getActivity());
        Utility.getInstance().setLanguage(getActivity(), languageCode);
        Utility.getInstance().localisation(getActivity(), languageCode);
        SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(mContext);
        String versioncodeCheck = sharedPreferences.getString("versionCode", "");
        if (versionCode >= Integer.parseInt(sharedPreferences.getString("versionCode", "1"))) {
            if (sharedPreferences.getString("activStatus", "").trim().toLowerCase().matches("true")) {
            } else {
                showInActivePopup();
            }
        } else {
            Utility.getInstance().onUpdateVersion("", mContext, activity);
        }
    }


    private void showInActivePopup() {
        Utility.getInstance().forceDeactivate(mContext);
    }

    @Override
    public void onMakeServerCalls() {
        if (isAdded() || isVisible()) {
            activity = getActivity();
            realm = RealmController.with(this).getRealm();
            language = Utility.getInstance().getLanguage(mContext);
            setTextTospeech();
            if (Utility.getInstance().isOnline(mContext)) {
                int value = checkPermission(mContext, Manifest.permission.RECORD_AUDIO, RECORD_AUDIO_PERMISSION_REQUEST_CODE);
                if (value == 1) {
                } else if (value == 2) {
                    displayNeverAskAgainDialog(mContext, getString(R.string.We_need_permission_Audio));
                }
            } else {
                Toast.makeText(mContext, getString(R.string.you_are_offline), Toast.LENGTH_SHORT).show();
            }
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
            this.mContext = ((HomeActivityTwo) getContext());
        }
    }


    /**
     * Function for speech to text puchase
     *
     * @param context
     * @param editText
     * @param dataType
     * @param micImage
     * @param valueforValidate
     */
    void speechToTextFuncVoiceForPurchase(Context context, EditText editText, int dataType, ImageView micImage, int valueforValidate) {
        if (Utility.getInstance().isOnline(mContext)) {
            int value = checkPermission(mContext, Manifest.permission.RECORD_AUDIO, RECORD_AUDIO_PERMISSION_REQUEST_CODE);
            if (value == 1) {
                mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(mContext);
                mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                String languageCode = Utility.getInstance().getLanguage(mContext);
                //         Locale current = getResources().getConfiguration().locale;
                if (languageCode.matches(langaugeCodeEnglish)) {
                    mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                } else if (languageCode.matches(languageCodeMarathi)) {
                    mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "mr_IN");
                } else {
                    mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                    setError("BaseFragmnet : speechToTextFunc Has problem");
                }
                mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
                    @Override
                    public void onReadyForSpeech(Bundle params) {
                        if (isVisible()) {
                            micImage.setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_IN);
                            if (editText != null) {
                                editText.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_green_light),
                                        PorterDuff.Mode.SRC_ATOP);
                            }
                        }
                    }

                    @Override
                    public void onBeginningOfSpeech() {

                    }

                    @Override
                    public void onRmsChanged(float rmsdB) {

                    }

                    @Override
                    public void onBufferReceived(byte[] buffer) {

                    }

                    @Override
                    public void onEndOfSpeech() {
                        if (isVisible()) {
                            micImage.setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_IN);
                            if (editText != null) {
                                editText.getBackground().setColorFilter(getResources().getColor(android.R.color.black),
                                        PorterDuff.Mode.SRC_ATOP);
                            }
                        }
                    }

                    @Override
                    public void onError(int error) {

                    }

                    @Override
                    public void onResults(Bundle results) {
                        if (isVisible()) {
                            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                            if (matches.get(0).toString().toLowerCase().contains(getString(R.string.save).toString().toLowerCase())) {
                                boolean valueStatus = validatePuchase();
                                if (valueStatus) {
                                    createPurchaseRequest(flowpurchaseReceivedOrPaid);
                                }
                            } else if (matches.get(0).toString().toLowerCase().contains(getString(R.string.cancel_two).toString().toLowerCase())) {
                                dialog.dismiss();
                                dialog.dismiss();
                                dialog.dismiss();
                                if (textToSpeech != null) {
                                    textToSpeech.stop();
                                }
                                Toast.makeText(mContext, getString(R.string.transaction_cancelled), Toast.LENGTH_SHORT).show();
                                speak(getString(R.string.transaction_cancelled), "");
                            }
                            if (editText != null) {
                                if (dataType == 1) {
                                    editText.setText(matches.get(0));
                                } else {
                                    for (int i = 0; i < matches.size(); i++) {
                                        if (matches.get(i).matches("^[0-9]*$")) {
                                            editText.setText(matches.get(i));
                                        }
                                    }
                                }


                                //Ankur
                                boolean valueValidate = validatePuchase();
                                if (valueValidate) {
                                    speak(getString(R.string.please_say_save_or_cancel), "");
                                    new Handler().postDelayed(() -> {
                                        speechToTextFuncVoiceForPurchase(mContext, null, 3, imageviewMicSaveCancel, 2);
                                    }, 3000);
                                }

                            }
                        }
                    }

                    @Override
                    public void onPartialResults(Bundle partialResults) {

                    }

                    @Override
                    public void onEvent(int eventType, Bundle params) {

                    }
                });

                if (dialog.isShowing()) {
                    mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                }
            } else if (value == 2) {
                displayNeverAskAgainDialog(mContext, getString(R.string.We_need_permission_Audio));
            }
        } else {
            Toast.makeText(context, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * create purchase request
     *
     * @param flowpurchaseReceivedOrPaid
     */
    private void createPurchaseRequest(int flowpurchaseReceivedOrPaid) {
        PurchaseTransactionRequest purchaseTransactionRequest = new PurchaseTransactionRequest();
        SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(mContext);
        purchaseTransactionRequest.setBusinessType(sharedPreferences.getString("BusinessId", ""));
        purchaseTransactionRequest.setDeviceId("");
        purchaseTransactionRequest.setLang(Utility.getInstance().getLanguage(mContext));
        purchaseTransactionRequest.setRegId(Utility.getInstance().getclientRegId(mContext));

        ArrayList<PurchaseTransactionRequest.SupplierList> supplierListsarray = new ArrayList<>();
        PurchaseTransactionRequest.SupplierList supplierList = new PurchaseTransactionRequest().new SupplierList();
        RealmResults<SupplierListRealm> supplierRealmModels = realm.where(SupplierListRealm.class).findAll();
        boolean value = false;
        String id = "";
        String supplierId = "";
        for (SupplierListRealm object : supplierRealmModels) {
            if ((relativelayoutSOustandingProfile.getVisibility() == View.VISIBLE) && textviewSOustandingId.getText().toString().toLowerCase().matches(object.getSupplierId().toLowerCase().toLowerCase())) {
                id = object.getID();
                value = true;
                supplierId = object.getSupplierId();
                break;
            }
        }
        if (value) {
            supplierList.setSupplierId(supplierId);
            supplierList.setId(id);
        } else {
            supplierList.setSupplierId("");
            supplierList.setId("");
        }
        supplierList.setName(editextPurchaseSupplierName.getText().toString());

        ArrayList<PurchaseTransactionRequest.SupplierList.TransactionsList> transactionsListArrayList = new ArrayList<>();
        PurchaseTransactionRequest.SupplierList.TransactionsList transactionsList = new PurchaseTransactionRequest().new SupplierList().new TransactionsList();
        transactionsList.setTransactionId("");
        transactionsList.setAmount(edittextPurchaseAmount.getText().toString());
        String currentDate = edittextPuchaseDate.getText().toString();
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(currentDate + " " + currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        transactionsList.setDate(date);
        transactionsList.setLiter("");
        transactionsList.setPurpose(edittextPurchasePurpose.getText().toString().trim());
        transactionsList.setTime(currentTime);
        transactionsList.setTransId("");
        transactionsList.setTransactionType(String.valueOf(flowpurchaseReceivedOrPaid));
        transactionsListArrayList.add(transactionsList);
        supplierList.setTransactionsListArrayList(transactionsListArrayList);
        supplierListsarray.add(supplierList);
        purchaseTransactionRequest.setSupplierListArrayList(supplierListsarray);


        if (Utility.getInstance().isOnline(mContext)) {
            Utility.getInstance().showProgressDialogue(mContext);
            mPresenter.postPurchaseData(purchaseTransactionRequest);
        } else {
            Toast.makeText(mContext, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is for validatePurchase
     *
     * @return
     */
    private boolean validatePuchase() {
        if (TextUtils.isEmpty(editextPurchaseSupplierName.getText().toString())) {
            editextPurchaseSupplierName.setError(getString(R.string.supplier_name));
            editextPurchaseSupplierName.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edittextPurchaseAmount.getText().toString())) {
            if (flowpurchaseReceivedOrPaid == 2) {
                edittextPurchaseAmount.setError(getString(R.string.menu_purchasePaid));
                edittextPurchaseAmount.requestFocus();

            } else {
                edittextPurchaseAmount.setError(getString(R.string.purchase_amount));
                edittextPurchaseAmount.requestFocus();
            }
            return false;
        }

        if (TextUtils.isEmpty(edittextPurchasePurpose.getText().toString())) {
            edittextPurchasePurpose.setError(getString(R.string.remark_bill_number));
            edittextPurchasePurpose.requestFocus();
            return false;
        }


        return true;
    }


    void speechToTextFuncVoice(Context mContext, EditText editText, int dataType, ImageView micImage, int valueforValidate) {
        if (Utility.getInstance().isOnline(mContext)) {
            int value = checkPermission(mContext, Manifest.permission.RECORD_AUDIO, RECORD_AUDIO_PERMISSION_REQUEST_CODE);
            if (value == 1) {
                mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(mContext);
                mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                String languageCode = Utility.getInstance().getLanguage(mContext);
                //      Locale current = getResources().getConfiguration().locale;
                if (languageCode.matches(langaugeCodeEnglish)) {
                    mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                } else if (languageCode.matches(languageCodeMarathi)) {
                    mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "mr_IN");
                } else {
                    mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                    setError("BaseFragmnet : speechToTextFunc Has problem");
                }
                mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, mContext.getPackageName());
                mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 10000);
                mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 10000);
                mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
                    @Override
                    public void onReadyForSpeech(Bundle params) {
                        if (isVisible()) {
                            micImage.setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_IN);
                            if (editText != null) {
                                editText.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_green_light),
                                        PorterDuff.Mode.SRC_ATOP);
                            }
                        }
                    }

                    @Override
                    public void onBeginningOfSpeech() {

                    }

                    @Override
                    public void onRmsChanged(float rmsdB) {

                    }

                    @Override
                    public void onBufferReceived(byte[] buffer) {

                    }

                    @Override
                    public void onEndOfSpeech() {
                        if (isVisible()) {
                            micImage.setColorFilter(getResources().getColor(R.color.mike_grey_color), PorterDuff.Mode.SRC_IN);
                            if (editText != null) {
                                editText.getBackground().setColorFilter(getResources().getColor(android.R.color.black),
                                        PorterDuff.Mode.SRC_ATOP);
                            }
                        }
                    }

                    @Override
                    public void onError(int error) {

                    }

                    @Override
                    public void onResults(Bundle results) {
                        if (isVisible()) {
                            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                            if (dataType == 1) {
                                if (editText != null) {
                                    editText.setText(matches.get(0));
                                    if (valueforValidate == 3) {
                                        boolean valueStatus = validateExpense();
                                        edittextExpenseName.setError(null);
                                        edittextExpesneAmount.setError(null);
                                        edittextExpensePurpose.setError(null);
                                        edittextExpenseDate.setError(null);
                                        if (valueStatus) {
                                            speak(getString(R.string.please_say_save_or_cancel), "");
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    speechToTextFuncVoice(mContext, null, 5, imageviewMicSaveCancel, 3);
                                                }
                                            }, 3000);
                                        }
                                    } else if (valueforValidate == 2) {
                                        boolean valueStatus = validateCreditDebit(flowSelected);
                                        if (valueStatus) {
                                            if (edittextdebitLitre.getVisibility() == View.VISIBLE) {
                                                if (!TextUtils.isEmpty(edittextdebitLitre.getText())) {
                                                    speak(getString(R.string.please_say_save_or_cancel), "");
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (flowSelected == 1) {
                                                                speechToTextFuncVoice(mContext, null, 3, imageviewMicSaveCancel, 2);
                                                            } else {
                                                                speechToTextFuncVoice(mContext, null, 4, imageviewMicSaveCancel, 2);
                                                            }
                                                        }
                                                    }, 3000);
                                                }
                                            } else {
                                                speak(getString(R.string.please_say_save_or_cancel), "");
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if (flowSelected == 1) {
                                                            speechToTextFuncVoice(mContext, null, 3, imageviewMicSaveCancel, 2);
                                                        } else {
                                                            speechToTextFuncVoice(mContext, null, 4, imageviewMicSaveCancel, 2);
                                                        }
                                                    }
                                                }, 3000);
                                            }
                                        }
                                    }
                                }
                            } else if (dataType == 3 || dataType == 4 || dataType == 5) {
                                if (matches.get(0).toString().toLowerCase().contains(getString(R.string.save).toString().toLowerCase())) {
                                    if (dataType == 5) {
                                        createRequestExpense();
                                    } else {
                                        saveCreditandDebit(dataType - 2);
                                    }
                                } else if (matches.get(0).toString().toLowerCase().contains(getString(R.string.cancel_two).toString().toLowerCase())) {
                                    dialog.dismiss();
                                    dialog.dismiss();
                                    dialog.dismiss();
                                    if (textToSpeech != null) {
                                        textToSpeech.stop();
                                    }
                                    Toast.makeText(mContext, getString(R.string.transaction_cancelled), Toast.LENGTH_SHORT).show();
                                    speak(getString(R.string.transaction_cancelled), "");
                                    imageviewMicExpense.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                                    imageviewDevitMic.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                                    imageviewMicCredit.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                                    imageviewOustMic.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                                }
                            } else {
                                for (int i = 0; i < matches.size(); i++) {
                                    if (matches.get(i).matches("^[0-9]*$")) {
                                        if (editText != null) {
                                            editText.setText(matches.get(i));
                                            if (valueforValidate == 3) {

                                                boolean valueStatus = validateExpense();
                                                edittextExpenseName.setError(null);
                                                edittextExpesneAmount.setError(null);
                                                edittextExpensePurpose.setError(null);
                                                edittextExpenseDate.setError(null);
                                                if (valueStatus) {
                                                    speak(getString(R.string.please_say_save_or_cancel), "");
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            speechToTextFuncVoice(mContext, null, 5, imageviewMicSaveCancel, 3);
                                                        }
                                                    }, 3000);
                                                }
                                            } else if (valueforValidate == 2) {
                                                boolean valueStatus = validateCreditDebit(flowSelected);
                                                if (valueStatus) {
                                                    if (flowSelected == 2) {
                                                        /*                                                       if (!TextUtils.isEmpty(edittextdebitLitre.getText())) {*/
                                                        if (true) {
                                                            speak(getString(R.string.please_say_save_or_cancel), "");
                                                            new Handler().postDelayed(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    if (flowSelected == 1) {
                                                                        speechToTextFuncVoice(mContext, null, 3, imageviewMicSaveCancel, 2);
                                                                    } else {
                                                                        speechToTextFuncVoice(mContext, null, 4, imageviewMicSaveCancel, 2);
                                                                    }
                                                                }
                                                            }, 3000);
                                                        }
                                                    } else {
                                                        speak(getString(R.string.please_say_save_or_cancel), "");
                                                        new Handler().postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                if (flowSelected == 1) {
                                                                    speechToTextFuncVoice(mContext, null, 3, imageviewMicSaveCancel, 2);
                                                                } else {
                                                                    speechToTextFuncVoice(mContext, null, 4, imageviewMicSaveCancel, 2);
                                                                }
                                                            }
                                                        }, 3000);
                                                    }
                                                }
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onPartialResults(Bundle partialResults) {

                    }

                    @Override
                    public void onEvent(int eventType, Bundle params) {

                    }
                });

                if (dialog.isShowing()) {
                    mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                }
            } else if (value == 2) {
                displayNeverAskAgainDialog(mContext, getString(R.string.We_need_permission_Audio));
            }
        } else {
            View rootView = ((Activity) mContext).getWindow().getDecorView().findViewById(android.R.id.content);
            Utility.getInstance().showSnackbar(rootView, getString(R.string.please_check_internet));
        }
    }

    /**
     * setting text to speech function
     */
    private void setTextTospeech() {
        textToSpeech = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    Set<Locale> languages = textToSpeech.getAvailableLanguages();
                    if (language.toLowerCase().contains(langaugeCodeEnglish)) {
                        int result = textToSpeech.setLanguage(new Locale("en", "IN"));
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            //Toast.makeText(mContext, result + " is not supported", Toast.LENGTH_SHORT).show();
                            Log.e("Text2SpeechWidget", result + " is not supported");
                        }
                    } else {
                        int result = textToSpeech.setLanguage(new Locale("hi", "IN"));
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                result = textToSpeech.setLanguage(Locale.forLanguageTag("hin"));
                                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                    SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(mContext);
                                    int applicationLoginCount = sharedPreferences.getInt(TTSDIALOGUECOUNT, 1);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    if (!sharedPreferences.getBoolean(LANGUAGEAVAILABILITYFIRSTTIME, true)) {
                                        editor.putBoolean(LANGUAGEAVAILABILITYFIRSTTIME, true);
                                        //   Utility.getInstance().ttsLanguageDialogue(mContext,getString(R.string.marathi_language_Available_update));
                                    }
                                    applicationLoginCount++;
                                    //  editor.putInt(TTSDIALOGUECOUNT, applicationLoginCount);
                                    editor.commit();
                                }
                            } else {
                                SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(mContext);
                                int applicationLoginCount = sharedPreferences.getInt(TTSDIALOGUECOUNT, 1);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                if (!sharedPreferences.getBoolean(LANGUAGEAVAILABILITYFIRSTTIME, true)) {
                                    editor.putBoolean(LANGUAGEAVAILABILITYFIRSTTIME, true);
                                    //    Utility.getInstance().ttsLanguageDialogue(mContext,getString(R.string.marathi_language_Available_update));
                                }
                                applicationLoginCount++;
                                //   editor.putInt(TTSDIALOGUECOUNT, applicationLoginCount);
                                editor.commit();

                                //  Toast.makeText(mContext, result + "Language is not supported", Toast.LENGTH_SHORT).show();
                                Log.e("Text2SpeechWidget", result + "Language is not supported");
                            }
                            Log.e("Text2SpeechWidget", result + " is not supported");
                        } else {
                            SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(mContext);
                            int applicationLoginCount = sharedPreferences.getInt(TTSDIALOGUECOUNT, 1);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            if (!sharedPreferences.getBoolean(LANGUAGEAVAILABILITYFIRSTTIME, true)) {
                                editor.putBoolean(LANGUAGEAVAILABILITYFIRSTTIME, true);
                                //   Utility.getInstance().ttsLanguageDialogue(mContext,getString(R.string.marathi_language_Available_update));
                            }
                            applicationLoginCount++;
                            //   editor.putInt(TTSDIALOGUECOUNT, applicationLoginCount);
                            editor.commit();
                        }
                        if (result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            showTtsLanuageNSDialog();
                        }
                    }
                }
            }
        });
    }

    private void showTtsLanuageNSDialog() {
        SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(mContext);

        int applicationLoginCount = sharedPreferences.getInt(TTSDIALOGUECOUNT, 1);
        if (applicationLoginCount == 1) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(LANGUAGEAVAILABILITYFIRSTTIME, false);
            applicationLoginCount++;
            editor.putInt(TTSDIALOGUECOUNT, applicationLoginCount);
            editor.commit();
            Utility.getInstance().ttsLanguageDialogue(mContext, getString(R.string.marathi_language_tts_is_not_supported));
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(TTSDIALOGUECOUNT, applicationLoginCount++);
            editor.commit();
        }
    }

    /**
     * setting pitch and speed and speaking the text
     *
     * @param s
     * @param text
     */
    private void speak(String s, String text) {
        if (Utility.getInstance().isOnline(mContext)) {
            try {
                float pitch = (float) 0.62;
                float speed = (float) 0.86;
                textToSpeech.setSpeechRate(speed);
                textToSpeech.setPitch(pitch);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(TextToSpeech.Engine.KEY_PARAM_STREAM, AudioManager.STREAM_MUSIC);
                    textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, bundle, null);
                    textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, bundle, null);
                } else {
                    HashMap<String, String> param = new HashMap<>();
                    param.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_MUSIC));
                    textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, param);
                    textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, param);
                }
            } catch (Exception ae) {
                ae.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void postResponseSuccess(TransactionResponse response, int valueofFlowOnlineOfline) {
        Utility.getInstance().dismissProgress();
        Utility.getInstance().dismissProgress();
        Utility.getInstance().dismissProgress();
//        Toast.makeText(mContext, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
        try {
            if (valueofFlowOnlineOfline == 1) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog.dismiss();
                    dialog.dismiss();
                }
                if (textToSpeech != null) {
                    textToSpeech.stop();
                }
                imageviewDevitMic.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                imageviewMicCredit.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                boolean transactionIdAvailable = false;

                ArrayList<Integer> numberIds = new ArrayList<>();
                ArrayList<CustomerListRealm> customerListRealmArrayList = new ArrayList<>();
                for (int i = 0; i < response.getData().getCustomerListArrayList().size(); i++) {
                    CustomerListRealm customerListRealm = new CustomerListRealm();
                    String customerIdFromresponse = response.getData().getCustomerListArrayList().get(i).getCustomerId();
                    int regIdLength = Utility.getInstance().getclientRegId(mContext).length();
                    String removeCFromResponse = customerIdFromresponse.substring(1 + regIdLength);
                    numberIds.add(Integer.parseInt(removeCFromResponse));
                    customerListRealm.setID(response.getData().getCustomerListArrayList().get(i).getID());
                    customerListRealm.setCustomerId(response.getData().getCustomerListArrayList().get(i).getCustomerId());
                    customerListRealm.setClientId(response.getData().getCustomerListArrayList().get(i).getClientId());
                    customerListRealm.setCFirstname(response.getData().getCustomerListArrayList().get(i).getCFirstname());
                    customerListRealm.setCMiddleName(response.getData().getCustomerListArrayList().get(i).getCMiddleName());
                    customerListRealm.setCLastname(response.getData().getCustomerListArrayList().get(i).getCLastname());
                    customerListRealm.setImagePath(response.getData().getCustomerListArrayList().get(i).getImagePath());
                    customerListRealm.setOutstanding(response.getData().getCustomerListArrayList().get(i).getOutstanding());
                    customerListRealm.setVillage(response.getData().getCustomerListArrayList().get(i).getVillage());
                    customerListRealm.setTaluka(response.getData().getCustomerListArrayList().get(i).getTaluka());
                    customerListRealm.setCustomerOnlineSaved(true);
                    customerListRealm.setDistrict(response.getData().getCustomerListArrayList().get(i).getDistrict());
                    customerListRealm.setCAlternetMobNo(response.getData().getCustomerListArrayList().get(i).getCAlternetMobNo());
                    customerListRealm.setCContactNo(response.getData().getCustomerListArrayList().get(i).getCContactNo());
                    if (response.getData().getCustomerListArrayList().get(i).getSMSFlag() != null && response.getData().getCustomerListArrayList().get(i).getSMSFlag().trim().toLowerCase().matches("true")) {
                        customerListRealm.setSMSFlag("true");
                    } else {
                        customerListRealm.setSMSFlag("false");
                    }
                    realm.beginTransaction();
                    realm.where(CustomerListRealm.class)
                            .equalTo("CustomerId", response.getData().getCustomerListArrayList().get(i).getCustomerId().toString()).findAll().deleteAllFromRealm();
                    realm.commitTransaction();

                    customerListRealmArrayList.add(customerListRealm);
                }
                for (CustomerListRealm customerListRealm : customerListRealmArrayList) {
                    realm.beginTransaction();
                    realm.copyToRealm(customerListRealm);
                    realm.commitTransaction();
                }

                if (response.getData().getCustomerListArrayList().size() > 0) {
                    String firstId = response.getData().getCustomerListArrayList().get(0).getCustomerId();
                    int regIdLength = Utility.getInstance().getclientRegId(mContext).length();
                    String firstIdResponse = firstId.substring(1 + regIdLength);
                    int maxCustomerId = Integer.parseInt(firstIdResponse);
                    for (int j = 0; j < numberIds.size(); j++) {
                        if (maxCustomerId < numberIds.get(j)) {
                            maxCustomerId = numberIds.get(j);
                        }
                    }
                    SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(mContext);
                    if (!TextUtils.isEmpty(sharedPreferences.getString(LASTCUSTOMERID, ""))) {
                        if (Integer.parseInt(sharedPreferences.getString(LASTCUSTOMERID, "")) < maxCustomerId) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(LASTCUSTOMERID, String.valueOf(maxCustomerId));
                            editor.commit();
                        }
                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(LASTCUSTOMERID, String.valueOf(maxCustomerId));
                        editor.commit();
                    }
                }
                edittextdebitAmount.setText("");
                edittextDebitcustomerName.setText("");
                edittextdebitPurpose.setText("");
                edittextdebitLitre.setText("");
                Utility.getInstance().dismissProgress();
                Utility.getInstance().dismissProgress();
                Utility.getInstance().dismissProgress();
                if (response.getSuccessTranaactionId() != null && !TextUtils.isEmpty(response.getSuccessTranaactionId())) {
//                    speak(getString(R.string.Data_saved), getString(R.string.transaction_id) + "" + response.getSuccessTranaactionId());
                    speak(getString(R.string.Data_saved), "");
                    Toast.makeText(mContext, getString(R.string.Data_saved) + " " + response.getSuccessTranaactionId(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, getString(R.string.transaction_cancelled), Toast.LENGTH_SHORT).show();
                }
                Utility.getInstance().dismissProgress();
                Utility.getInstance().dismissProgress();
                Utility.getInstance().dismissProgress();
            } else {
                Utility.getInstance().dismissProgress();
                Utility.getInstance().dismissProgress();
                Utility.getInstance().dismissProgress();
            }
            Utility.getInstance().dismissProgress();
            Utility.getInstance().dismissProgress();
            Utility.getInstance().dismissProgress();
        } catch (Exception ae) {
            ae.printStackTrace();
        } finally {
            Utility.getInstance().dismissProgress();
            Utility.getInstance().dismissProgress();
            Utility.getInstance().dismissProgress();
        }
    }


    @Override
    public void postResponseFailure(String message) {
        try {
            Utility.getInstance().dismissProgress();
            Utility.getInstance().dismissProgress();
            Utility.getInstance().dismissProgress();
            Utility.getInstance().dismissProgress();
            Utility.getInstance().dismissProgress();
            Utility.getInstance().dismissProgress();
            Toast.makeText(mContext, getString(R.string.transaction_cancelled), Toast.LENGTH_SHORT).show();
            //   Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        } catch (Exception ae) {
            ae.printStackTrace();
            Utility.getInstance().dismissProgress();
            Utility.getInstance().dismissProgress();
            Utility.getInstance().dismissProgress();
        } finally {
            Utility.getInstance().dismissProgress();
            Utility.getInstance().dismissProgress();
            Utility.getInstance().dismissProgress();
        }
    }

    @Override
    public void postExpenseSuccess(ExpenseResponse response, int valueofFlowOnlineOfline) {
        Utility.getInstance().dismissProgress();
        Utility.getInstance().dismissProgress();
        Utility.getInstance().dismissProgress();
        try {

            if (valueofFlowOnlineOfline == 1) {
                imageviewMicExpense.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                boolean transactionIdAvailable = false;
                if (response.getSuccessTranaactionId() != null && !TextUtils.isEmpty(response.getSuccessTranaactionId())) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            speak(getString(R.string.Data_saved), getString(R.string.transaction_id) + "" + response.getSuccessTranaactionId());
                            speak(getString(R.string.Data_saved), "");
                        }
                    }, 1000);
                    Toast.makeText(mContext, getString(R.string.Data_saved) + " " + response.getSuccessTranaactionId(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, getString(R.string.transaction_cancelled), Toast.LENGTH_SHORT).show();
                }
                if (dialog != null) {
                    dialog.dismiss();
                    dialog.dismiss();
                    dialog.dismiss();
                }
                if (textToSpeech != null) {
                    textToSpeech.stop();
                }
            } else {
            }
            Utility.getInstance().dismissProgress();
            Utility.getInstance().dismissProgress();
            Utility.getInstance().dismissProgress();
        } catch (Exception ae) {
            ae.printStackTrace();
            Utility.getInstance().dismissProgress();
            Utility.getInstance().dismissProgress();
        } finally {
            Utility.getInstance().dismissProgress();
            Utility.getInstance().dismissProgress();
        }
    }

    @Override
    public void postPurchaseResponseSuccess(PostPurchaseResponse response) {
        Utility.getInstance().dismissProgress();
        Utility.getInstance().dismissProgress();
        Utility.getInstance().dismissProgress();
        Toast.makeText(mContext, getString(R.string.Data_saved) + " " + response.getSuccessTranaactionId(), Toast.LENGTH_SHORT).show();
        try {
            if (dialog != null) {
                dialog.dismiss();
                dialog.dismiss();
                dialog.dismiss();
            }
            if (textToSpeech != null) {
                textToSpeech.stop();
            }


            ArrayList<SupplierListRealm> supplierListRealmArrayList = new ArrayList<>();
            for (int i = 0; i < response.getData().getSupplierlistArrayList().size(); i++) {
                SupplierListRealm supplierListRealm = new SupplierListRealm();
                supplierListRealm.setID(response.getData().getSupplierlistArrayList().get(i).getID());
                supplierListRealm.setSupplierId(response.getData().getSupplierlistArrayList().get(i).getSupplierId());
                supplierListRealm.setClientId(response.getData().getSupplierlistArrayList().get(i).getClientId());
                supplierListRealm.setSFirstname(response.getData().getSupplierlistArrayList().get(i).getSFirstname());
                supplierListRealm.setSMiddleName(response.getData().getSupplierlistArrayList().get(i).getSMiddleName());
                supplierListRealm.setSLastname(response.getData().getSupplierlistArrayList().get(i).getSLastname());
                supplierListRealm.setImagePath(response.getData().getSupplierlistArrayList().get(i).getImagePath());
                supplierListRealm.setOutstanding(response.getData().getSupplierlistArrayList().get(i).getOutstanding());
                supplierListRealm.setVillage(response.getData().getSupplierlistArrayList().get(i).getVillage());
                supplierListRealm.setTaluka(response.getData().getSupplierlistArrayList().get(i).getTaluka());
                supplierListRealm.setDistrict(response.getData().getSupplierlistArrayList().get(i).getDistrict());
                supplierListRealm.setSAlternetMobNo(response.getData().getSupplierlistArrayList().get(i).getSAlternetMobNo());
                supplierListRealm.setSContactNo(response.getData().getSupplierlistArrayList().get(i).getCreatedOn());
                if (response.getData().getSupplierlistArrayList().get(i).getSMSFlag() != null && response.getData().getSupplierlistArrayList().get(i).getSMSFlag().trim().toLowerCase().matches("true")) {
                    supplierListRealm.setSMSFlag("true");
                } else {
                    supplierListRealm.setSMSFlag("false");
                }
                realm.beginTransaction();
                realm.where(SupplierListRealm.class)
                        .equalTo("SupplierId", response.getData().getSupplierlistArrayList().get(i).getSupplierId().toString()).findAll().deleteAllFromRealm();
                realm.commitTransaction();

                supplierListRealmArrayList.add(supplierListRealm);
            }
            for (SupplierListRealm supplierListRealm : supplierListRealmArrayList) {
                realm.beginTransaction();
                realm.copyToRealm(supplierListRealm);
                realm.commitTransaction();
            }


        } catch (Exception ae) {
            ae.printStackTrace();
        }
    }

    @Override
    public void postResponseFailuretimeout() {
        Toast.makeText(mContext, getString(R.string.please_check_internet_and_try_again), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStateListClick(View view, int position) {
        try {
            dialogMultipleUser.dismiss();
            dialogMultipleUser.dismiss();
            dialogMultipleUser.dismiss();
            Glide.with(mContext).load(new CustomRetroRequest().imageURL + filterMultipleCustomerListTwo.get(position))
                    .into(imageviewOutsnadingProfile);
            textviewOustandingId.setText(filterMultipleCustomerListTwo.get(position).getCustomerId());
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
            textviewOustandingName.setText(finalname);
            if (edittextDebitcustomerName != null) {
                edittextDebitcustomerName.setText(finalname);
            }

            if (edittextoustandingcustomerName != null) {
                edittextoustandingcustomerName.setText(finalname);
            }
            if (filterMultipleCustomerListTwo.get(position).getOutstanding() != null && !TextUtils.isEmpty(filterMultipleCustomerListTwo.get(position).getOutstanding().toString())) {
                textviewOustandingAmount.setText(filterMultipleCustomerListTwo.get(position).getOutstanding());
            } else {
                textviewOustandingAmount.setText("0");
            }
            textview_notFound.setVisibility(View.GONE);
            relativelayoutOustandingProfile.setVisibility(View.VISIBLE);
            if (SpeakOutsatnding) {
                CharSequence s = textviewOustandingAmount.getText();
                if (s.toString().equals("-")) {
                    String languageCode = Utility.getInstance().getLanguage(mContext);
                    if (languageCode.matches("") || languageCode.matches(langaugeCodeEnglish)) {
                        speak(finalname + " " + getString(R.string.menuoutstanding) + " " + s, "");
                    } else {
                        speak(finalname + " " + getString(R.string.menuoutstanding) + " " + s, "");
                        /// speak(finalname + " " + getString(R.string.menuoutstanding) + " " + getString(R.string.minus) + " " + s, "");
                    }
                    //        speak(finalname + "" + getString(R.string.menu_outstanding) + getString(R.string.minus) + "" + s, "");
                } else {
                    speak(finalname + "" + getString(R.string.menu_outstanding) + "" + s, "");
                }
            }
            dialogMultipleUser.dismiss();
            dialogMultipleUser.dismiss();
            dialogMultipleUser.dismiss();
        } catch (Exception ae) {
            Log.d(LOGPOS, "onStateListClick: " + ae.getMessage());
        }
    }

    @Override
    public void onMultipleSupplierListener(View view, int position) {
        try {
            dialogMultipleUser.dismiss();
            dialogMultipleUser.dismiss();
            dialogMultipleUser.dismiss();
            Glide.with(mContext).load(new CustomRetroRequest().imageURL + filterMultipleSupplierListTwo.get(position))
                    .into(imageviewSOutsnadingProfile);
            textviewSOustandingId.setText(filterMultipleSupplierListTwo.get(position).getSupplierId());
            String firstname = "";
            String middlename = "";
            String lastname = "";
            if (filterMultipleSupplierListTwo.get(position).getSLastname() != null) {
                lastname = filterMultipleSupplierListTwo.get(position).getSLastname();
            }
            if (filterMultipleSupplierListTwo.get(position).getSMiddleName() != null) {
                middlename = filterMultipleSupplierListTwo.get(position).getSMiddleName();
            }
            if (filterMultipleSupplierListTwo.get(position).getSFirstname() != null) {
                firstname = filterMultipleSupplierListTwo.get(position).getSFirstname();
            }
            String finalname = "";
            if (TextUtils.isEmpty(middlename) && TextUtils.isEmpty(lastname)) {
                finalname = firstname;
            } else if (TextUtils.isEmpty(middlename)) {
                finalname = firstname + " " + lastname;
            } else {
                finalname = firstname + " " + middlename + " " + lastname;
            }
            textviewSOustandingName.setText(finalname);
            if (editextPurchaseSupplierName != null) {
                editextPurchaseSupplierName.removeTextChangedListener(textWatcherPurchaseSupplierName);
                editextPurchaseSupplierName.setText(finalname);
                editextPurchaseSupplierName.addTextChangedListener(textWatcherPurchaseSupplierName);
            }


            if (filterMultipleSupplierListTwo.get(position).getOutstanding() != null && !TextUtils.isEmpty(filterMultipleSupplierListTwo.get(position).getOutstanding().toString())) {
                textviewSOustandingAmount.setText(filterMultipleSupplierListTwo.get(position).getOutstanding());
            } else {
                textviewSOustandingAmount.setText("0");
            }
            textview_notFound.setVisibility(View.GONE);
            relativelayoutSOustandingProfile.setVisibility(View.VISIBLE);
            if (SpeakOutsatnding) {
                CharSequence s = textviewSOustandingAmount.getText();
                if (s.toString().equals("-")) {
                    String languageCode = Utility.getInstance().getLanguage(mContext);
                    if (languageCode.matches("") || languageCode.matches(langaugeCodeEnglish)) {
                        speak(finalname + " " + getString(R.string.menuoutstanding) + " " + s, "");
                    } else {
                        speak(finalname + " " + getString(R.string.menuoutstanding) + " " + s, "");
                        /// speak(finalname + " " + getString(R.string.menuoutstanding) + " " + getString(R.string.minus) + " " + s, "");
                    }
                    //        speak(finalname + "" + getString(R.string.menu_outstanding) + getString(R.string.minus) + "" + s, "");
                } else {
                    speak(finalname + "" + getString(R.string.menu_outstanding) + "" + s, "");
                }
            }
            dialogMultipleUser.dismiss();
            dialogMultipleUser.dismiss();
            dialogMultipleUser.dismiss();
        } catch (Exception ae) {
            Log.d(LOGPOS, "onStateListClick: " + ae.getMessage());
        }
    }
}