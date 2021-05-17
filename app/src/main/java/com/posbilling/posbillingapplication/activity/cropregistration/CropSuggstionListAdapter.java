package com.posbilling.posbillingapplication.activity.cropregistration;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.model.response.ResponseCropDropDown;

import java.util.ArrayList;

public class CropSuggstionListAdapter extends RecyclerView.Adapter<CropSuggstionListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ResponseCropDropDown.Data> arrayListCropSugegstions;
    private OnRecyclerviewClick onRecyclerviewClick;

    public CropSuggstionListAdapter(Context context, ArrayList<ResponseCropDropDown.Data> arrayListCropSugegstions, OnRecyclerviewClick onRecyclerviewClick) {
        this.context = context;
        this.arrayListCropSugegstions = arrayListCropSugegstions;
        this.onRecyclerviewClick = onRecyclerviewClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CropSuggstionListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cropsuggestionlistitem, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!TextUtils.isEmpty(arrayListCropSugegstions.get(position).getCropname())) {
            holder.textview_name.setText(arrayListCropSugegstions.get(position).getCropname().toString());
        } else {
            holder.textview_name.setText("-");
        }

        if (!TextUtils.isEmpty(arrayListCropSugegstions.get(position).getPerticidesTimesPerAcre())) {
            holder.textview_pesticides_amount.setText(arrayListCropSugegstions.get(position).getPerticidesTimesPerAcre().toString());
        } else {
            holder.textview_pesticides_amount.setText("-");
        }

        if (!TextUtils.isEmpty(arrayListCropSugegstions.get(position).getFertiliseTimesPerAcre())) {
            holder.textviewfertilizer_amount.setText(arrayListCropSugegstions.get(position).getFertiliseTimesPerAcre().toString());
        } else {
            holder.textviewfertilizer_amount.setText("-");
        }

        holder.linearlayout_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerviewClick.onReclerViewClick(v, holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayListCropSugegstions.size();
    }

    public void filterList(ArrayList<ResponseCropDropDown.Data> filterArraylist) {
        arrayListCropSugegstions = filterArraylist;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textview_name;
        private TextView textviewfertilizer_amount;
        private TextView textview_pesticides_amount;
        private LinearLayout linearlayout_parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearlayout_parent = itemView.findViewById(R.id.linearlayout_parent);
            textview_name = itemView.findViewById(R.id.textview_name);
            textviewfertilizer_amount = itemView.findViewById(R.id.textviewfertilizer_amount);
            textview_pesticides_amount = itemView.findViewById(R.id.textview_pesticides_amount);
        }
    }
}
