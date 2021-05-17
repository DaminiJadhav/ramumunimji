package com.posbilling.posbillingapplication.activity.dashboard.ui.outstanding;

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

/**
 * Created by Android PC (Ankur) on 28,February,2020
 */
public class OutstandingSummuryAdapter extends RecyclerView.Adapter<OutstandingSummuryAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<CustomerListResponse.Data.tblCustomers> customerArraylist;
    private OnRecyclerviewClick onRecyclerviewClick;

    public OutstandingSummuryAdapter(Context mContext, ArrayList<CustomerListResponse.Data.tblCustomers> customerArraylist, OnRecyclerviewClick onRecyclerviewClick) {
        this.mContext = mContext;
        this.customerArraylist = customerArraylist;
        this.onRecyclerviewClick = onRecyclerviewClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OutstandingSummuryAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.outstanding_summury_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /*if (customerArraylist.get(position).getOutstanding()!=null) {
         */
        try {
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

            if (customerArraylist.get(position).getOutstanding() != null && customerArraylist.get(position).getOutstanding().contains(".")) {
                String outstanding = customerArraylist.get(position).getOutstanding();
                String result = outstanding.substring(0, outstanding.indexOf("."));
                holder.textviewCustomerOutstanding.setText(mContext.getString(R.string.Rs)+" "+result);
            } else if (customerArraylist.get(position).getOutstanding() == null) {
                holder.textviewCustomerOutstanding.setText(mContext.getString(R.string.Rs)+" "+"0");
            } else {
                holder.textviewCustomerOutstanding.setText(mContext.getString(R.string.Rs)+" "+customerArraylist.get(position).getOutstanding());
            }

            try {
                //https://ramu.sdaemon.com/images//Ramuji//C136.jpg
                Glide.with(mContext).load(new CustomRetroRequest().imageURL + customerArraylist.get(position).getImagePath())
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
        return customerArraylist.size();
    }

    public void filterList(ArrayList<CustomerListResponse.Data.tblCustomers> filterArraylist) {
        customerArraylist = filterArraylist;
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


