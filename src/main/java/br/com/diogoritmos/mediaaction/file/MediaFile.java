package br.com.diogoritmos.mediaaction.file;

public class MediaFile {
    private String path;
    private long size;
    private String mimeType;

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
