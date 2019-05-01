package io.github.armani.common.protocol.packet.request;

import io.github.armani.common.protocol.RemoteCommand;

public interface RemoteRequestCommand extends RemoteCommand {

	Byte PING = 5;
	
    Byte LOGIN = 1;

    Byte ONE_2_ONE_CHAT = 2;

    Byte CREATE_GROUP = 3;

    Byte GROUP_CHAT = 4;
}
