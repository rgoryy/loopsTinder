package com.example.clothestinder.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tag_itself")
    private String tagItself;

    @ManyToMany(mappedBy = "tags")
    Set<Request> requests;

    @ManyToMany(mappedBy = "tags")
    Set<Request> loops;
}
