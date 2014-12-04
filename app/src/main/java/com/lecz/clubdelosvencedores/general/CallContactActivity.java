package com.lecz.clubdelosvencedores.general;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.lecz.clubdelosvencedores.DatabaseManagers.ContactFriendSource;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.UpdateInfoActivity;
import com.lecz.clubdelosvencedores.objects.Contact;
import com.lecz.clubdelosvencedores.register.RegisterActivityFive;
import com.lecz.clubdelosvencedores.register.RegisterActivityTwo;

import java.util.ArrayList;


public class CallContactActivity extends Activity {
    private ListView contactFriendsList, contactList;
    private EditText searchContacts;
    private String id;
    private ContactsFriendAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_contacts);

        Activity host = (Activity) this;

        ArrayList<Contact> listContacts = new ArrayList<Contact>();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        contactList = (ListView) findViewById(R.id.list);


        ContentResolver cr = host.getContentResolver(); //Activity/Application android.content.Context
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if(cursor.moveToFirst())
        {
            listContacts = new ArrayList<Contact>();
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
                        listContacts.add(contact);
                        break;
                    }
                    pCur.close();
                }


            } while (cursor.moveToNext());
        }

        adapter = new ContactsFriendAdapter(getApplicationContext(), listContacts, getResources());
        contactList.setAdapter(adapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.gotoUpdateInfo:
                Intent intent = new Intent(getApplicationContext(), UpdateInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.gotoUpdateFriends:
                Intent intents = new Intent(getApplicationContext(), RegisterActivityFive.class);
                startActivity(intents);
                break;
            case R.id.gotoRestartPlan:
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

                builder.setMessage("Está seguro que desea reiniciar el plan de fumado?").setIcon(R.drawable.pulmones)
                        .setTitle("Reiniciar plan?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intents = new Intent(getApplicationContext(), RegisterActivityTwo.class);
                        startActivity(intents);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
