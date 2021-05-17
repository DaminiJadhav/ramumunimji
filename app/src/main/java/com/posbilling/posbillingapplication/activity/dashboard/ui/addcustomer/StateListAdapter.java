package com.posbilling.posbillingapplication.activity.dashboard.ui.addcustomer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.interfaceclick.OnStateListClick;
import com.posbilling.posbillingapplication.model.StateModel;

import java.util.ArrayList;

import static com.posbilling.posbillingapplication.utility.Constants.langaugeCodeEnglish;
import static com.posbilling.posbillingapplication.utility.Constants.languageCodeMarathi;

/**
 * Created by Android PC (Ankur) on 27,February,2020
 */
public class StateListAdapter extends RecyclerView.Adapter<StateListAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<StateModel> stateList;
    private OnStateListClick onStateListClick;
    private String languageCode = "";
    public StateListAdapter(Context mContext, ArrayList<StateModel> stateList, OnStateListClick onStateListClick, String languageCode) {
        this.mContext = mContext;
        this.stateList = stateList;
        this.onStateListClick = onStateListClick;
        this.languageCode = languageCode;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StateListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.state_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (languageCode.equalsIgnoreCase(languageCodeMarathi)){
            holder.textview_name.setText(stateList.get(position).getStateNameMarathi());
        }else if(languageCode.equalsIgnoreCase(langaugeCodeEnglish)){
            holder.textview_name.setText(stateList.get(position).getStateNameEnglish());
        }else{
            holder.textview_name.setText(stateList.get(position).getStateNameEnglish());
        }

        holder.linearlayout_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStateListClick.onStateListClick(v,holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }

    public void filterList(ArrayList<StateModel> filteredStateList) {
        stateList.clear();
        stateList.addAll(filteredStateList);
        notifyDataSetChanged();
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
