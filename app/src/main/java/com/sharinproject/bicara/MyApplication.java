package com.sharinproject.bicara;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Hermansyah on 10/22/2016.
 */

public class MyApplication extends Application {
    private static MyApplication mInstance;
    private Context mContext;
    public DatabaseReference databaseReference;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("forumChat");
        mContext = getApplicationContext();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("forumchat")
                .schemaVersion(1)
                .build();

        Realm.setDefaultConfiguration(config);
        registerActivityLifecycleCallbacks(new MyLifecycleHandler());
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public String getMyAlias(){
        SharedPref sharedPref = new SharedPref(this);
        return sharedPref.getAlias();
    }

    public String getCurrentTimeDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return (dateFormat.format(date)); //2014/08/06 15:59:48
    }

    public String getGoogleID(){
        SharedPref sharedPref = new SharedPref(this);
        return sharedPref.getGoodle_uuid();
    }
}
