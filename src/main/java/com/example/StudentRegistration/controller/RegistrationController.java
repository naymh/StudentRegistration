package com.example.StudentRegistration.controller;
import com.example.StudentRegistration.entity.Student;
import com.example.StudentRegistration.service.StudentService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;

@Controller
public class RegistrationController {
    // Annotation
    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<Student> students = studentService.fetchStudentList();
        model.addAttribute("students", students);
        return "students"; // Assuming "students.jsp" is the name of your JSP file
    }

    @PostMapping("/students")
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentService.saveStudent(student);
        return "redirect:/";
    }

    // No need for separate GET mapping for fetching student list
    // Thymeleaf template engine will automatically map the GET request to viewHomePage method

    // Update operation (PUT Mapping)
    @PutMapping("/students/{id}")
    public ResponseEntity<String> updateStudent(@RequestBody  Student student) {
        studentService.updateStudent(student, student.getId());
        return ResponseEntity.ok("Update Successful!");
    }

    // Delete operation (DELETE Mapping)
    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable("id") Long id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/students/{id}")
    @ResponseBody
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id) {
        Student student = studentService.getStudentByID(id);
        if (student != null) {
            return ResponseEntity.ok(student); // Return 200 OK with student data as the response body
        } else {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if student with the given ID is not found
        }
    }

    @GetMapping("student-report/{format}/{id}")
    public ResponseEntity<Resource> getItemReport(@PathVariable String format, @PathVariable("id") Long id) throws JRException, IOException {

        Student student = studentService.getStudentByID(id);
        byte[] reportContent = studentService.getItemReport(student, format);

        ByteArrayResource resource = new ByteArrayResource(reportContent);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("item-report." + format)
                                .build().toString())
                .body(resource);
    }
}
