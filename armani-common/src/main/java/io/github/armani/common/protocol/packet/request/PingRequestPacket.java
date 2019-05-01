package io.github.armani.common.protocol.packet.request;

import io.github.armani.common.protocol.packet.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PingRequestPacket extends Packet implements RemoteRequestCommand {

	public static final PingRequestPacket INSTANCE = new PingRequestPacket();

	private String message = "ping";

	@Override
	public Byte getCommand() {
		return PING;
	}


}
