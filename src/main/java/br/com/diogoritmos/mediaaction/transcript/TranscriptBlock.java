package br.com.diogoritmos.mediaaction.transcript;

public class TranscriptBlock {
    private String text;
    private long startTime;
    private long endTime;

    public TranscriptBlock(String text, long startTime, long endTime) {
        this.text = text;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getText() {
        return text;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }
}
