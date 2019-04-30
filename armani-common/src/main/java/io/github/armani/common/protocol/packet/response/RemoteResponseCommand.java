package io.github.armani.common.protocol.packet.response;

import io.github.armani.common.protocol.RemoteCommand;

public interface RemoteResponseCommand extends RemoteCommand {

    Byte LOGIN = -1;

    Byte ONE_2_ONE_CHAT = -2;

    Byte CREATE_GROUP = -3;

    Byte GROUP_CHAT = -4;
}
