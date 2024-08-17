package br.com.diogoritmos.mediaaction.file;

public class MediaFile {
    private final String path;
    private final long size;
    private final String mimeType;

    public MediaFile(String path, long size, String mimeType) {
        this.path = path;
        this.size = size;
        this.mimeType = mimeType;
    }

    public String getPath() {
        return path;
    }

    public long getSize() {
        return size;
    }

    public String getMimeType() {
        return mimeType;
    }
}
