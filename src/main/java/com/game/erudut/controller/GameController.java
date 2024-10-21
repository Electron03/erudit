package com.game.erudut.controller;

import com.game.erudut.DTO.GameRecord;
import com.game.erudut.DTO.PlayerRequest;
import com.game.erudut.model.Player;
import com.game.erudut.model.Question;
import com.game.erudut.model.Room;
import com.game.erudut.repository.PlayerRepository;
import com.game.erudut.repository.QuestionRepository;
import com.game.erudut.repository.RoomRepository;
import com.game.erudut.service.GameService;
import com.game.erudut.service.QuestionService;
import com.game.erudut.service.RoomService;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class GameController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private GameService gameService;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private QuestionRepository questionRepository;

//    @CrossOrigin(origins = "*")
//    @PostMapping("/addPlayer")
//    public boolean joinRoom(@RequestBody Player player) {
//        List<Room> rooms = roomRepository.findAll();
//        Optional<Long> freeRoomId = rooms.stream()
//                .filter(Room::isGameActive)                    // Проверяем, активна ли игра
//                .filter(room -> room.getPlayers().size() < 4)   // Проверяем количество игроков в комнате
//                .map(Room::getRoomId)                          // Извлекаем идентификатор комнаты
//                .findFirst();
//        if (freeRoomId.isPresent()) {
//            Room room = roomRepository.getById(freeRoomId.get());
//                player = playerRepository.findById(player.getId()).orElse(player); // If player exists, reattach it
//                room.getPlayers().add(player);
//                System.out.println(room.getRoomId());
//                roomRepository.save(room);
//                return false;
//        }
//       player=playerRepository.getById(player.getId());
//        Room newRoom = new Room();
//        newRoom.getPlayers().add(player);
//        newRoom.setGameActive(true);
//        System.out.println(newRoom.getRoomId());
//        roomRepository.save(newRoom);
//        return true;
//    }




    @MessageMapping("/answer")
    @SendTo("/topic/answers")
    public ResponseEntity<?> processAnswer(Message message) {
        return gameService.processAnswer(message);
    }

    @GetMapping("/play")
    public List<Player> Player() {
        return playerRepository.findAllByOrderByScoreDesc();
    }
    @GetMapping("/room")
    public List<Room> Room() {
        return roomRepository.findAll();
    }
    @GetMapping("/question")
    public Question GetQuestion() {
        Question question =questionService.fetchNextQuestion();
        Question question1=new Question();
        question1.setQuestionText(question.getQuestionText());
        question1.setAnswers(question.getAnswers());
        question1.setCorrect(question.getCorrect());
        questionRepository.save(question1);
        return questionRepository.findByquestionText(question.getQuestionText());
    }
    @GetMapping("/ques")
    public List<Question> Question() {
        return questionRepository.findAll();
    }
   @PostMapping("/gameRecord")
   public int GameRecord(@RequestBody GameRecord gameRecord)
   {
       Optional<Player> players=playerRepository.findById(gameRecord.getPlayerId());
       Player player=players.get();
       if(player.getScore()<= gameRecord.getScore())return gameRecord.getScore();
       else {
           player.setScore(gameRecord.getScore());
           playerRepository.save(player);
           return player.getScore();
       }
   }

    @PostMapping("/reg")
    public Player RegUser(@RequestBody PlayerRequest request){
        Player player=new Player();
        player.setName(request.getName());
        player.setScore(0);
        if(playerRepository.existsByName(player.getName())) {
            return playerRepository.findByName(player.getName());
        }
        else {
            playerRepository.save(player);
            return playerRepository.findByName(player.getName());
        }
    }
}
