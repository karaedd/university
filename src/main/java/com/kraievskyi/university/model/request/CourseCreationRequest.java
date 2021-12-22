package com.kraievskyi.university.model.request;

import java.util.List;
import lombok.Data;

@Data
public class CourseCreationRequest {
    private String name;
    private List<String> studentsId;
    private String professorId;
}
