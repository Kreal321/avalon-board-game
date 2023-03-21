package me.kreal.avalon.util;

import me.kreal.avalon.domain.Player;
import me.kreal.avalon.dto.PlayerDTO;

public class PlayerMapper {

    public static PlayerDTO convertToDTO(Player player) {
        return PlayerDTO.builder()
                .playerId(player.getPlayerId())
                .displayName(player.getDisplayName())
                .game(player.getGame())
                .characterType(player.getCharacterType())
                .seatNum(player.getSeatNum())
                .build();
    }

}
