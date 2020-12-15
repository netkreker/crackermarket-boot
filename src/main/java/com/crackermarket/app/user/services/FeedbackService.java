package com.crackermarket.app.user.services;

import com.crackermarket.app.user.entities.Feedback;

import java.util.List;
import java.util.UUID;

public interface FeedbackService {

    // Search
    Feedback findFeedbackById(UUID id);

    List<Feedback> findAllFeedbacks();
    List<Feedback> findAllUserFeedbacks(UUID user_id);
    List<Feedback> findAllProductFeedbacks(UUID product_id);

    // Updating table
    void saveFeedback(Feedback feedback);
    void updateFeedback(Feedback feedback);
    void deleteFeedback(Feedback feedback);
}
