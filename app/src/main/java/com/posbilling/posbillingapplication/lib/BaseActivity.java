package com.posbilling.posbillingapplication.lib;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.posbilling.posbillingapplication.BuildConfig;
import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.utility.PermissionUtils;
import com.posbilling.posbillingapplication.utility.Utility;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.posbilling.posbillingapplication.utility.Constants.LOGPOS;
import static com.posbilling.posbillingapplication.utility.Constants.RECORD_AUDIO_PERMISSION_REQUEST_CODE;
import static com.posbilling.posbillingapplication.utility.Constants.langaugeCodeEnglish;
import static com.posbilling.posbillingapplication.utility.Constants.languageCodeMarathi;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    private Unbinder unbinder;
    private APIComponent apiComponent;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayout() != 0) {
            setContentView(getLayout());
            unbinder = ButterKnife.bind(this);
        } else
            showDebugToast("Get Layout Not Set");
        apiComponent = ((AppPOS) getApplication()).getAPIComponent();
        setPresenter();
        String languageCode = Utility.getInstance().getLanguage(this);
        Utility.getInstance().setLanguage(this, languageCode);
        Utility.getInstance().localisation(this, languageCode);
    }

    @Override
    public APIComponent getAPIComponent() {
        return apiComponent;
    }

    @Override
    protected void onPause() {
        if (getPresenter() != null && apiComponent != null) {
            getPresenter().onPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {


        if (getPresenter() != null)
            getPresenter().onDestroy();


        if (unbinder != null)
            unbinder.unbind();

        super.onDestroy();
    }

    public void showDebugToast(String message) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            Log.i("DEBUG", message);
        }
    }

    public void showError(View view , String message){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }


    protected abstract BasePresenter getPresenter();

    public abstract @LayoutRes
    int getLayout();

    protected abstract void setPresenter();

    public int checkPermission(Context context, String permission, int permissonRequestCode) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager
                .PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (PermissionUtils.neverAskAgainSelected(this, permission)) {
                    //displayNeverAskAgainDialog();
                    return 2;
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{permission},
                            permissonRequestCode);
                    return 3;
                }
            }
        } else {
            return 1;
        }
        return 0;
    }


    public int checkTwoPermission(Context context, String permission,String permissionTwo, int permissonRequestCode) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager
                .PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (PermissionUtils.neverAskAgainSelected(this, permission)) {
                    //displayNeverAskAgainDialog();
                    return 2;
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{permission,permissionTwo},
                            permissonRequestCode);
                    return 3;
                }
            }
        } else {
            return 1;
        }
        return 0;
    }


    public void setError(String error) {
        Log.e(LOGPOS, "setError: " + error);
    }

    public void speechToTextFunc(Context mContext, EditText editText, int dataType, ImageView micImage) {
        if (Utility.getInstance().isOnline(mContext)) {
            int value = checkPermission(mContext, Manifest.permission.RECORD_AUDIO, RECORD_AUDIO_PERMISSION_REQUEST_CODE);
            if (value == 1) {
                mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(mContext);
                mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                String languageCode = Utility.getInstance().getLanguage(mContext);
                if (languageCode.matches(langaugeCodeEnglish)) {
                    mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                } else if (languageCode.matches(languageCodeMarathi)) {
                    mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "mr_IN");
                } else {
                    mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                    setError("BaseFragmnet : speechToTextFunc Has problem");
                }

                mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, mContext.getPackageName());
                mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
                    @Override
                    public void onReadyForSpeech(Bundle params) {
                            micImage.setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_IN);
                            Toast.makeText(mContext, getString(R.string.Voice_recording_starts), Toast.LENGTH_SHORT).show();

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
                            micImage.setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_IN);

                    }

                    @Override
                    public void onError(int error) {

                    }

                    @Override
                    public void onResults(Bundle results) {
                            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                            if (dataType == 1) {
                                editText.setText(matches.get(0));
                            } else {
                                for (int i = 0; i < matches.size(); i++) {
                                    if (matches.get(i).matches("^[0-9]*$")) {
                                        editText.setText(matches.get(i));
                                        break;
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
                mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
            } else if (value == 2) {
                displayNeverAskAgainDialog(mContext, getString(R.string.We_need_permission_Audio));
            }
        } else {
            View rootView = ((Activity) mContext).getWindow().getDecorView().findViewById(android.R.id.content);
            Utility.getInstance().showSnackbar(rootView, getString(R.string.please_check_internet));
        }
    }


    //We need to send SMS for performing necessary task.
    public void displayNeverAskAgainDialog(Context context, String permissionString) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(permissionString + " " + getString(R.string.Please_permit_the_permission_through) + " "
                + "" + getString(R.string.Settings_screen) + "\n\nSelect Permissions -> Enable permission");
        builder.setCancelable(false);
        builder.setPositiveButton(getString(R.string.Permit_Manually), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), null);
        builder.show();
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void localisation() {
        String language_key = "";
//        if (Utility.getInstance().getPreference(context, Constants.getInstance().PREF_UI_FLIP).equalsIgnoreCase(Constants.getInstance().FLAG_TRUE))
        /*if (apiComponent != null) {
            if (apiComponent.getAppData() != null) {
                if (apiComponent.getAppData().getLanguage() != null) {
                    language_key = apiComponent.getAppData().getLanguage();
                }
            }
        } else {*/
            String lang = Locale.getDefault().getDisplayLanguage();

            if (lang.equalsIgnoreCase("Arabic")) {
                language_key = "ar";
            } else {
                language_key = "en";
            }

          /*  language_key = "ar";
        else
            language_key = "en";*/

        Locale locale = new Locale(language_key);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config,
                getResources().getDisplayMetrics());
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (getPresenter() != null)
            getPresenter().onStop();

    }
}
