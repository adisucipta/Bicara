package com.sharinproject.bicara.database;

/**
 * Created by Hermansyah on 10/22/2016.
 */

import io.realm.Realm;
import io.realm.RealmResults;


public class RealmController {
    private static RealmController instance =new RealmController();
    private final Realm realm;

    public RealmController() {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.setAutoRefresh(true);
    }

    //clear all objects from User.class
    public void clearAll() {

        realm.beginTransaction();
        realm.delete(ForumChat.class);
        realm.commitTransaction();
    }

    //find all objects in the User.class
    public RealmResults<ForumChat> getAllData() {

        return realm.where(ForumChat.class).findAll();
    }

    //query a single item with the given id
    public ForumChat getChatPotiton(int potition) {
        RealmResults<ForumChat> results = realm.where(ForumChat.class).findAll();
        return results.get(potition);
    }

    //check if User.class is empty
    public boolean isEmpty() {
        return !realm.isEmpty();
    }

    //query example
    public RealmResults<ForumChat> findMessage(String value) {

        return realm.where(ForumChat.class)
                .contains("chat_message", value)
                .findAll();
    }

    public RealmResults<ForumChat> findUserChats(String value) {

        return realm.where(ForumChat.class)
                .contains("chat_alias", value)
                .findAll();
    }

    public void newChat(ForumChat newChat){
        ForumChat chat = new ForumChat();
        chat.setChat_alias(newChat.getChat_alias());
        chat.setChat_message(newChat.getChat_message());
        chat.setChat_retievetime(newChat.getChat_retievetime());
        chat.setChat_uuid(newChat.getChat_uuid());
        realm.beginTransaction();
        realm.copyToRealm(chat);
        realm.commitTransaction();
    }

    public void deleteCurrentChat(int position){
        RealmResults<ForumChat> results = realm.where(ForumChat.class).findAll();
        realm.beginTransaction();
        results.deleteFromRealm(position);
        realm.commitTransaction();
    }
}