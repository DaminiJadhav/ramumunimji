package com.posbilling.posbillingapplication.activity.merge;

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
import com.posbilling.posbillingapplication.activity.dashboard.ui.voice.MultipleCustomerAdapter;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.model.response.CustomerListResponse;
import com.posbilling.posbillingapplication.retrofit.CustomRetroRequest;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MergeAdapter extends RecyclerView.Adapter<MergeAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<CustomerListResponse.Data.tblCustomers> customerList;
    private OnRecyclerviewClick onRecyclerviewClick;
    private String customerToStore;

    public MergeAdapter(Context mContext, ArrayList<CustomerListResponse.Data.tblCustomers> customerList, OnRecyclerviewClick onRecyclerviewClick, String customerToStore) {
        this.mContext = mContext;
        this.customerList = customerList;
        this.onRecyclerviewClick = onRecyclerviewClick;
        this.customerToStore = customerToStore;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MergeAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_merge_item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Glide.with(mContext).load(new CustomRetroRequest().imageURL + customerList.get(position).getImagePath())
                    .into(holder.imageviewOutsnadingProfile);
            if (!TextUtils.isEmpty(customerList.get(position).getCustomerId())) {
                holder.textviewOustandingId.setText(customerList.get(position).getCustomerId());
            } else {
                holder.textviewOustandingId.setText("-");
            }


            String firstname = "";
            String middlename = "";
            String lastname = "";
            if (customerList.get(position).getCLastname() != null) {
                lastname = customerList.get(position).getCLastname();
            }
            if (customerList.get(position).getCMiddleName() != null) {
                middlename = customerList.get(position).getCMiddleName();
            }
            if (customerList.get(position).getCFirstname() != null) {
                firstname = customerList.get(position).getCFirstname();
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
            if (customerList.get(position).getVillage() != null) {
                address = address + "" + customerList.get(position).getVillage().toString() + " ";
            }

            if (customerList.get(position).getTaluka() != null) {
                address = address + "" + customerList.get(position).getTaluka().toString() + " ";
            }

            if (customerList.get(position).getDistrict() != null) {
                address = address + "" + customerList.get(position).getDistrict().toString();
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
        return customerList.size();
    }

    public void filterList(ArrayList<CustomerListResponse.Data.tblCustomers> filterArraylist) {
        customerList = filterArraylist;
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
