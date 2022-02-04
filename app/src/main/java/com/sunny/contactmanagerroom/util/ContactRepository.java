package com.sunny.contactmanagerroom.util;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.sunny.contactmanagerroom.data.ContactDao;
import com.sunny.contactmanagerroom.data.ContactRoomDatabase;
import com.sunny.contactmanagerroom.model.Contact;

import java.util.List;

public class ContactRepository {

    private ContactDao mContactDao;
    private LiveData<List<Contact>> mAllContacts;

    public ContactRepository(Application application) {
        //we can also get data from an remote API and then put it on diff.  list

        ContactRoomDatabase db = ContactRoomDatabase.getDatabase(application);
        mContactDao = db.contactDao();
        mAllContacts = mContactDao.getAllContacts();

    }

    public LiveData<List<Contact>> getAllContacts(){
        return mAllContacts;
    }

    public void insert(Contact contact){
        new insertAsyncTask(mContactDao).execute(contact);
    }

    public void delete(int id){
        new deleteAsyncTask(mContactDao).execute(id);
    }

    public void update(Contact contact){
        new updateAsyncTask(mContactDao).execute(contact);
    }




    private class insertAsyncTask extends AsyncTask<Contact,Void,Void> {
        private ContactDao asyncTaskDao;
        public insertAsyncTask(ContactDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Contact... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private class deleteAsyncTask extends AsyncTask<Integer,Void,Void>{
        private ContactDao asyncTaskDao;
        public deleteAsyncTask(ContactDao mContactDao) {
            asyncTaskDao = mContactDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            asyncTaskDao.deleteContact(integers[0]);
            return null;
        }
    }

    private class updateAsyncTask extends AsyncTask<Contact,Void,Void>{
        ContactDao asyncTaskDao;
        public updateAsyncTask(ContactDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            asyncTaskDao.updateContact(contacts[0]);

            return null;
        }
    }
}
