package io.github.armani.common.protocol.packet.response;

import io.github.armani.common.protocol.RemoteCommand;

public interface RemoteResponseCommand extends RemoteCommand {

    Byte LOGIN = -1;

    Byte ONE_2_ONE_CHAT = -2;
}
