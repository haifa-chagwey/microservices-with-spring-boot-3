package com.haifachagwey.school;

import com.haifachagwey.school.client.StudentClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository repository;

    private final StudentClient client;

    public void saveSchool(School school) {
        repository.save(school);
    }

    public List<School> findAllSchools() {
        return repository.findAll();
    }

    //    Get school with students
    public FullSchoolResponse findSchoolWithStudents(Integer schoolId) {
        var school  = repository.findById(schoolId)
                .orElse(School.builder()
                        .name("NOTFOUND")
                        .email("NOTFOUND")
                        .build()
                );

        // Find school students from the student microservice
        var students = client.findStudentsBySchool(schoolId);
        return FullSchoolResponse.builder()
                .name(school.getName())
                .email(school.getEmail())
                .students(students)
                .build();
    }
}
