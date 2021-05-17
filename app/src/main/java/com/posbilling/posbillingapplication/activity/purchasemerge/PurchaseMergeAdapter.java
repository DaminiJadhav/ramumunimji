package com.posbilling.posbillingapplication.activity.purchasemerge;

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
import com.posbilling.posbillingapplication.activity.merge.MergeAdapter;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.model.response.CustomerListResponse;
import com.posbilling.posbillingapplication.model.response.GetSupplierResponseTwo;
import com.posbilling.posbillingapplication.model.response.GetSuppliersResponse;
import com.posbilling.posbillingapplication.retrofit.CustomRetroRequest;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PurchaseMergeAdapter extends RecyclerView.Adapter<PurchaseMergeAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<GetSupplierResponseTwo.Data> supplierList;
    private OnRecyclerviewClick onRecyclerviewClick;
    private String supplierToStore;

    public PurchaseMergeAdapter(Context mContext, ArrayList<GetSupplierResponseTwo.Data> supplierList, OnRecyclerviewClick onRecyclerviewClick, String supplierToStore) {
        this.mContext = mContext;
        this.supplierList = supplierList;
        this.onRecyclerviewClick = onRecyclerviewClick;
        this.supplierToStore = supplierToStore;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PurchaseMergeAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_merge_item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext).load(new CustomRetroRequest().imageURL + supplierList.get(position).getImagePath())
                .into(holder.imageviewOutsnadingProfile);
        if (!TextUtils.isEmpty(supplierList.get(position).getSupplierId())) {
            holder.textviewOustandingId.setText(supplierList.get(position).getStateId());
        } else {
            holder.textviewOustandingId.setText("-");
        }


        String firstname = "";
        String middlename = "";
        String lastname = "";
/*        if (supplierList.get(position).getSLastname() != null) {
            lastname = supplierList.get(position).getSLastname();
        }
        if (supplierList.get(position).getSMiddleName() != null) {
            middlename = supplierList.get(position).getSMiddleName();
        }*/
        if (supplierList.get(position).getSFirstname() != null) {
            firstname = supplierList.get(position).getSFirstname();
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
        if (supplierList.get(position).getVillage() != null) {
            address = address + "" + supplierList.get(position).getVillage().toString() + " ";
        }

        if (supplierList.get(position).getTaluka() != null) {
            address = address + "" + supplierList.get(position).getTaluka().toString() + " ";
        }

        if (supplierList.get(position).getDistrict() != null) {
            address = address + "" + supplierList.get(position).getDistrict().toString();
        }

        if (!TextUtils.isEmpty(address.trim())) {
            holder.textview_address.setText(address);
        } else {
            holder.textview_address.setText("-");
        }

        holder.relativeParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerviewClick.onReclerViewClick(v, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return supplierList.size();
    }

    public void filterList(ArrayList<GetSupplierResponseTwo.Data> filterArraylist) {
        supplierList = filterArraylist;
        notifyDataSetChanged();
    }


    class  ViewHolder extends RecyclerView.ViewHolder {
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
