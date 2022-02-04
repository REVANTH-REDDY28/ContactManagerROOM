package com.sunny.contactmanagerroom.ui;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.sunny.contactmanagerroom.R;
import com.sunny.contactmanagerroom.model.Contact;
import com.sunny.contactmanagerroom.model.ContactViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<Contact> contactArrayList;
    Context context;

    public  RecyclerViewAdapter(ArrayList<Contact> list,Context ctx){
        contactArrayList = list;
        context = ctx;
    }

    public RecyclerViewAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_row,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        if(contactArrayList != null){
            Contact contact = contactArrayList.get(position);
            holder.name.setText(contact.getName());
            holder.number.setText(contact.getNumber());
        }else{
            holder.name.setText("no contact");
            holder.number.setText("no number");
        }
    }
    public void setContacts(ArrayList<Contact> contactArrayList){
        this.contactArrayList = contactArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(contactArrayList != null){
            return contactArrayList.size();
        }else{
            return 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView number;
        Button updateContact;
        Button deleteContact;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.row_1_name_textView);
            number = itemView.findViewById(R.id.row_1_number_textView);
            updateContact = itemView.findViewById(R.id.row_2_update_btn);
            deleteContact = itemView.findViewById(R.id.row_2_delete_btn);

            updateContact.setOnClickListener(this);
            deleteContact.setOnClickListener(this);



        }

        @Override
        public void onClick(View view) {
            ContactViewModel contactViewModel = ViewModelProvider.AndroidViewModelFactory
                    .getInstance((Application) context.getApplicationContext())
                    .create(ContactViewModel.class);
            int position = getAdapterPosition();
            switch (view.getId()){
                case R.id.row_2_update_btn:
                    //update the contact
                    position = getAdapterPosition();
                    Contact contact = contactArrayList.get(position);
                    updateDialog(contactViewModel,contact);


                    break;
                case R.id.row_2_delete_btn:
                    //delete the contact

                    position = getAdapterPosition();
                    int contactId = contactArrayList.get(position).getId();
                    contactViewModel.delete(contactId);
            }
        }

        private void updateDialog(ContactViewModel contactViewModel, Contact contact) {
            View view = LayoutInflater.from(context).inflate(R.layout.pop_up_save_contact,null);
            Button updateBtn = view.findViewById(R.id.button_save_contact);
            updateBtn.setText("UPDATE");
            EditText nameView = view.findViewById(R.id.s_name);
            EditText numView = view.findViewById(R.id.s_number);

            nameView.setText(contact.getName());
            numView.setText(contact.getNumber());

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(view);
            AlertDialog dialog = builder.create();
            dialog.show();

            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String name = String.valueOf(nameView.getText());
                    String number = String.valueOf(numView.getText());

                    contact.setName(name);
                    contact.setNumber(number);

                    contactViewModel.update(contact);

                    dialog.dismiss();


                }
            });

        }

    }
}
