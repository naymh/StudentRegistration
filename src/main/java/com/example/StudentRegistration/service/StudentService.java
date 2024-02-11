package com.example.StudentRegistration.service;



import com.example.StudentRegistration.entity.Student;
import net.sf.jasperreports.components.items.Item;
import net.sf.jasperreports.engine.JRException;

import java.util.List;

public interface StudentService {
    // Save operation
    Student saveStudent(Student student);

    // Read operation
    List<Student> fetchStudentList();

    // Update operation
    Student updateStudent(Student student, Long id);

    // Delete operation
    void deleteStudentById(Long id);

    // Get Student by ID
    Student getStudentByID(Long id);

    public byte[] getItemReport(Student items, String format) throws JRException;
}
