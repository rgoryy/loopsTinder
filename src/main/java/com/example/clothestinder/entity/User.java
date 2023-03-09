package com.example.clothestinder.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

/*@Getter
@Setter
@ToString
@RequiredArgsConstructor*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usr")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;
    @Column(name = "telegram_id")
    private Long telegramId;
}
