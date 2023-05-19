package Homework_38.Repository;

import Homework_38.domain.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentMysqlRepository implements StudentRepository {
    private static final String DB_URL = "jdbc:mysql://robot-do-user-1968994-0.b.db.ondigitalocean.com:25060/pokemon";
    private static final String DB_USER = "doadmin";
    private static final String DB_PASSWORD = "AVNS_I6wlDKjGszZn1wvLr9t";
    private static final String SELECT_FROM_STUDENTS = "SELECT * FROM students";
    private static final String SELECT_STUDENT_BY_ID = "SELECT * FROM students WHERE id = ?";
    private static final String INSERT_STUDENT = "INSERT INTO students (id, name, age) VALUES(?, ?, ?)";

    @Override
    public void save(Student student) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(INSERT_STUDENT);;
            ps.setInt(1, student.getId());
            ps.setString(2, student.getName());
            ps.setInt(3, student.getAge());
            ps.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            try {
                assert conn != null;
                conn.rollback();
            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            }
            e.printStackTrace();
        } finally {
            try {
                assert conn != null;
                conn.close();
                assert ps != null;
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Student> findAll() {
        List<Student> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_FROM_STUDENTS)) {
            while (rs.next()) {
                Student student = Student.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .age(rs.getInt("age"))
                        .build();
                result.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Student findById(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            ps = conn.prepareStatement(SELECT_STUDENT_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return Student.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .age(rs.getInt("age"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}



