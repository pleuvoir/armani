package io.github.armani.common.protocol.packet.response;

import io.github.armani.common.protocol.packet.Packet;
import lombok.*;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResponsetPacket extends Packet implements RemoteResponseCommand {

    private String message;

    @Override
    public Byte getCommand() {
        return ONE_2_ONE_CHAT;
    }
}
