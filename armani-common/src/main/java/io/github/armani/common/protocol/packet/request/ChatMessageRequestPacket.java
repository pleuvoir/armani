package io.github.armani.common.protocol.packet.request;

import io.github.armani.common.protocol.packet.Packet;
import lombok.*;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageRequestPacket extends Packet implements RemoteRequestCommand {

    private String fromUserId;
    private String toUserId;
    private String message;

    @Override
    public Byte getCommand() {
        return ONE_2_ONE_CHAT;
    }
}
