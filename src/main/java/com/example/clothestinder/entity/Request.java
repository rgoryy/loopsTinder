package com.example.clothestinder.entity;


import com.example.clothestinder.entity.enums.Tonality;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long userId;
    @Column
    private LocalDate creationDate; //todo search date type for postgres
    @Column
    private Long bpmMin;
    @Column
    private Long bpmMax;
    @Column
    @Enumerated(EnumType.STRING)
    private Tonality tonality;
}
