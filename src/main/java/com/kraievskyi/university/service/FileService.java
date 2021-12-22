package com.kraievskyi.university.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    public void writeToFile(String filePath, String data) {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Can't create file " + filePath, e);
            }
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, false))) {
            bufferedWriter.write(data);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file", e);
        }
    }
}
