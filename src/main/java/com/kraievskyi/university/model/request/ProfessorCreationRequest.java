package com.kraievskyi.university.model.request;

import lombok.Data;

@Data
public class ProfessorCreationRequest {
    private String firstName;
    private String lastName;
    private int age;
    private String sex;
}
