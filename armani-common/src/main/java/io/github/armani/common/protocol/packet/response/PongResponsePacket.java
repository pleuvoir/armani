package io.github.armani.common.protocol.packet.response;

import io.github.armani.common.protocol.packet.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PongResponsePacket extends Packet implements RemoteResponseCommand {

	public static final PongResponsePacket INSTANCE = new PongResponsePacket();

	private String message = "pong";

	@Override
	public Byte getCommand() {
		return PONG;
	}

}
