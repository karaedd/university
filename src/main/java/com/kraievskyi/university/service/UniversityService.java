package com.kraievskyi.university.service;

import com.kraievskyi.university.exception.EntityNotFoundException;
import com.kraievskyi.university.model.Course;
import com.kraievskyi.university.model.Professor;
import com.kraievskyi.university.model.Student;
import com.kraievskyi.university.model.request.CourseCreationRequest;
import com.kraievskyi.university.model.request.ProfessorCreationRequest;
import com.kraievskyi.university.model.request.StudentCreationRequest;
import com.kraievskyi.university.repository.CourseRepository;
import com.kraievskyi.university.repository.ProfessorRepository;
import com.kraievskyi.university.repository.StudentRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UniversityService {
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final CourseRepository courseRepository;

    public Course getCourseById(String id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            return course.get();
        }
        throw new EntityNotFoundException("Can't find any course under given ID");
    }

    public Course findCourseByName(String name) {
        Optional<Course> course = courseRepository.findByName(name);
        if (course.isPresent()) {
            return course.get();
        }
        throw new EntityNotFoundException("Can't find any course under given name");
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course createCourse(CourseCreationRequest course) {
        Optional<Professor> professor = professorRepository.findById(course.getProfessorId());
        if (!professor.isPresent()) {
            throw new EntityNotFoundException("Professor Not Found");
        }
        Course courseToCreate = new Course();
        BeanUtils.copyProperties(course, courseToCreate);
        courseToCreate.setStartDate(Instant.now());
        courseToCreate.setEndDate(Instant.now().plus(90, ChronoUnit.DAYS));
        courseToCreate.setStudents(List.of());
        courseToCreate.setProfessor(professor.get());

        return courseRepository.save(courseToCreate);
    }

    public void deleteCourse(String id) {
        courseRepository.deleteById(id);
    }

    public void deleteAllCourses() {
        courseRepository.deleteAll();
    }

    public Course updateCourse(String id, CourseCreationRequest request) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (!optionalCourse.isPresent()) {
            throw new EntityNotFoundException("Course not present in the database");
        }
        Optional<Professor> professor = professorRepository.findById(request.getProfessorId());
        if (!professor.isPresent()) {
            throw new EntityNotFoundException("Professor Not Found");
        }
        Course course = optionalCourse.get();
        course.setName(request.getName());
        course.setStartDate(Instant.now());
        course.setEndDate(Instant.now().plus(90, ChronoUnit.DAYS));
        course.setProfessor(professor.get());
        return courseRepository.save(course);
    }

    public Course addStudentToCourse(Course course, Student student) {
        Optional<Course> optionalCourse = courseRepository.findById(course.getId());
        Optional<Student> optionalStudent = studentRepository.findById(student.getId());
        int numberOfStudentsEnrolled = 0;

        Course courseById = optionalCourse.orElseThrow(() ->
                new RuntimeException("Course is not present in the database"));
        Student studentById = optionalStudent.orElseThrow(() ->
                new RuntimeException("Student is not present in the database"));

        if (courseById.getStudents().contains(studentById)) {
            throw new RuntimeException("Student with id "
                    + studentById.getId() + " already exists in course");
        }

        if (courseById.getStudents().isEmpty()) {
            numberOfStudentsEnrolled = courseById.getStudents().size();
            courseById.getStudents().add(studentById);
            numberOfStudentsEnrolled++;
            courseById.setNumberOfStudentsEnrolled(numberOfStudentsEnrolled);
        } else {
            numberOfStudentsEnrolled = courseById.getStudents().size();
            courseById.getStudents().add(studentById);
            numberOfStudentsEnrolled++;
            courseById.setNumberOfStudentsEnrolled(numberOfStudentsEnrolled);
        }
        return courseRepository.save(courseById);
    }

    public Course deleteStudentFromCourse(Course course, Student student) {
        Optional<Course> optionalCourse = courseRepository.findById(course.getId());
        Optional<Student> optionalStudent = studentRepository.findById(student.getId());
        int numberOfStudentsEnrolled = 0;

        Course courseById = optionalCourse.orElseThrow(() ->
                new RuntimeException("Course is not present in the database"));
        Student studentById = optionalStudent.orElseThrow(() ->
                new RuntimeException("Student is not present in the database"));

        if (courseById.getStudents().contains(studentById)) {
            numberOfStudentsEnrolled = courseById.getStudents().size();
            courseById.getStudents().remove(studentById);
            numberOfStudentsEnrolled--;
            courseById.setNumberOfStudentsEnrolled(numberOfStudentsEnrolled);
        } else {
            throw new RuntimeException("There is no student with such an id = "
                    + course.getId() + " on the course");
        }
        return courseRepository.save(courseById);
    }

    public void deleteAllStudents() {
        studentRepository.deleteAll();
    }

    public void deleteStudent(String id) {
        studentRepository.deleteById(id);
    }

    public Student updateStudent(String id, StudentCreationRequest request) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (!optionalStudent.isPresent()) {
            throw new EntityNotFoundException("Student is not present in the database");
        }

        Student student = optionalStudent.get();
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setAge(request.getAge());
        student.setSex(request.getSex());
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student createStudent(StudentCreationRequest request) {
        Student student = new Student();
        BeanUtils.copyProperties(request, student);
        return studentRepository.save(student);
    }

    public Student getStudentById(String id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return student.get();
        }
        throw new EntityNotFoundException("Can't find student under given ID");
    }

    public Professor createProfessor(ProfessorCreationRequest request) {
        Professor professor = new Professor();
        BeanUtils.copyProperties(request, professor);
        return professorRepository.save(professor);
    }

    public Professor getProfessorById(String id) {
        Optional<Professor> professor = professorRepository.findById(id);
        if (professor.isPresent()) {
            return professor.get();
        }
        throw new EntityNotFoundException("Can't find professor under given ID");
    }

    public void deleteProfessor(String id) {
        professorRepository.deleteById(id);
    }

    public Professor updateProfessor(String id, ProfessorCreationRequest request) {
        Optional<Professor> optionalProfessor = professorRepository.findById(id);
        if (!optionalProfessor.isPresent()) {
            throw new EntityNotFoundException("Professor is not present in the database");
        }

        Professor professor = optionalProfessor.get();
        professor.setFirstName(request.getFirstName());
        professor.setLastName(request.getLastName());
        professor.setAge(request.getAge());
        professor.setSex(request.getSex());
        return professorRepository.save(professor);
    }

    public List<Professor> getAllProfessor() {
        return professorRepository.findAll();
    }

    public void deleteAllProfessors() {
        professorRepository.deleteAll();
    }
}
