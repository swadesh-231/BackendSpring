package com.backendspring.mapper;

import com.backendspring.dto.StudentPatchRequest;
import com.backendspring.dto.StudentRequest;
import com.backendspring.dto.StudentResponse;
import com.backendspring.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public Student toEntity(StudentRequest request) {
        return Student.builder()
                .name(request.name())
                .email(request.email())
                .course(request.course())
                .age(request.age())
                .build();
    }

    public StudentResponse toResponse(Student student) {
        return new StudentResponse(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getCourse(),
                student.getAge()
        );
    }

    public void updateEntity(Student student, StudentRequest request) {
        student.setName(request.name());
        student.setEmail(request.email());
        student.setCourse(request.course());
        student.setAge(request.age());
    }

    public void patchEntity(Student student, StudentPatchRequest request) {
        if (request.name() != null) {
            student.setName(request.name());
        }
        if (request.email() != null) {
            student.setEmail(request.email());
        }
        if (request.course() != null) {
            student.setCourse(request.course());
        }
        if (request.age() != null) {
            student.setAge(request.age());
        }
    }
}
