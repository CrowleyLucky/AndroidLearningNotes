package com.lmh.android.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.test.AndroidTestCase;
import android.util.Log;

import com.lmh.android.domain.Person;
import com.lmh.android.service.PersonService;

public class TestXMLPull extends AndroidTestCase{

	public void testGenerateXML() throws Exception {
		PersonService service = new PersonService();
		List<Person> persons = new ArrayList<Person>();
		persons.add(new Person(1,"smc","ÄÐ",22));
		persons.add(new Person(1,"Kevin","male",22));
		persons.add(new Person(1,"Lexi","Å®",21));
		persons.add(new Person(1,"Rebecca","female",20));
		persons.add(new Person(2, "Crowley", "male", 23));
		FileOutputStream out = this.getContext().openFileOutput("persons.xml", Context.MODE_PRIVATE);
		service.writePersonsToFile(persons, out);
	}
	
	public void testParseXML() throws XmlPullParserException, IOException {
		PersonService service = new PersonService();
		Log.d("loadPersonsFromFile", service.loadPersonsFromFile("person.xml").toString());
	}
	
}
