package com.example.clothestinder.dto;

import com.example.clothestinder.entity.Tag;
import com.example.clothestinder.entity.enums.Tonality;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddRequestDTO {
    private String login;
    private Long bpmMin;
    private Long bpmMax;
    private Tonality tonality; //todo
    private Set<Tag> tags;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddRequestDTO that = (AddRequestDTO) o;
        return login.equals(that.login) && Objects.equals(bpmMin, that.bpmMin) && Objects.equals(bpmMax, that.bpmMax) && tonality == that.tonality && tags.equals(that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, bpmMin, bpmMax, tonality, tags);
    }
}
