package com.posbilling.posbillingapplication.activity.editsupplier;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
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
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.dashboard.ui.addcustomer.StateListAdapter;
import com.posbilling.posbillingapplication.activity.editcustomer.ActivityEditCustomer;
import com.posbilling.posbillingapplication.activity.editcustomer.ContactsListAdapter;
import com.posbilling.posbillingapplication.activity.outstandinglist.ActivityOutstandingList;
import com.posbilling.posbillingapplication.activity.supplieractivity.ActivitySupplierOutstandingList;
import com.posbilling.posbillingapplication.interfaceclick.OnContactListClick;
import com.posbilling.posbillingapplication.interfaceclick.OnStateListClick;
import com.posbilling.posbillingapplication.lib.BaseActivity;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.model.Contactsmodel;
import com.posbilling.posbillingapplication.model.StateModel;
import com.posbilling.posbillingapplication.model.realmcontoller.RealmController;
import com.posbilling.posbillingapplication.model.realmmodel.CustomerListRealm;
import com.posbilling.posbillingapplication.model.realmmodel.SupplierListRealm;
import com.posbilling.posbillingapplication.model.request.SupplierEditRequest;
import com.posbilling.posbillingapplication.model.response.AddCustomerResponse;
import com.posbilling.posbillingapplication.model.response.CustomerListResponse;
import com.posbilling.posbillingapplication.model.response.GetSuppliersResponse;
import com.posbilling.posbillingapplication.retrofit.CustomRetroRequest;
import com.posbilling.posbillingapplication.utility.Constants;
import com.posbilling.posbillingapplication.utility.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.posbilling.posbillingapplication.utility.Constants.CAMERA_PERMISSION_REQUEST_CODE;
import static com.posbilling.posbillingapplication.utility.Constants.GALLERY_PERMISSION_REQUEST_CODE;
import static com.posbilling.posbillingapplication.utility.Constants.READ_CONTACTS_PERMISSION_CODE;
import static com.posbilling.posbillingapplication.utility.Constants.languageCodeMarathi;
import static com.posbilling.posbillingapplication.utility.DataState.stateId;
import static com.posbilling.posbillingapplication.utility.DataState.stateNamesEnglish;
import static com.posbilling.posbillingapplication.utility.DataState.stateNamesMarathi;

public class ActivityEditSupplier extends BaseActivity implements OnStateListClick, OnContactListClick, EditSupplierContractor.View {
    private Realm realm;
    private EditSupplierPresenter mPresenter;
    private String language = "en";
    private String languageCode = "";
    private boolean showdialogue = false;
    private OnStateListClick onStateListClick = this;
    private String stateIdString = "14";
    private StateListAdapter stateListAdapter;
    private Dialog dialogState;
    private OnContactListClick onContactListClick = this;
    private ArrayList<StateModel> stateList = new ArrayList<>();
    private String base64ImageString = "";
    private ArrayList<StateModel> secondStateList = new ArrayList<>();
    private ArrayList<StateModel> filteredStateList = new ArrayList<>();
    private EditText edt_search_state;
    private ArrayList<Contactsmodel> contactList = new ArrayList<>();
    private ArrayList<Contactsmodel> filteredList = new ArrayList<>();
    private Dialog dialogcontact;
    private ContactsListAdapter contactsListAdapter;
    private Uri picUri;
    SupplierListRealm supplierObject;
    private Bitmap myBitmap;
    private Uri outputUri = null;
    private EditText edt_search_contact;
    private String supplierToStore = "";
    private String supplierId = "";
    private SupplierEditRequest addSupplierParams;

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

    @OnClick(R.id.imageview_edit_pic)
    void imageview_edit_pic() {
        int value = checkTwoPermission(ActivityEditSupplier.this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, GALLERY_PERMISSION_REQUEST_CODE);
        int valueGallery = checkPermission(ActivityEditSupplier.this, Manifest.permission.READ_EXTERNAL_STORAGE, GALLERY_PERMISSION_REQUEST_CODE);
        int valueExternal = checkPermission(ActivityEditSupplier.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, GALLERY_PERMISSION_REQUEST_CODE);
        int valueCamera = checkPermission(ActivityEditSupplier.this, Manifest.permission.CAMERA, CAMERA_PERMISSION_REQUEST_CODE);
        if (value == 1 && valueGallery == 1 && valueCamera == 1) {
            startActivityForResult(getPickImageChooserIntent(), 200);
        } else if (value == 2) {
            displayNeverAskAgainDialog(ActivityEditSupplier.this, getString(R.string.We_need_permission));
        }
        //takeProfilePicturefromFragment(mContext);
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
            addSupplierDetails();
        } else {
            edittext_enterCustomerName.setError(getString(R.string.please_enter_customer_name));
        }
    }

    private void addSupplierDetails() {
        addSupplierParams = new SupplierEditRequest();
        addSupplierParams.setRegId(Utility.getInstance().getclientRegId(ActivityEditSupplier.this));
        addSupplierParams.setLanguageId("0");

        ArrayList<SupplierEditRequest.SupplierList> supplierListArrayList = new ArrayList<>();
        SupplierEditRequest.SupplierList supplierList = new SupplierEditRequest().new SupplierList();

        supplierList.setID(supplierObject.getID());
        supplierList.setSupplierId(supplierToStore);
        supplierList.setClientId(supplierObject.getClientId());

        supplierList.setSFirstname(edittext_enterCustomerName.getText().toString());
        supplierList.setSMiddleName("");
        supplierList.setSLastname("");
        supplierList.setSContactNo(edittext_enterMobileNumber.getText().toString());
        supplierList.setSAlternetMobNo("");
        supplierList.setSEmail("");
        supplierList.setVillage(edittext_enterVillageName.getText().toString());
        supplierList.setTaluka(edittext_enterTalukaName.getText().toString());
        supplierList.setDistrict(edittext_enterDistrictName.getText().toString());
        supplierList.setStateId(stateIdString);
        supplierList.setCountry("India");
        supplierList.setPaymentFreq("monthly");
        if (checkboxPaymentFlag.isChecked()) {
            supplierList.setSMSFlag("true");
        } else {
            supplierList.setSMSFlag("false");
        }

        supplierList.setImagename(supplierToStore);
        supplierList.setImagePath(base64ImageString);
        supplierList.setOutstanding(supplierObject.getOutstanding());
        supplierList.setCreatedBy("");
        supplierList.setUpdatedOn("");
        supplierList.setCreatedOn("");
        supplierList.setUpdatedBy("");
        supplierList.setIsActive("true");
        supplierListArrayList.add(supplierList);

        addSupplierParams.setSupplierListArrayList(supplierListArrayList);

        if (Utility.getInstance().isOnline(ActivityEditSupplier.this)) {
            Utility.getInstance().showProgressDialogue(ActivityEditSupplier.this);
            mPresenter.postAddSupplier(addSupplierParams);
        } else {
            Toast.makeText(this, getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }

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

    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? outputUri : data.getData();
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


    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_edit_supplier;
    }

    @Override
    protected void setPresenter() {
        mPresenter = new EditSupplierPresenter(this);
    }


    private void showDialogueContact() {
        if (contactList.size() > 0) {
            alertDialogContactList(contactList);
        } else {
        }
    }

    private void getContactList() {
        Utility.getInstance().showProgressDialogue(ActivityEditSupplier.this);
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


    private void alertDialogContactList(ArrayList<Contactsmodel> contactList) {
        dialogcontact = new Dialog(ActivityEditSupplier.this);
        dialogcontact.getWindow().addFlags(Window.FEATURE_NO_TITLE);
        dialogcontact.setCancelable(true);
        dialogcontact.setContentView(R.layout.custom_contact_dialoge);
        edt_search_contact = dialogcontact.findViewById(R.id.edt_search_contact);
        filteredList.clear();
        filteredList.addAll(contactList);
        RecyclerView recyclerView = dialogcontact.findViewById(R.id.recyclerview_contact);
        contactsListAdapter = new ContactsListAdapter(contactList, onContactListClick, recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityEditSupplier.this);
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

    private boolean getContactPermisson() {
        int valueContact = checkPermission(ActivityEditSupplier.this, Manifest.permission.READ_CONTACTS, READ_CONTACTS_PERMISSION_CODE);
        if (valueContact == 1) {
            //   startActivityForResult(getPickImageChooserIntent(), 200);
            return true;
        } else if (valueContact == 2) {
            displayNeverAskAgainDialog(ActivityEditSupplier.this, getString(R.string.We_need_permission));
            return false;
        } else {
            return false;
        }
    }


    protected String checkLanguageCode(Context mContext) {
        return Utility.getInstance().getLanguage(mContext);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = RealmController.with(this).getRealm();
        language = checkLanguageCode(this);
        languageCode = Utility.getInstance().getLanguage(this);

        toolbar_title.setText(getString(R.string.supplier_profile));
        getContactPermission();
        initStateDialogue();
        getIntentData();
        getSupplierData();
        //   setPhonebookClick();
        boolean value = getContactPermisson();
        if (value && contactList.size() <= 0) {
            getContactList();
        }
    }

    private void getSupplierData() {
        RealmResults<SupplierListRealm> supplierListRealmModels = realm.where(SupplierListRealm.class).findAll();
        for (SupplierListRealm object : supplierListRealmModels) {
            CustomerListResponse.Data.tblCustomers tblCustomers = new CustomerListResponse().new Data().new tblCustomers();
            if (supplierId.toLowerCase().trim().matches(object.getID().toLowerCase().trim())) {
                String firstname = "";
                String middlename = "";
                String lastname = "";
                supplierObject = new SupplierListRealm();
                supplierObject = object;
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
                if (TextUtils.isEmpty(middlename)) {
                    finalname = firstname + " " + lastname;
                } else {
                    finalname = firstname + " " + middlename + " " + lastname;
                }
                edittext_enterCustomerName.setText(finalname);
                String path = object.getImagePath();

                //https://ramu.sdaemon.com/images//Ramuji//C136.jpg
                Glide.with(ActivityEditSupplier.this).load(new CustomRetroRequest().imageURL + object.getImagePath())
                        .placeholder(R.drawable.ic_default_profile_image)
                        .into(imageview_profile);
                String smsflag = object.getSMSFlag();
                if (object.getSMSFlag() != null && object.getSMSFlag().toLowerCase().trim().matches("true")) {
                    checkboxPaymentFlag.setChecked(true);
                }

                if (object.getSContactNo() != null && !TextUtils.isEmpty(object.getSContactNo().toString())) {
                    edittext_enterMobileNumber.setText(object.getSContactNo());
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

                if (object.getSupplierId() != null && !TextUtils.isEmpty(object.getSupplierId())) {
                    // textview_id.setText(object.getCustomerId());
                    supplierToStore = object.getSupplierId();
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


    private void getIntentData() {
        supplierToStore = getIntent().getStringExtra("StringSupplierId");
        supplierId = getIntent().getStringExtra("supplierID");
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
                    dialogState = new Dialog(ActivityEditSupplier.this);
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
                        stateListAdapter = new StateListAdapter(ActivityEditSupplier.this, stateList, onStateListClick, languageCode);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityEditSupplier.this);
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
    public void onBackPressed() {
        Intent intent = new Intent(ActivityEditSupplier.this, ActivitySupplierOutstandingList.class);
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }

    @Override
    public void OnContactClick(int position) {
        dialogcontact.dismiss();
        String str = filteredList.get(position).getPhoneNumber().toString();
        String mno = str.replaceAll("\\s", "");
        String finalMno = mno.substring(mno.length() - 10);
        edittext_enterMobileNumber.setText(finalMno);
    }

    @Override
    public void addSupplierSuccess(GetSuppliersResponse body) {
        /*realm.beginTransaction();
        realm.where(SupplierListRealm.class).equalTo("SupplierId", supplierObject.getSupplierId())
                .findAll().deleteAllFromRealm();
        realm.commitTransaction();*/

        realm.beginTransaction();
        realm.where(SupplierListRealm.class)
                .findAll().deleteAllFromRealm();
        realm.commitTransaction();

        for (GetSuppliersResponse.Data.tblSuppliers item : body.getData().getTblSuppliersArrayList()) {
            SupplierListRealm supplierListRealm = new SupplierListRealm();
            supplierListRealm.setID(item.getID());
            supplierListRealm.setSupplierId(item.getSupplierId());
            supplierListRealm.setClientId(item.getClientId());
            supplierListRealm.setSFirstname(item.getSFirstname());
            supplierListRealm.setSMiddleName(item.getSMiddleName());
            supplierListRealm.setSLastname(item.getSLastname());
            supplierListRealm.setSAlternetMobNo(item.getSAlternetMobNo());
            supplierListRealm.setSContactNo(item.getSContactNo());
            supplierListRealm.setSEmail(item.getSEmail());
            supplierListRealm.setVillage(item.getVillage());
            supplierListRealm.setTaluka(item.getTaluka());
            supplierListRealm.setDistrict(item.getDistrict());
            supplierListRealm.setStateId(item.getStateId());
            supplierListRealm.setCountry(item.getCountry());
            supplierListRealm.setPaymentFreq(item.getPaymentFreq());
            supplierListRealm.setSMSFlag(item.getSMSFlag());
            supplierListRealm.setImagename(item.getImagename());
            supplierListRealm.setImagePath(item.getImagePath());
            supplierListRealm.setOutstanding(item.getOutstanding());
            supplierListRealm.setCreatedOn(item.getCreatedOn());
            supplierListRealm.setCreatedBy(item.getCreatedBy());
            supplierListRealm.setUpdatedBy(item.getUpdatedBy());
            supplierListRealm.setUpdatedOn(item.getUpdatedOn());
            supplierListRealm.setIsActive(item.getIsActive());

            realm.beginTransaction();
            realm.copyToRealm(supplierListRealm);
            realm.commitTransaction();


            //   supplierObject = supplierListRealm;
        }

        Utility.getInstance().dismissProgress();
        Toast.makeText(this, getString(R.string.Supplier_updated), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addSupplierFailure(String message) {
        Utility.getInstance().dismissProgress();
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
    }
}
