package br.com.diogoritmos.mediaaction.offload;

import br.com.diogoritmos.mediaaction.transcript.MediaTranscript;
import br.com.diogoritmos.mediaaction.transcript.TranscriptBlock;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.ai.textanalytics.models.*;
import com.azure.ai.textanalytics.TextAnalyticsClientBuilder;
import com.azure.ai.textanalytics.TextAnalyticsClient;

import java.util.ArrayList;

public class OffloadMediaContentGptImpl implements OffloadMediaContent {
    private static final String LANGUAGE_KEY = System.getenv("LANGUAGE_KEY");
    private static final String LANGUAGE_ENDPOINT = System.getenv("LANGUAGE_ENDPOINT");

    @Override
    public void offload(MediaTranscript transcript) {
        System.out.println("Offloading media content in GPT...");

        final var sb = new StringBuilder();
        for (TranscriptBlock block : transcript.getTranscriptBlocks()) {
            sb.append(block.getText()).append("\n");
        }

        TextAnalyticsClient client = authenticateClient(LANGUAGE_KEY, LANGUAGE_ENDPOINT);

        final var documents = new ArrayList<String>();
        documents.add(sb.toString());

        final var syncPoller =
                client.beginAnalyzeActions(documents,
                        new TextAnalyticsActions().setDisplayName("{tasks_display_name}")
                                .setExtractiveSummaryActions(new ExtractiveSummaryAction()),
                        "en",
                        new AnalyzeActionsOptions());

        syncPoller.waitForCompletion();

        syncPoller.getFinalResult().forEach(actionsResult -> {
            System.out.println("Extractive Summarization action results:");
            for (final var actionResult : actionsResult.getExtractiveSummaryResults()) {
                if (!actionResult.isError()) {
                    for (final var documentResult : actionResult.getDocumentsResults()) {
                        if (!documentResult.isError()) {
                            System.out.println("\tExtracted summary sentences:");
                            for (final var summarySentence : documentResult.getSentences()) {
                                System.out.printf(
                                        "\t\t Sentence text: %s, length: %d, offset: %d, rank score: %f.%n",
                                        summarySentence.getText(), summarySentence.getLength(),
                                        summarySentence.getOffset(), summarySentence.getRankScore());
                            }
                        } else {
                            System.out.printf("\tCannot extract summary sentences. Error: %s%n",
                                    documentResult.getError().getMessage());
                        }
                    }
                } else {
                    System.out.printf("\tCannot execute Extractive Summarization action. Error: %s%n",
                            actionResult.getError().getMessage());
                }
            }
        });
    }

    private TextAnalyticsClient authenticateClient(String key, String endpoint) {
        return new TextAnalyticsClientBuilder()
                .credential(new AzureKeyCredential(key))
                .endpoint(endpoint)
                .buildClient();
    }
}
