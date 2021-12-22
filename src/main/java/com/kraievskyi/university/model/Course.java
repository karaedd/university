package com.kraievskyi.university.model;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class Course {
    @Id
    @Indexed(unique = true)
    private String id;
    private String name;
    private Instant startDate;
    private Instant endDate;
    @DBRef
    private List<Student> students;
    private int numberOfStudentsEnrolled;
    @DBRef
    private Professor professor;

    @Override
    public String toString() {
        return "Course{"
                + "id='" + id + '\''
                + ", name='" + name + '\''
                + ", startDate=" + startDate
                + ", endDate=" + endDate
                + ", students=" + students
                + ", professor=" + professor
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Course course = (Course) o;
        return numberOfStudentsEnrolled == course.numberOfStudentsEnrolled
                && Objects.equals(id, course.id)
                && Objects.equals(name, course.name)
                && Objects.equals(startDate, course.startDate)
                && Objects.equals(endDate, course.endDate)
                && Objects.equals(students, course.students)
                && Objects.equals(professor, course.professor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startDate, endDate, students,
                numberOfStudentsEnrolled, professor);
    }
}
