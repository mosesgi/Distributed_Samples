package com.moses.distributed.serialize.extnds;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ExtndsDemo {
	public static void main(String[] args) {
		serializePerson();
		User user = deSerializePerson();
		System.out.println(user);
	}

	private static void serializePerson() {
		try {
			ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(new File("person")));
			User person = new User();
			person.setAge(18);
			oo.writeObject(person);

			System.out.println("序列化成功");
			oo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static User deSerializePerson() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(new File("person")));
			User user = (User) ois.readObject();
			return user;
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
