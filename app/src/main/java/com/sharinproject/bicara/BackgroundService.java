package com.sharinproject.bicara;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sharinproject.bicara.database.ForumChat;
import com.sharinproject.bicara.database.ForumChatJSON;
import com.sharinproject.bicara.database.RealmController;

import io.realm.Realm;

/**
 * Created by Hermansyah on 10/22/2016.
 */

public class BackgroundService extends Service {


    private ValueEventListener handler;
    int LED_ON_MS = 200;
    int LED_OFF_MS = 1000;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        handler = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot arg0) {
                Log.i("chat", arg0.getValue(String.class));

                String alias = "";
                String message = "";
                String uuid = "";
                String sendat = "";
                ForumChatJSON forumChatJSON = JSON.parseObject(arg0.getValue(String.class), ForumChatJSON.class);
                if (forumChatJSON!=null){
                    alias = forumChatJSON.getChat_alias();
                    message = forumChatJSON.getChat_message();
                    uuid = forumChatJSON.getChat_uuid();
                    sendat = forumChatJSON.getChat_retievetime();
                }

                ForumChat forumChat = new ForumChat();
                Log.i("Alias updated", alias);
                Log.i("Message updated", message);
                Log.i("uuid updated", uuid);
                forumChat.setChat_alias(alias);
                forumChat.setChat_message(message);
                forumChat.setChat_uuid(uuid);
                forumChat.setChat_retievetime(sendat);
                Log.w("tes message 1", forumChat.getChat_message());
                if (!MyLifecycleHandler.isApplicationInForeground() || !MyLifecycleHandler.isApplicationVisible()){
                    if (RealmController.getInstance().getAllData().size()>0){
                        String last_retieve = RealmController.getInstance().getChatPotiton(RealmController.getInstance().getAllData().size()-1).getChat_retievetime();
                        if (!sendat.equals(last_retieve)){
                            Log.i("uuid", uuid);
                            Log.i("last_uuid", last_retieve);
                            RealmController.getInstance().newChat(forumChat);
                            pushNotification(alias, message);
                        }else {
                            Log.w("Firebase", "Same data");
                        }
                    }else {
                        Log.w("tes message", forumChat.getChat_message());
                        RealmController.getInstance().newChat(forumChat);
                    }
                }else {
                    Log.w("tes message 2 ", forumChat.getChat_message());
                    if (RealmController.getInstance().getAllData().size()>0){
                        String last_retieve = RealmController.getInstance().getChatPotiton(RealmController.getInstance().getAllData().size()-1).getChat_retievetime();
                        if (!sendat.equals(last_retieve)){
                            Log.i("uuid", uuid);
                            Log.i("last_uuid", last_retieve);
                            RealmController.getInstance().newChat(forumChat);
                            if (MainActivity.getInstance() != null){
                                MainActivity.getInstance().setRealmData();
                            }
                        }else {
                            Log.w("Firebase", "Same data");
                        }
                    }else {
                        Log.w("tes message 3", forumChat.getChat_message());
                        RealmController.getInstance().newChat(forumChat);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        MyApplication.getInstance().databaseReference.addValueEventListener(handler);
    }

    private void pushNotification(String notificationTitle, String notificationMessage){
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new Notification.Builder(this)
                .setContentTitle(notificationTitle)
                .setContentText(notificationMessage)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .build();
        notification.ledARGB = Color.BLUE;
        notification.flags = Notification.FLAG_AUTO_CANCEL|Notification.FLAG_SHOW_LIGHTS;
        notification.ledOnMS = LED_ON_MS;
        notification.ledOffMS = LED_OFF_MS;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(7432, notification);

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.notification_sound);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
    }
}
