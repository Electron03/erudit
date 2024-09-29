package com.game.erudut.service;

import com.game.erudut.model.Question;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    public Question fetchNextQuestion() {
        // запрос к API для получения вопроса
        return new Question();
    }
}
