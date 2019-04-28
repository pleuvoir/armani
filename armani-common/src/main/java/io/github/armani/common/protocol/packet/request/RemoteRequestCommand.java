package io.github.armani.common.protocol.packet.request;

import io.github.armani.common.protocol.RemoteCommand;

public interface RemoteRequestCommand extends RemoteCommand {

    Byte LOGIN = 1;

    Byte ONE_2_ONE_CHAT = 2;
}
