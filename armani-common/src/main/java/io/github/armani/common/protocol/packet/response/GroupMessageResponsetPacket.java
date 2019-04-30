package io.github.armani.common.protocol.packet.response;

import io.github.armani.common.protocol.packet.Packet;
import lombok.*;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class GroupMessageResponsetPacket extends Packet implements RemoteResponseCommand {

    private String groupId;
    private String message;

    @Override
    public Byte getCommand() {
        return GROUP_CHAT;
    }
}
