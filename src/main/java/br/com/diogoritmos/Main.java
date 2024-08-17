package br.com.diogoritmos;

import br.com.diogoritmos.mediaaction.MediaFile;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        var mediaAction = new MediaAction();
        MediaFile file = null;

        try {
            file = mediaAction.loadMedia("./mock.mp4");
        } catch (FileNotFoundException e) {
            System.out.println("Error loading media file: " + e.getMessage());
        }

        var transcript = mediaAction.generateTranscript(file);

        mediaAction.offloadContent(transcript);
    }
}