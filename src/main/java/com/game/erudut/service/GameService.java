package com.game.erudut.service;

import com.game.erudut.model.Player;
import com.game.erudut.model.Question;
import com.game.erudut.model.Room;
import com.game.erudut.repository.QuestionRepository;
import org.apache.logging.log4j.message.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    // Обработка ответа игрока
    public ResponseEntity<?> processAnswer(Message message) {
        // Логика обработки сообщения и ответа, начисление баллов
        return ResponseEntity.ok("Ответ успешно обработан");
    }
    public QuestionRepository questionRepository;
    // Обновление счета игрока
    public void updatePlayerScore(String playerName, Long roomId) {
        Room room = new Room(roomId); // Создаем комнату
        Player player = room.getPlayerByName(playerName); // Находим игрока по имени
        player.setScore(player.getScore() + 10); // Добавляем 10 баллов за правильный ответ
    }

    // Проверка правильности ответа
    public boolean checkAnswer(Long questionId, Long answerId) {
        Long correctAnswer = findCorrectAnswerByQuestionId(questionId); // Поиск правильного ответа
        if(answerId==correctAnswer)return true;
        else return false;
    }

    // Метод для поиска правильного ответа по ID вопроса
    public Long findCorrectAnswerByQuestionId(Long questionId) {
        // Логика получения вопроса и правильного ответа
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Вопрос не найден"));
        return question.getCorrectAnswer();
    }
}

