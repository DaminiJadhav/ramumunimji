package com.posbilling.posbillingapplication.activity.dashboard.ui.home;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.dashboard.ActivityDashboard;
import com.posbilling.posbillingapplication.lib.BaseFragment;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.model.realmcontoller.RealmController;
import com.posbilling.posbillingapplication.model.realmmodel.CustomerListRealm;
import com.posbilling.posbillingapplication.model.request.TransactionRequest;
import com.posbilling.posbillingapplication.model.response.ExpenseResponse;
import com.posbilling.posbillingapplication.model.response.PostPurchaseResponse;
import com.posbilling.posbillingapplication.model.response.TransactionResponse;
import com.posbilling.posbillingapplication.utility.Utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.posbilling.posbillingapplication.utility.Constants.LOGPOS;
import static com.posbilling.posbillingapplication.utility.Constants.RECORD_AUDIO_PERMISSION_REQUEST_CODE;
import static com.posbilling.posbillingapplication.utility.Constants.langaugeCodeEnglish;
import static com.posbilling.posbillingapplication.utility.Constants.voiceEnglishCode;
import static com.posbilling.posbillingapplication.utility.Constants.voiceMarathiCode;

/**
 * Created by Ankur Shinde on 26,February,2020
 */

public class FragmentHome extends BaseFragment implements HomeContractor.View {
    private Context mContext;
    private HomeContractor.Presenter mPresenter;
    private String language = "en";
    private String voiceRecLanguageKey = voiceEnglishCode;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    private String text = "";
    private Dialog dialog;
    private TextToSpeech textToSpeech;
    private Timer timer;
    private ImageView imageviewCross;
    private int popUpFlow = 0;
    private EditText edittext_CustName;
    private EditText edittext_enterAmount;
    private TextView textview_header;
    private Button button_submit;
    // Animation
    private Animation animFadein;
    private TextView textview_Listening;
    private int selectedFlow = 0;
    private Realm realm;


    @BindView(R.id.imageview_profile)
    CircleImageView imageview_profile;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mContext == null) {
            this.mContext = ((ActivityDashboard) getContext());
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void setPresenter() {
        mPresenter = new HomePresenter(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.mContext = context;
        super.onAttach(context);
    }

    /**
     * start listening the voice
     */
    @Override
    public void onStart() {
        super.onStart();
        realm = RealmController.with(this).getRealm();
        if (isAdded() || isVisible()) {
            if (Utility.getInstance().isOnline(mContext)) {
                int value = checkPermission(mContext, Manifest.permission.RECORD_AUDIO, RECORD_AUDIO_PERMISSION_REQUEST_CODE);
                if (value == 1) {
                    startListeningTheVoice();
                } else if (value == 2) {
                    displayNeverAskAgainDialog(mContext, getString(R.string.We_need_permission));
                }
            } else {
                Toast.makeText(mContext, getString(R.string.you_are_offline), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * start listening the voice
     */
    @Override
    public void onResume() {
        super.onResume();
        if (isVisible()) {
            if (Utility.getInstance().isOnline(mContext)) {
                int value = checkPermission(mContext, Manifest.permission.RECORD_AUDIO, RECORD_AUDIO_PERMISSION_REQUEST_CODE);
                if (value == 1) {
                    startListeningTheVoice();
                } else if (value == 2) {
                    displayNeverAskAgainDialog(mContext, getString(R.string.We_need_permission));
                }
            } else {
                Toast.makeText(mContext, getString(R.string.you_are_offline), Toast.LENGTH_SHORT).show();
            }
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
                    if (language.toLowerCase().contains(langaugeCodeEnglish)) {
                        int result = textToSpeech.setLanguage(new Locale("en", "IN"));
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Toast.makeText(mContext, result + " is not supported", Toast.LENGTH_SHORT).show();
                            Log.e("Text2SpeechWidget", result + " is not supported");
                        }
                    } else {
                        int result = textToSpeech.setLanguage(new Locale("hi", "IN"));
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                textToSpeech.setLanguage(Locale.forLanguageTag("hin"));
                            } else {
                                Toast.makeText(mContext, result + "Language is not supported", Toast.LENGTH_SHORT).show();
                            }
                            Log.e("Text2SpeechWidget", result + " is not supported");
                        }
                    }
                }
            }
        });
    }

    /**
     * created intent of voice recognization
     * and after every 3 seconds the start listening method calls again using Timer
     */
    private void startListeningTheVoice() {
        mSpeechRecognizerIntent = null;
        mSpeechRecognizer = null;
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(mContext);
        Log.e(LOGPOS, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(mContext));
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, voiceRecLanguageKey);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, mContext.getPackageName());
        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                imageview_profile.setColorFilter(getResources().getColor(R.color.colorGreen), PorterDuff.Mode.SRC_IN);
                if (mContext != null) {
                    Toast.makeText(mContext, getString(R.string.Im_hearing_You), Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(LOGPOS, "onReadyForSpeech: mcontext is null");
                }
            }

            @Override
            public void onBeginningOfSpeech() {
                Log.e(LOGPOS, "onBeginningOfSpeech: ");
            }

            @Override
            public void onRmsChanged(float rmsdB) {
                Log.e(LOGPOS, "onRmsChanged: " + rmsdB);

            }

            @Override
            public void onBufferReceived(byte[] buffer) {
                Log.e(LOGPOS, "onBufferReceived: ");
            }

            @Override
            public void onEndOfSpeech() {
                imageview_profile.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
                Log.e(LOGPOS, "onEndOfSpeech: Called PosBilling");
                if (isVisible()) {
                    Log.e(LOGPOS, "onEndOfSpeech: start listening again");
                }
            }

            @Override
            public void onError(int error) {
                if (isVisible()) {
                    String text = getErrorText(error);
                    Log.e(LOGPOS, "onError: voice rec " + text + " error number : " + error);
                    /*if (SpeechRecognizer.ERROR_SPEECH_TIMEOUT == error || SpeechRecognizer.ERROR_NO_MATCH == error || SpeechRecognizer.ERROR_RECOGNIZER_BUSY == error) {
                        Log.e(LOGPOS, "onError: voice rec " + text + " error number : " + error + " Voice recognization starts again");
                        //mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        //startListeningTheVoice();
                    }*/
                }
            }

            @Override
            public void onResults(Bundle results) {
                text = "";
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                checkForTheText(matches.get(0).toLowerCase());
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                Log.e(LOGPOS, "onPartialResults: ");
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
                Log.e(LOGPOS, "onEvent: ");
            }
        });

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (isVisible() && isResumed()) {
                    getActivity().runOnUiThread(new TimerTask() {
                        @Override
                        public void run() {
                            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        }
                    });
                }
            }
        }, 0, 3000);
    }


    private void checkForTheText(String text) {
        if (!TextUtils.isEmpty(text.trim())) {
            if (dialog != null && dialog.isShowing() && popUpFlow != 0) {
                checkThePopUPVisibleFLow(text);
            } else if (text.toLowerCase().contains(getString(R.string.Credit).toLowerCase())) {
                selectedFlow = 1;
                visiblepopUp(getString(R.string.Credit));
                speak(getString(R.string.Credit), getString(R.string.please_tell_customer_name));
                popUpFlow = 1;

            } else if (text.toLowerCase().contains(getString(R.string.Debit).toLowerCase())) {
                selectedFlow = 2;
                visiblepopUp(getString(R.string.Debit));
                speak(getString(R.string.Debit), getString(R.string.please_tell_customer_name));
                popUpFlow = 1;

            } else if (text.toLowerCase().contains(getString(R.string.expenses).toLowerCase())) {
                selectedFlow = 3;
                visiblepopUp(getString(R.string.expenses));
                speak(getString(R.string.expenses), getString(R.string.Tell_purpose));
                popUpFlow = 1;

            }
        }
    }

    private void checkThePopUPVisibleFLow(String text) {
        switch (popUpFlow) {
            case 0:
                Log.e(LOGPOS, "checkThePopUPVisibleFLow: No need of popup");
                break;
            case 1:
                if (text != null && !TextUtils.isEmpty(text.trim())) {
                    if (!text.toLowerCase().contains(getString(R.string.tell)) && !text.toLowerCase().contains(getString(R.string.tell_dot)) && !text.toLowerCase().contains(getString(R.string.name))) {
                        edittext_CustName.setText(text);
                        popUpFlow = 2;
                        speak(text, getString(R.string.please_tell_amount_to_be_enter));
                    }
                }
                break;
            case 2:
                if (text != null && !TextUtils.isEmpty(text.trim())) {

                    if (text.matches("^[0-9]*$")) {
                        edittext_enterAmount.setText(text);
                        popUpFlow = 3;
                        speak(getString(R.string.amount) + " " + text, getString(R.string.Say_ok_to_save));
                        break;
                    }
                }
                break;
            case 3:
                if (!TextUtils.isEmpty(edittext_CustName.getText().toString().trim())) {
                    if (!TextUtils.isEmpty(edittext_enterAmount.getText().toString().trim())) {
                        if (text.toLowerCase().trim().matches(getString(R.string.ok).trim().toLowerCase())) {
                            dataSaveAction();
                        }
                    } else {
                        edittext_enterAmount.requestFocus();
                        if (selectedFlow == 3) {
                            edittext_enterAmount.setError(getString(R.string.enter_purpose));
                        } else {
                            edittext_enterAmount.setError(getString(R.string.please_enter_amount));
                        }
                        speak(getString(R.string.please_enter_amount), "");
                    }
                } else {
                    edittext_CustName.requestFocus();
                    edittext_CustName.setError(getString(R.string.please_enter_customer_name));
                    speak(getString(R.string.please_enter_customer_name), "");
                }
                break;
        }
    }

    private void dataSaveAction() {
        switch (selectedFlow) {
            case 1:
                saveCreditandDebit();
                break;
            case 2:
                saveCreditandDebit();
                break;
            case 3:
                //createRequestExpense();
                break;
            default:
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    /*private void createRequestExpense() {
        ExpensesRequest expensesRequest = new ExpensesRequest();
        expensesRequest.setAmount(edittext_enterAmount.getText().toString());
        expensesRequest.setClientId(Utility.getInstance().getclientRegId(mContext));
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Date date = null;
        try {
            date = new SimpleDateFormat("dd-MM-yyyy").parse(currentDate );
            // time = new SimpleDateFormat("HH:mm").parse(currentDate + " " + currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        RealmResults<ExpendetureListRealm> expendetureListRealmRealmResults = realm.where(ExpendetureListRealm.class).findAll();
        String expeId = "";
        if (expendetureListRealmRealmResults.size()<=0){
            expeId = "T1";
        }else {
            expeId="T"+String.valueOf(expendetureListRealmRealmResults.size()+1);
        }
        expensesRequest.setExpendDate(date);
        expensesRequest.setExpenditureId(expeId);
        expensesRequest.setTransactionId("");
        expensesRequest.setPurpose(edittext_CustName.getText().toString());
        expensesRequest.setExpenseType("pesonal");
        Utility.getInstance().showProgressDialogue(mContext);
        mPresenter.postExpense(expensesRequest, 1);
    }*/

    private void saveCreditandDebit() {
        //Ankur
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



/*
        transactionRequest.setCustomers("");

        ArrayList<TransactionRequest.TransactionModel> transactionModelsArraylist = new ArrayList<>();
        TransactionRequest.TransactionModel transactionModel = new TransactionRequest().new TransactionModel();*/
        RealmResults<CustomerListRealm> customerListRealmModels = realm.where(CustomerListRealm.class).findAll();
        boolean value = false;
        String id = "";
        String customerId = "";
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
            if (edittext_CustName.getText().toString().toLowerCase().matches(finalname.toLowerCase())) {
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
            customerListClass.setName(edittext_CustName.getText().toString());

/*            transactionModel.setId(id);
            transactionModel.setCustomerId(customerId);*/
        } else {/*
            transactionModel.setId("");
            transactionModel.setCustomerId(createId);

*/
            String createId = "C" + Utility.getInstance().getclientRegId(mContext) + String.valueOf(customerListRealmModels.size() + 1);

            customerListClass.setId("");
            customerListClass.setCustomerId(createId);
            customerListClass.setName(edittext_CustName.getText().toString());

        }


        ArrayList<TransactionRequest.CustomerListClass.TransactionsListClass> transactionsListClassArrayList = new ArrayList<>();
        TransactionRequest.CustomerListClass.TransactionsListClass transactionsListClass = new TransactionRequest().new CustomerListClass().new TransactionsListClass();

        transactionsListClass.setAmount(edittext_enterAmount.getText().toString());

        transactionsListClass.setLiter("");
        transactionsListClass.setPurpose("");
        transactionsListClass.setTransactionId(transactionId);
        transactionsListClass.setTransactionType(String.valueOf(selectedFlow));
/*
        transactionModel.setTrunsactionId();
        transactionModel.setName(edittext_enterCustomerName.getText().toString().trim());
        transactionModel.setTransactionType("2");


        transactionModel.setPurpose(edittext_enterpurpose.getText().toString());
        transactionModel.setAmount(edittext_enterAmount.getText().toString());*/
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        Date date = null;
        Date time = null;
        try {
            //  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            //   date = formatter.parse(formatter.format(new Date()));
            date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(currentDate + " " + currentTime);
            // time = new SimpleDateFormat("HH:mm").parse(currentDate + " " + currentTime);

            DateFormat sdf = new SimpleDateFormat("hh:mm");
            time = sdf.parse(currentTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        transactionsListClass.setDate(date);
        transactionsListClass.setTime(currentTime);

        /*transactionModel.setDate(date);
         */
        /*transactionModel.setTime(currentTime);
        transactionModel.setLiter(edittext_enterLitre.getText().toString());
        transactionModelsArraylist.add(transactionModel);
        transactionRequest.setTransaction(transactionModelsArraylist);*/

        transactionsListClassArrayList.add(transactionsListClass);
        customerListClass.setTransactionsList(transactionsListClassArrayList);
        customerListClassArrayList.add(customerListClass);
        transactionRequest.setCustomerList(customerListClassArrayList);
        if (Utility.getInstance().isOnline(mContext)) {
            Utility.getInstance().showProgressDialogue(mContext);
            mPresenter.postTransaction(transactionRequest, 2);
        } else {







            Toast.makeText(mContext, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * this method returns the error message according to error code arrives during the voice recognization
     *
     * @param errorCode
     * @return
     */
    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    /**
     * setting pitch and speed and speaking the text
     *
     * @param s
     * @param text
     */
    private void speak(String s, String text) {
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
    }

    /**
     * popup visibles here when the text is occurs
     *
     * @param text
     */
    private void visiblepopUp(String text) {
        dialog = new Dialog(mContext);
        if (!dialog.isShowing()) {
            dialog.setCancelable(false);
            dialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.layout_popup_flow);
            imageviewCross = dialog.findViewById(R.id.imageviewCross);
            textview_Listening = dialog.findViewById(R.id.textview_Listening);
            button_submit = dialog.findViewById(R.id.button_submit);
            edittext_CustName = dialog.findViewById(R.id.edittext_CustName);
            if (selectedFlow == 3) {
                edittext_CustName.setHint(getString(R.string.enter_purpose));
            }
            edittext_enterAmount = dialog.findViewById(R.id.edittext_enterAmount);
            textview_header = dialog.findViewById(R.id.textview_header);
            textview_header.setText(text);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            imageviewCross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    popUpFlow = 0;
                }
            });

            button_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popUpFlow = 3;
                    if (!TextUtils.isEmpty(edittext_CustName.getText().toString().trim())) {
                        if (!TextUtils.isEmpty(edittext_enterAmount.getText().toString().trim())) {
                                dataSaveAction();
                        } else {
                            edittext_enterAmount.requestFocus();
                            if (selectedFlow == 3) {
                                edittext_enterAmount.setError(getString(R.string.enter_purpose));
                            } else {
                                edittext_enterAmount.setError(getString(R.string.please_enter_amount));
                            }
                            speak(getString(R.string.please_enter_amount), "");
                        }
                    } else {
                        edittext_CustName.requestFocus();
                        edittext_CustName.setError(getString(R.string.please_enter_customer_name));
                        speak(getString(R.string.please_enter_customer_name), "");
                    }
                }
            });

            dialog.show();
        }
    }

    /**
     * stops listeneing the voice
     */
    @Override
    public void onPause() {
        Log.e(LOGPOS, "onPause: Called");
        if (mSpeechRecognizer != null)
            mSpeechRecognizer.stopListening();
        super.onPause();
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        Log.e(LOGPOS, "onAttachFragment: ");
        super.onAttachFragment(childFragment);
    }

    /**
     * stops listeneing the voice
     */
    @Override
    public void onDetach() {
        Log.e(LOGPOS, "onDetach: ");
        if (mSpeechRecognizer != null)
            mSpeechRecognizer.stopListening();
        Log.e(LOGPOS, "onDetach: ");
        super.onDetach();
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        Log.e(LOGPOS, "onAttach: ");
        super.onAttach(activity);
    }

    /**
     * stops listeneing the voice
     */
    @Override
    public void onDestroyView() {
        Log.e(LOGPOS, "onDestroyView: ");
        if (mSpeechRecognizer != null)
            mSpeechRecognizer.stopListening();
        Log.e(LOGPOS, "onDestroyView: ");
        super.onDestroyView();
    }

    /**
     * stops listeneing the voice
     */
    @Override
    public void onStop() {
        Log.e(LOGPOS, "onStop: ");
        if (mSpeechRecognizer != null)
            mSpeechRecognizer.stopListening();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.e(LOGPOS, "onDestroy: ");
        //getFragmentManager().removeOnBackStackChangedListener(this::onDestroy);
        super.onDestroy();
    }

    /**
     * created by developer Ankur
     * this methods calls when fragment visibles
     */
    @Override
    public void onMakeServerCalls() {
        if (mContext == null) {
            this.mContext = ((ActivityDashboard) getContext());
        }
        ((ActivityDashboard) getContext()).setTitleOfScreen(getString(R.string.menu_home));
        ((ActivityDashboard) getContext()).setVisibilityofRamumunimjiVisible(false,1);
        language = Utility.getInstance().getLanguage(mContext);
        setTextTospeech();
        if (language.equals(langaugeCodeEnglish)) {
            voiceRecLanguageKey = voiceEnglishCode;
        } else {
            voiceRecLanguageKey = voiceMarathiCode;
        }
        if (isVisible()) {
            if (Utility.getInstance().isOnline(mContext)) {
                int value = checkPermission(mContext, Manifest.permission.RECORD_AUDIO, RECORD_AUDIO_PERMISSION_REQUEST_CODE);
                if (value == 1) {
                    startListeningTheVoice();
                } else if (value == 2) {
                    displayNeverAskAgainDialog(mContext, getString(R.string.We_need_permission));
                }
            } else {
                Toast.makeText(mContext, getString(R.string.you_are_offline), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void postResponseSuccess(TransactionResponse response, int valueofFlowOnlineOfline) {
        Utility.getInstance().dismissProgress();
        dialog.dismiss();
        popUpFlow = 0;
        speak(getString(R.string.Data_saved), "");




        edittext_CustName.setText("");
        edittext_enterAmount.setText("");
        //edittext_enterpurpose.setText("");
        Toast.makeText(mContext, "Data Save", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postResponseFailure(String message) {
        Utility.getInstance().dismissProgress();
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postExpenseSuccess(ExpenseResponse response, int valueofFlowOnlineOfline) {
        Utility.getInstance().dismissProgress();
        Toast.makeText(mContext, "Data Save", Toast.LENGTH_SHORT).show();
        /*ExpendetureListRealm expendetureListRealm = new ExpendetureListRealm();
        expendetureListRealm.setExpenditureId(response.getData().getExpenditureId());
        expendetureListRealm.setTransactionId(response.getData().getTransactionId());
        expendetureListRealm.setClientId(response.getRegId());
        expendetureListRealm.setPurpose(response.getData().getPurpose());
        expendetureListRealm.setExpenseType("personal");
        expendetureListRealm.setAmount(response.getData().getAmount());
        expendetureListRealm.setExpendDate(response.getData().getExpendDate());
        */realm.beginTransaction();
        //realm.copyToRealm(expendetureListRealm);
        realm.commitTransaction();
        dialog.dismiss();
        popUpFlow = 0;
        speak(getString(R.string.Data_saved), "");
    }

    @Override
    public void postPurchaseResponseSuccess(PostPurchaseResponse body) {

    }

    @Override
    public void postResponseFailuretimeout() {

    }
}
