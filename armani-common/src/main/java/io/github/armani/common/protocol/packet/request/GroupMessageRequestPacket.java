package io.github.armani.common.protocol.packet.request;

import io.github.armani.common.protocol.packet.Packet;
import lombok.*;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class GroupMessageRequestPacket extends Packet implements RemoteRequestCommand {

    private String groupId;
    private String message;

    @Override
    public Byte getCommand() {
        return GROUP_CHAT;
    }
}
