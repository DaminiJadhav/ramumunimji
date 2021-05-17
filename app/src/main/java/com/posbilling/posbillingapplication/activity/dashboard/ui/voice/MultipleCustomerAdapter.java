package com.posbilling.posbillingapplication.activity.dashboard.ui.voice;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.outstandinglist.ActivityOutstandingList;
import com.posbilling.posbillingapplication.interfaceclick.OnStateListClick;
import com.posbilling.posbillingapplication.model.realmmodel.CustomerListRealm;
import com.posbilling.posbillingapplication.retrofit.CustomRetroRequest;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MultipleCustomerAdapter extends RecyclerView.Adapter<MultipleCustomerAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<CustomerListRealm> sameUserList;
    private OnStateListClick onStateListClick;

    public MultipleCustomerAdapter(Context mContext, ArrayList<CustomerListRealm> sameUserList, OnStateListClick onStateListClick) {
        this.mContext = mContext;
        this.sameUserList = sameUserList;
        this.onStateListClick = onStateListClick;
    }

    @NonNull
    @Override
    public MultipleCustomerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MultipleCustomerAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_multiple_customert_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MultipleCustomerAdapter.ViewHolder holder, int position) {
        Glide.with(mContext).load(new CustomRetroRequest().imageURL + sameUserList.get(position).getImagePath())
                .into(holder.imageviewOutsnadingProfile);
        if (!TextUtils.isEmpty(sameUserList.get(position).getCustomerId())) {
            holder.textviewOustandingId.setText(sameUserList.get(position).getCustomerId());
        } else {
            holder.textviewOustandingId.setText("-");
        }


        String firstname = "";
        String middlename = "";
        String lastname = "";
        if (sameUserList.get(position).getCLastname() != null) {
            lastname = sameUserList.get(position).getCLastname();
        }
        if (sameUserList.get(position).getCMiddleName() != null) {
            middlename = sameUserList.get(position).getCMiddleName();
        }
        if (sameUserList.get(position).getCFirstname() != null) {
            firstname = sameUserList.get(position).getCFirstname();
        }
        String finalname = "";
        if (TextUtils.isEmpty(middlename) && TextUtils.isEmpty(lastname)) {
            finalname = firstname;
        } else if (TextUtils.isEmpty(middlename)) {
            finalname = firstname + " " + lastname;
        } else {
            finalname = firstname + " " + middlename + " " + lastname;
        }

        holder.textviewOustandingName.setText(finalname);

        String address = "";
        if (sameUserList.get(position).getVillage()!=null){
            address = address+ ""+sameUserList.get(position).getVillage().toString()+" ";
        }

        if (sameUserList.get(position).getTaluka()!=null){
            address = address+""+sameUserList.get(position).getTaluka().toString()+" ";
        }

        if (sameUserList.get(position).getDistrict()!=null){
            address = address+""+sameUserList.get(position).getDistrict().toString();
        }

        if (!TextUtils.isEmpty(address.trim())) {
            holder.textview_address.setText(address);
        }else {
            holder.textview_address.setText("-");
        }

        holder.relativeParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStateListClick.onStateListClick(v,holder.getAdapterPosition());
            }
        });

    }

   public void filter(ArrayList<CustomerListRealm> filterList){
        sameUserList = filterList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return sameUserList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imageviewOutsnadingProfile;
        private TextView textviewOustandingId;
        private TextView textviewOustandingName;
        private TextView textview_address;
        private RelativeLayout relativeParent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeParent = itemView.findViewById(R.id.relativeParent);
            imageviewOutsnadingProfile = itemView.findViewById(R.id.imageviewOutsnadingProfile);
            textviewOustandingId = itemView.findViewById(R.id.textviewOustandingId);
            textviewOustandingName = itemView.findViewById(R.id.textviewOustandingName);
            textview_address = itemView.findViewById(R.id.textview_address);
        }
    }
}
