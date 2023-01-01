package com.asherbakov.springweb.services.impl;

import com.asherbakov.springweb.services.FileService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public boolean saveToFile(Path path, String str) {
        try {
            cleanFile(path);
            Files.writeString(path, str);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String readFromFile(Path path) {
        String result;
        try {
            if (!Files.exists(path)) {
                cleanFile(path);
            }
            result = Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public File getDataFile(Path path) {
        return path.toFile();
    }

    @Override
    public File getDataFile(String path) {
        Path p = Path.of(path);
        return getDataFile(p);
    }

    private boolean cleanFile(Path path) {
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
            Files.writeString(path, "{}");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
