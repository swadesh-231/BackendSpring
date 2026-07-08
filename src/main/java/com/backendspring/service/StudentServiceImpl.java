package com.backendspring.service;

import com.backendspring.dto.StudentPatchRequest;
import com.backendspring.dto.StudentRequest;
import com.backendspring.dto.StudentResponse;
import com.backendspring.entity.Student;
import com.backendspring.exception.DuplicateEmailException;
import com.backendspring.exception.StudentNotFoundException;
import com.backendspring.mapper.StudentMapper;
import com.backendspring.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public StudentResponse createStudent(StudentRequest request) {
        if (studentRepository.existsByEmailAndDeletedIsFalse(request.email())) {
            throw new DuplicateEmailException(request.email());
        }
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
        verifyEmailAvailable(request.email(), id);
        studentMapper.updateEntity(student, request);
        return studentMapper.toResponse(studentRepository.save(student));
    }

    @Override
    public StudentResponse patchStudent(Long id, StudentPatchRequest request) {
        Student student = findStudent(id);
        verifyEmailAvailable(request.email(), id);
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

    private void verifyEmailAvailable(String email, Long id) {
        if (email != null && studentRepository.existsByEmailAndDeletedIsFalseAndIdNot(email, id)) {
            throw new DuplicateEmailException(email);
        }
    }
}
