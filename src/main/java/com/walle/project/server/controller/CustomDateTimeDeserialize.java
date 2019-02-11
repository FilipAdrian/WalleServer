package com.walle.project.server.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class CustomDateTimeDeserialize extends JsonDeserializer {
    private SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd");

    @Override
    public Date deserialize(JsonParser paramJsonParser,
                            DeserializationContext paramDeserializationContext)
            throws IOException, JsonProcessingException {
        String str = paramJsonParser.getText();
        java.sql.Date sqlData = null;
        try {
            sqlData = new java.sql.Date (dateFormat.parse(str).getTime ());

            return sqlData;
        } catch (ParseException e) {
            // Handle exception here
        }
        return  new java.sql.Date (paramDeserializationContext.parseDate(str).getTime ());
    }

}
