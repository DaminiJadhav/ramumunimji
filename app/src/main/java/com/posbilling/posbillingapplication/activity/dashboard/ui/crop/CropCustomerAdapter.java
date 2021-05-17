package com.posbilling.posbillingapplication.activity.dashboard.ui.crop;

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
import com.posbilling.posbillingapplication.model.response.CustomerListResponse;
import com.posbilling.posbillingapplication.retrofit.CustomRetroRequest;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CropCustomerAdapter extends RecyclerView.Adapter<CropCustomerAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<CustomerListResponse.Data.tblCustomers> customerArraylist;
    private OnRecyclerviewClick onRecyclerviewClick;

    public CropCustomerAdapter(Context mContext, ArrayList<CustomerListResponse.Data.tblCustomers> customerList, OnRecyclerviewClick onRecyclerviewClick) {
        this.mContext=mContext;
        this.customerArraylist = customerList;
        this.onRecyclerviewClick = onRecyclerviewClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CropCustomerAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.crop_customer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String firstname = "";
        String middlename = "";
        String lastname = "";
        if (customerArraylist.get(position).getCLastname() != null) {
            lastname = customerArraylist.get(position).getCLastname();
        }
        if (customerArraylist.get(position).getCMiddleName() != null) {
            middlename = customerArraylist.get(position).getCMiddleName();
        }
        if (customerArraylist.get(position).getCFirstname() != null) {
            firstname = customerArraylist.get(position).getCFirstname();
        }

        holder.textviewName.setText(firstname + " " + middlename + " " + lastname);

        if (customerArraylist.get(position).getOutstanding() != null) {
            holder.textviewCustomerOutstanding.setText(customerArraylist.get(position).getOutstanding());
        }

        //https://ramu.sdaemon.com/images//Ramuji//C136.jpg
            Glide.with(mContext).load(new CustomRetroRequest().imageURL + customerArraylist.get(position).getImagePath())
                    .into(holder.imageview_profile);


        holder.relativeParenOutstandingItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerviewClick.onReclerViewClick(v, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerArraylist.size();
    }

    public void filterList(ArrayList<CustomerListResponse.Data.tblCustomers> filterArraylist) {
        customerArraylist = filterArraylist;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
