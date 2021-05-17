package com.posbilling.posbillingapplication.activity.outstandinglist;

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
import com.posbilling.posbillingapplication.model.response.TransactionListResponse;
import com.posbilling.posbillingapplication.utility.Utility;

import java.util.ArrayList;

/**
 * Created by Android PC (Ankur) on 07,March,2020
 */
public class OutstandigListAdapter extends RecyclerView.Adapter<OutstandigListAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<TransactionListResponse.TransactionList> outsandingList;
    private OnRecyclerviewClick onRecyclerviewClick;

    public OutstandigListAdapter(Context mContext, ArrayList<TransactionListResponse.TransactionList> outsandingList, OnRecyclerviewClick onRecyclerviewClick) {
        this.mContext = mContext;
        this.outsandingList = outsandingList;
        this.onRecyclerviewClick = onRecyclerviewClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OutstandigListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.outstanding_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (TextUtils.isEmpty(outsandingList.get(position).getTransactionId())) {
            holder.textview_id.setText("-");
        } else {
            holder.textview_id.setText(outsandingList.get(position).getTransactionId());
        }

        if (TextUtils.isEmpty(outsandingList.get(position).getPurpose().toString())) {
            holder.textview_purpose.setText("-");
        } else {
            holder.textview_purpose.setText(outsandingList.get(position).getPurpose());
        }

        if (!TextUtils.isEmpty(outsandingList.get(position).getLiter())) {
            holder.textview_litre.setText(outsandingList.get(position).getLiter());
        } else {
            holder.textview_litre.setText("-");
        }

/*        if (holder.sharedPreferences.getString("BusinessId", "").matches("4")) {
            holder.textview_litre.setVisibility(View.VISIBLE);
        }*/

        String creditAmt = outsandingList.get(position).getCrAmt();
        if (creditAmt != null) {
            if (creditAmt.contains(".")) {
                String result = creditAmt.substring(0, creditAmt.indexOf("."));
                holder.textview_Credit.setText(result);
            } else {
                holder.textview_Credit.setText(outsandingList.get(position).getCrAmt());
            }
        }


        String debitAmt = outsandingList.get(position).getDrAmt();
        if (debitAmt != null) {
            if (debitAmt.contains(".")) {
                String result = debitAmt.substring(0, debitAmt.indexOf("."));
                holder.textview_Debit.setText(result);
            } else {
                holder.textview_Debit.setText(outsandingList.get(position).getDrAmt());
            }
        }


        if (outsandingList.get(position).isOnline()) {
            String date = outsandingList.get(position).getBillDate();
            String subDate = date.substring(0, 10);
            String yyyy = subDate.substring(0, 4);
            String mm = subDate.substring(5, 7);
            String dd = subDate.substring(8, 10);
            holder.textview_date.setText(dd + "/" + mm + "/" + yyyy);
            String time = outsandingList.get(position).getTime();
            String subTime = time.substring(0, 5);
            holder.textview_time.setText(subTime);
        } else {
            String date = outsandingList.get(position).getBillDate();
            holder.textview_date.setText(date);
            holder.textview_time.setText("00:00");
        }


        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerviewClick.onReclerViewClick(v, holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return outsandingList.size();
    }

    public void filterList(ArrayList<TransactionListResponse.TransactionList> seeAllFilter) {
        this.outsandingList = seeAllFilter;
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
