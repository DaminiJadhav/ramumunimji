package com.posbilling.posbillingapplication.activity.dashboard.ui.garage;

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
import com.posbilling.posbillingapplication.activity.cropdetail.CropDetailListAdapter;
import com.posbilling.posbillingapplication.activity.dashboard.ActivityDashboard;
import com.posbilling.posbillingapplication.interfaceclick.OnRecyclerviewClick;
import com.posbilling.posbillingapplication.model.realmmodel.CustomerListRealm;
import com.posbilling.posbillingapplication.model.response.ResponseVehicleCustomerList;
import com.posbilling.posbillingapplication.retrofit.CustomRetroRequest;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;

public class AdapterVehicleSummury extends RecyclerView.Adapter<AdapterVehicleSummury.ViewHolder> {
    private Context mContext;
    private ArrayList<ResponseVehicleCustomerList.Data> vehicleSummuryList;
    private OnRecyclerviewClick onRecyclerviewClick;
    private Realm realm;

    public AdapterVehicleSummury(Context mContext, ArrayList<ResponseVehicleCustomerList.Data> vehicleSummuryList, OnRecyclerviewClick onRecyclerviewClick, Realm realm) {
        this.mContext = mContext;
        this.realm = realm;
        this.vehicleSummuryList = vehicleSummuryList;
        this.onRecyclerviewClick = onRecyclerviewClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterVehicleSummury.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_summury_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(mContext)
                .load(new CustomRetroRequest().imageURL + vehicleSummuryList.get(position).getImagePath())
                .into(holder.imageview_profile);

        RealmResults<CustomerListRealm> customerListRealmModels = realm.where(CustomerListRealm.class).equalTo("CustomerId", vehicleSummuryList.get(position).getCustomerId())
                .findAll();


        String firstname = "";
        String middlename = "";
        String lastname = "";
        if (customerListRealmModels.get(0).getCLastname() != null) {
            lastname = customerListRealmModels.get(0).getCLastname();
        }
        if (customerListRealmModels.get(0).getCMiddleName() != null) {
            middlename = customerListRealmModels.get(0).getCMiddleName();
        }
        if (customerListRealmModels.get(0).getCFirstname() != null) {
            firstname = customerListRealmModels.get(0).getCFirstname();
        }
        String finalname = "";
        if (TextUtils.isEmpty(middlename) && TextUtils.isEmpty(lastname)) {
            finalname = firstname;
        } else if (TextUtils.isEmpty(middlename)) {
            finalname = firstname + " " + lastname;
        } else {
            finalname = firstname + " " + middlename + " " + lastname;
        }

        if (vehicleSummuryList.get(position).getVehicleNo()!=null){
            holder.textviewVnumber.setText(vehicleSummuryList.get(position).getVehicleNo().toString());
        }else {
            holder.textviewVnumber.setText("-");
        }


        holder.textviewName.setText(finalname);

        String date = vehicleSummuryList.get(position).getDueDate();
        String subDate = date.substring(0, 10);
        String yyyy = subDate.substring(0,4);
        String mm = subDate.substring(5,7);
        String dd = subDate.substring(8,10);
        holder.textviewDate.setText(dd+"/"+mm+"/"+yyyy);
        holder.textviewId.setText(vehicleSummuryList.get(position).getCustomerId());

        holder.relativeParentVehicleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerviewClick.onReclerViewClick(v,holder.getAdapterPosition());
            }
        });

        holder.textviewType.setText(vehicleSummuryList.get(position).getVehicleType());

        //private TextView textviewDate;
        //private TextView textviewId */
    }

    @Override
    public int getItemCount() {
        return vehicleSummuryList.size();
    }

    public void filterList(ArrayList<ResponseVehicleCustomerList.Data> filterArraylist) {
        vehicleSummuryList = filterArraylist;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imageview_profile;
        private TextView textviewName;
        private TextView textviewDate;
        private TextView textviewVnumber;
        private TextView textviewId;
        private RelativeLayout relativeParentVehicleItem;
        private TextView textviewType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeParentVehicleItem = itemView.findViewById(R.id.relativeParentVehicleItem);
            imageview_profile = itemView.findViewById(R.id.imageview_profile);
            textviewName = itemView.findViewById(R.id.textviewName);
            textviewDate = itemView.findViewById(R.id.textviewDate);
            textviewId = itemView.findViewById(R.id.textviewId);
            textviewType = itemView.findViewById(R.id.textviewType);
            textviewVnumber = itemView.findViewById(R.id.textviewVnumber);
        }
    }
}
