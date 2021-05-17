package com.posbilling.posbillingapplication.activity.dashboard.ui.setting;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.dashboard.ActivityDashboard;
import com.posbilling.posbillingapplication.activity.dashboard.ui.addcustomer.StateListAdapter;
import com.posbilling.posbillingapplication.activity.editcustomer.ActivityEditCustomer;
import com.posbilling.posbillingapplication.activity.homeactivitytwo.HomeActivityTwo;
import com.posbilling.posbillingapplication.activity.splash.ActivitySplash;
import com.posbilling.posbillingapplication.interfaceclick.OnStateListClick;
import com.posbilling.posbillingapplication.lib.BaseFragment;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.model.StateModel;
import com.posbilling.posbillingapplication.model.request.UpdateProfile;
import com.posbilling.posbillingapplication.model.response.BuisnessIdListResponse;
import com.posbilling.posbillingapplication.model.response.ClientUpdateResponse;
import com.posbilling.posbillingapplication.retrofit.CustomRetroRequest;
import com.posbilling.posbillingapplication.utility.RealPathUtil;
import com.posbilling.posbillingapplication.utility.Utility;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;
import static com.posbilling.posbillingapplication.utility.Constants.CAMERA_IMAGE_REQUEST_CODE;
import static com.posbilling.posbillingapplication.utility.Constants.CAMERA_PERMISSION_REQUEST_CODE;
import static com.posbilling.posbillingapplication.utility.Constants.CLIENTREGID;
import static com.posbilling.posbillingapplication.utility.Constants.GALLERY_IMAGE_REQUEST_CODE;
import static com.posbilling.posbillingapplication.utility.Constants.GALLERY_PERMISSION_REQUEST_CODE;
import static com.posbilling.posbillingapplication.utility.Constants.langaugeCodeEnglish;
import static com.posbilling.posbillingapplication.utility.Constants.languageCodeMarathi;
import static com.posbilling.posbillingapplication.utility.DataBuisnessName.buisnessId;
import static com.posbilling.posbillingapplication.utility.DataBuisnessName.buisnessNamesEnglish;
import static com.posbilling.posbillingapplication.utility.DataBuisnessName.buisnessNamesMarathi;
import static com.posbilling.posbillingapplication.utility.DataState.stateId;
import static com.posbilling.posbillingapplication.utility.DataState.stateNamesEnglish;
import static com.posbilling.posbillingapplication.utility.DataState.stateNamesMarathi;

/**
 * Created by Android PC (Ankur) on 24,February,2020
 */
public class FragmentSetting extends BaseFragment implements SettingContractor.View, OnStateListClick {

    private String languageCode = "";
    private Context mContext;
    private File profileImageFile;
    private Uri imageUri;
    private Uri outputUri = null;
    private Uri imgUri;
    private final int REQUEST_IMAGE_CAPTURE = 101;
    String realPathImage = " ";
    private Uri picUri;
    private SettingContractor.Presenter mPresenter;
    private Bitmap myBitmap;
    private Activity activity;
    private ArrayList<BuisnessIdListResponse.Data> buisnessIdArrayList = new ArrayList<>();
    private OnStateListClick onStateListClick = this;
    private ArrayList<StateModel> stateList = new ArrayList<>();
    private String base64ImageString = "";
    private String imageName = "";
    private Dialog dialogBuisness;
    private String buisnessIdString = "1";

    @BindView(R.id.switch_notification)
    SwitchCompat switch_notification;
    @BindView(R.id.textView_English)
    TextView textView_English;
    @BindView(R.id.imageview_profile)
    CircleImageView imageview_profile;
    @BindView(R.id.textview_Marathi)
    TextView textview_Marathi;
    @BindView(R.id.edittext_enterOwnerName)
    EditText edittext_enterOwnerName;
    @BindView(R.id.edittext_enterAddress)
    EditText edittext_enterAddress;
    @BindView(R.id.edittext_enterVillageName)
    EditText edittext_enterVillageName;
    @BindView(R.id.edittext_enterBuisness)
    EditText edittext_enterBuisness;
    @BindView(R.id.edittext_enterDistrictName)
    EditText edittext_enterDistrictName;
    @BindView(R.id.imageview_mic_enter_name)
    ImageView imageview_mic_enter_name;
    @BindView(R.id.imageview_mic_address)
    ImageView imageview_mic_address;
    @BindView(R.id.imageview_mic_village_name)
    ImageView imageview_mic_village_name;
    @BindView(R.id.imageview_mic_taluka_name)
    ImageView imageview_mic_taluka_name;
    @BindView(R.id.imageview_mic_buisness_name)
    ImageView imageview_mic_buisness_name;
    @BindView(R.id.imageview_mic_district_name)
    ImageView imageview_mic_district_name;
    @BindView(R.id.edittext_enterMobileNumber)
    EditText edittext_enterMobileNumber;
    @BindView(R.id.edittext_ShopId)
    EditText edittext_ShopId;
    @BindView(R.id.edittext_enterTalukaName)
    EditText edittext_enterTalukaName;
    @BindView(R.id.edittext_buisnessType)
    EditText edittext_buisnessType;


    @OnClick(R.id.imageview_mic_enter_name)
    void imageview_mic_enter_name() {
        speechToTextFunc(mContext, edittext_enterOwnerName, 1, imageview_mic_enter_name);
    }

    @OnClick(R.id.imageview_mic_address)
    void imageview_mic_address() {
        speechToTextFunc(mContext, edittext_enterAddress, 1, imageview_mic_address);
    }

    @OnClick(R.id.imageview_mic_village_name)
    void imageview_mic_village_name() {
        speechToTextFunc(mContext, edittext_enterVillageName, 1, imageview_mic_village_name);
    }

    @OnClick(R.id.imageview_mic_taluka_name)
    void imageview_mic_taluka_name() {
        speechToTextFunc(mContext, edittext_enterTalukaName, 1, imageview_mic_taluka_name);
    }

    @OnClick(R.id.imageview_mic_buisness_name)
    void imageview_mic_buisness_name() {
        speechToTextFunc(mContext, edittext_enterBuisness, 1, imageview_mic_buisness_name);
    }

    @OnClick(R.id.imageview_mic_district_name)
    void imageview_mic_district_name() {
        speechToTextFunc(mContext, edittext_enterDistrictName, 1, imageview_mic_district_name);
    }


    @OnClick(R.id.edittext_buisnessType)
    void edittext_buisnessType(){
        if (Utility.getInstance().isOnline(mContext)) {
            Utility.getInstance().showProgressDialogue(mContext);
            mPresenter.getBuisnessIDList();
        }else {
            Toast.makeText(mContext, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.imageview_edit_pic)
    void imageview_edit_pic() {
        int value = checkTwoPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, GALLERY_PERMISSION_REQUEST_CODE);
        int valueGallery = checkPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE, GALLERY_PERMISSION_REQUEST_CODE);
        int valueExternal = checkPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE, GALLERY_PERMISSION_REQUEST_CODE);
        int valueCamera = checkPermission(mContext, Manifest.permission.CAMERA, CAMERA_PERMISSION_REQUEST_CODE);
        if (value == 1 && valueGallery == 1 && valueCamera == 1) {
            startActivityForResult(getPickImageChooserIntent(), 200);
            /*Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), GALLERY_IMAGE_REQUEST_CODE);*/
        } else if (value == 2) {
            displayNeverAskAgainDialog(mContext, getString(R.string.We_need_permission));
        }
        //takeProfilePicturefromFragment(mContext);
    }

    @OnClick(R.id.relativelayout_saveButton)
    void relativelayout_saveButtonClick() {
//        Intent intent=new Intent(getContext(), ActivityLogin.class);
//        startActivity(intent);
        if (Utility.getInstance().isOnline(mContext)) {
            boolean value = validae();
            if (value) {
                SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(mContext);
                UpdateProfile updateProfile = new UpdateProfile();
                updateProfile.setRegId(sharedPreferences.getString(CLIENTREGID, ""));
                updateProfile.setFirstname(edittext_enterOwnerName.getText().toString());
                if (!TextUtils.isEmpty(sharedPreferences.getString("ImagePath", ""))) {
                    Glide.with(mContext)
                            .load(new CustomRetroRequest().imageURL + sharedPreferences.getString("ImagePath", ""))
                            .into(imageview_profile);
                }
                updateProfile.setMidename("");
                updateProfile.setLastname("");
                updateProfile.setBusinessName(edittext_enterBuisness.getText().toString());
                updateProfile.setAddress(edittext_enterAddress.getText().toString());
                imageName = "C" + sharedPreferences.getString(CLIENTREGID, "") + new Date().getTime();
                updateProfile.setImagePath(base64ImageString);
                /*if (base64ImageString!=null && !TextUtils.isEmpty(base64ImageString)) {
                 */
                updateProfile.setImagename(imageName);
                /*}else {
                    updateProfile.setImagename("");
                }*/

                updateProfile.setBusinessId(buisnessIdString);
                updateProfile.setVillage(edittext_enterVillageName.getText().toString());
                updateProfile.setDistrict(edittext_enterDistrictName.getText().toString());
                updateProfile.setTaluka(edittext_enterTalukaName.getText().toString());


                Utility.getInstance().showProgressDialogue(mContext);
                mPresenter.postProfileData(updateProfile);
            }
        } else {
            View rootView = activity.getWindow().getDecorView().getRootView();
            Utility.getInstance().showSnackbar(rootView, getString(R.string.you_are_offline));
        }
    }

    private boolean validae() {
        if (TextUtils.isEmpty(edittext_enterOwnerName.getText().toString())) {
            edittext_enterOwnerName.setError(getString(R.string.enter_owner_name));
            edittext_enterOwnerName.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edittext_enterAddress.getText().toString())) {
            edittext_enterAddress.setError(getString(R.string.enter_address));
            edittext_enterAddress.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edittext_enterBuisness.getText().toString())) {
            edittext_enterBuisness.setError(getString(R.string.buisness_name));
            edittext_enterBuisness.requestFocus();
            return false;
        }

        return true;
    }

    @OnClick(R.id.switch_notification)
    void switchLanguageClick() {
        /*switch_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {*/
        if (switch_notification.isChecked()) {
            setOnMarathi(textView_English, textview_Marathi);
            languageCode = languageCodeMarathi;
        } else {
            setOnEnglish(textView_English, textview_Marathi);
            languageCode = langaugeCodeEnglish;
        }


        if (languageCode.matches(Utility.getInstance().getLanguage(mContext))) {
           // Toast.makeText(mContext, "Language not changed", Toast.LENGTH_SHORT).show();
        } else {
            Utility.getInstance().localisation(mContext, languageCode);
            Utility.getInstance().setLanguage(mContext, languageCode);
           // ((ActivityDashboard) getContext()).closeApplication();
            startActivity(new Intent(mContext, ActivitySplash.class));
        }


    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void setPresenter() {
        mPresenter = new SettingPresenter(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_setting;
    }

    @Override
    public void onMakeServerCalls() {
        if (isAdded() || isVisible()) {
            activity = getActivity();
            if (Utility.getInstance().isOnline(mContext)) {

                getSharedPrefData();


                imageview_profile.setOutlineProvider(new ViewOutlineProvider() {
                    @Override
                    public void getOutline(View view, Outline outline) {
                        outline.setOval(0, 0, view.getWidth(), view.getHeight());
                    }
                });
                imageview_profile.setClipToOutline(true);

          //      mPresenter.getBuisnessIDList();
            }else {
                Toast.makeText(mContext, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getSharedPrefData() {
        SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(mContext);
        String fistname = sharedPreferences.getString("FirstName", "");
        String lastname = sharedPreferences.getString("LastName", "");
        String finalName = fistname + " " + lastname;
        buisnessIdString = sharedPreferences.getString("BusinessId", "");
        edittext_buisnessType.setText(sharedPreferences.getString("BusinessTypeName", ""));
        languageCode = Utility.getInstance().getLanguage(mContext);
        for (int i = 0; i < buisnessIdArrayList.size(); i++) {
            if (buisnessIdString.matches(buisnessIdArrayList.get(i).getID())) {
                if (languageCode.equalsIgnoreCase(languageCodeMarathi)) {
                    edittext_buisnessType.setText(buisnessIdArrayList.get(i).getTypename());
                } else {
                    edittext_buisnessType.setText(buisnessIdArrayList.get(i).getTypename());
                }
            }
        }


        edittext_enterOwnerName.setText(finalName);
        edittext_enterAddress.setText(sharedPreferences.getString("Address", ""));
        edittext_enterBuisness.setText(sharedPreferences.getString("ShopName", ""));
        String Village = sharedPreferences.getString("Village", "");
        String Taluka = sharedPreferences.getString("Taluka", "");
        String District = sharedPreferences.getString("District", "");
        edittext_enterVillageName.setText(Village);
        edittext_enterTalukaName.setText(Taluka);
        edittext_enterDistrictName.setText(District);
        String address = "";

    /*    address = Village + " " + Taluka + " " + District;

        if (!TextUtils.isEmpty(address.trim())) {
            edittext_enterAddress.setText(address.trim());
        }*/

        if (!TextUtils.isEmpty(sharedPreferences.getString("ImagePath", ""))) {
            Glide.with(mContext).load(new CustomRetroRequest().imageURL + sharedPreferences.getString("ImagePath", "")).into(imageview_profile);
        }

        edittext_enterMobileNumber.setText(sharedPreferences.getString("ContactNumber", ""));
        edittext_ShopId.setText("C" + sharedPreferences.getString(CLIENTREGID, ""));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mContext == null) {
            mContext = ((HomeActivityTwo) getContext());
        }
//        ((HomeActivityTwo) getContext()).setTitleOfScreen(getString(R.string.menu_setting));
//        ((HomeActivityTwo) getContext()).setVisibilityofRamumunimjiVisible(false,1);

    }

    //Setting English language selected UI
    private void setOnEnglish(TextView textView_english, TextView textview_marathi) {
        textView_english.setTextColor(getContext().getResources().getColor(R.color.colorGreen));
        textview_marathi.setTextColor(getContext().getResources().getColor(R.color.black));
        switch_notification.setChecked(false);
        //imageView.setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_IN);
    }

    //Setting Marathi language selected UI
    private void setOnMarathi(TextView textView_english, TextView textview_marathi) {
        textview_marathi.setTextColor(getContext().getResources().getColor(R.color.colorGreen));
        textView_english.setTextColor(getContext().getResources().getColor(R.color.black));
        switch_notification.setChecked(true);
    }


    //camera/gallery selector dialogue
    public void takeProfilePicturefromFragment(Context mContext) {
        final CharSequence[] items = {getString(R.string.camera), getString(R.string.gallery),
                getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.camera))) {
//                    cameraIntent();
                    //pickImageByCamera();

                    startActivityForResult(getPickImageChooserIntent(), 200);

                    dialog.dismiss();
                } else if (items[item].equals(getString(R.string.gallery))) {
                    galleryIntent();
                } else if (items[item].equals(getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    public void pickImageByCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
        imgUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }


    //Capturing camera image
    private void cameraIntent() {
        int value = checkTwoPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, GALLERY_PERMISSION_REQUEST_CODE);
        //int value2 =  checkPermission(mContext, Manifest.permission.CAMERA, CAMERA_PERMISSION_REQUEST_CODE);
        if (value == 1 /*&& value2 == 1*/) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_IMAGE_REQUEST_CODE);
        } else if (value == 2 /*|| value2 == 1*/) {
            displayNeverAskAgainDialog(mContext, getString(R.string.We_need_permission));
        }
    }

    ///taking gallery image
    private void galleryIntent() {
        int value = checkTwoPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, GALLERY_PERMISSION_REQUEST_CODE);
        //   int value2 =  checkPermission(mContext, Manifest.permission.CAMERA, CAMERA_PERMISSION_REQUEST_CODE);
        if (value == 1 /*&& value2 == 1*/) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), GALLERY_IMAGE_REQUEST_CODE);
        } else if (value == 2 /*|| value2 == 2*/) {
            displayNeverAskAgainDialog(mContext, getString(R.string.We_need_permission));
        }
    }


    ///Checking languageCode and updating UI
    private void checkingLanguageCode() {
        languageCode = Utility.getInstance().getLanguage(mContext);
        if (languageCode.matches(langaugeCodeEnglish)) {
            setOnEnglish(textView_English, textview_Marathi);
        } else if (languageCode.matches(languageCodeMarathi)) {
            setOnMarathi(textView_English, textview_Marathi);
        } else {
            setError("FragmnetSetting : Something went wrong in checking languageCode");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Utility.getInstance().isOnline(mContext)) {
            checkingLanguageCode();
        }else {
            Toast.makeText(mContext, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private void initBuisnessDialogue() {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                    // showdialogue = true;
                    dialogBuisness = new Dialog(mContext);
                    if (!dialogBuisness.isShowing()) {
                        stateList.clear();

                        for (int i = 0; i < buisnessIdArrayList.size(); i++) {
                            StateModel stateModel = new StateModel();
                            stateModel.setStateNameEnglish(buisnessIdArrayList.get(i).getTypename());
                            stateModel.setStateNameMarathi(buisnessIdArrayList.get(i).getTypename());
                            stateModel.setStateId(buisnessIdArrayList.get(i).getID());
                            stateList.add(stateModel);
                        }
                        dialogBuisness.getWindow().addFlags(Window.FEATURE_NO_TITLE);
                        dialogBuisness.setCancelable(true);
                        dialogBuisness.setContentView(R.layout.custom_state_dialogue);
                        EditText edt_search_state = dialogBuisness.findViewById(R.id.edt_search_state);
                        edt_search_state.setVisibility(View.GONE);
                        TextView textview_header = dialogBuisness.findViewById(R.id.textview_header);

                        textview_header.setText(getString(R.string.Select_buisness_type));
                        RecyclerView recyclerView = dialogBuisness.findViewById(R.id.recyclerview_contact);
                        StateListAdapter stateListAdapter = new StateListAdapter(mContext, stateList, onStateListClick, languageCode);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(stateListAdapter);
                        dialogBuisness.show();
                    }

    }


    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? outputUri : data.getData();
    }


    ////Camera and gallery result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap;
        if (resultCode == Activity.RESULT_OK) {

            if (getPickImageResultUri(data) != null) {
                picUri = getPickImageResultUri(data);

                imageview_profile.setImageURI(picUri);
                Glide.with(mContext).load(picUri.toString()).into(imageview_profile);

                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), picUri);
                    // myBitmap = rotateImageIfRequired(myBitmap, picUri);
                    myBitmap = getResizedBitmap(myBitmap, 300);

                    myBitmap = getRotateBitmapImage(myBitmap,picUri.getPath());
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    myBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    base64ImageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    imageview_profile.setImageBitmap(myBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                bitmap = (Bitmap) data.getExtras().get("data");
                myBitmap = bitmap;
            }

        }
    }


    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public Bitmap getRotateBitmapImage(Bitmap image,String photoPath){

        ExifInterface ei = null;
        try {
            ei = new ExifInterface(photoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = 0;
        try {
        orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);
        }catch (Exception ae){
            ae.printStackTrace();
        }

        Bitmap rotatedBitmap = null;
        switch(orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(image, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(image, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(image, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = image;
        }
        return rotatedBitmap;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }


    public Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<Intent>();
        PackageManager packageManager = mContext.getPackageManager();

        // collect all camera intents
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }


    /**
     * Get URI to image received from capture by camera.
     */
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = mContext.getExternalCacheDir();
        String child = "profile" + new Date().getTime() + ".png";
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), child));
            this.outputUri = outputFileUri;
        }
        return outputFileUri;
    }


    private void performCropLib(Uri uri) {
        Log.i("Tag", "performCropLib: URI = " + uri);
        CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .setAllowRotation(true)
                .setAutoZoomEnabled(true)
                .setAllowFlipping(true)
                .start(getContext(), this);
    }


    //--------------------------------------------- Image from camera -----------------------------------------//

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        profileImageFile = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            //Create file and put image into file
            profileImageFile.createNewFile();
            fo = new FileOutputStream(profileImageFile);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!profileImageFile.isFile()) {
            return;
        } else {
            // uploadFile(profileImageFile);
        }
        Uri cameraImageUri = getImageUri(getContext(), thumbnail);
        saveImage(cameraImageUri);
        Glide.with(getContext()).load(cameraImageUri.toString()).into(imageview_profile);
        //createUploadData();
    }

    //--------------------------------------------- On Select from gallery -----------------------------------------//
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                Uri selectedImage = data.getData();
                String path = getRealPathFromURI(selectedImage);
                if (!TextUtils.isEmpty(path)) {
                    profileImageFile = new File(path);
                    //uploadFile(profileImageFile);
                } else {
                    bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    getImageData(bm);
                }
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Uri galleryImageUri = getImageUri(getContext(), bm);
        saveImage(galleryImageUri);
        Glide.with(mContext).load(galleryImageUri.toString()).into(imageview_profile);
    }


    //---------------------------------------------- Method to get uri of image --------------------------------------------//

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    //---------------------------------------------- save imag uri --------------------------------------------//
    private void saveImage(Uri imageUri) {
        this.imageUri = imageUri;
        String userImageUri = imageUri.toString();
        //sharedPreferences.putProfileUrl(userImageUri);
    }

    //-------------------------- Create image file for edit post ------------------------------//
    private void getImageData(Bitmap bitmap) {

        if (bitmap != null) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

            profileImageFile = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");
            FileOutputStream fo;
            try {
                profileImageFile.createNewFile();
                fo = new FileOutputStream(profileImageFile);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!profileImageFile.isFile()) {
                return;
            } else {
                //uploadFile(profileImageFile);
            }
        }
    }


    @Override
    public void profileUpdateResponse(ClientUpdateResponse body) {
        Toast.makeText(mContext, getString(R.string.data_save), Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(mContext);
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        editor.putString("FirstName", edittext_enterOwnerName.getText().toString());
        editor.putString("MiddleName", "");
        editor.putString("LastName", "");
        editor.putString("Village", edittext_enterVillageName.getText().toString());
        editor.putString("Taluka", edittext_enterTalukaName.getText().toString());
        editor.putString("ShopName", edittext_enterBuisness.getText().toString());
        editor.putString("ImagePath", body.getImagePath());
        editor.putString("District", edittext_enterDistrictName.getText().toString());
        editor.putString("Address",edittext_enterAddress.getText().toString());
        editor.putString("BusinessTypeName",edittext_buisnessType.getText().toString());
        editor.putString("BusinessId",buisnessIdString);
        editor.commit();
        Glide.with(mContext)
                .load(new CustomRetroRequest().imageURL + body.getImagePath())
                .into(imageview_profile);
        /*((ActivityDashboard) getContext()).updateProfile(body.getImagePath(), edittext_enterBuisness.getText().toString());*/

        Utility.getInstance().dismissProgress();
/*        ((ActivityDashboard) getContext()).closeApplication();
        startActivity(new Intent(mContext, ActivitySplash.class));*/
    }

    @Override
    public void profileUpdateFailure(String message) {
        Utility.getInstance().dismissProgress();
        Toast.makeText(mContext, getString(R.string.something_went_wrong_please_try_again), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void buisnessIDListSuccess(BuisnessIdListResponse body) {
        buisnessIdArrayList.clear();
        buisnessIdArrayList.addAll(body.getDataArrayList());
        initBuisnessDialogue();
        Utility.getInstance().dismissProgress();
    }

    @Override
    public void onStateListClick(View view, int position) {
        if (languageCode.equalsIgnoreCase(languageCodeMarathi)) {
            edittext_buisnessType.setText(stateList.get(position).getStateNameMarathi());
            buisnessIdString = stateList.get(position).getStateId();
        } else {
            edittext_buisnessType.setText(stateList.get(position).getStateNameEnglish());
            buisnessIdString = stateList.get(position).getStateId();
        }
        dialogBuisness.dismiss();
    }
}
