package com.eazyschool.eazyschool.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name = "holidays")
public class Holiday {

    @Id
    private String day;
    private String reason;

    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        FESTIVAL, FEDERAL
    }
}
