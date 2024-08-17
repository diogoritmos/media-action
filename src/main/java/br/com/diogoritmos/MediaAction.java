package br.com.diogoritmos;

import br.com.diogoritmos.mediaaction.ContentOffloader;
import br.com.diogoritmos.mediaaction.MediaFile;
import br.com.diogoritmos.mediaaction.MediaTranscript;
import br.com.diogoritmos.mediaaction.transcript.SubtitleBlock;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class MediaAction {
    private static List<String> SUPPORTED_MIMETYPES = List.of("video/mp4");

    private FileInputStream fis;
    private MediaTranscript transcript;

    public void loadMedia(MediaFile file) throws FileNotFoundException {
        if (!SUPPORTED_MIMETYPES.contains(file.getMimeType())) {
            throw new IllegalArgumentException("Unsupported media type");
        }

        var resource = getClass().getClassLoader().getResource(file.getPath());

        if (resource == null) {
            throw new FileNotFoundException("File not found");
        }

        try (FileInputStream fis = new FileInputStream(resource.getFile())) {
            this.fis = fis;
        } catch (IOException e) {
            System.out.println("Error loading media file because of: " + e.getMessage());
            throw new RuntimeException("Error loading media file");
        }
    }

    public void generateTranscript() {
        if (this.fis == null) {
            throw new IllegalStateException("Media file not loaded");
        }

        this.transcript = new MediaTranscript(
            Arrays.asList(
                new SubtitleBlock("Hello, world!", 0, 1000),
                new SubtitleBlock("This is a test", 1000, 2000)
            ),
            "en"
        );
    }

    public void offloadContent() {
        if (this.transcript == null) {
            throw new IllegalStateException("Transcript not generated");
        }

        for (SubtitleBlock block : this.transcript.getSubtitleBlocks()) {
            System.out.println(block.getText());
        }
    }
}