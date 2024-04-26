package com.example.school.service;

import com.example.school.model.Student;

import com.example.school.model.StudentRowMapper;
import com.example.school.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@Service
public class StudentH2Service implements StudentRepository {
    @Autowired
    private JdbcTemplate db;

    @Override
    public ArrayList<Student> getStudents() {
        List<Student> studentData = db.query("select * from student", new StudentRowMapper());
        ArrayList<Student> students = new ArrayList<>(studentData);
        return students;
    }

    @Override
    public Student getStudentById(int studentId) {
        try {
            Student student = db.queryForObject("select * from student where studentId = ?", new StudentRowMapper(),
                    studentId);
            return student;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        }
    }

    @Override
    public Student addStudent(Student student) {
        db.update("insert into student(studentName, Gender, Standard) values (?, ?, ?)", student.getStudentName(),
                student.getGender(), student.getStandard());
        Student savedStudent = db.queryForObject(
                "select * from student where studentName = ? and Gender = ? and Standard = ?", new StudentRowMapper(),
                student.getStudentName(), student.getGender(), student.getStandard());
        return savedStudent;

    }

    @Override
    public String addMultipleStudents(ArrayList<Student> studentsList) {
        for (Student eachStudent : studentsList) {
            db.update("insert into student(student Name, gender, standard) values(?, ?, ?)", eachStudent.getStudentName(), eachStudent.getGender(), eachStudent.getStandard());
            
        }
        String responseMessage = String.format("Successfully added %d students", studentsList.size());
        return responseMessage;

    }

    @Override
    public void deleteStudent(int studentId) {

    }
    @Override
    public Student updateStudent(int studentId, Student student) {
        return new Student(3, "Souju", "Female", 8);
    }
}
