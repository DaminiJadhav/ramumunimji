package com.posbilling.posbillingapplication.activity.supplieractivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.model.response.GetSuppliersOutstandingResponse;
import com.posbilling.posbillingapplication.utility.Utility;

import java.util.ArrayList;

public class SupplierListAdapter extends RecyclerView.Adapter<SupplierListAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<GetSuppliersOutstandingResponse.Data.tblKharediInvices> supplierDetailList;
    private OnRecyclerviewClick onRecyclerviewClick;

    public SupplierListAdapter(Context mContext, ArrayList<GetSuppliersOutstandingResponse.Data.tblKharediInvices> supplierDetailList, OnRecyclerviewClick onRecyclerviewClick) {
        this.mContext = mContext;
        this.supplierDetailList = supplierDetailList;
        this.onRecyclerviewClick = onRecyclerviewClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SupplierListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.supplier_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (TextUtils.isEmpty(supplierDetailList.get(position).getTransactionId())) {
            holder.textview_id.setText("-");
        } else {
            holder.textview_id.setText(supplierDetailList.get(position).getTransactionId());
        }

        if (TextUtils.isEmpty(supplierDetailList.get(position).getPurpose().toString())) {
            holder.textview_purpose.setText("-");
        } else {
            holder.textview_purpose.setText(supplierDetailList.get(position).getPurpose());
        }

        if (!TextUtils.isEmpty(supplierDetailList.get(position).getLiter())) {
            holder.textview_litre.setText(supplierDetailList.get(position).getLiter());
        } else {
            holder.textview_litre.setText("-");
        }

        if (holder.sharedPreferences.getString("BusinessId", "").matches("4")) {
            holder.textview_litre.setVisibility(View.GONE);
        }

        String creditAmt = supplierDetailList.get(position).getCrAmt();
        if (creditAmt != null) {
            if (creditAmt.contains(".")) {
                String result = creditAmt.substring(0, creditAmt.indexOf("."));
                holder.textview_Debit.setText(result);
            } else {
                holder.textview_Debit.setText(supplierDetailList.get(position).getCrAmt());
            }
        }


        String debitAmt = supplierDetailList.get(position).getDrAmt();
        if (debitAmt != null) {
            if (debitAmt.contains(".")) {
                String result = debitAmt.substring(0, debitAmt.indexOf("."));
                holder.textview_Credit.setText(result);
            } else {
                holder.textview_Credit.setText(supplierDetailList.get(position).getDrAmt());
            }
        }



            String date = supplierDetailList.get(position).getBillDate();
            String subDate = date.substring(0, 10);
            String yyyy = subDate.substring(0, 4);
            String mm = subDate.substring(5, 7);
            String dd = subDate.substring(8, 10);
            holder.textview_date.setText(dd + "/" + mm + "/" + yyyy);
            String time = supplierDetailList.get(position).getTime();
            String subTime = time.substring(0, 5);
            holder.textview_time.setText(subTime);



        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerviewClick.onReclerViewClick(v,holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return supplierDetailList.size();
    }

    public void filterList(ArrayList<GetSuppliersOutstandingResponse.Data.tblKharediInvices> finalFilterList) {
        this.supplierDetailList = finalFilterList;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textview_id;
        TextView textview_purpose;
        TextView textview_Debit;
        TextView textview_Credit;
        TextView textview_date;
        TextView textview_time;
        TextView textview_litre;
        RelativeLayout parent_layout;

        private SharedPreferences sharedPreferences;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textview_id = itemView.findViewById(R.id.textview_id);
            textview_purpose = itemView.findViewById(R.id.textview_purpose);
            textview_Debit = itemView.findViewById(R.id.textview_Debit);
            textview_Credit = itemView.findViewById(R.id.textview_Credit);
            textview_date = itemView.findViewById(R.id.textview_date);
            textview_time = itemView.findViewById(R.id.textview_time);
            textview_litre = itemView.findViewById(R.id.textview_litre);
            parent_layout = itemView.findViewById(R.id.parent_layout);
            sharedPreferences = Utility.getInstance().getSharedPReference(mContext);
        }
    }
}
