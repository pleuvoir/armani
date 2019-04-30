package io.github.armani.common.protocol.packet.response;

import io.github.armani.common.protocol.packet.Packet;
import lombok.*;

import java.util.List;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CreateGroupResponsePacket extends Packet implements RemoteResponseCommand {

    private String groupId;
    private List<String> userIdList;
    private List<String> userNameList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP;
    }
}
