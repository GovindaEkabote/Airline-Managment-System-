package com.airline.service;

import com.airline.domain.AncillaryMataData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class AncillaryMataDataConverter implements AttributeConverter<AncillaryMataData, String> {

    private static final ObjectMapper objectMapper =new ObjectMapper();


    @Override
    public String convertToDatabaseColumn(
            AncillaryMataData ancillaryMataData) {

        if (ancillaryMataData == null) {
            return null;
        }

        try {
            return objectMapper.writeValueAsString(ancillaryMataData);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(
                    "Failed to convert ancillary metadata to JSON", e);
        }
    }

    @Override
    public AncillaryMataData convertToEntityAttribute(String json) {
        if (json == null || json.isBlank()) {
            return null;
        }

        try {
            return objectMapper.readValue(json, AncillaryMataData.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(
                    "Failed to convert JSON to ancillary metadata", e);
        }
    }
}
