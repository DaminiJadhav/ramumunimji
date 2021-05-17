package com.posbilling.posbillingapplication.activity.cropdetail;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.model.response.ResponseCustomerCropDetails;

import java.util.ArrayList;


public class CropDetailListAdapter extends RecyclerView.Adapter<CropDetailListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ResponseCustomerCropDetails.Data> cropDetailList;
    private OnRecyclerviewClick onRecyclerviewClick;

    public CropDetailListAdapter(Context context, ArrayList<ResponseCustomerCropDetails.Data> cropDetailList, OnRecyclerviewClick onRecyclerviewClick) {
        this.context = context;
        this.cropDetailList = cropDetailList;
        this.onRecyclerviewClick = onRecyclerviewClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CropDetailListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_crop_detail_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!TextUtils.isEmpty(cropDetailList.get(position).getCreatedOn())) {
            String date = cropDetailList.get(position).getCreatedOn().toString();
            String subDate = date.substring(0, 10);
            String yyyy = subDate.substring(0, 4);
            String mm = subDate.substring(5, 7);
            String dd = subDate.substring(8, 10);
            holder.textview_Date_Plantation.setText(dd+"/"+mm+"/"+yyyy);
        } else {
            holder.textview_Date_Plantation.setText("-");
        }

        if (!TextUtils.isEmpty(cropDetailList.get(position).getCropname())) {
            holder.textview_purpose.setText(cropDetailList.get(position).getCropname().toString());
        } else {
            holder.textview_purpose.setText("-");
        }

        if (!TextUtils.isEmpty(cropDetailList.get(position).getCropPeriod())) {
            holder.textview_time.setText(cropDetailList.get(position).getCropPeriod().toString());
        } else {
            holder.textview_time.setText("-");
        }

        if (!TextUtils.isEmpty(cropDetailList.get(position).getPerticidesTimesPerAcre())) {
            float acres = Float.parseFloat(cropDetailList.get(position).getAcres());
            float pesticides = Float.parseFloat(cropDetailList.get(position).getPerticidesTimesPerAcre());
            float valuePesticides = acres * pesticides;
            holder.textview_pesticides.setText(String.valueOf(valuePesticides));
        } else {
            holder.textview_pesticides.setText("-");
        }

        if (!TextUtils.isEmpty(cropDetailList.get(position).getFertiliseTimesPerAcre())) {
            float acres = Float.parseFloat(cropDetailList.get(position).getAcres());
            float fertilzer = Float.parseFloat(cropDetailList.get(position).getFertiliseTimesPerAcre());
            float valueFertilizer = acres * fertilzer;
            holder.textview_fertilizers.setText(String.valueOf(valueFertilizer));
        } else {
            holder.textview_fertilizers.setText("-");
        }

        if (!TextUtils.isEmpty(cropDetailList.get(position).getAcres())) {
            holder.textview_acre.setText(cropDetailList.get(position).getAcres().toString());
        } else {
            holder.textview_acre.setText("-");
        }

        if(cropDetailList.get(position).getId()!=null){
            holder.textview_cropId.setText(cropDetailList.get(position).getTransactionId().toString());
        }else {
            holder.textview_cropId.setText("-");
        }


    }

    @Override
    public int getItemCount() {
        return cropDetailList.size();
    }

    public void filterList(ArrayList<ResponseCustomerCropDetails.Data> filterArraylist) {
        this.cropDetailList = filterArraylist;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textview_Date_Plantation;
        private TextView textview_purpose;
        private TextView textview_time;
        private TextView textview_acre;
        private TextView textview_pesticides;
        private TextView textview_fertilizers;
        private TextView textview_cropId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textview_Date_Plantation = itemView.findViewById(R.id.textview_Date_Plantation);
            textview_purpose = itemView.findViewById(R.id.textview_purpose);
            textview_time = itemView.findViewById(R.id.textview_time);
            textview_acre = itemView.findViewById(R.id.textview_acre);
            textview_pesticides = itemView.findViewById(R.id.textview_pesticides);
            textview_fertilizers = itemView.findViewById(R.id.textview_fertilizers);
            textview_cropId = itemView.findViewById(R.id.textview_cropId);
        }
    }
}
