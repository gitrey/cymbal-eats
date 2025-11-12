package com.example.customerservice.reviewagent;

public class ReviewAgent {

    /**
     * Analyzes the sentiment of a given review text.
     *
     * @param reviewText The text of the review.
     * @return A sentiment score between -1.0 (negative) and 1.0 (positive).
     */
    public double analyzeSentiment(String reviewText) {
        if (reviewText == null || reviewText.isEmpty()) {
            return 0.0;
        }

        // This is a mock implementation. A real implementation would use a sentiment analysis library.
        if (reviewText.toLowerCase().contains("great") || reviewText.toLowerCase().contains("good")) {
            return 0.8;
        } else if (reviewText.toLowerCase().contains("bad") || reviewText.toLowerCase().contains("poor")) {
            return -0.8;
        }

        return 0.1; // Neutral
    }
}
