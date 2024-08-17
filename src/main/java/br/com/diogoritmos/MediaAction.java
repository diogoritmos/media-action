package br.com.diogoritmos;

import br.com.diogoritmos.mediaaction.MediaFile;
import br.com.diogoritmos.mediaaction.MediaTranscript;
import br.com.diogoritmos.mediaaction.transcript.SubtitleBlock;
import org.apache.tika.Tika;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class MediaAction {
    private static final List<String> SUPPORTED_MIMETYPES = List.of("video/mp4");
    private static final Tika tika = new Tika();

    public MediaFile loadMedia(String path) throws FileNotFoundException {
        var beforeMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println("Before memory: " + beforeMemory);

        var resource = getClass().getClassLoader().getResource(path);
        if (resource == null) {
            throw new FileNotFoundException("File not found");
        }

        var resourcePath = resource.getFile();
        try (FileInputStream fis = new FileInputStream(resourcePath)) {
            var content = fis.readAllBytes();
            System.out.println("Content length: " + content.length);
            var mimeType = tika.detect(content);
            System.out.println("Detected MIME type: " + mimeType);

            long afterMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            System.out.println("After memory: " + afterMemory);
            System.out.println("Memory consumed: " + (afterMemory - beforeMemory) + " bytes");

            if (!SUPPORTED_MIMETYPES.contains(mimeType)) {
                throw new IllegalArgumentException("Unsupported media type");
            }

            return new MediaFile(
                resourcePath,
                content.length,
                mimeType
            );
        } catch (IOException e) {
            System.out.println("Error loading media file because of: " + e.getMessage());
            throw new RuntimeException("Error loading media file");
        }
    }

    public MediaTranscript generateTranscript(MediaFile file) {
        return new MediaTranscript(
            Arrays.asList(
                new SubtitleBlock("Hello, world!", 0, 1000),
                new SubtitleBlock("This is a test", 1000, 2000)
            ),
            "en"
        );
    }

    public void offloadContent(MediaTranscript transcript) {
        for (SubtitleBlock block : transcript.getSubtitleBlocks()) {
            System.out.println(block.getText());
        }
    }
}