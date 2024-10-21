package com.game.erudut.repository;

import com.game.erudut.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    Question findByquestionText(String questionText);
}
