package com.sunny.contactmanagerroom.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sunny.contactmanagerroom.util.ContactRepository;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private ContactRepository contactRepository;
    private LiveData<List<Contact>> allContacts;
    public ContactViewModel(@NonNull Application application) {
        super(application);
        contactRepository = new ContactRepository(application);
        allContacts = contactRepository.getAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts(){
        return allContacts;
    }

    public void insert(Contact contact){
        contactRepository.insert(contact);
    }

    public void delete(int id){
        contactRepository.delete(id);
    }

    public void update(Contact contact){
        contactRepository.update(contact);
    }
}
