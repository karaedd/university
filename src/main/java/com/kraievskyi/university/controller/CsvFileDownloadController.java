package com.kraievskyi.university.controller;

import com.kraievskyi.university.model.Course;
import com.kraievskyi.university.repository.CourseRepository;
import com.kraievskyi.university.repository.ProfessorRepository;
import com.kraievskyi.university.repository.StudentRepository;
import com.kraievskyi.university.service.FileService;
import com.kraievskyi.university.service.ReportService;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

@Controller
public class CsvFileDownloadController {
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;

    public CsvFileDownloadController(CourseRepository courseRepository,
                                     ProfessorRepository professorRepository,
                                     StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
        this.studentRepository = studentRepository;
    }

    @RequestMapping(value = "/downloadCSV")
    public void downloadCsv(HttpServletResponse response) throws IOException {
        String csvFileName = "books.csv";
        response.setContentType("text/csv");

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment); filename=\"%s\"",
                csvFileName);
        response.setHeader(headerKey, headerValue);

        List<Course> allCourses = courseRepository.findAll();

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);

        String[] header = {"id", "name", "startDate",
                "endDate", "numberOfStudentsEnrolled", "professor"};

        csvWriter.writeHeader(header);

        try {
            for (Course course : allCourses) {
                csvWriter.write(course, header);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file", e);
        } finally {
            csvWriter.close();
        }
        FileService fileService = new FileService();
        ReportService reportService = new ReportService();
        String report = reportService.makeReport(allCourses);
        fileService.writeToFile("src/main/resources/report/outPutValue.csv", report);
    }
}
