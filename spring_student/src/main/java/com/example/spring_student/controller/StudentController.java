package com.example.spring_student.controller;

import com.example.spring_student.domain.Student;
import com.example.spring_student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public String home(Model model) {
        List<Student> students = studentService.findAllStudents();
        model.addAttribute("students", students);
        model.addAttribute("newStudent", new Student());
        return "home";
    }

    @PostMapping("/")
    public String search(@RequestParam("searchName") String searchName, Model model) {
        List<Student> searchResults = studentService.findStudentsByName(searchName);
        if (searchResults.isEmpty()) {
            model.addAttribute("message", "No students found.");
        } else {
            model.addAttribute("students", searchResults);
        }
        model.addAttribute("newStudent", new Student());
        return "home";
    }

    @PostMapping("/create")
    public String createStudent(@ModelAttribute("newStudent") Student student) {
        studentService.saveStudent(student);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute Student student) {
        studentService.updateStudent(id, student);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/";
    }
}
