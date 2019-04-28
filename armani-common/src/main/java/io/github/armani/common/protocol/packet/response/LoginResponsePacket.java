package io.github.armani.common.protocol.packet.response;

import io.github.armani.common.protocol.packet.Packet;
import lombok.*;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponsePacket extends Packet implements RemoteResponseCommand {

    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return LOGIN;
    }
}
