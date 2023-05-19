package Homework_38.Repository;

import Homework_38.domain.Student;

import java.util.List;

public interface StudentRepository {
    void save(Student student);
    List<Student> findAll();
    Student findById(int id);
}
