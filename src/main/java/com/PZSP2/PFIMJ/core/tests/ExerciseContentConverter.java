package com.PZSP2.PFIMJ.core.tests;

import com.PZSP2.PFIMJ.db.entities.Exercise;
import com.PZSP2.PFIMJ.db.entities.ExerciseVersion;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class ExerciseContentConverter implements AttributeConverter<List<ExerciseVersion>, String> {
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<ExerciseVersion> testContent) {

        String testJson = null;
        try {
            testJson = mapper.writeValueAsString(testContent);
        } catch (final JsonProcessingException e) {
        }
        return testJson;
    }

    @Override
    public List<ExerciseVersion> convertToEntityAttribute(String testContentJSON) {

        List<ExerciseVersion> content = null;
        try {
            content = mapper.readValue(testContentJSON, mapper.getTypeFactory().constructCollectionType(List.class, ExerciseVersion.class));
        } catch (final IOException e) {
        }

        return content;
    }
}

