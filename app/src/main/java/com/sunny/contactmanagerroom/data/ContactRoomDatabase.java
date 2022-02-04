package com.sunny.contactmanagerroom.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.sunny.contactmanagerroom.model.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactRoomDatabase extends RoomDatabase {

    public abstract ContactDao contactDao();

    private static volatile ContactRoomDatabase INSTANCE;
//    private static final int NUMBER_OF_THREADS = 4;


    public static ContactRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null){
            synchronized (ContactRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ContactRoomDatabase.class,"contact_database")

                            .build();
                }
            }
        }
        return INSTANCE;
    }

//    private static RoomDatabase.Callback roomDatabaseCallback =
//            new RoomDatabase.Callback(){
//                class PopulateDbAsync extends AsyncTask<Void,Void,Void> {
//                    private final ContactDao contactDao;
//                    public PopulateDbAsync(ContactRoomDatabase db) {
//                        contactDao = db.contactDao();
//                    }
//
//                    @Override
//                    protected Void doInBackground(Void... voids) {
////                        contactDao.deleteAll();
////                        Contact c1 = new Contact("hello","1");
////                        contactDao.insert(c1);
//
//                        return null;
//                    }
//                }
//
//                @Override
//                public void onOpen(@NonNull SupportSQLiteDatabase db) {
//                    super.onOpen(db);
//                    new PopulateDbAsync(INSTANCE).execute();
//                }
//            };

}
