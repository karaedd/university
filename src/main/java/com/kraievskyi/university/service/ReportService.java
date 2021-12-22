package com.kraievskyi.university.service;

import com.kraievskyi.university.model.Course;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private static final String HEAD = "id, name, startDate, "
            + "endDate, numberOfStudentsEnrolled, professor";
    private static final String SEPARATOR = ", ";

    public String makeReport(List<Course> allCourses) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HEAD);
        for (Course course : allCourses) {
            stringBuilder.append("\n")
                    .append(course.getId())
                    .append(SEPARATOR)
                    .append(course.getName())
                    .append(SEPARATOR)
                    .append(course.getStartDate())
                    .append(SEPARATOR)
                    .append(course.getEndDate())
                    .append(SEPARATOR)
                    .append(course.getNumberOfStudentsEnrolled())
                    .append(" student(s)")
                    .append(SEPARATOR)
                    .append(course.getProfessor().getFirstName())
                    .append(" ")
                    .append(course.getProfessor().getLastName());
        }
        return stringBuilder.toString();
    }
}
