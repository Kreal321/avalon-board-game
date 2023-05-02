package me.kreal.avalon.util;

import me.kreal.avalon.domain.Player;
import me.kreal.avalon.dto.PlayerDTO;
import me.kreal.avalon.dto.response.PlayerResponse;

public class PlayerMapper {

    public static PlayerDTO convertToDTO(Player player) {
        return PlayerDTO.builder()
                .playerId(player.getPlayerId())
                .displayName(player.getDisplayName())
                .characterType(player.getCharacterType())
                .seatNum(player.getSeatNum())
                .build();
    }

    public static PlayerResponse convertToResponse(Player player) {
        return PlayerResponse.builder()
                .playerId(player.getPlayerId())
                .displayName(player.getDisplayName())
                .characterType(player.getCharacterType())
                .seatNum(player.getSeatNum())
                .isAssassinated(player.getIsAssassinated())
                .build();
    }
}
