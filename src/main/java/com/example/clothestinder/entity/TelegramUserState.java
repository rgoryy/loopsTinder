package com.example.clothestinder.entity;

import com.example.clothestinder.bot.UserState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "")
public class TelegramUserState {
    @Id //TODO убрать эту колонку
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "patient_id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "user_state")
    private UserState userState;

}
