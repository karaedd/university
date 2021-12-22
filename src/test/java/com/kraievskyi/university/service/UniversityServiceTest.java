package com.kraievskyi.university.service;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Java6Assertions.assertThat;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

class UniversityServiceTest {
    private UniversityService universityService;
    private StudentRepository studentRepository;
    private ProfessorRepository professorRepository;
    private CourseRepository courseRepository;
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
        studentRepository = Mockito.mock(StudentRepository.class);
        professorRepository = Mockito.mock(ProfessorRepository.class);
        courseRepository = Mockito.mock(CourseRepository.class);
        universityService = new UniversityService(
                studentRepository, professorRepository, courseRepository);
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
    void getCourseById_Ok() {
        when(courseRepository.findById(biologyCourse.getId()))
                .thenReturn(Optional.of(biologyCourse));

        Course actual = universityService.getCourseById(biologyCourse.getId());

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(biologyCourse.getId(), actual.getId());
    }

    @Test
    void getCourseById_CourseIdNotFound() {
        when(courseRepository.findById(biologyCourse.getId()))
                .thenReturn(Optional.of(biologyCourse));

        try {
            universityService.getCourseById("f8s2j4l0s7dfh4k234l87dcv");
        } catch (EntityNotFoundException e) {
            Assertions.assertEquals(
                    "Can't find any course under given ID", e.getMessage());
            return;
        }
        Assertions.fail("Expected to receive EntityNotFoundException");
    }

    @Test
    void findCourseByName_Ok() {
        when(courseRepository.findByName(biologyCourse.getName()))
                .thenReturn(Optional.of(biologyCourse));

        Course actual = universityService
                .findCourseByName(biologyCourse.getName());

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(biologyCourse.getName(), actual.getName());
    }

    @Test
    void findCourseByName_CourseNameNotFound() {
        when(courseRepository.findByName(biologyCourse.getName()))
                .thenReturn(Optional.of(biologyCourse));

        try {
            universityService.findCourseByName("Economic");
        } catch (EntityNotFoundException e) {
            Assertions.assertEquals(
                    "Can't find any course under given name", e.getMessage());
            return;
        }
        Assertions.fail("Expected to receive EntityNotFoundException");
    }

    @Test
    void getAllCourses_Ok() {
        List<Course> courseList = new ArrayList<>();

        courseList.add(biologyCourse);
        courseList.add(historyCourse);
        courseList.add(physicCourse);

        Mockito.when(courseRepository.findAll()).thenReturn(courseList);

        List<Course> actual = universityService.getAllCourses();

        Assertions.assertEquals(3, actual.size());
    }

    @Test
    void createCourse_Ok() {
        Mockito.when(professorRepository.findById(sergioMarquina.getId()))
                .thenReturn(Optional.of(sergioMarquina));
        when(courseRepository.save(any(Course.class))).thenReturn(biologyCourse);

        Course savedCourse = courseRepository.save(biologyCourse);

        assertThat(savedCourse.getName()).isNotNull();
    }

    @Test
    void deleteCourseById_Ok() {
        when(courseRepository.save(any(Course.class))).thenReturn(biologyCourse);
        universityService.deleteCourse(biologyCourse.getId());
        verify(courseRepository).deleteById(biologyCourse.getId());
    }

    @Test
    void deleteAllCourses_Ok() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(biologyCourse);
        courseList.add(historyCourse);
        courseList.add(physicCourse);

        Mockito.when(courseRepository.findAll()).thenReturn(courseList);

        universityService.deleteAllCourses();

        verify(courseRepository).deleteAll();
    }

    @Test
    void updateCourse_Ok() {
        CourseCreationRequest courseCreationRequest = new CourseCreationRequest();
        courseCreationRequest.setName("Biology and Nature");
        Professor professor = new Professor();
        professor.setId("f8s2j4l0s7dfh4k234l87dcv");
        courseCreationRequest.setProfessorId(professor.getId());

        Course updateCourse = new Course();
        updateCourse.setId("f8s2j4l0s7dfh4k234l87vcd");
        updateCourse.setName(courseCreationRequest.getName());
        updateCourse.setStartDate(Instant.now());
        updateCourse.setEndDate(Instant.now().plus(90, ChronoUnit.DAYS));
        Professor professor1 = new Professor();
        professor1.setId(courseCreationRequest.getProfessorId());
        updateCourse.setProfessor(professor1);

        Mockito.when(professorRepository.findById(professor.getId()))
                .thenReturn(Optional.of(professor));
        Mockito.when(courseRepository.findById(updateCourse.getId()))
                .thenReturn(Optional.of(updateCourse));

        universityService.updateCourse("f8s2j4l0s7dfh4k234l87vcd", courseCreationRequest);
        verify(courseRepository).save(updateCourse);
    }

    @Test
    void addStudentToCourse_Ok() {
        List<Student> studentList = new ArrayList<>();
        historyCourse.setStudents(studentList);

        Mockito.when(studentRepository.findById(stepanVlizko.getId()))
                .thenReturn(Optional.of(stepanVlizko));
        Mockito.when(courseRepository.findById(historyCourse.getId()))
                .thenReturn(Optional.of(historyCourse));

        universityService.addStudentToCourse(historyCourse, stepanVlizko);
        verify(courseRepository).save(historyCourse);
    }

    @Test
    void deleteStudentFromCourse_Ok() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(ivanPetrov);
        biologyCourse.setStudents(studentList);

        Mockito.when(studentRepository.findById(ivanPetrov.getId()))
                .thenReturn(Optional.of(ivanPetrov));
        Mockito.when(courseRepository.findById(biologyCourse.getId()))
                .thenReturn(Optional.of(biologyCourse));

        universityService.deleteStudentFromCourse(biologyCourse, ivanPetrov);
        verify(courseRepository).save(biologyCourse);
    }

    @Test
    void getProfessorById_Ok() {
        when(professorRepository.findById(albertEinstein.getId()))
                .thenReturn(Optional.of(albertEinstein));

        Professor actual = universityService.getProfessorById(albertEinstein.getId());

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(albertEinstein.getId(), actual.getId());
    }

    @Test
    void getProfessorById_ProfessorIdNotFound() {
        when(professorRepository.findById(philipPreobrajenskyi.getId()))
                .thenReturn(Optional.of(philipPreobrajenskyi));

        try {
            universityService.getProfessorById("f8s2j4l0s7dfh4k234l87dcv");
        } catch (EntityNotFoundException e) {
            Assertions.assertEquals(
                    "Can't find professor under given ID", e.getMessage());
            return;
        }
        Assertions.fail("Expected to receive EntityNotFoundException");
    }

    @Test
    void getAllProfessors_Ok() {
        List<Professor> professorList = new ArrayList<>();

        professorList.add(sergioMarquina);
        professorList.add(albertEinstein);
        professorList.add(philipPreobrajenskyi);

        Mockito.when(professorRepository.findAll()).thenReturn(professorList);

        List<Professor> actual = universityService.getAllProfessor();

        Assertions.assertEquals(3, actual.size());
    }

    @Test
    void createProfessor_Ok() {
        when(professorRepository.save(any(Professor.class)))
                .thenReturn(sergioMarquina);

        Professor savedProfessor = professorRepository.save(sergioMarquina);
        assertThat(savedProfessor.getFirstName()).isNotNull();
    }

    @Test
    void deleteProfessorById_Ok() {
        when(professorRepository.save(any(Professor.class)))
                .thenReturn(sergioMarquina);

        universityService.deleteProfessor(sergioMarquina.getId());
        verify(professorRepository).deleteById(sergioMarquina.getId());
    }

    @Test
    void deleteAllProfessors() {
        List<Professor> professorList = new ArrayList<>();

        professorList.add(sergioMarquina);
        professorList.add(albertEinstein);
        professorList.add(philipPreobrajenskyi);

        Mockito.when(professorRepository.findAll()).thenReturn(professorList);

        universityService.deleteAllProfessors();

        verify(professorRepository).deleteAll();
    }

    @Test
    void updateProfessor_Ok() {
        ProfessorCreationRequest professorCreationRequest = new ProfessorCreationRequest();
        professorCreationRequest.setFirstName("Sergio");
        professorCreationRequest.setLastName("Marquina");
        professorCreationRequest.setAge(40);
        professorCreationRequest.setSex("male");

        Professor updateProfessor = new Professor();
        updateProfessor.setId("f8s2j4l0s7dfh4k234l87acd");
        updateProfessor.setFirstName(professorCreationRequest.getFirstName());
        updateProfessor.setLastName(professorCreationRequest.getLastName());
        updateProfessor.setAge(professorCreationRequest.getAge());
        updateProfessor.setSex(professorCreationRequest.getSex());

        Mockito.when(professorRepository.findById(updateProfessor.getId()))
                .thenReturn(Optional.of(updateProfessor));
        universityService.updateProfessor(
                "f8s2j4l0s7dfh4k234l87acd", professorCreationRequest);

        verify(professorRepository).save(updateProfessor);
    }

    @Test
    void getStudentById_Ok() {
        when(studentRepository.findById(andriyShevschenko.getId()))
                .thenReturn(Optional.of(andriyShevschenko));

        Student actual = universityService.getStudentById(andriyShevschenko.getId());

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(andriyShevschenko.getId(), actual.getId());
    }

    @Test
    void getStudentById_StudentIdNotFound() {
        when(studentRepository.findById(ivanPetrov.getId()))
                .thenReturn(Optional.of(ivanPetrov));

        try {
            universityService.getStudentById("f8s2j4l0s7dfh4k234l87dcv");
        } catch (EntityNotFoundException e) {
            Assertions.assertEquals(
                    "Can't find student under given ID", e.getMessage());
            return;
        }
        Assertions.fail("Expected to receive EntityNotFoundException");
    }

    @Test
    void getAllStudents_Ok() {
        List<Student> studentList = new ArrayList<>();

        studentList.add(ivanPetrov);
        studentList.add(stepanVlizko);
        studentList.add(andriyShevschenko);

        Mockito.when(studentRepository.findAll()).thenReturn(studentList);

        List<Student> actual = universityService.getAllStudents();

        Assertions.assertEquals(3, actual.size());
    }

    @Test
    void createStudent_Ok() {
        when(studentRepository.save(any(Student.class)))
                .thenReturn(stepanVlizko);

        Student savedStudent = studentRepository.save(stepanVlizko);
        assertThat(savedStudent.getFirstName()).isNotNull();
    }

    @Test
    void deleteStudentById_Ok() {
        when(studentRepository.save(any(Student.class)))
                .thenReturn(andriyShevschenko);

        universityService.deleteStudent(andriyShevschenko.getId());
        verify(studentRepository).deleteById(andriyShevschenko.getId());
    }

    @Test
    void deleteAllStudents() {
        List<Student> studentsList = new ArrayList<>();

        studentsList.add(ivanPetrov);
        studentsList.add(stepanVlizko);
        studentsList.add(andriyShevschenko);

        Mockito.when(studentRepository.findAll())
                .thenReturn(studentsList);

        universityService.deleteAllStudents();

        verify(studentRepository).deleteAll();
    }

    @Test
    void updateAllStudents_Ok() {
        StudentCreationRequest studentCreationRequest = new StudentCreationRequest();
        studentCreationRequest.setFirstName("Oleg");
        studentCreationRequest.setLastName("Bezbatchenko");
        studentCreationRequest.setAge(32);
        studentCreationRequest.setSex("male");

        Student updateStudent = new Student();
        updateStudent.setId("f8s2j4l0s7dfh4k234l87rte");
        updateStudent.setFirstName(studentCreationRequest.getFirstName());
        updateStudent.setLastName(studentCreationRequest.getLastName());
        updateStudent.setAge(studentCreationRequest.getAge());
        updateStudent.setSex(studentCreationRequest.getSex());

        Mockito.when(studentRepository.findById(updateStudent.getId()))
                .thenReturn(Optional.of(updateStudent));
        universityService.updateStudent(
                "f8s2j4l0s7dfh4k234l87rte", studentCreationRequest);

        verify(studentRepository).save(updateStudent);
    }
}
