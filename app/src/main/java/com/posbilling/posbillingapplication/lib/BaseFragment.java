package com.posbilling.posbillingapplication.lib;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.utility.PermissionUtils;
import com.posbilling.posbillingapplication.utility.Utility;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.posbilling.posbillingapplication.utility.Constants.LOGPOS;
import static com.posbilling.posbillingapplication.utility.Constants.RECORD_AUDIO_PERMISSION_REQUEST_CODE;
import static com.posbilling.posbillingapplication.utility.Constants.langaugeCodeEnglish;
import static com.posbilling.posbillingapplication.utility.Constants.languageCodeMarathi;

public abstract class BaseFragment extends Fragment implements BaseView{
    private Unbinder unbinder;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    private boolean mWasViewDestroyed = true;
    private BaseActivity mBasaActivity;
    private APIComponent mApiComponent;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        View returnView = inflater.inflate(getLayout(), container, false);
       // Utility.getInstance().localisation(getActivity(), languageCode);
        String languageCode = Utility.getInstance().getLanguage(getActivity());
        Utility.getInstance().setLanguage(getActivity(), languageCode);
        Utility.getInstance().localisation(getActivity(), languageCode);
        return returnView;
    }

    protected String checkLanguageCode(Context mContext) {
        return Utility.getInstance().getLanguage(mContext);
    }

    public boolean getHasOptionsMenu() {
        return false;
    }

    protected abstract BasePresenter getPresenter();

    protected abstract void setPresenter();

    protected abstract int getLayout();


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
                        if (isVisible()) {
                            micImage.setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_IN);
                            Toast.makeText(mContext, getString(R.string.Voice_recording_starts), Toast.LENGTH_SHORT).show();
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

    @Override
    public APIComponent getAPIComponent() {
        return mApiComponent;
    }
    @Override
    public void onPause() {
        if (getPresenter() != null)
            getPresenter().onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();

        mWasViewDestroyed = true;
    }


    public abstract void onMakeServerCalls();


    @Override
    public void onStart() {
        super.onStart();
        unbinder = ButterKnife.bind(this, getView());
        mApiComponent = getBaseActivity().getAPIComponent();
        if (mWasViewDestroyed) {
            setPresenter();
            mWasViewDestroyed = false;
            onMakeServerCalls();
        }
    }

    public BaseActivity getBaseActivity() {
        if (mBasaActivity == null) {
            if (getAPIComponent() != null)
                mBasaActivity = getAPIComponent().getApp().getCurrentForegroundActivity();
            else
                Log.i(LOGPOS, "getBaseActivity: getApiComponent() is null");
        }
        return mBasaActivity;
    }

    public void setError(String error) {
        Log.e(LOGPOS, "setError: " + error);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mBasaActivity = (BaseActivity) context;
    }


    public int checkPermission(Context context, String permission, int permissonRequestCode) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager
                .PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (PermissionUtils.neverAskAgainSelected(getActivity(), permission)) {
                    //displayNeverAskAgainDialog();
                    return 2;
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{permission},
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
                if (PermissionUtils.neverAskAgainSelected(getActivity(), permission)) {
                    //displayNeverAskAgainDialog();
                    return 2;
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{permission,permissionTwo},
                            permissonRequestCode);
                    return 3;
                }
            }
        } else {
            return 1;
        }
        return 0;
    }


    //We need to send SMS for performing necessary task.
    public void displayNeverAskAgainDialog(Context context, String permissionString) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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




    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (CAMERA_PERMISSION_REQUEST_CODE == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setError("Permission granted successfully");
                Toast.makeText(getContext(), "Permission granted successfully", Toast.LENGTH_LONG).show();
                //cameraIntent();
            } else {
                PermissionUtils.setShouldShowStatus(getContext(), Manifest.permission.CAMERA);
            }
        }else if(GALLERY_PERMISSION_REQUEST_CODE == requestCode){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setError("Permission granted successfully");
                Toast.makeText(getContext(), "Permission granted successfully", Toast.LENGTH_LONG).show();
                //galleryIntent();
            } else {
                PermissionUtils.setShouldShowStatus(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }*/


    public String getRealPathFromURI(Uri contentUri) {
        String result = null;
        String filePath = "";
        String wholeID = null;


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;


            if (isKitKat && DocumentsContract.isDocumentUri(getContext(), contentUri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(contentUri)) {
                    final String docId = DocumentsContract.getDocumentId(contentUri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }

                    // TODO handle non-primary volumes
                }
                // DownloadsProvider
                else if (isDownloadsDocument(contentUri)) {

                    final String id1 = DocumentsContract.getDocumentId(contentUri);
                    final Uri contentUri1 = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id1));

                    return getDataColumn(getContext(), contentUri1, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(contentUri)) {
                    final String docId = DocumentsContract.getDocumentId(contentUri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri1 = null;
                    if ("image".equals(type)) {
                        contentUri1 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri1 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri1 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };

                    return getDataColumn(getContext(), contentUri1, selection, selectionArgs);
                } else {

                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(contentUri.getScheme())) {

                // Return the remote address
                if (isGooglePhotosUri(contentUri))
                    return contentUri.getLastPathSegment();

                return getDataColumn(getContext(), contentUri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(contentUri.getScheme())) {
                return contentUri.getPath();
            } else {
                wholeID = DocumentsContract.getDocumentId(contentUri);


                // Split at colon, use second item in the array
                String[] idSplit = wholeID.split(":");
                String id = "";
                if (idSplit.length > 2) {
                    id = wholeID.split(":")[1];
                }


                String[] column = {MediaStore.Images.Media.DATA};

                // where id is equal to
                String sel = MediaStore.Images.Media._ID + "=?";

                Cursor cursor = getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{id}, null);

                int columnIndex = cursor.getColumnIndex(column[0]);

                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(columnIndex);
                }
                cursor.close();

            }

        } else if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB
                && (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT)) {

            String[] proj = {MediaStore.Images.Media.DATA};
            CursorLoader cursorLoader = new CursorLoader(
                    getContext(),
                    contentUri, proj, null, null, null);
            Cursor cursor = cursorLoader.loadInBackground();

            if (cursor != null) {
                int column_index =
                        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                filePath = cursor.getString(column_index);
            }
        }
        return filePath;
    }


    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        String filePath = "";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                filePath = cursor.getString(index);

            }
        } catch (IllegalArgumentException e) {

        } finally {
            if (cursor != null)
                cursor.close();
        }
        return filePath;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
