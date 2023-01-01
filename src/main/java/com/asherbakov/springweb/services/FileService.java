package com.asherbakov.springweb.services;

import java.io.File;
import java.nio.file.Path;

public interface FileService {
    boolean saveToFile(Path path, String str);

    String readFromFile(Path path);

    File getDataFile(Path path);

    File getDataFile(String path);
}
