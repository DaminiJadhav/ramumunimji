package com.posbilling.posbillingapplication.activity.editcustomer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.dashboard.ui.addcustomer.StateListAdapter;
import com.posbilling.posbillingapplication.activity.outstandinglist.ActivityOutstandingList;
import com.posbilling.posbillingapplication.interfaceclick.OnContactListClick;
import com.posbilling.posbillingapplication.interfaceclick.OnStateListClick;
import com.posbilling.posbillingapplication.lib.BaseActivity;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.model.Contactsmodel;
import com.posbilling.posbillingapplication.model.StateModel;
import com.posbilling.posbillingapplication.model.realmcontoller.RealmController;
import com.posbilling.posbillingapplication.model.realmmodel.CustomerListRealm;
import com.posbilling.posbillingapplication.model.request.AddCustomerParams;
import com.posbilling.posbillingapplication.model.response.AddCustomerResponse;
import com.posbilling.posbillingapplication.model.response.CustomerListResponse;
import com.posbilling.posbillingapplication.retrofit.CustomRetroRequest;
import com.posbilling.posbillingapplication.utility.Constants;
import com.posbilling.posbillingapplication.utility.PermissionUtils;
import com.posbilling.posbillingapplication.utility.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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
import okhttp3.internal.Util;

import static com.posbilling.posbillingapplication.utility.Constants.CAMERA_PERMISSION_REQUEST_CODE;
import static com.posbilling.posbillingapplication.utility.Constants.CUSTOMERID;
import static com.posbilling.posbillingapplication.utility.Constants.GALLERY_PERMISSION_REQUEST_CODE;
import static com.posbilling.posbillingapplication.utility.Constants.LOGPOS;
import static com.posbilling.posbillingapplication.utility.Constants.READ_CONTACTS_PERMISSION_CODE;
import static com.posbilling.posbillingapplication.utility.Constants.RECORD_AUDIO_PERMISSION_REQUEST_CODE;
import static com.posbilling.posbillingapplication.utility.Constants.langaugeCodeEnglish;
import static com.posbilling.posbillingapplication.utility.Constants.languageCodeMarathi;
import static com.posbilling.posbillingapplication.utility.DataState.stateId;
import static com.posbilling.posbillingapplication.utility.DataState.stateNamesEnglish;
import static com.posbilling.posbillingapplication.utility.DataState.stateNamesMarathi;

public class ActivityEditCustomer extends BaseActivity implements OnStateListClick, EditCustomerContractor.View, OnContactListClick {

    private String customerId = "";
    private Realm realm;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    private String customerIdToStore = "";
    private boolean showdialogue = false;
    private Dialog dialogState;
    private OnContactListClick onContactListClick = this;
    private EditText edt_search_state;
    private Dialog dialogcontact;
    private ArrayList<StateModel> stateList = new ArrayList<>();
    private ArrayList<StateModel> secondStateList = new ArrayList<>();
    private ArrayList<StateModel> filteredStateList = new ArrayList<>();
    private StateListAdapter stateListAdapter;
    private String languageCode = "";
    private String stateIdString = "14";
    private OnStateListClick onStateListClick = this;
    private Uri outputUri = null;
    private String language = "en";
    private ContactsListAdapter contactsListAdapter;
    private Uri picUri;
    private Bitmap myBitmap;
    private EditText edt_search_contact;
    private String base64ImageString = "";
    private AddCustomerParams addCustomerParams;
    private String customerToStore = "";
    private EditCustomerContractor.Presenter mPresenter;
    private ArrayList<Contactsmodel> contactList = new ArrayList<>();
    private ArrayList<Contactsmodel> filteredList = new ArrayList<>();

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
    CheckBox checkboxPaymentFlag;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @OnClick(R.id.imageview_mic_mobile_number)
    void imageview_mic_mobile_number() {
        edittext_enterMobileNumber.requestFocus();
        showdialogue = true;
        boolean value = getContactPermisson();
        if (value && contactList.size() <= 0) {
            getContactList();
        } else if (contactList.size() > 0) {
            showDialogueContact();
        }

    }

    @OnClick(R.id.imageview_mic_enter_name)
    void imageview_mic_enter_name() {
        edittext_enterCustomerName.requestFocus();
        imageview_mic_enter_name.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
        speechToTextFunc(this, edittext_enterCustomerName, 1, imageview_mic_enter_name);
    }

    @OnClick(R.id.imageview_mic_village_name)
    void imageview_mic_village_name() {
        edittext_enterVillageName.requestFocus();
        speechToTextFunc(this, edittext_enterVillageName, 1, imageview_mic_village_name);
    }

    @OnClick(R.id.imageview_mic_taluka_name)
    void imageview_mic_taluka_name() {
        edittext_enterTalukaName.requestFocus();
        speechToTextFunc(this, edittext_enterTalukaName, 1, imageview_mic_taluka_name);
    }

    @OnClick(R.id.imageview_mic_district_name)
    void imageview_mic_district_name() {
        edittext_enterDistrictName.requestFocus();
        speechToTextFunc(this, edittext_enterDistrictName, 1, imageview_mic_district_name);
    }

    @OnClick(R.id.relativelayout_submitButton)
    void relativelayout_submitButton() {
        if (!TextUtils.isEmpty(edittext_enterCustomerName.getText().toString().trim())) {
            addEssentialParams();
            addCustomerDetails();
        } else {
            edittext_enterCustomerName.setError(getString(R.string.please_enter_customer_name));
        }
    }


    private void addCustomerDetails() {
        ArrayList<AddCustomerParams.CustomerList> customerListArrayList = new ArrayList<>();
        AddCustomerParams.CustomerList customerList = new AddCustomerParams().new CustomerList();
        RealmResults<CustomerListRealm> customerListRealmModels = realm.where(CustomerListRealm.class).findAll();
        // String createId = "C"+Utility.getInstance().getclientRegId(this)+String.valueOf(customerListRealmModels.size()+1);
        customerList.setCustomerId(customerToStore);
        customerList.setImagename(customerToStore + new Date().getTime());
        customerList.setClientId(Utility.getInstance().getclientRegId(this).toString());
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

            if (checkboxPaymentFlag.isChecked()) {
                customerList.setSMSFlag(true);
            } else {
                customerList.setSMSFlag(false);
            }
            customerList.setID(customerId);
            customerList.setCEmail("");
            customerList.setCAlternetMobNo("");
            customerList.setVillage(edittext_enterVillageName.getText().toString().trim());
            customerList.setTaluka(edittext_enterTalukaName.getText().toString().trim());
            customerList.setDistrict(edittext_enterDistrictName.getText().toString().trim());
            customerList.setStateId(stateIdString);
            customerList.setCountry("INDIA");
            //  customerList.setImagename("");
            String base64Image = "";
            /*if (profileImageFile != null) {
                if (profileImageFile.exists() && profileImageFile.length() > 0 && !TextUtils.isEmpty(profileImageFilePath)) {
                if (profileImageFile.exists() && profileImageFile.length() > 0 && !TextUtils.isEmpty(profileImageFilePath)) {
                    Bitmap bm = BitmapFactory.decodeFile(profileImageFilePath);
                    ByteArrayOutputStream bOut = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, bOut);
                    base64Image = Base64.encodeToString(bOut.toByteArray(), Base64.DEFAULT);
                }
            }*/
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
            customerList.setCreatedBy(Utility.getInstance().getclientRegId(this).toString());
            customerList.setUpdatedBy("");
            customerListArrayList.add(customerList);
            addCustomerParams.setCustomerList(customerListArrayList);

            if (Utility.getInstance().isOnline(this)) {
                Utility.getInstance().showProgressDialogue(this);
                mPresenter.postAddCustomer(addCustomerParams);
            } else {
                Toast.makeText(this, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
            }
        } else {
            edittext_enterCustomerName.requestFocus();
            edittext_enterCustomerName.setError(getString(R.string.please_enter_customer_name));
        }
    }

    private void addEssentialParams() {
        addCustomerParams = new AddCustomerParams();
        AddCustomerParams.Essential essential = new AddCustomerParams().new Essential();
        essential.setClientId(Utility.getInstance().getclientRegId(this).toString());
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
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_add_customer;
    }

    @Override
    protected void setPresenter() {
        mPresenter = new EditCustomerPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = RealmController.with(this).getRealm();
        language = checkLanguageCode(this);
        // imageview_profile.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        languageCode = Utility.getInstance().getLanguage(this);

        toolbar_title.setText(getString(R.string.profile));
        getContactPermission();
        initStateDialogue();
        getIntentData();
        getCusomerData();
        //   setPhonebookClick();
        boolean value = getContactPermisson();
        if (value && contactList.size() <= 0) {
            getContactList();
        }
    }

    private void getContactPermission() {

    }

    //set drawable click at editext phone nuber @Ankur
    void setPhonebookClick() {
        String tag = "";
        imageview_mic_mobile_number.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                edittext_enterMobileNumber.requestFocus();

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edittext_enterMobileNumber.getRight() - edittext_enterMobileNumber.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (tag.equalsIgnoreCase(Constants.TAG_CONTACTS)) {

                        } else {
                            //changeFragment(new SelectContactFragment(), Constants.TAG_CONTACTS);
                            showdialogue = true;
                            boolean value = getContactPermisson();
                            if (value && contactList.size() <= 0) {
                                getContactList();
                            } else if (contactList.size() > 0) {
                                showDialogueContact();
                            }
                        }

                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void alertDialogContactList(ArrayList<Contactsmodel> contactList) {
        dialogcontact = new Dialog(ActivityEditCustomer.this);
        dialogcontact.getWindow().addFlags(Window.FEATURE_NO_TITLE);
        dialogcontact.setCancelable(true);
        dialogcontact.setContentView(R.layout.custom_contact_dialoge);
        edt_search_contact = dialogcontact.findViewById(R.id.edt_search_contact);
        filteredList.clear();
        filteredList.addAll(contactList);
        RecyclerView recyclerView = dialogcontact.findViewById(R.id.recyclerview_contact);
        contactsListAdapter = new ContactsListAdapter(contactList, onContactListClick, recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityEditCustomer.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(contactsListAdapter);
        setSearchFilterContact();
        dialogcontact.show();
    }


    private void setSearchFilterContact() {
        edt_search_contact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterContact(s.toString());
            }

        });
    }


    private void filterContact(String text) {
        filteredList.clear();
        try {
            for (Contactsmodel item : contactList) {
                if (item != null && item.getName() != null) {
                    if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                        filteredList.add(item);
                    }
                }
            }
            if (text.trim().equals("")) {
                filteredList.clear();
                filteredList.addAll(contactList);
            }
            contactsListAdapter.filterList(filteredList);
        } catch (Exception a) {
        }
    }


    private void showDialogueContact() {
        if (contactList.size() > 0) {
            alertDialogContactList(contactList);
        } else {
        }
    }

    private boolean getContactPermisson() {
        int valueContact = checkPermission(ActivityEditCustomer.this, Manifest.permission.READ_CONTACTS, READ_CONTACTS_PERMISSION_CODE);
        if (valueContact == 1) {
            //   startActivityForResult(getPickImageChooserIntent(), 200);
            return true;
        } else if (valueContact == 2) {
            displayNeverAskAgainDialog(ActivityEditCustomer.this, getString(R.string.We_need_permission));
            return false;
        } else {
            return false;
        }
    }


    private void getContactList() {
        Utility.getInstance().showProgressDialogue(ActivityEditCustomer.this);
        contactList.clear();
        ContentResolver contentResolver = getContentResolver();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                if ((cursor != null ? cursor.getCount() : 0) > 0) {
                    while (cursor != null && cursor.moveToNext()) {
                        String phoneNumber = "";
                        String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        if (cursor.getInt(cursor.getColumnIndex(
                                ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                            Cursor pCur = contentResolver.query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                    new String[]{id}, null);
                            while (pCur.moveToNext()) {
                                phoneNumber = pCur.getString(pCur.getColumnIndex(
                                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                            }
                            pCur.close();
                        }
                        Contactsmodel contactsmodel = new Contactsmodel();
                        contactsmodel.setName(name);
                        contactsmodel.setPhoneNumber(phoneNumber);
                        contactsmodel.setCheck(false);
                        contactList.add(contactsmodel);
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }

            }
        });

        Utility.getInstance().dismissProgress();
    }


    protected String checkLanguageCode(Context mContext) {
        return Utility.getInstance().getLanguage(mContext);
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
                    dialogState = new Dialog(ActivityEditCustomer.this);
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
                        stateListAdapter = new StateListAdapter(ActivityEditCustomer.this, stateList, onStateListClick, languageCode);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityEditCustomer.this);
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

    @OnClick(R.id.imageview_edit_pic)
    void imageview_edit_pic() {
        int value = checkTwoPermission(ActivityEditCustomer.this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, GALLERY_PERMISSION_REQUEST_CODE);
        int valueGallery = checkPermission(ActivityEditCustomer.this, Manifest.permission.READ_EXTERNAL_STORAGE, GALLERY_PERMISSION_REQUEST_CODE);
        int valueExternal = checkPermission(ActivityEditCustomer.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, GALLERY_PERMISSION_REQUEST_CODE);
        int valueCamera = checkPermission(ActivityEditCustomer.this, Manifest.permission.CAMERA, CAMERA_PERMISSION_REQUEST_CODE);
        if (value == 1 && valueGallery == 1 && valueCamera == 1) {
            startActivityForResult(getPickImageChooserIntent(), 200);
        } else if (value == 2) {
            displayNeverAskAgainDialog(ActivityEditCustomer.this, getString(R.string.We_need_permission));
        }
        //takeProfilePicturefromFragment(mContext);
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
                Glide.with(this).load(picUri.toString()).into(imageview_profile);

                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picUri);
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
    

    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? outputUri : data.getData();
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
        PackageManager packageManager = getPackageManager();

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
        File getImage = getExternalCacheDir();
        String child = "profile" + new Date().getTime() + ".png";
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), child));
            this.outputUri = outputFileUri;
        }
        return outputFileUri;
    }

    public int checkTwoPermission(Context context, String permission, String permissionTwo, int permissonRequestCode) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager
                .PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (PermissionUtils.neverAskAgainSelected(this, permission)) {
                    //displayNeverAskAgainDialog();
                    return 2;
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{permission, permissionTwo},
                            permissonRequestCode);
                    return 3;
                }
            }
        } else {
            return 1;
        }
        return 0;
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

    private void getIntentData() {
        customerToStore = getIntent().getStringExtra("StringCustomerId");
        customerId = getIntent().getStringExtra(CUSTOMERID);
    }


    private void getCusomerData() {
        RealmResults<CustomerListRealm> customerListRealmModels = realm.where(CustomerListRealm.class).findAll();
        for (CustomerListRealm object : customerListRealmModels) {
            CustomerListResponse.Data.tblCustomers tblCustomers = new CustomerListResponse().new Data().new tblCustomers();
            if (customerId.toLowerCase().trim().matches(object.getID().toLowerCase().trim())) {
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
                if (TextUtils.isEmpty(middlename)) {
                    finalname = firstname + " " + lastname;
                } else {
                    finalname = firstname + " " + middlename + " " + lastname;
                }
                edittext_enterCustomerName.setText(finalname);
                String path = object.getImagePath();

                //https://ramu.sdaemon.com/images//Ramuji//C136.jpg
                Glide.with(ActivityEditCustomer.this).load(new CustomRetroRequest().imageURL + object.getImagePath())
                        .placeholder(R.drawable.ic_default_profile_image)
                        .into(imageview_profile);
                String smsflag = object.getSMSFlag();
                if (object.getSMSFlag() != null && object.getSMSFlag().toLowerCase().trim().matches("true")) {
                    checkboxPaymentFlag.setChecked(true);
                }

                if (object.getCContactNo() != null && !TextUtils.isEmpty(object.getCContactNo().toString())) {
                    edittext_enterMobileNumber.setText(object.getCContactNo());
                } else {
                    edittext_enterMobileNumber.setText("");
                }
                String address = "";
                String village = "";
                String district = "";
                String taluka = "";
                if (object.getTaluka() != null && !TextUtils.isEmpty(object.getTaluka())) {
                    taluka = object.getTaluka();
                    edittext_enterTalukaName.setText(taluka);
                }
                if (object.getVillage() != null && !TextUtils.isEmpty(object.getVillage())) {
                    village = object.getVillage();
                    edittext_enterVillageName.setText(village);
                }
                if (object.getDistrict() != null && !TextUtils.isEmpty(object.getDistrict())) {
                    district = object.getDistrict();
                    edittext_enterDistrictName.setText(district);
                }
                address = village + " " + taluka + " " + district;
                /*if (!TextUtils.isEmpty(address.trim())) {
                    textview_address.setText(address.trim());
                } else {
                    textview_address.setText("-");
                }*/

                if (object.getCustomerId() != null && !TextUtils.isEmpty(object.getCustomerId())) {
                    // textview_id.setText(object.getCustomerId());
                    customerIdToStore = object.getCustomerId();
                } else {
                    // textview_id.setText("-");
                }


                if (object.getStateId() != null && TextUtils.isEmpty(object.getStateId())) {
                    int stateid = Integer.valueOf(object.getStateId());
                    StateModel stateModel = new StateModel();

                    String statename = "";

                    if (languageCode.equalsIgnoreCase(languageCodeMarathi)) {
                        statename = stateNamesMarathi[stateid - 1];
                        edittext_selectStateName.setText(statename);
                    } else {
                        statename = stateNamesEnglish[stateid - 1];
                        edittext_selectStateName.setText(statename);
                    }
                }

                break;
            }
        }
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
                    setError("ActivityEditCustomer : speechToTextFunc Has problem");
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


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityEditCustomer.this, ActivityOutstandingList.class);
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }

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
        ArrayList<CustomerListRealm> customerListRealmArrayList = new ArrayList<>();
        for (int i = 0; i < customerList.size(); i++) {
            CustomerListRealm customerListRealm = new CustomerListRealm();
            customerListRealm.setID(customerList.get(i).getID());
            customerListRealm.setCustomerId(customerList.get(i).getCustomerId());

            customerListRealm.setCustomerOnlineSaved(true);

            customerListRealm.setClientId(customerList.get(i).getClientId());
            customerListRealm.setCFirstname(customerList.get(i).getCFirstname());
            customerListRealm.setStateId(customerList.get(i).getStateId());
            customerListRealm.setCMiddleName(customerList.get(i).getCMiddleName());
            customerListRealm.setCLastname(customerList.get(i).getCLastname());
            customerListRealm.setImagePath(customerList.get(i).getImagePath());
            customerListRealm.setOutstanding(customerList.get(i).getOutstanding());
            customerListRealm.setVillage(customerList.get(i).getVillage());
            customerListRealm.setTaluka(customerList.get(i).getTaluka());
            customerListRealm.setDistrict(customerList.get(i).getDistrict());
            customerListRealm.setCAlternetMobNo(customerList.get(i).getCAlternetMobNo());
            customerListRealm.setCContactNo(customerList.get(i).getCContactNo());
            customerListRealmArrayList.add(customerListRealm);
        }
        //   Log.i(getClass().getName(), "=========tempListHelpScreenModel SIZE: " + tempListHelpScreenModel.size());
        for (CustomerListRealm customerListRealm : customerListRealmArrayList) {
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(customerListRealm);
            realm.commitTransaction();
        }


        Utility.getInstance().dismissProgress();
        /*imageview_profile.setImageDrawable(getResources().getDrawable(R.drawable.ic_default_profile_image));
        edittext_enterCustomerName.setText("");
        edittext_enterVillageName.setText("");
        edittext_enterDistrictName.setText("");
        edittext_enterTalukaName.setText("");
        edittext_enterMobileNumber.setText("");
        base64ImageString = "";*/
        Toast.makeText(this, getString(R.string.Customer_updated), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addCustomerFailure(String message) {
        Utility.getInstance().dismissProgress();
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnContactClick(int position) {
        dialogcontact.dismiss();
        String str = filteredList.get(position).getPhoneNumber().toString();
        String mno = str.replaceAll("\\s", "");
        String finalMno = mno.substring(mno.length() - 10);
        edittext_enterMobileNumber.setText(finalMno);
    }
}
