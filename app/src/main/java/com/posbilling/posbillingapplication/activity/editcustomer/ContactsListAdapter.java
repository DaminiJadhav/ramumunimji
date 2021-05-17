package com.posbilling.posbillingapplication.activity.editcustomer;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.interfaceclick.OnContactListClick;
import com.posbilling.posbillingapplication.model.Contactsmodel;

import java.util.ArrayList;


public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ViewHolder> {
    private ArrayList<Contactsmodel> contactList;
    private OnContactListClick onContactListClick;
    private RecyclerView recyclerView;

    public ContactsListAdapter(ArrayList<Contactsmodel> contactList, OnContactListClick onContactListClick, RecyclerView recyclerView) {
        this.contactList = contactList;
        this.onContactListClick = onContactListClick;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactsListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (contactList.get(position).getName() != null && !contactList.get(position).getName().equalsIgnoreCase(""))
            holder.textview_name.setText(contactList.get(position).getName());
        if (contactList.get(position).getPhoneNumber() != null && !contactList.get(position).getPhoneNumber().equalsIgnoreCase(""))
            holder.textview_number.setText(contactList.get(position).getPhoneNumber());
        holder.relativelayout_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.radio_contact.setChecked(true);
                holder.radio_contact.setSelected(true);
                recyclerView.setClickable(false);
                recyclerView.setFocusable(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onContactListClick.OnContactClick(holder.getAdapterPosition());
                    }
                }, 170);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }


    public void filterList(ArrayList<Contactsmodel> filteredList) {
        this.contactList = filteredList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textview_name;
        TextView textview_number;
        LinearLayout relativelayout_parent;
        RadioButton radio_contact;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textview_name = itemView.findViewById(R.id.textview_name);
            textview_number = itemView.findViewById(R.id.textview_number);
            relativelayout_parent = itemView.findViewById(R.id.relativelayout_parent);
            radio_contact = itemView.findViewById(R.id.radio_contact);
        }
    }
}
