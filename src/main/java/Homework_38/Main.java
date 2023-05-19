package Homework_38;
import Homework_38.Repository.StudentMysqlRepository;
import Homework_38.Repository.StudentRepository;
import Homework_38.domain.Student;

import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StudentRepository repository = new StudentMysqlRepository();
        List<Student> studentList = repository.findAll();

        Scanner scan = new Scanner(System.in);
        System.out.println("Якщо вам потрібно заглянути в базу - натисніть 1");
        System.out.println("Якщо вам потрібно додати в базу нового студента - натисніть 2");
        System.out.println("Якщо вам потрібно знайти студента за ID - натисніть 3");
        int a = scan.nextInt();

        if(a==1) {
            System.out.println("Всі студенти:");
            System.out.println(studentList);
        }else if (a==2) {
            System.out.println("Введіть ID, ім'я та вік");
            System.out.println("Увага! Врахуйте, що ці id вже використані:");
            for (Student student : studentList) {
                int id = student.getId();
                System.out.print(id + " ");
            }
            System.out.println();
            int id = scan.nextInt();
            String name = scan.next();
            int age = scan.nextInt();
            Student student = new Student(id, name, age);
            repository.save(student);
            studentList.add(student);
            System.out.println("Студент збережений.");
            System.out.println(studentList);
        }else if(a==3) {
            System.out.println("Введіть ID студента:");
            int studentId = scan.nextInt();
            Student retrievedStudent = repository.findById(studentId);
            if (retrievedStudent != null) {
                System.out.println("Ваш студент:");
                System.out.println(retrievedStudent);
            } else {
                System.out.println("Студент з ID " + studentId + " не знайдений.");
            }
        }
        scan.close();
    }
}
