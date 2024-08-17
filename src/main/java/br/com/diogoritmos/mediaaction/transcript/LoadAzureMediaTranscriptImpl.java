package br.com.diogoritmos.mediaaction.transcript;

import br.com.diogoritmos.mediaaction.file.MediaFile;
import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LoadAzureMediaTranscriptImpl implements LoadMediaTranscript {
    private static final String SPEECH_KEY = System.getenv("SPEECH_KEY");
    private static final String SPEECH_REGION = System.getenv("SPEECH_REGION");

    private boolean isRunning = true;

    @Override
    public MediaTranscript loadTranscript(MediaFile file, String language) {
        final var speechConfig = SpeechConfig.fromSubscription(SPEECH_KEY, SPEECH_REGION);
        speechConfig.setSpeechRecognitionLanguage(language);

        final AudioConfig audioConfig = AudioConfig.fromWavFileInput(file.getPath());

        final var recognizer = new SpeechRecognizer(speechConfig, audioConfig);
        final var transcriptBlocks = new ArrayList<TranscriptBlock>();

        // Set up the event handlers
        recognizer.sessionStarted.addEventListener((s, e) -> {
            System.out.println("Started to recognize media file.");
            this.isRunning = true;
        });

        recognizer.recognized.addEventListener((s, e) -> {
            if (e.getResult().getReason() == ResultReason.RecognizedSpeech) {
                System.out.println("Recognized: " + e.getResult().getText());
                transcriptBlocks.add(new TranscriptBlock(e.getResult().getText()));
            } else if (e.getResult().getReason() == ResultReason.NoMatch) {
                System.out.println("No speech could be recognized.");
            }
        });

        recognizer.canceled.addEventListener((s, e) -> {
            System.out.println("Recognition canceled: " + e.getReason());
            this.isRunning = false;

            if (e.getReason() == CancellationReason.Error) {
                System.out.println("Error details: " + e.getErrorDetails());
            }
        });

        recognizer.sessionStopped.addEventListener((s, e) -> {
            System.out.println("Session stopped.");
            this.isRunning = false;
        });

        // Start continuous recognition
        final var startTime = System.currentTimeMillis();
        try {
            recognizer.startContinuousRecognitionAsync().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        // @TO-DO: timeout if it takes too long
        while (this.isRunning) {}

        // Stop continuous recognition
        try {
            recognizer.stopContinuousRecognitionAsync().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        final var endTime = System.currentTimeMillis();
        System.out.println("Transcription finished in " + (endTime - startTime) + " milliseconds.");

        return new MediaTranscript(transcriptBlocks, language);
    }
}
