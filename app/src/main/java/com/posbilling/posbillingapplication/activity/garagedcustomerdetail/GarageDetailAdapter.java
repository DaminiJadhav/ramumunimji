package com.posbilling.posbillingapplication.activity.garagedcustomerdetail;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.merge.MergeAdapter;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.model.response.ResponseVehicleCustomerList;

import java.util.ArrayList;

public class GarageDetailAdapter extends RecyclerView.Adapter<GarageDetailAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<ResponseVehicleCustomerList.Data> vehicleSummuryList;
    private OnRecyclerviewClick onRecyclerviewClick;

    public GarageDetailAdapter(Context mContext, ArrayList<ResponseVehicleCustomerList.Data> vehicleSummuryList, OnRecyclerviewClick onRecyclerviewClick) {
        this.mContext = mContext;
        this.vehicleSummuryList = vehicleSummuryList;
        this.onRecyclerviewClick = onRecyclerviewClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GarageDetailAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_garage_detail_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (vehicleSummuryList.get(position).getVehicleModel() != null) {
            holder.textview_vehiclename.setText(vehicleSummuryList.get(position).getVehicleModel());
        } else {
            holder.textview_vehiclename.setText("-");
        }
        String date = vehicleSummuryList.get(position).getDueDate();
        String subDate = date.substring(0, 10);
        String yyyy = subDate.substring(0, 4);
        String mm = subDate.substring(5, 7);
        String dd = subDate.substring(8, 10);
        holder.textivew_dueDate.setText(dd + "/" + mm + "/" + yyyy);

        String dateTwo = vehicleSummuryList.get(position).getServieDate();
        String subDateTwo = dateTwo.substring(0, 10);
        String yyyy2 = subDateTwo.substring(0, 4);
        String mm2 = subDateTwo.substring(5, 7);
        String dd2 = subDateTwo.substring(8, 10);
        holder.textview_service_Date.setText(dd2 + "/" + mm2 + "/" + yyyy2);

        if (vehicleSummuryList.get(position).getTransactionId() != null) {
            holder.textview_Date_Plantation.setText(vehicleSummuryList.get(position).getTransactionId());
        } else {
            holder.textview_Date_Plantation.setText("-");
        }

        if (vehicleSummuryList.get(position).getComment() != null && !TextUtils.isEmpty(vehicleSummuryList.get(position).getComment())) {
            holder.textview_time.setText(vehicleSummuryList.get(position).getComment());
        } else {
            holder.textview_time.setText("-");
        }


    }

    @Override
    public int getItemCount() {
        return vehicleSummuryList.size();
    }

    public void filterList(ArrayList<ResponseVehicleCustomerList.Data> filterArraylist) {
        this.vehicleSummuryList = filterArraylist;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textview_vehiclename;
        private TextView textivew_dueDate;
        private TextView textview_service_Date;
        private TextView textview_Date_Plantation;
        private TextView textview_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textview_Date_Plantation = itemView.findViewById(R.id.textview_Date_Plantation);
            textview_vehiclename = itemView.findViewById(R.id.textview_vehiclename);
            textivew_dueDate = itemView.findViewById(R.id.textivew_dueDate);
            textview_service_Date = itemView.findViewById(R.id.textview_service_Date);
            textview_time = itemView.findViewById(R.id.textview_time);
        }
    }
}
