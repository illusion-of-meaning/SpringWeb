package com.asherbakov.springweb.services;

import java.nio.file.Path;

public interface FileService {
    boolean saveToFile(Path path, String str);

    String readFromFile(Path path);
}
