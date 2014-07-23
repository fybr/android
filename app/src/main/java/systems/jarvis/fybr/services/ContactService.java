package systems.jarvis.fybr.services;

import android.app.IntentService;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import systems.jarvis.fybr.providers.Api;
import systems.jarvis.fybr.providers.Auth;
import systems.jarvis.fybr.providers.Contact;

public class ContactService extends IntentService {

    public ContactService() {
        super("Contacts");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.i("Service", "Contacts syncing");
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        List<Contact> contacts = new ArrayList<Contact>();
        Api api = new Auth(this).connect();
        while (cur.moveToNext()) {
            Contact contact = new Contact();
            contact.id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
            contact.name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String uri = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
            if (uri != null) {
                InputStream input = null;
                try {
                    input = this.getContentResolver().openInputStream(Uri.parse(uri));
                    if (input != null) {
                        Bitmap bmp = BitmapFactory.decodeStream(input);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] b = baos.toByteArray();
                        contact.picture = "data:image/jpeg;base64," + Base64.encodeToString(b, Base64.DEFAULT);
                    }
                } catch (FileNotFoundException e) {

                }
            }
            if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{contact.id}, null);
                while (pCur.moveToNext()) {
                    String phone = pCur.getString(
                            pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Contact c = new Contact();
                    c.id = contact.id;
                    c.name = contact.name;
                    c.picture = contact.picture;
                    c.number = phone.replaceAll("[\\s\\(\\)\\-]", "").replaceAll("\\+\\d", "");
                    contacts.add(c);
                }
                pCur.close();
            }
        }

        api.event(contacts, "contact");


    }

}
