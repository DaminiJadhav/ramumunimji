package com.posbilling.posbillingapplication.activity.dashboard.ui.expenesreport;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.dashboard.ui.garage.AdapterVehicleSummury;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.model.response.ExpenseResponse;


import java.util.ArrayList;

import io.realm.Realm;

public class ExpenseReportAdapter extends RecyclerView.Adapter<ExpenseReportAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<ExpenseResponse.Data> expensereportsList;
    private OnRecyclerviewClick onRecyclerviewClick;
    private Realm realm;

    public ExpenseReportAdapter(Context mContext, ArrayList<ExpenseResponse.Data> expensereportsList, OnRecyclerviewClick onRecyclerviewClick, Realm realm) {
        this.mContext = mContext;
        this.expensereportsList = expensereportsList;
        this.onRecyclerviewClick = onRecyclerviewClick;
        this.realm = realm;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExpenseReportAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_reports_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (expensereportsList.get(position).getPurpose() != null) {
            holder.textviewPurpose.setText(expensereportsList.get(position).getPurpose());
        } else {
            holder.textviewPurpose.setText("-");
        }
        holder.textviewName.setText(expensereportsList.get(position).getExpenseType());
        holder.textviewId.setText(expensereportsList.get(position).getExpenditureId());
        if (expensereportsList.get(position).getExpendDate() != null) {
                String date = expensereportsList.get(position).getExpendDate();
                String subDate = date.substring(0, 10);
                String yyyy = subDate.substring(0, 4);
                String mm = subDate.substring(5, 7);
                String dd = subDate.substring(8, 10);
                holder.textviewdate.setText(dd + "/" + mm + "/" + yyyy);

        } else {
            holder.textviewdate.setText("-");
        }


        if (expensereportsList.get(position).getAmount()!=null){
            holder.textviewamount.setText(mContext.getString(R.string.Rs)+" "+removeContainsDot(expensereportsList.get(position).getAmount()));
        }else {
            holder.textviewamount.setText(mContext.getString(R.string.Rs)+" "+"0");
        }

    }

    private String removeContainsDot(String amount){
        if (amount!=null && !TextUtils.isEmpty(amount)) {
            if (amount.contains(".")) {
                String result = amount.substring(0, amount.indexOf("."));
                return result;
            } else {
                return amount;
            }
        }
        return "0";
    }

    @Override
    public int getItemCount() {
        return expensereportsList.size();
    }

    public void filterList(ArrayList<ExpenseResponse.Data> filterArraylist) {
        expensereportsList = filterArraylist;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textviewPurpose;
        private TextView textviewName;
        private TextView textviewId;
        private TextView textviewdate;
        private TextView textviewamount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textviewamount = itemView.findViewById(R.id.textviewamount);
            textviewPurpose = itemView.findViewById(R.id.textviewPurpose);
            textviewName = itemView.findViewById(R.id.textviewName);
            textviewId = itemView.findViewById(R.id.textviewId);
            textviewdate = itemView.findViewById(R.id.textviewdate);
        }
    }
}
