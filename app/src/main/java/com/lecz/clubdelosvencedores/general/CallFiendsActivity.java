package com.lecz.clubdelosvencedores.general;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lecz.clubdelosvencedores.DatabaseManagers.ContactFriendSource;
import com.lecz.clubdelosvencedores.Game.Game;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.objects.Contact;
import com.lecz.clubdelosvencedores.register.ContactsAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;


public class CallFiendsActivity extends Activity {
    private ListView contactFriendsList, contactList;
    private EditText searchContacts;
    private String id;
    private ContactsFriendAdapter adapter;
    ArrayList<Contact> list;
    ImageButton callOne, callTwo, callThree;
    Button callAnother;
    TextView nameOne, nameTwo, nameThree, numberOne, numberTwo, numberThree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_friends);

        callOne = (ImageButton) findViewById(R.id.call_contact_1);
        callTwo = (ImageButton) findViewById(R.id.call_contact_2);
        callThree = (ImageButton) findViewById(R.id.call_contact_3);
        nameOne = (TextView) findViewById(R.id.name_contact_1);
        nameTwo = (TextView) findViewById(R.id.name_contact_2);
        nameThree = (TextView) findViewById(R.id.name_contact_3);
        numberOne = (TextView) findViewById(R.id.number_contact_1);
        numberTwo = (TextView) findViewById(R.id.number_contact_2);
        numberThree = (TextView) findViewById(R.id.number_contact_3);

        callAnother = (Button) findViewById(R.id.call_another);

        ContactFriendSource cds = new ContactFriendSource(getApplicationContext());
        cds.open();
        list = cds.getContacts();
        cds.close();

        if(list.size() == 1){
            callOne.setVisibility(View.VISIBLE);
            nameOne.setVisibility(View.VISIBLE);
            numberOne.setVisibility(View.VISIBLE);

            nameOne.setText(list.get(0).getName());
            numberOne.setText(list.get(0).getPhone());

            callOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
                    phoneCallIntent.setData(Uri.parse("tel:" + list.get(0).getPhone()));
                    phoneCallIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(phoneCallIntent);
                }
            });

        }else{
            if(list.size() == 2){
                callOne.setVisibility(View.VISIBLE);
                nameOne.setVisibility(View.VISIBLE);
                numberOne.setVisibility(View.VISIBLE);
                callTwo.setVisibility(View.VISIBLE);
                nameTwo.setVisibility(View.VISIBLE);
                numberTwo.setVisibility(View.VISIBLE);

                nameOne.setText(list.get(0).getName());
                numberOne.setText(list.get(0).getPhone());

                callOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
                        phoneCallIntent.setData(Uri.parse("tel:" + list.get(0).getPhone()));
                        phoneCallIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(phoneCallIntent);
                    }
                });

                nameTwo.setText(list.get(1).getName());
                numberTwo.setText(list.get(1).getPhone());

                callTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
                        phoneCallIntent.setData(Uri.parse("tel:" + list.get(1).getPhone()));
                        phoneCallIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(phoneCallIntent);
                    }
                });

            }else{
                callOne.setVisibility(View.VISIBLE);
                nameOne.setVisibility(View.VISIBLE);
                numberOne.setVisibility(View.VISIBLE);
                callTwo.setVisibility(View.VISIBLE);
                nameTwo.setVisibility(View.VISIBLE);
                numberTwo.setVisibility(View.VISIBLE);
                callThree.setVisibility(View.VISIBLE);
                nameThree.setVisibility(View.VISIBLE);
                numberThree.setVisibility(View.VISIBLE);

                nameOne.setText(list.get(0).getName());
                numberOne.setText(list.get(0).getPhone());

                callOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
                        phoneCallIntent.setData(Uri.parse("tel:" + list.get(0).getPhone()));
                        phoneCallIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(phoneCallIntent);
                    }
                });

                nameTwo.setText(list.get(1).getName());
                numberTwo.setText(list.get(1).getPhone());

                callTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
                        phoneCallIntent.setData(Uri.parse("tel:" + list.get(1).getPhone()));
                        phoneCallIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(phoneCallIntent);
                    }
                });

                nameThree.setText(list.get(2).getName());
                numberThree.setText(list.get(2).getPhone());

                callThree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
                        phoneCallIntent.setData(Uri.parse("tel:" + list.get(2).getPhone()));
                        phoneCallIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(phoneCallIntent);
                    }
                });

            }
        }

        callAnother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CallFiendsActivity.this, CallContactActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.panic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
