package com.example.clothestinder.entity.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class TonalityConverter implements AttributeConverter<Tonality, String> {
    @Override
    public String convertToDatabaseColumn(Tonality tonality) {
        if (tonality == null) {
            return null;
        }
        return tonality.getCode();
    }

    @Override
    public Tonality convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }
        return Stream.of(Tonality.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
