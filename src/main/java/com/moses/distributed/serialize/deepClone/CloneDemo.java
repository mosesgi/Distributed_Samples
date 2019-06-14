package com.moses.distributed.serialize.deepClone;

import java.io.IOException;

public class CloneDemo {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
        Teacher teacher=new Teacher();
        teacher.setName("TeacherA");

        Student student=new Student();
        student.setName("StudentA");
        student.setAge(16);
        student.setTeacher(teacher);

        Student student2=(Student) student.deepClone(); //克隆一个对象
        System.out.println(student);

        student2.getTeacher().setName("TeacherB");
        System.out.println(student2);
    }
}
