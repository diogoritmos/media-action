package br.com.diogoritmos.mediaaction.transcript;

import br.com.diogoritmos.mediaaction.file.MediaFile;

import java.util.Arrays;

public class LoadMockedMediaTranscriptImpl implements LoadMediaTranscript {
    @Override
    public MediaTranscript loadTranscript(MediaFile file) {
        return new MediaTranscript(
                Arrays.asList(
                        new TranscriptBlock("Hello, world!", 0, 1000),
                        new TranscriptBlock("This is a test", 1000, 2000)
                ),
                "en"
        );
    }
}
