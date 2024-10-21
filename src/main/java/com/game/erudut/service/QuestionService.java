package com.game.erudut.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.game.erudut.model.Answers;
import com.game.erudut.model.Question;
import com.game.erudut.repository.QuestionRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private QuestionRepository questionRepository;

    private String prompt ="Ты телеведущий очень популярной передачи,\n" +
            "твоя задача задавать вопросы на тему 'История минувших дней',\n" +
            "ты должен задать мне вопрос на историческую тему и затем дать четыре ответа(1 правильный, 3 неправильных).\n" +
            "Вот шаблон которому тебе надо следовать:\n" +
            "{\n" +
            "  question: \"Вопрос который задал ведущий, то есть ты\",\n" +
            "  answers: [\n" +
            "    {\n" +
            "      id: id вопроса, тип число,\n" +
            "      text: ответ на вопрос, тип строка\n" +
            "    }\n" +
            "  ],\n" +
            "  correct: id правильного ответа из массива answers, тип число\n" +
            "},\n" +
            "Важно, длина массива answers должна быть 4, то есть 4 элемента у него должно быть, никаких пробелов, все в одну строку.\n" +
            "Тебе нельзя повторять вопросы.\n"+
            "Вот вопросы которые ты уже говорил и тебе нельзя их повторять:" ; // ваш текст

    private final String API_URL_TEMPLATE = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=%s";
    private final String geminiKey="AIzaSyA8J0N47R0dn1eZFHEpeL9Yp2bPfW-YC0M";
    public static boolean isValidJSON(String jsonString) {
        try {
            new JSONObject(jsonString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public String convertQuestionsToJsonString(List<Question> questions) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // Преобразование списка объектов в JSON
            String jsonString = objectMapper.writeValueAsString(questions);
            return jsonString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Question fetchNextQuestion() {
        while (true) {
            String quest = convertQuestionsToJsonString(questionRepository.findAll());
            String apiUrl = String.format(API_URL_TEMPLATE, geminiKey);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            ObjectMapper objectMapper = new ObjectMapper();

            ObjectNode partsNode = objectMapper.createObjectNode();
            partsNode.put("text", prompt + quest);

            // Создаем основной JSON для запроса
            ObjectNode contentNode = objectMapper.createObjectNode();
            contentNode.set("parts", objectMapper.createArrayNode().add(partsNode));

            ObjectNode requestBodyNode = objectMapper.createObjectNode();
            requestBodyNode.set("contents", contentNode);

            String requestBody;
            try {
                requestBody = objectMapper.writeValueAsString(requestBodyNode);
            } catch (Exception e) {
                throw new RuntimeException("Failed to construct JSON request body", e);
            }

            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

            // Отправляем запрос
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);
           if(isValidJSON(response.getBody())) {
               System.out.println(response.getBody());
               if (response.getStatusCode() == HttpStatus.OK) {
                   return parseQuestionFromResponse(response.getBody());

               } else {
                   throw new RuntimeException("Failed to fetch question from API");
               }

           }

        }
    }

    private Question parseQuestionFromResponse(String responseBody) {
            try {
                System.out.println(responseBody);
                JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

                // Извлечение массива "candidates"
                JsonArray candidates = jsonObject.getAsJsonArray("candidates");
                JsonObject candidate = candidates.get(0).getAsJsonObject();  // Берём первого кандидата

                // Извлечение текста, содержащего модель данных
                JsonObject content = candidate.getAsJsonObject("content");
                JsonArray parts = content.getAsJsonArray("parts");
                JsonObject part = parts.get(0).getAsJsonObject();

                // Извлечение строки с JSON-текстом
                String modelText = part.get("text").getAsString();

                // Парсинг вложенной строки с моделью данных
                JsonObject modelData = JsonParser.parseString(modelText).getAsJsonObject();

                // Извлечение вопроса и ответов
                String question1 = modelData.get("question").getAsString();
                JsonArray answers = modelData.getAsJsonArray("answers");
                List<Answers> answers1 = new ArrayList<>();
                System.out.println("Вопрос: " + question1);
                System.out.println("Ответы:");
                for (int i = 0; i < answers.size(); i++) {
                    JsonObject answer = answers.get(i).getAsJsonObject();
                    int id = answer.get("id").getAsInt();
                    String answerText = answer.get("text").getAsString();
                    Answers answers2=new Answers();
                    answers2.setIdAnswer(id);
                    answers2.setAnswer(answerText);
                    answers1.add(answers2);
                    System.out.println(id + ": " + answerText);
                }

                // Извлечение правильного ответа
                int correctAnswerId = modelData.get("correct").getAsInt();
                System.out.println("Правильный ответ ID: " + correctAnswerId);
                // Создаем объект Question
                Question question = new Question();
                question.setQuestionText(question1);
                question.setAnswers(answers1);
                question.setCorrect(correctAnswerId);

                return question;
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse question from API response", e);
            }
        }


}



