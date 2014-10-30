package com.lecz.clubdelosvencedores.register;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lecz.clubdelosvencedores.general.MainActivity;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.objects.Contact;

import java.util.ArrayList;


public class RegisterActivityFive extends Activity {

    private Activity mActivity;
    private ListView mContactsList;
    private ArrayList<Contact> alContacts;
    private ContactsAdapter adapter;
    private ViewPager viewPager;
    private TextView countf;
    private String id;
    private ArrayList<Contact> listFriends = new ArrayList<Contact>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity_five);

        Activity host = (Activity) this;

        // Gets the ListView from the View list of the parent activity
        mContactsList = (ListView) findViewById(R.id.list);
        countf = (TextView) findViewById(R.id.count_friends);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.friends);

        ImageView iv = new ImageView(this);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(30, 40);
        params.leftMargin = 50;
        params.topMargin = 60;
        rl.addView(iv, params);

        ContentResolver cr = host.getContentResolver(); //Activity/Application android.content.Context
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if(cursor.moveToFirst())
        {
            alContacts = new ArrayList<Contact>();
            do
            {
                id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",new String[]{ id }, null);
                    while (pCur.moveToNext())
                    {
                        String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String contactName = pCur.getString(pCur.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                        int contactId = Integer.parseInt(id);

                        Uri contactUri = ContentUris.withAppendedId(
                                ContactsContract.Contacts.CONTENT_URI,
                                Long.parseLong(id)
                        );
                        Uri displayPhotoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.DISPLAY_PHOTO);

                        Contact contact = new Contact(contactId, contactName, contactNumber, false);
                        contact.setPhoto(displayPhotoUri);
                        alContacts.add(contact);
                        break;
                    }
                    pCur.close();
                }


            } while (cursor.moveToNext());


            adapter = new ContactsAdapter(getApplicationContext(), alContacts);
            mContactsList.setAdapter(adapter);


        }


        Button button = (Button) findViewById(R.id.save);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor friends = settings.edit();

                friends.putInt("friend01", listFriends.get(0).getId());
                friends.putInt("friend02", listFriends.get(1).getId());
                friends.putInt("friend03", listFriends.get(2).getId());

                friends.commit();

                Intent myIntent = new Intent(getApplication(), MainActivity.class);
                startActivity(myIntent);
            }
        });

        mContactsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {

                if(listFriends.size() < 3 ){

                    if(alContacts.get(position).isSelected()){
                        ImageView check = (ImageView) arg1.findViewById(R.id.checkmark);
                        alContacts.get(position).setSelected(false);
                        check.setVisibility(View.INVISIBLE);
                        listFriends.remove(alContacts.get(position));
                    }else{
                        ImageView check = (ImageView) arg1.findViewById(R.id.checkmark);
                        alContacts.get(position).setSelected(true);
                        check.setVisibility(View.VISIBLE);
                        listFriends.add(alContacts.get(position));
                    }
                }else{
                    if(alContacts.get(position).isSelected()){
                        ImageView check = (ImageView) arg1.findViewById(R.id.checkmark);
                        alContacts.get(position).setSelected(false);
                        check.setVisibility(View.INVISIBLE);
                        listFriends.remove(alContacts.get(position));
                    }
                }

                countf.setText(listFriends.size() + "/3");

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register_activity_five, menu);
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
