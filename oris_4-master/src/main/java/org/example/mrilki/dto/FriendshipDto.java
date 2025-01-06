package org.example.mrilki.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendshipDto {
    private Long userId;
    private Long friendId;
}
