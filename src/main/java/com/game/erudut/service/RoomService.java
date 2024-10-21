package com.game.erudut.service;

import com.game.erudut.model.Player;
import com.game.erudut.model.Room;
import com.game.erudut.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
//    public void createRoom(Player player) {
//        Room room=new Room();
//        System.out.println(player.getName());
//        List<Player> players = new ArrayList<>();
//        players.add(player);
//        room.setPlayers(players);
//        room.setQuestions(null);
//        room.setGameActive(true);
//        room.setCurrentQuestionIndex(0);
//        roomRepository.save(room);
//    }
//
//    public Room joinRoom(Long roomId, Player player) {
//       Room room = roomRepository.getById(roomId);
//        List<Player> players = new ArrayList<>();
//        players.add(player);
//        room.setPlayers(players);
//       roomRepository.save(room);
//        return room;
//    }
}

