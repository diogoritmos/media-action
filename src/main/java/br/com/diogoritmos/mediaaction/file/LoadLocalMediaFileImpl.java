package br.com.diogoritmos.mediaaction.file;

import org.apache.commons.io.input.CountingInputStream;
import org.apache.tika.Tika;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class LoadLocalMediaFileImpl implements LoadMediaFile {
    private static final List<String> SUPPORTED_MIMETYPES = List.of("audio/vnd.wave");
    private static final Tika tika = new Tika();
    private static final long MAX_SIZE = 1_073_741_824; // 1GB in bytes

    @Override
    public MediaFile loadFile(String path) {
        var fullPath = Paths.get(path);
        var resourcePath = fullPath.toString();

        try (FileInputStream fis = new FileInputStream(resourcePath)) {
            final var cis = new CountingInputStream(fis);
            var mimeType = tika.detect(cis);
            System.out.println("Detected MIME type: " + mimeType);

            if (!SUPPORTED_MIMETYPES.contains(mimeType)) {
                throw new IllegalArgumentException("Unsupported media type");
            }

            byte[] buffer = new byte[8192];
            while (cis.read(buffer) != -1) {
                if (cis.getByteCount() > MAX_SIZE) {
                    throw new IOException("File size exceeds the maximum allowed");
                }
            }
            System.out.println("Media content length: " + cis.getByteCount());

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
