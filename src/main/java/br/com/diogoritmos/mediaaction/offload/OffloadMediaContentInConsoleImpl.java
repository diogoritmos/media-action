package br.com.diogoritmos.mediaaction.offload;

import br.com.diogoritmos.mediaaction.transcript.MediaTranscript;
import br.com.diogoritmos.mediaaction.transcript.TranscriptBlock;

public class OffloadMediaContentInConsoleImpl implements OffloadMediaContent {
    @Override
    public void offload(MediaTranscript transcript) {
        for (TranscriptBlock block : transcript.getTranscriptBlocks()) {
            System.out.println(block.getText());
        }
    }
}
