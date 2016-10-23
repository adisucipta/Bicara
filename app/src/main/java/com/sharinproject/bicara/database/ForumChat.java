package com.sharinproject.bicara.database;

import com.sharinproject.bicara.MyApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by Hermansyah on 10/22/2016.
 */

public class ForumChat extends RealmObject {
    private String chat_alias;
    private String chat_message;
    private String chat_retievetime;
    private String chat_uuid;

    public ForumChat(){
        this.chat_retievetime = MyApplication.getInstance().getCurrentTimeDate();
    }
    public String getChat_alias() {
        return chat_alias;
    }

    public String getChat_message() {
        return chat_message;
    }

    public String getChat_retievetime() {
        return chat_retievetime;
    }

    public String getChat_uuid() {
        return chat_uuid;
    }

    public void setChat_alias(String chat_alias) {
        this.chat_alias = chat_alias;
    }

    public void setChat_message(String chat_message) {
        this.chat_message = chat_message;
    }

    public void setChat_retievetime(String chat_retievetime) {
        this.chat_retievetime = chat_retievetime;
    }

    public void setChat_uuid(String chat_uuid) {
        this.chat_uuid = chat_uuid;
    }
}
