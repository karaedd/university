package com.kraievskyi.university.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import com.kraievskyi.university.model.Course;
import com.kraievskyi.university.model.Professor;
import com.kraievskyi.university.model.Student;
import com.kraievskyi.university.model.request.CourseCreationRequest;
import com.kraievskyi.university.model.request.ProfessorCreationRequest;
import com.kraievskyi.university.model.request.StudentCreationRequest;
import com.kraievskyi.university.service.UniversityService;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

class UniversityControllerTest {
    private UniversityController universityController;
    private UniversityService universityService;
    private Course biologyCourse;
    private Course historyCourse;
    private Course physicCourse;
    private Professor sergioMarquina;
    private Professor albertEinstein;
    private Professor philipPreobrajenskyi;
    private Student ivanPetrov;
    private Student stepanVlizko;
    private Student andriyShevschenko;

    @BeforeEach
    void setUp() {
        universityService = Mockito.mock(UniversityService.class);
        universityController = new UniversityController(universityService);
    }

    @BeforeEach
    void init() {
        biologyCourse = new Course();
        biologyCourse.setId("k543lwe09jg7c3zf789slk04");
        biologyCourse.setName("Biology");
        biologyCourse.setProfessor(philipPreobrajenskyi);

        historyCourse = new Course();
        historyCourse.setId("k543lwe09jg7c3zf789slk05");
        historyCourse.setName("History");
        historyCourse.setProfessor(sergioMarquina);

        physicCourse = new Course();
        physicCourse.setId("k543lwe09jg7c3zf789slk06");
        physicCourse.setName("Physic");
        physicCourse.setProfessor(albertEinstein);

        sergioMarquina = new Professor();
        sergioMarquina.setId("f8s2j4l0s7dfh4k234l87dcv");
        sergioMarquina.setFirstName("Sergio");
        sergioMarquina.setLastName("Marquina");
        sergioMarquina.setAge(40);
        sergioMarquina.setSex("male");

        albertEinstein = new Professor();
        albertEinstein.setId("f8s2j4l0s7dfh4k234l87dcc");
        albertEinstein.setFirstName("Albert");
        albertEinstein.setLastName("Einstein");
        albertEinstein.setAge(142);
        albertEinstein.setSex("male");

        philipPreobrajenskyi = new Professor();
        philipPreobrajenskyi.setId("f8s2j4l0s7dfh4k234l87ddd");
        philipPreobrajenskyi.setFirstName("Philip");
        philipPreobrajenskyi.setLastName("Preobrajenskyi");
        philipPreobrajenskyi.setAge(100);
        philipPreobrajenskyi.setSex("male");

        ivanPetrov = new Student();
        ivanPetrov.setId("f8s2j4l0s7dfh4k234l87tt3");
        ivanPetrov.setFirstName("Ivan");
        ivanPetrov.setLastName("Petrov");
        ivanPetrov.setAge(18);
        ivanPetrov.setSex("male");

        stepanVlizko = new Student();
        stepanVlizko.setId("f8s2j4l0s7dfh4k234l87tt2");
        stepanVlizko.setFirstName("Stepan");
        stepanVlizko.setLastName("Vlizko");
        stepanVlizko.setAge(17);
        stepanVlizko.setSex("male");

        andriyShevschenko = new Student();
        andriyShevschenko.setId("f8s2j4l0s7dfh4k234l87tt1");
        andriyShevschenko.setFirstName("Andriy");
        andriyShevschenko.setLastName("Schevchenko");
        andriyShevschenko.setAge(19);
        andriyShevschenko.setSex("male");
    }

    @Test
    void courseCreation_Ok() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Mockito.when(universityService.createCourse(
                any(CourseCreationRequest.class))).thenReturn(physicCourse);

        CourseCreationRequest courseCreationRequest = new CourseCreationRequest();
        courseCreationRequest.setName(physicCourse.getName());

        ResponseEntity<Course> responseEntity
                = universityController.createCourse(courseCreationRequest);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void getCourse_Ok() {
        Mockito.when(universityService.getCourseById(physicCourse
                .getId())).thenReturn(physicCourse);

        ResponseEntity<Course> result = universityController
                .getCourse(physicCourse.getId());

        Assertions.assertNotNull(result);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void getCourseByName_Ok() {
        Mockito.when(universityService.findCourseByName(biologyCourse
                .getName())).thenReturn(biologyCourse);

        ResponseEntity<Course> result = universityController
                .getCourse(biologyCourse.getName());

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void deleteCourse_Ok() {
        Mockito.when(universityService.getCourseById(historyCourse
                .getId())).thenReturn(historyCourse);

        assertThat(universityController.deleteCourse(historyCourse
                .getId()).getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void updateCourse_Ok() {
        CourseCreationRequest courseCreationRequest = new CourseCreationRequest();
        courseCreationRequest.setName("Biology and Nature");
        courseCreationRequest.setProfessorId(philipPreobrajenskyi.getId());

        Course updateCourse = new Course();
        updateCourse.setId("f8s2j4l0s7dfh4k234l87vcd");
        updateCourse.setName(courseCreationRequest.getName());
        updateCourse.setStartDate(Instant.now());
        updateCourse.setEndDate(Instant.now()
                .plus(90, ChronoUnit.DAYS));
        Professor professor1 = new Professor();
        professor1.setId(courseCreationRequest.getProfessorId());
        updateCourse.setProfessor(professor1);

        Mockito.when(universityService.getProfessorById(
                        philipPreobrajenskyi.getId()))
                .thenReturn(philipPreobrajenskyi);
        Mockito.when(universityService
                        .getCourseById(updateCourse.getId()))
                .thenReturn(updateCourse);

        ResponseEntity<Course> courseResponseEntity = universityController
                .updateCourse("f8s2j4l0s7dfh4k234l87vcd", courseCreationRequest);

        assertThat(courseResponseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void deleteAllCourses_Ok() {
        List<Course> courseList = new ArrayList<>();

        courseList.add(biologyCourse);
        courseList.add(historyCourse);
        courseList.add(physicCourse);

        Mockito.when(universityService.getAllCourses()).thenReturn(courseList);

        assertThat(universityController.deleteAllCourses()
                .getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void addStudentToCourse_Ok() {
        List<Student> studentList = new ArrayList<>();
        biologyCourse.setStudents(studentList);

        Mockito.when(universityService
                        .getStudentById(andriyShevschenko.getId()))
                .thenReturn(andriyShevschenko);
        Mockito.when(universityService
                        .getCourseById(biologyCourse.getId()))
                .thenReturn(biologyCourse);

        ResponseEntity<Course> courseResponseEntity = universityController
                .addStudent(biologyCourse.getId()
                        , andriyShevschenko.getId());

        assertThat(courseResponseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void deleteStudentFromCourse_Ok() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(stepanVlizko);
        historyCourse.setStudents(studentList);

        Mockito.when(universityService.getStudentById(stepanVlizko.getId()))
                .thenReturn(stepanVlizko);
        Mockito.when(universityService.getCourseById(historyCourse.getId()))
                .thenReturn(historyCourse);

        ResponseEntity<Course> courseResponseEntity = universityController
                .deleteStudentFromCourse(historyCourse.getId()
                        , stepanVlizko.getId());

        assertThat(courseResponseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void getProfessor_Ok() {
        Mockito.when(universityService.getProfessorById(philipPreobrajenskyi.getId()))
                .thenReturn(philipPreobrajenskyi);

        ResponseEntity<Professor> result = universityController
                .getProfessor(philipPreobrajenskyi.getId());

        Assertions.assertNotNull(result);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void deleteProfessor_Ok() {
        Mockito.when(universityService.getProfessorById(albertEinstein.getId()))
                .thenReturn(albertEinstein);

        assertThat(universityController.deleteProfessor(albertEinstein.getId())
                .getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void updateProfessorById_Ok() {
        ProfessorCreationRequest professorCreationRequest = new ProfessorCreationRequest();
        professorCreationRequest.setFirstName("Oleg");

        Professor updateProfessor = new Professor();
        updateProfessor.setId("f8s2j4l0s7dfh4k234l87vcd");
        updateProfessor.setFirstName(professorCreationRequest.getFirstName());

        Mockito.when(universityService.getProfessorById(updateProfessor.getId()))
                .thenReturn(updateProfessor);

        ResponseEntity<Professor> professorResponseEntity = universityController
                .updateProfessor("f8s2j4l0s7dfh4k234l87vcd", professorCreationRequest);
        assertThat(professorResponseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void professorCreation_Ok() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Mockito.when(universityService.createProfessor(any(ProfessorCreationRequest.class)))
                .thenReturn(albertEinstein);

        ProfessorCreationRequest professorCreationRequest = new ProfessorCreationRequest();
        professorCreationRequest.setFirstName(albertEinstein.getFirstName());

        ResponseEntity<Professor> responseEntity = universityController
                .createProfessor(professorCreationRequest);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void getStudent_Ok() {
        Mockito.when(universityService.getStudentById(andriyShevschenko.getId()))
                .thenReturn(andriyShevschenko);

        ResponseEntity<Student> result = universityController
                .getStudent(andriyShevschenko.getId());

        Assertions.assertNotNull(result);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void deleteStudent_Ok() {
        Mockito.when(universityService.getStudentById(stepanVlizko.getId()))
                .thenReturn(stepanVlizko);

        assertThat(universityController.deleteStudent(stepanVlizko.getId())
                .getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void updateStudentById_Ok() {
        StudentCreationRequest studentCreationRequest = new StudentCreationRequest();
        studentCreationRequest.setFirstName("Oleg");

        Student updateStudent = new Student();
        updateStudent.setId("f8s2j4l0s7dfh4k234l87vcd");
        updateStudent.setFirstName(studentCreationRequest.getFirstName());

        Mockito.when(universityService.getStudentById(updateStudent.getId()))
                .thenReturn(updateStudent);

        ResponseEntity<Student> studentResponseEntity = universityController
                .updateStudent("f8s2j4l0s7dfh4k234l87vcd", studentCreationRequest);

        assertThat(studentResponseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void studentCreation_Ok() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Mockito.when(universityService.createStudent(any(StudentCreationRequest.class)))
                .thenReturn(andriyShevschenko);

        StudentCreationRequest studentCreationRequest = new StudentCreationRequest();
        studentCreationRequest.setFirstName(andriyShevschenko.getFirstName());

        ResponseEntity<Student> responseEntity = universityController
                .createStudent(studentCreationRequest);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }
}
