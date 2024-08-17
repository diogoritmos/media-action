package br.com.diogoritmos.mediaaction.file;

import org.apache.commons.io.input.CountingInputStream;
import org.apache.tika.Tika;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class LoadLocalMediaFileImpl implements LoadMediaFile {
    private static final List<String> SUPPORTED_MIMETYPES = List.of("audio/vnd.wave");
    private static final Tika tika = new Tika();
    private static final long MAX_SIZE = 1_073_741_824; // 1GB in bytes

    @Override
    public MediaFile loadFile(String path) throws FileNotFoundException {
        var beforeMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        var resource = getClass().getClassLoader().getResource(path);
        if (resource == null) {
            throw new FileNotFoundException("File not found");
        }

        var resourcePath = resource.getFile();
        System.out.println("Resource path: " + resourcePath);

        try (FileInputStream fis = new FileInputStream(resourcePath)) {
            final var cis = new CountingInputStream(fis);
            var mimeType = tika.detect(cis);
            System.out.println("Detected MIME type: " + mimeType);
            System.out.println("Bytes read until MIME type detection: " + cis.getByteCount());

            if (!SUPPORTED_MIMETYPES.contains(mimeType)) {
                throw new IllegalArgumentException("Unsupported media type");
            }

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = cis.read(buffer)) != -1) {
                if (cis.getByteCount() > MAX_SIZE) {
                    throw new IOException("File size exceeds the maximum allowed");
                }
            }
            System.out.println("Bytes read in total: " + cis.getByteCount());

            long afterMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            System.out.println("Memory consumed after load: " + (afterMemory - beforeMemory) + " bytes");

            return new MediaFile(
                    resourcePath,
                    cis.getByteCount(),
                    mimeType
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
