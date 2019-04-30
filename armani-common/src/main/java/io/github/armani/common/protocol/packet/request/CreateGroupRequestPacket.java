package io.github.armani.common.protocol.packet.request;

import io.github.armani.common.protocol.packet.Packet;
import lombok.*;

import java.util.List;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CreateGroupRequestPacket extends Packet implements RemoteRequestCommand {

    private List<String> userIdList;

    private String fromUserId;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP;
    }


}
