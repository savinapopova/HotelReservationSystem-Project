package com.example.hotelreservationsystem.util;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class JsonParser {

    private static Gson gson;

    @Autowired
    public JsonParser(Gson gson) {
        this.gson = gson;
    }


    public static <T> T[] getDTOArray(Class<T[]> clazz, String filePath) throws IOException {
        String json = Files.readString(Path.of(filePath));
        return gson.fromJson(json, clazz);
    }

    public static  <T> T getDTO(Class<T> dtoClass, String filePath) throws IOException {

        String json = Files.readString(Path.of(filePath));
        T dto = gson.fromJson(json, dtoClass);
        return dto;
    }

}
