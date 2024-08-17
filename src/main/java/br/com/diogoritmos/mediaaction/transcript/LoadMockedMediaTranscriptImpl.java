package br.com.diogoritmos.mediaaction.transcript;

import br.com.diogoritmos.mediaaction.file.MediaFile;

import java.util.Arrays;

public class LoadMockedMediaTranscriptImpl implements LoadMediaTranscript {
    @Override
    public MediaTranscript loadTranscript(MediaFile file, String language) {
        return new MediaTranscript(
                Arrays.asList(
                        new TranscriptBlock("Hello, world!"),
                        new TranscriptBlock("This is a test")
                ),
                "en-US"
        );
    }
}
