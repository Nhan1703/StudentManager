package com.example.studentmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.studentmanager.entity.Student;
import com.example.studentmanager.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student save(Student student) {
        return repository.save(student);
    }

    public Student getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    // ✅ THÊM CHO TÌM KIẾM THEO TÊN
    public List<Student> findByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }
}
