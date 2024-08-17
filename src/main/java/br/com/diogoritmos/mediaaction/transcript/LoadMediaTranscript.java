package br.com.diogoritmos.mediaaction.transcript;

import br.com.diogoritmos.mediaaction.file.MediaFile;

public interface LoadMediaTranscript {
    MediaTranscript loadTranscript(MediaFile file, String language);
}
