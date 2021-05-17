package com.posbilling.posbillingapplication.activity.dashboard.ui.salesreport;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.model.MonthYearModel;
import com.posbilling.posbillingapplication.utility.Utility;

import java.util.ArrayList;

import io.realm.Realm;


public class SalesReportAdapter extends RecyclerView.Adapter<SalesReportAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<MonthYearModel> arraylistMonthYear;
    private OnRecyclerviewClick onRecyclerviewClick;
    private Realm realm;

    public SalesReportAdapter(Context mContext, ArrayList<MonthYearModel> arraylistMonthYear, OnRecyclerviewClick onRecyclerviewClick, Realm realm) {
        this.mContext = mContext;
        this.arraylistMonthYear = arraylistMonthYear;
        this.onRecyclerviewClick = onRecyclerviewClick;
        this.realm = realm;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SalesReportAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_report_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String month = arraylistMonthYear.get(position).getMonth().toString();
        String year = arraylistMonthYear.get(position).getYear().toString();
        holder.textview_purpose.setText(month + ", " + year);

        /*try {
          */  SharedPreferences sharedPreferences = Utility.getInstance().getSharedPReference(mContext);
            if (true) {
                holder.textviewLiterThree.setVisibility(View.GONE);
            }

            holder.textviewDebit.setText(mContext.getString(R.string.Rs)+" "+String.valueOf(arraylistMonthYear.get(position).getTotal()));
            if (arraylistMonthYear.get(position).getLiter() != 0 && !TextUtils.isEmpty(String.valueOf(arraylistMonthYear.get(position).getLiter()))) {
                holder.textviewLiterThree.setText(String.valueOf(arraylistMonthYear.get(position).getLiter()));
            } else {
                holder.textviewLiterThree.setText("0");
            }
        /*} catch (Exception ae) {
            Log.e(LOGPOS, "onBindViewHolder: " + ae.getMessage());
        }*/

    }

    @Override
    public int getItemCount() {
        return arraylistMonthYear.size();
    }

    public void filterFunc(ArrayList<MonthYearModel> filterList) {
        this.arraylistMonthYear = filterList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textview_purpose;
     //   private TextView textviewLiterSecond;
        private TextView textviewDebit;
        private TextView textviewLiterThree;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textview_purpose = itemView.findViewById(R.id.textview_purpose);
       //     textviewLiterSecond = itemView.findViewById(R.id.textviewLiterSecond);
            textviewLiterThree = itemView.findViewById(R.id.textviewLiterThree);
            textviewDebit = itemView.findViewById(R.id.textviewDebit);
        }
    }
}
