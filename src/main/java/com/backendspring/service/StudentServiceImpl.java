package com.backendspring.service;

import com.backendspring.dto.StudentRequest;
import com.backendspring.dto.StudentResponse;
import com.backendspring.entity.Student;
import com.backendspring.exception.StudentNotFoundException;
import com.backendspring.mapper.StudentMapper;
import com.backendspring.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public StudentResponse createStudent(StudentRequest request) {
        Student student = studentMapper.toEntity(request);
        return studentMapper.toResponse(studentRepository.save(student));
    }

    @Override
    public List<StudentResponse> getAllStudents() {
        return studentRepository.findByDeletedIsFalse().stream()
                .map(studentMapper::toResponse)
                .toList();
    }

    @Override
    public StudentResponse getStudentById(Long id) {
        return studentMapper.toResponse(findStudent(id));
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentRequest request) {
        Student student = findStudent(id);
        studentMapper.updateEntity(student, request);
        return studentMapper.toResponse(studentRepository.save(student));
    }

    @Override
    public StudentResponse patchStudent(Long id, StudentRequest request) {
        Student student = findStudent(id);
        studentMapper.patchEntity(student, request);
        return studentMapper.toResponse(studentRepository.save(student));
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = findStudent(id);
        student.setDeleted(true);
        studentRepository.save(student);
    }

    private Student findStudent(Long id) {
        return studentRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }
}
