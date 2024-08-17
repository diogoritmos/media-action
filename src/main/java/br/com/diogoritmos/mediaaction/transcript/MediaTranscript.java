package br.com.diogoritmos.mediaaction.transcript;

import java.util.List;

public class MediaTranscript {
    private final List<TranscriptBlock> transcriptBlocks;
    private final String language;

    public MediaTranscript(List<TranscriptBlock> transcriptBlocks, String language) {
        this.transcriptBlocks = transcriptBlocks;
        this.language = language;
    }

    public List<TranscriptBlock> getTranscriptBlocks() {
        return transcriptBlocks;
    }

    public String getLanguage() {
        return language;
    }
}
