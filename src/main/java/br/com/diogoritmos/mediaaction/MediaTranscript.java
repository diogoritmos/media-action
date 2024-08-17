package br.com.diogoritmos.mediaaction;

import br.com.diogoritmos.mediaaction.transcript.SubtitleBlock;

import java.util.List;

public class MediaTranscript {
    private List<SubtitleBlock> subtitleBlocks;
    private String language;

    public MediaTranscript(List<SubtitleBlock> subtitleBlocks, String language) {
        this.subtitleBlocks = subtitleBlocks;
        this.language = language;
    }

    public List<SubtitleBlock> getSubtitleBlocks() {
        return subtitleBlocks;
    }

    public String getLanguage() {
        return language;
    }
}
