package com.api.sample.common.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BooleanToCharConverter implements AttributeConverter<Boolean, Character> {

    @Override
    public Character convertToDatabaseColumn(Boolean attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute ? 'Y' : 'N';
    }

    @Override
    public Boolean convertToEntityAttribute(Character dbData) {
        if (dbData == null) {
            return null;
        }
        return 'Y' == dbData;
    }
}
