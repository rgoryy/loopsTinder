package com.example.clothestinder.entity;


import com.example.clothestinder.entity.enums.Tonality;
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
@Table(name = "loop")
public class Loop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long userID;
    @Column
    private String filePath; //todo
    @Column
    @Enumerated(EnumType.STRING)
    private Tonality tonality; //todo
    @Column
    private Long bpm;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Loop loop;

    @ManyToMany
    @JoinTable(
            name = "loop_tags",
            joinColumns = { @JoinColumn(name = "loop_id")},
            inverseJoinColumns = { @JoinColumn(name = "tag_id")}
    )
    private Set<Loop> loops;

}
