package com.kraievskyi.university.model;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class Professor {
    @Id
    @Indexed(unique = true)
    private String id;
    private String firstName;
    private String lastName;
    private int age;
    private String sex;

    @Override
    public String toString() {
        return "Professor{"
                + "id='" + id + '\''
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", age=" + age
                + ", sex='" + sex + '\''
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
        Professor professor = (Professor) o;
        return age == professor.age
                && Objects.equals(id, professor.id)
                && Objects.equals(firstName, professor.firstName)
                && Objects.equals(lastName, professor.lastName)
                && Objects.equals(sex, professor.sex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, age, sex);
    }
}
