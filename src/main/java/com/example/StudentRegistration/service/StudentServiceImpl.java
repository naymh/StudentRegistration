package com.example.StudentRegistration.service;

import com.example.StudentRegistration.entity.Student;
import com.example.StudentRegistration.repository.StudentRepository;
import net.sf.jasperreports.components.items.Item;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

// Annotation
@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    private StudentRepository studentRepository;

    // Save operation
    @Override
    public Student saveStudent(Student student)
    {
        student.setCreateDate(new Date());
        student.setModifiedDate(new Date());
        return studentRepository.save(student);
    }

    // Read operation
    @Override
    public List<Student> fetchStudentList()
    {
        return (List<Student>) studentRepository.findAll();
    }

    // Update operation
    @Override
    public Student
    updateStudent(Student student, Long id)
    {
        Student data = studentRepository.findById(id).get();

        if (Objects.nonNull(student.getName()) && !student.getName().equalsIgnoreCase("")) {
            data.setName(student.getName());
        }

        if (Objects.nonNull(student.getAddress()) && !student.getAddress().equalsIgnoreCase("")) {
            data.setAddress(student.getAddress());
        }

        if (Objects.nonNull(student.getAge()) && student.getAge() > 0 ) {
            data.setAge(student.getAge());
        }

        if (Objects.nonNull(student.getCourse()) && !student.getCourse().equalsIgnoreCase("")) {
            data.setCourse(student.getCourse());
        }

        if (Objects.nonNull(student.getGender()) && !student.getGender().equalsIgnoreCase("")) {
            data.setGender(student.getGender());
        }

        if (Objects.nonNull(student.getDateOfBirth())) {
            data.setDateOfBirth(student.getDateOfBirth());
        }
        data.setModifiedDate(new Date());
        return studentRepository.save(data);
    }

    // Delete operation
    @Override
    public void deleteStudentById(Long id)
    {
        studentRepository.deleteById(id);
    }

    @Override
    public Student getStudentByID(Long id){
        return studentRepository.findById(id).get();
    }

    @Override
    public byte[] getItemReport(Student item, String format) {
        JasperPrint jasperPrint = null;
        byte[] reportContent = null;

        try {
            // Load JRXML file from the classpath
            InputStream inputStream = getClass().getResourceAsStream("/student-report.jrxml");

            // Compile JRXML to JasperReport template
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            // Create list of items
            List<Student> items = new ArrayList<>();
            items.add(item);

            // Set report data
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(items);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("title", "Student Report");

            // Fill the report with data
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Export report to the specified format
            switch (format) {
                case "pdf":
                    reportContent = JasperExportManager.exportReportToPdf(jasperPrint);
                    break;
                case "xml":
                    reportContent = JasperExportManager.exportReportToXml(jasperPrint).getBytes();
                    break;
                default:
                    throw new RuntimeException("Unknown report format");
            }
        } catch (JRException e) {
            // Handle JasperReports exception
            e.printStackTrace(); // Log the exception or handle it as needed
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace(); // Log the exception or handle it as needed
        }
        return reportContent;
    }
}
