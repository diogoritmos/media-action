package br.com.diogoritmos;

import br.com.diogoritmos.mediaaction.file.LoadLocalMediaFileImpl;
import br.com.diogoritmos.mediaaction.file.LoadMediaFile;
import br.com.diogoritmos.mediaaction.file.MediaFile;
import br.com.diogoritmos.mediaaction.offload.OffloadMediaContent;
import br.com.diogoritmos.mediaaction.offload.OffloadMediaContentInConsoleImpl;
import br.com.diogoritmos.mediaaction.transcript.LoadAzureMediaTranscriptImpl;
import br.com.diogoritmos.mediaaction.transcript.LoadMediaTranscript;
import br.com.diogoritmos.mediaaction.transcript.MediaTranscript;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        final LoadMediaFile loadMediaFile = new LoadLocalMediaFileImpl();
        final MediaFile file;

        try {
            file = loadMediaFile.loadFile("mock.wav");
        } catch (FileNotFoundException e) {
            System.out.println("Error loading media file: " + e.getMessage());
            return;
        }

        final LoadMediaTranscript loadMediaTranscript = new LoadAzureMediaTranscriptImpl();
        final MediaTranscript transcript = loadMediaTranscript.loadTranscript(file, "pt-BR");

        final OffloadMediaContent offloadMediaContent = new OffloadMediaContentInConsoleImpl();
        offloadMediaContent.offload(transcript);
    }
}