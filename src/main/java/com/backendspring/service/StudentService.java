package com.backendspring.service;

import com.backendspring.dto.StudentPatchRequest;
import com.backendspring.dto.StudentRequest;
import com.backendspring.dto.StudentResponse;

import java.util.List;

public interface StudentService {

    StudentResponse createStudent(StudentRequest request);

    List<StudentResponse> getAllStudents();

    StudentResponse getStudentById(Long id);

    StudentResponse updateStudent(Long id, StudentRequest request);

    StudentResponse patchStudent(Long id, StudentPatchRequest request);

    void deleteStudent(Long id);
}
