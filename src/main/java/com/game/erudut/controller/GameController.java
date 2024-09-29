package com.game.erudut.controller;

import com.game.erudut.model.Player;
import com.game.erudut.model.Room;
import com.game.erudut.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GameController {
    @Autowired
    private RoomService roomService;

    @PostMapping("/room/create")
    public Room createRoom() {
        return roomService.createRoom();
    }

    @PostMapping("/room/join/{roomId}")
    public Room joinRoom(@PathVariable Long roomId, @RequestBody Player player) {
        return roomService.joinRoom(roomId, player);
    }
}
