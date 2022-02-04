package com.sunny.contactmanagerroom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sunny.contactmanagerroom.model.Contact;
import com.sunny.contactmanagerroom.model.ContactViewModel;
import com.sunny.contactmanagerroom.ui.RecyclerViewAdapter;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;


    ContactViewModel contactViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        contactViewModel = ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication())
                .create(ContactViewModel.class);



//        Contact c1 =new Contact( "a","1");
//        Contact c2 =new Contact( "b","1");
//        Contact c3 =new Contact( "c","1");
//        Contact c4 =new Contact( "d","1");
//        Contact c5 =new Contact( "e","1");
//        Contact c6 =new Contact( "f","1");
//        Contact c7 =new Contact( "g","1");
//
//        ArrayList<Contact> contactArrayList = new ArrayList<>();
//        contactArrayList.add(c1);
//        contactArrayList.add(c2);
//        contactArrayList.add(c3);
//        contactArrayList.add(c4);
//        contactArrayList.add(c5);
//        contactArrayList.add(c6);
//        contactArrayList.add(c7);




        recyclerView = findViewById(R.id.my_recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setHasFixedSize(true);

        adapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);

//        if(!contactArrayList.isEmpty()){
//            Log.d("loli", "onCreate: "+contactArrayList);
//            adapter = new RecyclerViewAdapter(contactArrayList,getApplicationContext());
//            recyclerView.setAdapter(adapter);
//        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            AlertDialog alertDialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            View popup_view = MainActivity.this.getLayoutInflater().inflate(R.layout.pop_up_save_contact, null);
            builder.setView(popup_view);

            alertDialog = builder.create();
            alertDialog.show();


            Button s_button = popup_view.findViewById(R.id.button_save_contact);
            s_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //save new contacts here
                    EditText name = popup_view.findViewById(R.id.s_name);
                    EditText number = popup_view.findViewById(R.id.s_number);
                    Contact contact = new Contact();
                    contact.setName(String.valueOf(name.getText()));
                    contact.setNumber(String.valueOf(number.getText()));
                    contactViewModel.insert(contact);

                    alertDialog.dismiss();

                }
            });


        });

        contactViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                //update the cached copy of contacts in the adapter
                ArrayList<Contact> c = new ArrayList<>(contacts);
                adapter.setContacts(c);

            }
        });



    }
}