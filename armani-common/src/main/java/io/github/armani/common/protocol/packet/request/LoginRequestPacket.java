package io.github.armani.common.protocol.packet.request;

import io.github.armani.common.protocol.packet.Packet;
import lombok.*;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestPacket extends Packet implements RemoteRequestCommand {

    private String username;
    private String userId;
    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN;
    }
}
