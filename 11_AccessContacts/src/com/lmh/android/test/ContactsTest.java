package com.lmh.android.test;

import java.util.ArrayList;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;

public class ContactsTest extends AndroidTestCase{

	//traversal contacts
	@Deprecated
	public void testTraversalContacts() {
		ContentResolver resolver = this.getContext().getContentResolver();
		Uri uri = Uri.parse("content://com.android.contacts/contacts");
		Cursor cursor = resolver.query(uri, new String[]{"_id"}, null, null, null);
		while(cursor.moveToNext()) {
			int contactId = cursor.getInt(0);
			uri = Uri.parse("content://com.android.contacts/contacts/" + contactId + "/data");
			Cursor dataSets = resolver.query(uri, null, null, null, null); 
			while(dataSets.moveToNext()) {
				String type = dataSets.getString(dataSets.getColumnIndex("mimetype"));
				String data = dataSets.getString(dataSets.getColumnIndex("data1"));
				if("vnd.android.cursor.item/phone_v2".equals(type)) {
					System.out.println("phone:" + data);
				} else if ("vnd.android.cursor.item/email_v2".equals(type)) {
					System.out.println("email:" + data);
				} else if ("vnd.android.cursor.item/postal-address_v2".equals(type)) {
					System.out.println("address:" + data);
				} else if ("vnd.android.cursor.item/name".equals(type)) {
					System.out.println("name:" + data);
				}
				
				/*for(int i = 0; i < dataSets.getColumnCount(); i ++) {
					System.out.println("name:" + dataSets.getColumnName(i) + ", value:" + dataSets.getString(i));
				}*/
			}
			System.out.println("---------------------");
			dataSets.close();
		}
		cursor.close();
	}
	
	//retrieve contact with the phone number
	@Deprecated
	public void testAccessContactWithPhoneNumber() {
		String number = "15523311061";
		Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/" + number);
		ContentResolver resolver = this.getContext().getContentResolver();
		Cursor cursor = resolver.query(uri, new String[]{"display_name"}, null, null, null);
		while(cursor.moveToNext()) {
			System.out.println("name:" + cursor.getString(cursor.getColumnIndex("display_name")));
		}
		cursor.close();
	}
	
	//add contacts via content provider
	@Deprecated
	public void testAddContacts() {
		//insert main record to table "raw_contacts"
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		ContentResolver resolver = this.getContext().getContentResolver();
		ContentValues values = new ContentValues();
		values.put("display_name", "Manchang Shu");
		long contactId = ContentUris.parseId(resolver.insert(uri, values));
		
		//insert name to table "data"
		values.clear();
		uri = Uri.parse("content://com.android.contacts/data");
		values.put("raw_contact_id", contactId);
		values.put("mimetype", "vnd.android.cursor.item/name");
		values.put("data1", "Manchang Shu");
		values.put("data2", "Manchang");
		values.put("data3", "Shu");
		resolver.insert(uri, values);
		
		//insert phone number to table "data"
		values.clear();
		values.put("raw_contact_id", contactId);
		uri = Uri.parse("content://com.android.contacts/data");
		values.put("data1", "13193196401");
		values.put("mimetype", "vnd.android.cursor.item/phone_v2");
		resolver.insert(uri, values);
		
		//insert email to table "data"
		values.clear();
		values.put("raw_contact_id", contactId);
		uri = Uri.parse("content://com.android.contacts/data");
		values.put("data1", "shumanchang826@gmail.com");
		values.put("mimetype", "vnd.android.cursor.item/email_v2");
		resolver.insert(uri, values);
		
	}
	
	//
	@Deprecated
	public void testAddContactsWithTransaction() throws Exception {
		ContentResolver resolver = this.getContext().getContentResolver();
		ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
		
		//insert main record to table "raw_contacts"
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		ContentValues values = new ContentValues();
		values.put("display_name", "Kevin Chan");
		ContentProviderOperation addMainRecord = ContentProviderOperation.newInsert(uri).withValues(values).build();
		operations.add(addMainRecord);
		//insert name to table "data"
		uri = Uri.parse("content://com.android.contacts/data");
		values = new ContentValues();
		values.clear();
		values.put("mimetype", "vnd.android.cursor.item/name");
		values.put("data1", "Kevin Chan");
		values.put("data2", "Kevin");
		values.put("data3", "chan");
		ContentProviderOperation addName = ContentProviderOperation.newInsert(uri)
				.withValueBackReference("raw_contact_id", 0)
				.withValues(values).build();
		operations.add(addName);
		//insert phone number to table "data"
		uri = Uri.parse("content://com.android.contacts/data");
		values = new ContentValues();
		values.clear();
		values.put("data1", "13355422114");
		values.put("mimetype", "vnd.android.cursor.item/phone_v2");
		ContentProviderOperation addPhoneNumber = ContentProviderOperation.newInsert(uri)
				.withValueBackReference("raw_contact_id", 0)
				.withValues(values).build();
		operations.add(addPhoneNumber);
		//insert email to table "data"
		uri = Uri.parse("content://com.android.contacts/data");
		values = new ContentValues();
		values.clear();
		values.put("data1", "shumanchang123@gmail.com");
		values.put("mimetype", "vnd.android.cursor.item/email_v2");
		ContentProviderOperation addEmail = ContentProviderOperation.newInsert(uri)
				.withValueBackReference("raw_contact_id", 0)
				.withValues(values).build();
		operations.add(addEmail);
		
		resolver.applyBatch("com.android.contacts", operations);
	}
}





