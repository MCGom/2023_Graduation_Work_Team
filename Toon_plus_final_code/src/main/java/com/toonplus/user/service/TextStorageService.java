package com.toonplus.user.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class TextStorageService {

    private static final String STORAGE_DIRECTORY = "C:\\save"; // 저장할 디렉토리

    public void saveText(String username, String text) throws IOException {
        String userDirectory = STORAGE_DIRECTORY + File.separator + username;
        File directory = new File(userDirectory);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = userDirectory + File.separator + "memo.txt";
        Files.write(Paths.get(filePath), text.getBytes());
    }

    public String loadText(String username) throws IOException {
        String filePath = STORAGE_DIRECTORY + File.separator + username + File.separator + "memo.txt";

        if (Files.exists(Paths.get(filePath))) {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } else {
            return "";
        }
    }
}
