package com.example.hotelreservationsystem.util;


import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;


public class Logger {




    public static void log(String path, String result) {
        File file = createFile(path);

        try (java.io.FileWriter fileWriter = new java.io.FileWriter(file, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(result);
            bufferedWriter.newLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static File createFile(String path) {
        File file = new File(path);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return file;
    }
}
