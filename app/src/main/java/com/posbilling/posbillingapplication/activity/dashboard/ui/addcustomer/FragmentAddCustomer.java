package com.posbilling.posbillingapplication.activity.dashboard.ui.addcustomer;

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
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.dashboard.ActivityDashboard;
import com.posbilling.posbillingapplication.interfaceclick.OnStateListClick;
import com.posbilling.posbillingapplication.lib.APIComponent;
import com.posbilling.posbillingapplication.lib.BaseFragment;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.model.StateModel;
import com.posbilling.posbillingapplication.model.realmcontoller.RealmController;
import com.posbilling.posbillingapplication.model.realmmodel.CustomerListRealm;
import com.posbilling.posbillingapplication.model.request.AddCustomerParams;
import com.posbilling.posbillingapplication.model.request.AddCustomerParams.Essential;
import com.posbilling.posbillingapplication.model.response.AddCustomerResponse;
import com.posbilling.posbillingapplication.utility.RealPathUtil;
import com.posbilling.posbillingapplication.utility.Utility;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.posbilling.posbillingapplication.utility.Constants.CAMERA_IMAGE_REQUEST_CODE;
import static com.posbilling.posbillingapplication.utility.Constants.CAMERA_PERMISSION_REQUEST_CODE;
import static com.posbilling.posbillingapplication.utility.Constants.GALLERY_IMAGE_REQUEST_CODE;
import static com.posbilling.posbillingapplication.utility.Constants.GALLERY_PERMISSION_REQUEST_CODE;
import static com.posbilling.posbillingapplication.utility.Constants.LASTCUSTOMERID;
import static com.posbilling.posbillingapplication.utility.Constants.langaugeCodeEnglish;
import static com.posbilling.posbillingapplication.utility.Constants.languageCodeMarathi;
import static com.posbilling.posbillingapplication.utility.DataState.stateId;
import static com.posbilling.posbillingapplication.utility.DataState.stateNamesEnglish;
import static com.posbilling.posbillingapplication.utility.DataState.stateNamesMarathi;

/**
 * Created by Ankur Shinde on 26,February,2020
 */

public class FragmentAddCustomer extends BaseFragment implements OnStateListClick, AddCustomerContractor.View {

    private Context mContext;
    private File profileImageFile;
    private String profileImageFilePath;
    private Uri imageUri;
    private Dialog dialogState;
    private EditText edt_search_state;
    private ArrayList<StateModel> stateList = new ArrayList<>();
    private ArrayList<StateModel> secondStateList = new ArrayList<>();
    private ArrayList<StateModel> filteredStateList = new ArrayList<>();
    private StateListAdapter stateListAdapter;
    private OnStateListClick onStateListClick = this;
    private String languageCode = "";
    private boolean showdialogue = false;
    private String language = "en";
    private AddCustomerParams addCustomerParams;
    private AddCustomerContractor.Presenter mPresenter;
    private String stateIdString = "14";
    private Uri imgUri;
    private Realm realm;
    private File file;
    private Uri outputUri = null;
    private Uri picUri;
    private Bitmap myBitmap;
    private String base64ImageString = "";
    private boolean checkBoxValue = false;


    @BindView(R.id.imageview_profile)
    CircleImageView imageview_profile;
    @BindView(R.id.edittext_enterCustomerName)
    EditText edittext_enterCustomerName;
    @BindView(R.id.edittext_enterVillageName)
    EditText edittext_enterVillageName;
    @BindView(R.id.edittext_enterTalukaName)
    EditText edittext_enterTalukaName;
    @BindView(R.id.edittext_enterMobileNumber)
    EditText edittext_enterMobileNumber;
    @BindView(R.id.edittext_selectStateName)
    EditText edittext_selectStateName;
    @BindView(R.id.edittext_enterDistrictName)
    EditText edittext_enterDistrictName;
    @BindView(R.id.imageview_mic_enter_name)
    ImageView imageview_mic_enter_name;
    @BindView(R.id.imageview_mic_mobile_number)
    ImageView imageview_mic_mobile_number;
    @BindView(R.id.imageview_mic_village_name)
    ImageView imageview_mic_village_name;
    @BindView(R.id.imageview_mic_taluka_name)
    ImageView imageview_mic_taluka_name;
    @BindView(R.id.imageview_mic_district_name)
    ImageView imageview_mic_district_name;
    @BindView(R.id.checkboxPaymentFlag)
    MaterialCheckBox checkboxPaymentFlag;

    @OnClick(R.id.imageview_mic_mobile_number)
    void imageview_mic_mobile_number() {
        edittext_enterMobileNumber.requestFocus();
        speechToTextFunc(mContext, edittext_enterMobileNumber, 2, imageview_mic_mobile_number);
    }

    @OnClick(R.id.imageview_mic_enter_name)
    void imageview_mic_enter_name() {
        imageview_mic_enter_name.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
        speechToTextFunc(mContext, edittext_enterCustomerName, 1, imageview_mic_enter_name);
        edittext_enterCustomerName.requestFocus();
    }

    @OnClick(R.id.imageview_mic_village_name)
    void imageview_mic_village_name() {
        edittext_enterVillageName.requestFocus();
        speechToTextFunc(mContext, edittext_enterVillageName, 1, imageview_mic_village_name);
    }

    @OnClick(R.id.imageview_mic_taluka_name)
    void imageview_mic_taluka_name() {
        edittext_enterTalukaName.requestFocus();
        speechToTextFunc(mContext, edittext_enterTalukaName, 1, imageview_mic_taluka_name);
    }

    @OnClick(R.id.imageview_mic_district_name)
    void imageview_mic_district_name() {
        edittext_enterDistrictName.requestFocus();
        speechToTextFunc(mContext, edittext_enterDistrictName, 1, imageview_mic_district_name);
    }

    @OnClick(R.id.imageview_edit_pic)
    void imageview_edit_pic() {
        int value = checkTwoPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, GALLERY_PERMISSION_REQUEST_CODE);
        int valueGallery = checkPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE, GALLERY_PERMISSION_REQUEST_CODE);
        int valueCamera = checkPermission(mContext, Manifest.permission.CAMERA, CAMERA_PERMISSION_REQUEST_CODE);
        if (value == 1 && valueGallery == 1 && valueCamera == 1) {
            startActivityForResult(getPickImageChooserIntent(), 200);
        } else if (value == 2) {
            displayNeverAskAgainDialog(mContext, getString(R.string.We_need_permission));
        }
        //takeProfilePicturefromFragment(mContext);
    }

    @OnClick(R.id.relativelayout_submitButton)
    void relativelayout_submitButton() {
        if (!TextUtils.isEmpty(edittext_enterCustomerName.getText().toString().trim())) {
            addEssentialParams();
            addCustomerDetails();
            Utility.getInstance().showProgressDialogue(mContext);


        } else {
            edittext_enterCustomerName.setError(getString(R.string.please_enter_customer_name));
        }
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


    private void addCustomerDetails() {
        ArrayList<AddCustomerParams.CustomerList> customerListArrayList = new ArrayList<>();
        AddCustomerParams.CustomerList customerList = new AddCustomerParams().new CustomerList();

        RealmResults<CustomerListRealm> customerListRealmModels = realm.where(CustomerListRealm.class).findAll();
        //  String createId = "C" + Utility.getInstance().getclientRegId(mContext) + String.valueOf(customerListRealmModels.size() + 1);
        SharedPreferences sharedPreferences1 = Utility.getInstance().getSharedPReference(mContext);
        String createId = "";
        if (TextUtils.isEmpty(sharedPreferences1.getString(LASTCUSTOMERID, ""))) {
            createId = "C" + Utility.getInstance().getclientRegId(mContext) + "1";
        } else {
            createId = "C" + Utility.getInstance().getclientRegId(mContext) + String.valueOf(Integer.parseInt(sharedPreferences1.getString(LASTCUSTOMERID, "")) + 1);
        }
        customerList.setCustomerId(createId);
        customerList.setImagename(createId);
        customerList.setClientId(Utility.getInstance().getclientRegId(mContext).toString());
        if (!TextUtils.isEmpty(edittext_enterCustomerName.getText().toString().trim())) {
            String[] splitedName = edittext_enterCustomerName.getText().toString().trim().split("\\s+");
            if (splitedName.length == 3) {
                customerList.setCFirstname(splitedName[0]);
                customerList.setCMiddleName(splitedName[1]);
                customerList.setCLastname(splitedName[2]);
            } else if (splitedName.length == 2) {
                customerList.setCFirstname(splitedName[0]);
                customerList.setCLastname(splitedName[1]);
            } else {
                customerList.setCFirstname(splitedName[0]);
            }
            if (!TextUtils.isEmpty(edittext_enterMobileNumber.getText().toString().trim())) {
                if (!Utility.getInstance().isContactValid(edittext_enterMobileNumber.getText().toString().trim())) {
                    edittext_enterMobileNumber.requestFocus();
                    edittext_enterMobileNumber.setError(getString(R.string.please_enter_10_digit_mobile_number));
                    return;
                } else {
                    customerList.setCContactNo(edittext_enterMobileNumber.getText().toString());
                }
            } else {
                customerList.setCContactNo(edittext_enterMobileNumber.getText().toString());
            }
            customerList.setPaymentFreq("0");
            customerList.setCEmail("");

            if (checkboxPaymentFlag.isChecked()) {
                customerList.setSMSFlag(true);
            } else {
                customerList.setSMSFlag(false);
            }
            customerList.setCAlternetMobNo("");
            customerList.setVillage(edittext_enterVillageName.getText().toString().trim());
            customerList.setTaluka(edittext_enterTalukaName.getText().toString().trim());
            customerList.setDistrict(edittext_enterDistrictName.getText().toString().trim());
            customerList.setStateId(stateIdString);
            customerList.setCountry("INDIA");
            //  customerList.setImagename("");
            String base64Image = "";
            if (profileImageFile != null) {
                if (profileImageFile.exists() && profileImageFile.length() > 0 && !TextUtils.isEmpty(profileImageFilePath)) {
                    Bitmap bm = BitmapFactory.decodeFile(profileImageFilePath);
                    ByteArrayOutputStream bOut = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, bOut);
                    base64Image = Base64.encodeToString(bOut.toByteArray(), Base64.DEFAULT);
                }
            }
            customerList.setImagePath(base64ImageString);
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
            Date date = null;
            try {
                date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(currentDate + " " + currentTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //customerList.setCreatedOn(currentDate + " " + currentTime);
            customerList.setCreatedOn(date);
            customerList.setUpdatedOn("");
            customerList.setCreatedBy(Utility.getInstance().getclientRegId(mContext).toString());
            customerList.setUpdatedBy("");
            customerListArrayList.add(customerList);
            addCustomerParams.setCustomerList(customerListArrayList);

            if (Utility.getInstance().isOnline(mContext)) {
                mPresenter.postAddCustomer(addCustomerParams);
            } else {
                Toast.makeText(mContext, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
            }
        } else {
            edittext_enterCustomerName.requestFocus();
            edittext_enterCustomerName.setError(getString(R.string.please_enter_customer_name));
        }
    }

    private void addEssentialParams() {
        addCustomerParams = new AddCustomerParams();
        Essential essential = new AddCustomerParams().new Essential();
        essential.setClientId(Utility.getInstance().getclientRegId(mContext).toString());
        if (language.toLowerCase().contains(langaugeCodeEnglish.toLowerCase())) {
            essential.setLanguageId("1");
        } else {
            essential.setLanguageId("2");
        }
        essential.setDeviceId("");
        essential.setMasterId("");
        essential.setSessionId("");
        addCustomerParams.setEssential(essential);
    }


    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void setPresenter() {
        mPresenter = new AddCustomerPresenter(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_add_customer;
    }

    @Override
    public void onMakeServerCalls() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mContext == null) {
            mContext = ((ActivityDashboard) getContext());
        }
        ((ActivityDashboard) getContext()).setTitleOfScreen(getString(R.string.menu_add_customer));
        ((ActivityDashboard) getContext()).setVisibilityofRamumunimjiVisible(false,1);
        languageCode = Utility.getInstance().getLanguage(mContext);
    }

    @Override
    public void onStart() {
        super.onStart();
        realm = RealmController.with(this).getRealm();
        language = checkLanguageCode(mContext);
        initCheckBox();
        initStateDialogue();
    }

    private void initCheckBox() {
        checkboxPaymentFlag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxValue = isChecked;
            }
        });
    }


    private void initStateDialogue() {
        String tag = "";
        edittext_selectStateName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    showdialogue = true;
                    dialogState = new Dialog(getContext());
                    if (!dialogState.isShowing()) {
                        stateList.clear();

                        for (int i = 0; i < stateId.length; i++) {
                            StateModel stateModel = new StateModel();
                            stateModel.setStateNameEnglish(stateNamesEnglish[i]);
                            stateModel.setStateNameMarathi(stateNamesMarathi[i]);
                            stateModel.setStateId(stateId[i]);
                            stateList.add(stateModel);
                        }
                        dialogState.getWindow().addFlags(Window.FEATURE_NO_TITLE);
                        dialogState.setCancelable(true);
                        dialogState.setContentView(R.layout.custom_state_dialogue);
                        edt_search_state = dialogState.findViewById(R.id.edt_search_state);
                        secondStateList.clear();
                        filteredStateList.clear();
                        secondStateList.addAll(stateList);
                        filteredStateList.addAll(stateList);
                        RecyclerView recyclerView = dialogState.findViewById(R.id.recyclerview_contact);
                        stateListAdapter = new StateListAdapter(mContext, stateList, onStateListClick, languageCode);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(stateListAdapter);
                        setSearchFilter();
                        dialogState.show();
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
        filteredStateList.clear();

        for (StateModel item : secondStateList) {
            if (item != null) {
                if (languageCode.equalsIgnoreCase(languageCodeMarathi)) {
                    if (item.getStateNameMarathi().toLowerCase().contains(text.toLowerCase())) {
                        filteredStateList.add(item);
                    }
                } else {
                    if (item.getStateNameEnglish().toLowerCase().contains(text.toLowerCase())) {
                        filteredStateList.add(item);
                    }
                }
            }
        }
        if (text.trim().equals("")) {
            filteredStateList.clear();
            filteredStateList.addAll(secondStateList);
        }
        stateListAdapter.filterList(filteredStateList);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
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
                    cameraIntent();
                } else if (items[item].equals(getString(R.string.gallery))) {
                    galleryIntent();
                } else if (items[item].equals(getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    ///taking gallery image
    private void galleryIntent() {
        int value = checkPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE, GALLERY_PERMISSION_REQUEST_CODE);
        if (value == 1) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), GALLERY_IMAGE_REQUEST_CODE);
        } else if (value == 2) {
            displayNeverAskAgainDialog(mContext, getString(R.string.We_need_permission));
        }
    }

    //Capturing camera image
    private void cameraIntent() {
        int value = checkPermission(mContext, Manifest.permission.CAMERA, CAMERA_PERMISSION_REQUEST_CODE);
        if (value == 1) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
            imgUri = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
            startActivityForResult(intent, CAMERA_IMAGE_REQUEST_CODE);
        } else if (value == 2) {
            displayNeverAskAgainDialog(mContext, getString(R.string.We_need_permission));
        }
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

        /*if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_IMAGE_REQUEST_CODE) {
                onSelectFromGalleryResult(data);
            } else if (requestCode == CAMERA_IMAGE_REQUEST_CODE) {
                onCaptureImageResult(data);
            }
        }*/
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


    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? outputUri : data.getData();
    }


    public Bitmap loadBitmap(String url) {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bm;
    }


    //--------------------------------------------- Image from camera -----------------------------------------//

    private void onCaptureImageResult(Intent data) {
        //Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        //  Bitmap thumbnail = loadBitmap(imgUri);
        Bitmap thumbnail = null;
        try {
            thumbnail = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), imgUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        profileImageFile = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        profileImageFilePath = profileImageFile.getAbsolutePath();
        /*profileImageFilePath = file.getAbsolutePath();
            if(  imgUri!=null   ){
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), imgUri);
                imageview_profile.setImageBitmap(bitmap);*/

        /*FileOutputStream fo;
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
        }*/

        /*if (!profileImageFile.isFile()) {
            return;
        } else {
            // uploadFile(profileImageFile);
        }*/
        /*Uri cameraImageUri = getImageUri(getContext(), thumbnail);
        saveImage(cameraImageUri);*/
        /*Uri uri = imgUri;
        imageview_profile.setImageURI(imgUri);*/
        Glide.with(getContext()).load(imgUri.toString()).into(imageview_profile);
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
                    profileImageFilePath = path;
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
    public void onStateListClick(View view, int position) {
        if (languageCode.equalsIgnoreCase(languageCodeMarathi)) {
            edittext_selectStateName.setText(filteredStateList.get(position).getStateNameMarathi());
            stateIdString = filteredStateList.get(position).getStateId();
        } else {
            edittext_selectStateName.setText(filteredStateList.get(position).getStateNameEnglish());
            stateIdString = filteredStateList.get(position).getStateId();
        }
        dialogState.dismiss();
    }


    @Override
    public void addCustomerSuccess(AddCustomerResponse body) {
        RealmController.with(this).clearCustomerList();
        ArrayList<AddCustomerResponse.Data.tblCustomers> customerList = new ArrayList<>();
        customerList.addAll(body.getData().getTblCustomersArrayList());

        ArrayList<Integer> numberIds = new ArrayList<>();
        ArrayList<CustomerListRealm> customerListRealmArrayList = new ArrayList<>();
        for (int i = 0; i < customerList.size(); i++) {
            int regIdLength = Utility.getInstance().getclientRegId(mContext).length();
            String customerIdFromresponse = customerList.get(i).getCustomerId();
            String removeCFromResponse = customerIdFromresponse.substring(1 + regIdLength);
            numberIds.add(Integer.parseInt(removeCFromResponse));
            CustomerListRealm customerListRealm = new CustomerListRealm();
            customerListRealm.setID(customerList.get(i).getID());
            customerListRealm.setCustomerId(customerList.get(i).getCustomerId());
            customerListRealm.setClientId(customerList.get(i).getClientId());
            customerListRealm.setCFirstname(customerList.get(i).getCFirstname());
            customerListRealm.setStateId(customerList.get(i).getStateId());

            customerListRealm.setCustomerOnlineSaved(true);

            customerListRealm.setCMiddleName(customerList.get(i).getCMiddleName());
            customerListRealm.setCLastname(customerList.get(i).getCLastname());
            customerListRealm.setImagePath(customerList.get(i).getImagePath());
            customerListRealm.setOutstanding(customerList.get(i).getOutstanding());
            customerListRealm.setVillage(customerList.get(i).getVillage());
            customerListRealm.setTaluka(customerList.get(i).getTaluka());
            customerListRealm.setDistrict(customerList.get(i).getDistrict());
            customerListRealm.setCAlternetMobNo(customerList.get(i).getCAlternetMobNo());
            customerListRealm.setCContactNo(customerList.get(i).getCContactNo());
            if (customerList.get(i).getSMSFlag()!=null && customerList.get(i).getSMSFlag().trim().toLowerCase().matches("true")) {
                customerListRealm.setSMSFlag("true");
            }else {
                customerListRealm.setSMSFlag("false");
            }


            customerListRealmArrayList.add(customerListRealm);
        }
        //   Log.i(getClass().getName(), "=========tempListHelpScreenModel SIZE: " + tempListHelpScreenModel.size());
        for (CustomerListRealm customerListRealm : customerListRealmArrayList) {
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(customerListRealm);
            realm.commitTransaction();
        }

        if (customerList.size()>0) {
            int regIdLength = Utility.getInstance().getclientRegId(mContext).length();
            String firstId = customerList.get(0).getCustomerId();
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

        imageview_profile.setImageDrawable(getResources().getDrawable(R.drawable.ic_default_profile_image));
        edittext_enterCustomerName.setText("");
        edittext_enterVillageName.setText("");
        edittext_enterDistrictName.setText("");
        edittext_enterTalukaName.setText("");
        edittext_enterMobileNumber.setText("");
        profileImageFilePath = "";
        base64ImageString = "";
        Utility.getInstance().dismissProgress();
        Toast.makeText(mContext, "Customer Created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addCustomerFailure(String message) {
        Utility.getInstance().dismissProgress();
        Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
    }
}