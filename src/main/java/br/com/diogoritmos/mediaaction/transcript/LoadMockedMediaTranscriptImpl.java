package br.com.diogoritmos.mediaaction.transcript;

import br.com.diogoritmos.mediaaction.file.MediaFile;

import java.math.BigInteger;
import java.util.Arrays;

public class LoadMockedMediaTranscriptImpl implements LoadMediaTranscript {
    @Override
    public MediaTranscript loadTranscript(MediaFile file, String language) {
        return new MediaTranscript(
                Arrays.asList(
                        new TranscriptBlock("Hello, world!", BigInteger.valueOf(0), BigInteger.valueOf(1000)),
                        new TranscriptBlock("This is a test", BigInteger.valueOf(1000), BigInteger.valueOf(2000))
                ),
                "en-US"
        );
    }
}
