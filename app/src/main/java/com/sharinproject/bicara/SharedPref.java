package com.sharinproject.bicara;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Hermansyah on 10/22/2016.
 */

public class SharedPref {
    private String my_pref = "bi_cara";
    private static final String alias_name = "alias_name";
    private static final String goodle_uuid = "uuid";

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    public SharedPref(Context context){
        sharedpreferences = context.getSharedPreferences(my_pref, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    public void setAlias(String value){
        editor.putString(alias_name, value);
        editor.commit();
    }

    public void setGoodle_uuid(String value) {
        editor.putString(goodle_uuid, value);
        editor.commit();
    }

    public String getAlias(){
        return sharedpreferences.getString(alias_name,"");
    }

    public String getGoodle_uuid() {
        return sharedpreferences.getString(goodle_uuid,"");
    }
}
