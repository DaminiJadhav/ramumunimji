package com.posbilling.posbillingapplication.fragment.fragmentsupplierreport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.model.response.GetSupplierResponseTwo;
import com.posbilling.posbillingapplication.retrofit.CustomRetroRequest;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SupplierSummuryAdapter extends RecyclerView.Adapter<SupplierSummuryAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<GetSupplierResponseTwo.Data> suppliersList;
    private OnRecyclerviewClick onRecyclerviewClick;

    public SupplierSummuryAdapter(Context mContext, ArrayList<GetSupplierResponseTwo.Data> suppliersList, OnRecyclerviewClick onRecyclerviewClick) {
        this.mContext = mContext;
        this.suppliersList = suppliersList;
        this.onRecyclerviewClick = onRecyclerviewClick;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SupplierSummuryAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.outstanding_summury_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            String firstname = "";
            String middlename = "";
            String lastname = "";
/*            if (suppliersList.get(position).getSLastname() != null) {
                lastname = suppliersList.get(position).getSLastname();
            }
            if (suppliersList.get(position).getSMiddleName() != null) {
                middlename = suppliersList.get(position).getSMiddleName();
            }*/
            if (suppliersList.get(position).getSFirstname() != null) {
                firstname = suppliersList.get(position).getSFirstname();
            }

            holder.textviewName.setText(firstname + " " + middlename + " " + lastname);

            if (suppliersList.get(position).getOutstanding() != null && suppliersList.get(position).getOutstanding().contains(".")) {
                String outstanding = suppliersList.get(position).getOutstanding();
                String result = outstanding.substring(0, outstanding.indexOf("."));
                holder.textviewCustomerOutstanding.setText(mContext.getString(R.string.Rs)+" "+result);
            } else if (suppliersList.get(position).getOutstanding() == null) {
                holder.textviewCustomerOutstanding.setText(mContext.getString(R.string.Rs)+" "+"0");
            } else {
                holder.textviewCustomerOutstanding.setText(mContext.getString(R.string.Rs)+" "+suppliersList.get(position).getOutstanding());
            }

            try {
                //https://ramu.sdaemon.com/images//Ramuji//C136.jpg
                Glide.with(mContext).load(new CustomRetroRequest().imageURL + suppliersList.get(position).getImagePath())
                        .into(holder.imageview_profile);
            } catch (Exception ae) {
                ae.printStackTrace();
            }

            holder.relativeParenOutstandingItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecyclerviewClick.onReclerViewClick(v, holder.getAdapterPosition());
                }
            });
        /*}else {
            holder.itemView.setVisibility(View.GONE);
*//*            holder.setVisibility(View.GONE);*//*
        }*/
        } catch (Exception ae) {
            ae.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return suppliersList.size();
    }

    public void filterList(ArrayList<GetSupplierResponseTwo.Data> filterArraylist) {
        suppliersList = filterArraylist;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageview_profile;
        TextView textviewCustomerOutstanding;
        TextView textviewName;
        RelativeLayout relativeParenOutstandingItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview_profile = itemView.findViewById(R.id.imageview_profile);
            textviewCustomerOutstanding = itemView.findViewById(R.id.textviewCustomerOutstanding);
            textviewName = itemView.findViewById(R.id.textviewName);
            relativeParenOutstandingItem = itemView.findViewById(R.id.relativeParenOutstandingItem);
        }
    }
}
