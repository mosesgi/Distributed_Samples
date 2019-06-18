package com.moses.distributed.serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeTest {
	public static void main(String[] args) {
		serializePerson();
		Person p = deSerializePerson();
		
		System.out.println(p);
	}

	private static void serializePerson() {
		ObjectInputStream ois = null;
		try {
			ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(new File("person")));
			Person person = new Person();
			person.setAge(18);
			person.setName("mic");
			oo.writeObject(person);
			oo.flush();
			ois = new ObjectInputStream(new FileInputStream(new File("person")));
			Person person1 = (Person) ois.readObject();
//			person = new Person();
			person.setName("mic1");
			person.setAge(28);
			oo.writeObject(person);
			oo.flush();
			System.out.println("序列化成功: " + new File("person").length());

			Person person2 = (Person) ois.readObject();
			System.out.println(person1 + "->" + person2);
			
			System.out.println(person1 == person2);

			oo.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static Person deSerializePerson() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(new File("person")));
			Person person = (Person) ois.readObject();
			return person;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
