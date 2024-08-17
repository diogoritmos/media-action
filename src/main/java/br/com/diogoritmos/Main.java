package br.com.diogoritmos;

import br.com.diogoritmos.mediaaction.transcript.MediaTranscript;
import br.com.diogoritmos.mediaaction.file.LoadLocalMediaFileImpl;
import br.com.diogoritmos.mediaaction.file.LoadMediaFile;
import br.com.diogoritmos.mediaaction.file.MediaFile;
import br.com.diogoritmos.mediaaction.offload.OffloadMediaContent;
import br.com.diogoritmos.mediaaction.offload.OffloadMediaContentInConsoleImpl;
import br.com.diogoritmos.mediaaction.transcript.LoadMediaTranscript;
import br.com.diogoritmos.mediaaction.transcript.LoadMockedMediaTranscriptImpl;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        final LoadMediaFile loadMediaFile = new LoadLocalMediaFileImpl();
        final MediaFile file;

        try {
            file = loadMediaFile.loadFile("./mock.mp4");
        } catch (FileNotFoundException e) {
            System.out.println("Error loading media file: " + e.getMessage());
            return;
        }

        final LoadMediaTranscript loadMediaTranscript = new LoadMockedMediaTranscriptImpl();
        final MediaTranscript transcript = loadMediaTranscript.loadTranscript(file);

        final OffloadMediaContent offloadMediaContent = new OffloadMediaContentInConsoleImpl();
        offloadMediaContent.offload(transcript);
    }
}