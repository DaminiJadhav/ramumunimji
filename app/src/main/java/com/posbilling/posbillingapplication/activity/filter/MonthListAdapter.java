package com.posbilling.posbillingapplication.activity.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.model.StateModel;

import java.util.ArrayList;

import static com.posbilling.posbillingapplication.utility.Constants.langaugeCodeEnglish;
import static com.posbilling.posbillingapplication.utility.Constants.languageCodeMarathi;

public class MonthListAdapter extends RecyclerView.Adapter<MonthListAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<String> MonthList;
    private OnRecyclerviewClick onRecyclerviewClick;
    private String languageCode = "";
    public MonthListAdapter(Context mContext, ArrayList<String> MonthList, OnRecyclerviewClick onRecyclerviewClick) {
        this.mContext = mContext;
        this.MonthList = MonthList;
        this.onRecyclerviewClick = onRecyclerviewClick;
        this.languageCode = languageCode;
    }

    @NonNull
    @Override
    public MonthListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MonthListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.state_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MonthListAdapter.ViewHolder holder, int position) {

            holder.textview_name.setText(MonthList.get(position));


        holder.linearlayout_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerviewClick.onReclerViewClick(v,holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return MonthList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textview_name;
        private LinearLayout linearlayout_parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textview_name = itemView.findViewById(R.id.textview_name);
            linearlayout_parent = itemView.findViewById(R.id.linearlayout_parent);
        }
    }
}
