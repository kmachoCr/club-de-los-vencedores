package com.lecz.clubdelosvencedores.register;

import android.app.Activity;
import android.app.Fragment;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lecz.clubdelosvencedores.DatabaseManagers.ContactFriendSource;
import com.lecz.clubdelosvencedores.DatabaseManagers.UserDataSource;
import com.lecz.clubdelosvencedores.MyActivity;
import com.lecz.clubdelosvencedores.*;
import com.lecz.clubdelosvencedores.objects.Contact;
import com.lecz.clubdelosvencedores.objects.User;

import java.util.ArrayList;


public class RegisterActivityFive extends Fragment {

    private Activity mActivity;
    private ListView mContactsList;
    private ArrayList<Contact> alContacts;
    private ContactsAdapter adapter;
    private ViewPager viewPager;
    private TextView countf;
    private String id;
    private ArrayList<Contact> listFriends = new ArrayList<Contact>();
    private ContactFriendSource cds;
    private ArrayList<Contact> listContacts;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity host = getActivity();

        rootView =  inflater.inflate(R.layout.activity_register_activity_five, container, false);

        // Gets the ListView from the View list of the parent activity
        mContactsList = (ListView) rootView.findViewById(R.id.list);
        countf = (TextView) rootView.findViewById(R.id.count_friends);
        RelativeLayout rl = (RelativeLayout) rootView.findViewById(R.id.friends);

        ImageView iv = new ImageView(rootView.getContext());

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(30, 40);
        params.leftMargin = 50;
        params.topMargin = 60;
        rl.addView(iv, params);

        cds = new ContactFriendSource(rootView.getContext());
        ContentResolver cr = host.getContentResolver(); //Activity/Application android.content.Context
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        Log.i("contacts", cursor.getCount() + "");
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
        }

        UserDataSource userds = new UserDataSource(rootView.getContext());
        userds.open();
        User validateUser = userds.getUser();
        userds.close();

        if(validateUser != null){
            cds.open();
            listContacts = cds.getContacts();
            cds.close();

            for(int i = 0; i < alContacts.size(); i++ ){
                for(int t = 0; t < listContacts.size(); t++ ){
                    if(listContacts.get(t).getContact_id() == alContacts.get(i).getContact_id()){
                        alContacts.get(i).setSelected(true);
                        listFriends.add(alContacts.get(i));
                    }
                }
            }
            countf.setText(listFriends.size() + "/3");
        }


        adapter = new ContactsAdapter(rootView.getContext(), alContacts);
        mContactsList.setAdapter(adapter);

        Button button = (Button) rootView.findViewById(R.id.save);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                cds.open();
                if(listContacts.size() > 0) {
                    for (int i = 0; i < listContacts.size(); i++) {
                        cds.deleteContact(listContacts.get(i));
                    }
                }

                for (int i = 0; i < listFriends.size(); i++) {
                    cds.createContact(new Contact(listFriends.get(i).getContact_id(), listFriends.get(i).getName(), listFriends.get(i).getPhone(), true));
                }

                cds.close();

                SharedPreferences mPrefs = rootView.getContext().getSharedPreferences("label", 0);
                Boolean register_completed = mPrefs.getBoolean("register_completed", false);

                if(register_completed){
                    Toast.makeText(rootView.getContext(), "Cambios guardados", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(rootView.getContext(), MyActivity.class);
                    startActivity(myIntent);
                }else {
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putBoolean("register_completed", true).commit();
                    Intent myIntent = new Intent(rootView.getContext(), MyActivity.class);
                    startActivity(myIntent);
                }

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

        return rootView;

    }
}
