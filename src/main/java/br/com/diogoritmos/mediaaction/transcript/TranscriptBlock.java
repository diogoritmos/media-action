package br.com.diogoritmos.mediaaction.transcript;

import java.math.BigInteger;

public class TranscriptBlock {
    private final String text;
    private final BigInteger startTime;
    private final BigInteger endTime;

    public TranscriptBlock(String text, BigInteger startTime, BigInteger endTime) {
        this.text = text;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getText() {
        return text;
    }

    public BigInteger getStartTime() {
        return startTime;
    }

    public BigInteger getEndTime() {
        return endTime;
    }
}
