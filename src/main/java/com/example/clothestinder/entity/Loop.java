package com.example.clothestinder.entity;


import com.example.clothestinder.entity.enums.Tonality;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loop")
public class Loop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userID;
    @Column(name = "file_path")
    private String filePath; //todo
    @Column(name = "tonality")
    @Enumerated(EnumType.STRING)
    private Tonality tonality; //todo
    @Column(name = "bpm")
    private Long bpm;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "loop_tags",
            joinColumns = {@JoinColumn(name = "loop_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private Set<Loop> loops;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Loop loop = (Loop) o;
        return id != null && Objects.equals(id, loop.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
