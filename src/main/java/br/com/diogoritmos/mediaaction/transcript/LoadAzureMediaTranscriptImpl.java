package br.com.diogoritmos.mediaaction.transcript;

import br.com.diogoritmos.mediaaction.file.MediaFile;
import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LoadAzureMediaTranscriptImpl implements LoadMediaTranscript {
    private static final String SPEECH_KEY = System.getenv("SPEECH_KEY");
    private static final String SPEECH_REGION = System.getenv("SPEECH_REGION");

    @Override
    public MediaTranscript loadTranscript(MediaFile file, String language) {
        final var speechConfig = SpeechConfig.fromSubscription(SPEECH_KEY, SPEECH_REGION);
        speechConfig.setSpeechRecognitionLanguage(language);

        final AudioConfig audioConfig = AudioConfig.fromWavFileInput(file.getPath());

        final var speechRecognizer = new SpeechRecognizer(speechConfig, audioConfig);
        final var task = speechRecognizer.recognizeOnceAsync();
        final SpeechRecognitionResult result;

        try {
            result = task.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error recognizing speech: " + e.getMessage());

            throw new RuntimeException(e);
        }

        if (result.getReason() == ResultReason.RecognizedSpeech) {
            System.out.println("RECOGNIZED: Text=" + result.getText());
        } else if (result.getReason() == ResultReason.NoMatch) {
            System.out.println("NOMATCH: Speech could not be recognized.");
        } else if (result.getReason() == ResultReason.Canceled) {
            CancellationDetails cancellation = CancellationDetails.fromResult(result);
            System.out.println("CANCELED: Reason=" + cancellation.getReason());

            if (cancellation.getReason() == CancellationReason.Error) {
                System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                System.out.println("CANCELED: Did you set the speech resource key and region values?");
            }
        }

        return new MediaTranscript(
                List.of(
                        new TranscriptBlock(
                                result.getText(),
                                BigInteger.ZERO,
                                result.getDuration()
                        )), language);
    }
}
