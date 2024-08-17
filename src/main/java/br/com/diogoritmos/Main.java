package br.com.diogoritmos;

import br.com.diogoritmos.mediaaction.MediaFile;

import java.io.FileNotFoundException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        var mediaAction = new MediaAction();
        var mediaFile = new MediaFile("./mock.mp4", 1024, "video/mp4");

        try {
            mediaAction.loadMedia(mediaFile);
        } catch (FileNotFoundException e) {
            System.out.println("Error loading media file: " + e.getMessage());
        }

        mediaAction.generateTranscript();

        mediaAction.offloadContent();
    }
}