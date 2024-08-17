package br.com.diogoritmos.mediaaction.file;

import java.io.FileNotFoundException;

public interface LoadMediaFile {
    MediaFile loadFile(String path) throws FileNotFoundException;
}
