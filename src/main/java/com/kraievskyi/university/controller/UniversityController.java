package com.kraievskyi.university.controller;

import com.kraievskyi.university.model.Course;
import com.kraievskyi.university.model.Professor;
import com.kraievskyi.university.model.Student;
import com.kraievskyi.university.model.request.CourseCreationRequest;
import com.kraievskyi.university.model.request.ProfessorCreationRequest;
import com.kraievskyi.university.model.request.StudentCreationRequest;
import com.kraievskyi.university.service.UniversityService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/university")
@RequiredArgsConstructor
public class UniversityController {
    private final UniversityService universityService;

    @GetMapping("/course/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable String id) {
        return ResponseEntity.ok(universityService.getCourseById(id));
    }

    @PostMapping("/course")
    public ResponseEntity<Course> createCourse(@RequestBody CourseCreationRequest request) {
        return ResponseEntity.ok(universityService.createCourse(request));
    }

    @PatchMapping("/course/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable("id") String id,
                                               @RequestBody CourseCreationRequest request) {
        return ResponseEntity.ok(universityService.updateCourse(id, request));
    }

    @DeleteMapping("/course/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String id) {
        universityService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/course")
    public ResponseEntity<Void> deleteAllCourses() {
        universityService.deleteAllCourses();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/course")
    public ResponseEntity courseByName(@RequestParam(required = false) String name) {
        if (name == null) {
            return ResponseEntity.ok(universityService.getAllCourses());
        }
        return ResponseEntity.ok(universityService.findCourseByName(name));
    }

    @PostMapping("/professor")
    public ResponseEntity<Professor> createProfessor(
            @RequestBody ProfessorCreationRequest request) {
        return ResponseEntity.ok(universityService.createProfessor(request));
    }

    @GetMapping("/professor/{id}")
    public ResponseEntity<Professor> getProfessor(@PathVariable String id) {
        return ResponseEntity.ok(universityService.getProfessorById(id));
    }

    @DeleteMapping("/professor/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable String id) {
        universityService.deleteProfessor(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/professor")
    public ResponseEntity<List<Professor>> getAllProfessor() {
        return ResponseEntity.ok(universityService.getAllProfessor());
    }

    @PatchMapping("/professor/{id}")
    public ResponseEntity<Professor> updateProfessor(
            @PathVariable("id") String id,
             @RequestBody ProfessorCreationRequest request) {
        return ResponseEntity.ok(universityService.updateProfessor(id, request));
    }

    @DeleteMapping("/professor")
    public ResponseEntity<Void> deleteAllProfessor() {
        universityService.deleteAllProfessors();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/student")
    public ResponseEntity<Student> createStudent(@RequestBody StudentCreationRequest request) {
        return ResponseEntity.ok(universityService.createStudent(request));
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable String id) {
        return ResponseEntity.ok(universityService.getStudentById(id));
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
        universityService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addStudent/{courseId}/{studentId}")
    public ResponseEntity<Course> addStudent(
            @PathVariable String courseId, @PathVariable String studentId) {
        return ResponseEntity.ok(universityService.addStudentToCourse(
                universityService.getCourseById(courseId),
                universityService.getStudentById(studentId)));
    }

    @PatchMapping("/deleteStudent/{courseId}/{studentId}")
    public ResponseEntity<Course> deleteStudentFromCourse(
            @PathVariable String courseId, @PathVariable String studentId) {
        return ResponseEntity.ok(universityService.deleteStudentFromCourse(
                universityService.getCourseById(courseId),
                universityService.getStudentById(studentId)));
    }

    @PatchMapping("/student/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable("id") String id,
            @RequestBody StudentCreationRequest request) {
        return ResponseEntity.ok(universityService.updateStudent(id, request));
    }

    @GetMapping("/student")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(universityService.getAllStudents());
    }

    @DeleteMapping("/student")
    public ResponseEntity<Void> deleteAllStudents() {
        universityService.deleteAllStudents();
        return ResponseEntity.ok().build();
    }
}
