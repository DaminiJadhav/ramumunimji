package com.posbilling.posbillingapplication.activity.edittransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.editcustomer.EditCustomerContractor;
import com.posbilling.posbillingapplication.activity.merge.ActivityMerge;
import com.posbilling.posbillingapplication.lib.BaseActivity;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.model.realmmodel.CustomerListRealm;
import com.posbilling.posbillingapplication.model.request.MergeRequest;
import com.posbilling.posbillingapplication.model.request.TransactionRequest;
import com.posbilling.posbillingapplication.model.response.TransactionResponse;
import com.posbilling.posbillingapplication.utility.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;


public class ActivityEditTransaction extends BaseActivity implements EditTransactionContractor.View {

    private EditTransactionContractor.Presenter mPresenter;
    private MaterialDialog materialDialog;

    @BindView(R.id.edittext_enterId)
    EditText edittext_enterId;
    @BindView(R.id.edittext_enterDrAmount)
    EditText edittext_enterDrAmount;
    @BindView(R.id.edittext_enterDate)
    EditText edittext_enterDate;
    @BindView(R.id.edittext_enterPurpose)
    EditText edittext_enterPurpose;
    @BindView(R.id.edittext_enterCrAmount)
    EditText edittext_enterCrAmount;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;


    @OnClick(R.id.relativeDelete)
    void relativeDelete() {
        if (Utility.getInstance().isOnline(ActivityEditTransaction.this)) {
            LayoutInflater layoutInflater = LayoutInflater.from(ActivityEditTransaction.this);
            View view1 = layoutInflater.inflate(R.layout.logout_dialogue, null);
            TextView yes = view1.findViewById(R.id.button_yes);
            TextView text_title = view1.findViewById(R.id.text_title);
            TextView no = view1.findViewById(R.id.button_no);
            TextView text_logout = view1.findViewById(R.id.text_logout);
            text_logout.setText(getString(R.string.delete_confirmation));
            text_title.setText(getString(R.string.delete));
            yes.setText(getString(R.string.yes));
            no.setText(getString(R.string.no));
            materialDialog = new MaterialDialog.Builder(ActivityEditTransaction.this)
                    .customView(view1, false)
                    .show();
            materialDialog.setCanceledOnTouchOutside(false);
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteRequest();
                    //materialDialog.dismiss();
                    // mPresenter.getLogout(userId, privateKey, mContext);

                }
            });

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    materialDialog.dismiss();
                }
            });
        } else {
            Toast.makeText(this, "" + getString(R.string.please_check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteRequest() {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setRegId(Utility.getInstance().getclientRegId(ActivityEditTransaction.this));
        transactionRequest.setLang("1");
        transactionRequest.setDeviceId("");
        SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(ActivityEditTransaction.this);
        transactionRequest.setBusinessType(sharedPreferences.getString("BusinessId", ""));
        ArrayList<TransactionRequest.CustomerListClass> customerListClassArrayList = new ArrayList<>();
        TransactionRequest.CustomerListClass customerListClass = new TransactionRequest().new CustomerListClass();

        customerListClass.setId(getIntent().getStringExtra("customerID"));
        customerListClass.setCustomerId(getIntent().getStringExtra("customerIdToStore"));
        customerListClass.setName(getIntent().getStringExtra("customerName"));

        ArrayList<TransactionRequest.CustomerListClass.TransactionsListClass> transactionsListClassArrayList = new ArrayList<>();
        TransactionRequest.CustomerListClass.TransactionsListClass transactionsListClass = new TransactionRequest().new CustomerListClass().new TransactionsListClass();
        transactionsListClass.setAmount("");
        transactionsListClass.setLiter("");
        transactionsListClass.setPurpose("");
        transactionsListClass.setTransactionId(getIntent().getStringExtra("transactionId"));
        transactionsListClass.setTransactionType("3");
        Date date = null;
        transactionsListClass.setDate(date);
        transactionsListClass.setTime("");
        transactionsListClass.setTransId(getIntent().getStringExtra("transId"));
        transactionsListClassArrayList.add(transactionsListClass);
        customerListClass.setTransactionsList(transactionsListClassArrayList);
        customerListClassArrayList.add(customerListClass);
        transactionRequest.setCustomerList(customerListClassArrayList);
        if (Utility.getInstance().isOnline(ActivityEditTransaction.this)) {
            Utility.getInstance().showProgressDialogue(ActivityEditTransaction.this);
            mPresenter.deleteTransaction(transactionRequest);
        }else {
            Toast.makeText(this, ""+getString(R.string.you_are_offline), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_edit_transaction;
    }

    @Override
    protected void setPresenter() {
        mPresenter = new EditTransactionPresenter(this);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar_title.setText(getString(R.string.transaction));
        getIntentData();
    }

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

    private void getIntentData() {
        if (getIntent() != null) {
            edittext_enterId.setText(getIntent().getStringExtra("transactionId"));

            String date = getIntent().getStringExtra("date");
            String subDate = date.substring(0, 10);
            String yyyy = subDate.substring(0, 4);
            String mm = subDate.substring(5, 7);
            String dd = subDate.substring(8, 10);
            edittext_enterDate.setText(dd + "/" + mm + "/" + yyyy);
            edittext_enterPurpose.setText(getIntent().getStringExtra("purpose"));
            if (getIntent().getStringExtra("debitAmount") != null) {
                edittext_enterDrAmount.setText(removeContainsDot(getIntent().getStringExtra("debitAmount")));
            } else {
                edittext_enterDrAmount.setText("0");
            }

            if (getIntent().getStringExtra("creditAmount") != null) {
                edittext_enterCrAmount.setText(removeContainsDot(getIntent().getStringExtra("creditAmount")));
            } else {
                edittext_enterCrAmount.setText("0");
            }
        }
    }

    @Override
    public void postDeleteSuccess(TransactionResponse body) {
        Utility.getInstance().dismissProgress();
        Toast.makeText(this, getString(R.string.transaction_delete), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void postDeleteFailure(String s) {
        Utility.getInstance().dismissProgress();
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
    }
}
