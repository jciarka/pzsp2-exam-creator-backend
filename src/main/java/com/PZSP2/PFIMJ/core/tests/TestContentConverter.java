package com.PZSP2.PFIMJ.core.tests;

import com.PZSP2.PFIMJ.db.entities.Exercise;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class TestContentConverter implements AttributeConverter<List<Exercise>, String> {

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Exercise> testContent) {

        String testJson = null;
        try {
            testJson = mapper.writeValueAsString(testContent);
        } catch (final JsonProcessingException e) {

        }
        return testJson;
    }

    @Override
    public List<Exercise> convertToEntityAttribute(String testContentJSON) {

        List<Exercise> testContent = null;
        try {
            testContent = mapper.readValue(testContentJSON, mapper.getTypeFactory().constructCollectionType(List.class, Exercise.class));
        } catch (final IOException e) {
        }

        return testContent;
    }
}
