package com.sunny.contactmanagerroom.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.sunny.contactmanagerroom.model.Contact;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Contact contact);

    @Query("DELETE FROM contact_table")
    void deleteAll();

    @Query("DELETE FROM CONTACT_TABLE WHERE id =:id")
    void deleteContact(int id);

    @Query("SELECT * FROM contact_table")
    LiveData<List<Contact>> getAllContacts();

    @Update
    void updateContact(Contact contact);
}
