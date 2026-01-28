package com.example.studentmanager.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.studentmanager.entity.Student;
import com.example.studentmanager.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // 1. Hiển thị tất cả sinh viên + form thêm
    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("student", new Student());
        return "students";
    }

    // 2. Lưu (thêm hoặc cập nhật)
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentService.save(student);
        return "redirect:/students";
    }

    // 3. Sửa (load dữ liệu lên form)
    @GetMapping("/edit/{id}")
    public String editStudent(@PathVariable Integer id, Model model) {
        Student student = studentService.getById(id);

        model.addAttribute("student", student);
        model.addAttribute("students", studentService.getAllStudents());

        return "students";
    }

    // 4. Xóa
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        studentService.delete(id);
        return "redirect:/students";
    }

    // 5. 🔍 Tìm kiếm theo tên hoặc ID, trống thì hiển thị tất cả
    @GetMapping("/search")
    public String searchStudents(@RequestParam(value = "keyword", required = false) String keyword,Model model) {

        List<Student> result = new ArrayList<>();

        if (keyword == null || keyword.trim().isEmpty()) {
            result = studentService.getAllStudents();
        } else {
            try {
                // Nếu nhập số → tìm theo ID
                Integer id = Integer.parseInt(keyword);
                Student student = studentService.getById(id);
                if (student != null) {
                    result.add(student);
                }
            } catch (NumberFormatException e) {
                // Nếu nhập chữ → tìm theo tên
                result = studentService.findByName(keyword);
            }
        }

        model.addAttribute("students", result);
        model.addAttribute("student", new Student());
        model.addAttribute("keyword", keyword);

        return "students";
    }
}
