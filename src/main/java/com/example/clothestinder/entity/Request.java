package com.example.clothestinder.entity;


import com.example.clothestinder.entity.enums.Tonality;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

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
    @Column(name = "user_id")
    private Long userId;
    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDateTime;
    @Column(name = "bpm_min")
    private Long bpmMin;
    @Column(name = "bpm_max")
    private Long bpmMax;
    @Column(name = "tonality")
    @Enumerated(EnumType.STRING)
    private Tonality tonality;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "request_tags",
            joinColumns = {@JoinColumn(name = "request_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    Set<Tag> tags;
}
