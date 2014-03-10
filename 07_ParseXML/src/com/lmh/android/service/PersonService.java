package com.lmh.android.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import com.lmh.android.domain.Person;

public class PersonService {
	
	public void writePersonsToFile(List<Person> persons, OutputStream os) throws Exception{
		XmlSerializer xml = XmlPullParserFactory.newInstance().newSerializer();
		xml.setOutput(os, "UTF-8");
		xml.startDocument("UTF-8", true);
		xml.startTag(null, "Persons");
		for(Person person : persons) {
			xml.startTag(null, "person");
			xml.attribute(null, "id", person.getId().toString());
			xml.startTag(null, "name");
			xml.text(person.getName());
			xml.endTag(null, "name");
			xml.startTag(null, "gender");
			xml.text(person.getGender());
			xml.endTag(null, "gender");
			xml.startTag(null, "age");
			xml.text(person.getAge().toString());
			xml.endTag(null, "age");
			xml.endTag(null, "person");
		}
		xml.endTag(null, "Persons");
		xml.endDocument();
	}

	
	public List<Person> loadPersonsFromFile(String fileName) throws XmlPullParserException, IOException {
		XmlPullParser xml = XmlPullParserFactory.newInstance().newPullParser();
		xml.setInput(this.getClass().getClassLoader().getResourceAsStream(fileName), "UTF-8");
		int event = xml.getEventType();
		List<Person> persons = null;
		Person person = null;
		while(event != XmlPullParser.END_DOCUMENT) {
			switch(event) {
			case XmlPullParser.START_DOCUMENT:
				persons = new ArrayList<Person>();
				break;
			case XmlPullParser.START_TAG:
				System.out.println("tag name:" + xml.getName());
				if("person".equals(xml.getName())) {
					System.out.println(person);
					person = new Person();
					person.setId(Integer.valueOf(xml.getAttributeValue(0)));
				}
				if("name".equals(xml.getName())) {
					person.setName(xml.nextText());
				}
				if("gender".equals(xml.getName())) {
					person.setGender(xml.nextText());
				}
				if("age".equals(xml.getName())) {
					person.setAge(Integer.valueOf(xml.nextText()));
				}
				break;
			case XmlPullParser.END_TAG:
				if("person".equals(xml.getName())) {
					persons.add(person);
				}
				break;
			case XmlPullParser.END_DOCUMENT:
				break;
			}
			event = xml.next();
		}
		
		return persons;
	}
	
}
