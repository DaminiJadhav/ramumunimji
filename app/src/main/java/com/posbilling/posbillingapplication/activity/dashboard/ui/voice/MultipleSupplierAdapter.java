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
import com.posbilling.posbillingapplication.interfaceclick.OnMultipleSupplierListener;
import com.posbilling.posbillingapplication.model.realmmodel.SupplierListRealm;
import com.posbilling.posbillingapplication.retrofit.CustomRetroRequest;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MultipleSupplierAdapter extends RecyclerView.Adapter<MultipleSupplierAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<SupplierListRealm> sameSupplierList;
    private OnMultipleSupplierListener onMultipleSupplierListener;

    public MultipleSupplierAdapter(Context mContext, ArrayList<SupplierListRealm> sameSupplierList, OnMultipleSupplierListener onMultipleSupplierListener) {
        this.mContext = mContext;
        this.sameSupplierList = sameSupplierList;
        this.onMultipleSupplierListener = onMultipleSupplierListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MultipleSupplierAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_multiple_customert_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext).load(new CustomRetroRequest().imageURL + sameSupplierList.get(position).getImagePath())
                .into(holder.imageviewOutsnadingProfile);
        if (!TextUtils.isEmpty(sameSupplierList.get(position).getSupplierId())) {
            holder.textviewOustandingId.setText(sameSupplierList.get(position).getSupplierId());
        } else {
            holder.textviewOustandingId.setText("-");
        }


        String firstname = "";
        String middlename = "";
        String lastname = "";
        if (sameSupplierList.get(position).getSLastname() != null) {
            lastname = sameSupplierList.get(position).getSLastname();
        }
        if (sameSupplierList.get(position).getSMiddleName() != null) {
            middlename = sameSupplierList.get(position).getSMiddleName();
        }
        if (sameSupplierList.get(position).getSFirstname() != null) {
            firstname = sameSupplierList.get(position).getSFirstname();
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
        if (sameSupplierList.get(position).getVillage()!=null){
            address = address+ ""+sameSupplierList.get(position).getVillage().toString()+" ";
        }

        if (sameSupplierList.get(position).getTaluka()!=null){
            address = address+""+sameSupplierList.get(position).getTaluka().toString()+" ";
        }

        if (sameSupplierList.get(position).getDistrict()!=null){
            address = address+""+sameSupplierList.get(position).getDistrict().toString();
        }

        if (!TextUtils.isEmpty(address.trim())) {
            holder.textview_address.setText(address);
        }else {
            holder.textview_address.setText("-");
        }

        holder.relativeParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMultipleSupplierListener.onMultipleSupplierListener(v,holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return sameSupplierList.size();
    }

    public void filter(ArrayList<SupplierListRealm> filterMultipleSupplierList) {
        sameSupplierList = filterMultipleSupplierList;
        notifyDataSetChanged();
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
