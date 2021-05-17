/*

public Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<Intent>();
        PackageManager packageManager = UpdateInactivateParty.this.getPackageManager();

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

*/
/**
 * Get URI to image received from capture by camera.
 *//*

private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = UpdateInactivateParty.this.getExternalCacheDir();
        if (getImage != null) {
        outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
        }

@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap bitmap;
        if (resultCode == Activity.RESULT_OK) {

        if (getPickImageResultUri(data) != null) {
        picUri = getPickImageResultUri(data);

        try {
        myBitmap = MediaStore.Images.Media.getBitmap(UpdateInactivateParty.this.getContentResolver(), picUri);
        // myBitmap = rotateImageIfRequired(myBitmap, picUri);
        myBitmap = getResizedBitmap(myBitmap, 500);

        MultipartBody.Part body = getFileFromBitmap(picUri, myBitmap);

        apiService.profileUpload(AppConstant.acceptLangauge, AppConstant.contentType, body).enqueue(new Callback<FileDto>() {
@Override
public void onResponse(Call<FileDto> call, Response<FileDto> response) {

        Log.d("Post Result Success ", call.request().url().toString());
        Log.d("Post Result Success ", response.body().toString());
        //session.setFileId(response.body().getFileId().toString());
        profileimage = response.body().getFileId().toString();

        Picasso.with(UpdateInactivateParty.this)
        .load("http://dealapp-env.eba-x3ytpjvp.us-east-2.elasticbeanstalk.com/dealmanagement/api/downloadfile/thumbnail_" + response.body().getFileId())
        .resize(200, 200)
        .into(iv);

        }

@Override
public void onFailure(Call<FileDto> call, Throwable t) {

        Log.d("Post Result Failure", "Unable to get Result." + call.request().body().toString());
        Log.d("Generated Url", call.request().url().toString());
        }
        });
//                    iv.setImageBitmap(myBitmap);

        } catch (IOException e) {
        e.printStackTrace();
        }


        } else {


        bitmap = (Bitmap) data.getExtras().get("data");

        myBitmap = bitmap;

        }

        }

        }

public MultipartBody.Part getFileFromBitmap(Uri uri, Bitmap bitmap) {

        MultipartBody.Part body = null;
        try {

        //create a file to write bitmap data
        String filename = getFileName(picUri);

        Log.d("File name", "File Name  : " + filename);

        File file = new File(UpdateInactivateParty.this.getApplicationContext().getCacheDir(), filename);
        file.createNewFile();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 0 */
/*ignored for PNG*//*
, byteArrayOutputStream);
        byte[] bitmapdata = byteArrayOutputStream.toByteArray();

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(bitmapdata);

        RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);

        fileOutputStream.flush();
        fileOutputStream.close();

        return body;

        } catch (IOException exception) {
        exception.printStackTrace();
        return body;
        }
        }

public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
        Cursor cursor = UpdateInactivateParty.this.getContentResolver().query(uri, null, null, null, null);
        try {
        if (cursor != null && cursor.moveToFirst()) {
        result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
        }
        } finally {
        cursor.close();
        }
        }
        if (result == null) {
        result = uri.getPath();
        int cut = result.lastIndexOf('/');
        if (cut != -1) {
        result = result.substring(cut + 1);
        }
        }
        return result;
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


        return isCamera ? getCaptureImageOutputUri() : data.getData();
        }

@Override
public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("pic_uri", picUri);
        }

*/
/*
public class ExpendetureListRealm extends RealmObject {
    String ExpenditureId;
    String TransactionId;
    String ClientId;
    String Purpose;
    String ExpenseType;
    String Amount;
    String ExpendDate;
    boolean isExpenseOnlineSaved = true;


    public String getExpenditureId() {
        return ExpenditureId;
    }

    public void setExpenditureId(String expenditureId) {
        ExpenditureId = expenditureId;
    }

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }

    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String clientId) {
        ClientId = clientId;
    }

    public String getPurpose() {
        return Purpose;
    }

    public void setPurpose(String purpose) {
        Purpose = purpose;
    }

    public String getExpenseType() {
        return ExpenseType;
    }

    public void setExpenseType(String expenseType) {
        ExpenseType = expenseType;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getExpendDate() {
        return ExpendDate;
    }

    public void setExpendDate(String expendDate) {
        ExpendDate = expendDate;
    }

    public boolean isExpenseOnlineSaved() {
        return isExpenseOnlineSaved;
    }

    public void setExpenseOnlineSaved(boolean expenseOnlineSaved) {
        isExpenseOnlineSaved = expenseOnlineSaved;
    }
}

 */

/*
public class TransactionListRealm extends RealmObject {
    String Id;
    String customerId;
    String CFirstname;
    String CLastname;
    String BillDate;
    String Time;
    String CrAmt;
    String DrAmt;
    String Purpose;
    String Liter;
    String TransactionId;
    boolean isOnlineDataSaved = true;

    public boolean isOnlineDataSaved() {
        return isOnlineDataSaved;
    }

    public void setOnlineDataSaved(boolean onlineDataSaved) {
        isOnlineDataSaved = onlineDataSaved;
    }

    public String getBillDate() {
        return BillDate;
    }

    public void setBillDate(String billDate) {
        BillDate = billDate;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getCrAmt() {
        return CrAmt;
    }

    public void setCrAmt(String crAmt) {
        CrAmt = crAmt;
    }

    public String getDrAmt() {
        return DrAmt;
    }

    public void setDrAmt(String drAmt) {
        DrAmt = drAmt;
    }

    public String getPurpose() {
        return Purpose;
    }

    public void setPurpose(String purpose) {
        Purpose = purpose;
    }

    public String getLiter() {
        return Liter;
    }

    public void setLiter(String liter) {
        Liter = liter;
    }

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCFirstname() {
        return CFirstname;
    }

    public void setCFirstname(String CFirstname) {
        this.CFirstname = CFirstname;
    }

    public String getCLastname() {
        return CLastname;
    }

    public void setCLastname(String CLastname) {
        this.CLastname = CLastname;
    }

}

 */