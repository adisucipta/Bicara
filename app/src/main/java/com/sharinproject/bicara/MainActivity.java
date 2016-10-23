package com.sharinproject.bicara;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;

import com.alibaba.fastjson.JSON;
import com.sharinproject.bicara.adapter.ForumMessageAdapter;
import com.sharinproject.bicara.database.ForumChat;
import com.sharinproject.bicara.database.ForumChatJSON;
import com.sharinproject.bicara.database.RealmController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ForumMessageAdapter.OnItemClickListener {

    @BindView(R.id.mainactivity_rv_chat)
    RecyclerView recyclerView;
    @BindView(R.id.mainactivity_et_message)
    EditText editTextMessage;

    private ForumMessageAdapter adapter;
    LinearLayoutManager mLayoutManager;
    private static MainActivity mInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        RealmController.getInstance().refresh();

        adapter = new ForumMessageAdapter(RealmController.getInstance().getAllData(), this, this);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.scrollToPosition(RealmController.getInstance().getAllData().size());
        recyclerView.scrollToPosition(RealmController.getInstance().getAllData().size());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public static synchronized MainActivity getInstance() {
        return mInstance;
    }

    @OnClick(R.id.mainactivity_btn_sendmessage)
    public void OnButtonSendClicked(View v){
        ForumChatJSON forumChat = new ForumChatJSON();
        forumChat.setChat_alias(MyApplication.getInstance().getMyAlias());
        forumChat.setChat_message(editTextMessage.getText().toString());
        forumChat.setChat_retievetime(MyApplication.getInstance().getCurrentTimeDate());
        forumChat.setChat_uuid(MyApplication.getInstance().getGoogleID());
        String jsonString = JSON.toJSONString(forumChat);
        Log.i("JSON", jsonString);
        editTextMessage.setText("");
        editTextMessage.clearFocus();
        MyApplication.getInstance().databaseReference.setValue(jsonString);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRealmData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setRealmData() {
        RealmController.getInstance().refresh();

        adapter = new ForumMessageAdapter(RealmController.getInstance().getAllData(), this, this);
        mLayoutManager.scrollToPosition(RealmController.getInstance().getAllData().size());
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(RealmController.getInstance().getAllData().size());
    }

    @Override
    public void onItemClick(ForumChat mItem) {

    }
}
