package com.game.erudut.service;

import com.game.erudut.model.Player;
import com.game.erudut.model.Room;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class RoomService {
    private Map<Long, Room> rooms = new HashMap<>();

    public Room createRoom() {
        UUID uuid = UUID.randomUUID();
        Long roomId = uuid.getMostSignificantBits() & Long.MAX_VALUE;
        Room room = new Room(roomId);
        rooms.put(roomId, room);
        return room;
    }

    public Room joinRoom(Long roomId, Player player) {
        Room room = rooms.get(roomId);
        room.addPlayer(player);
        return room;
    }
}

