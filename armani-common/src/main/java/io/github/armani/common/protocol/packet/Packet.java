package io.github.armani.common.protocol.packet;

import io.github.armani.common.utils.ToJSON;

public abstract class Packet implements ToJSON {

    public abstract Byte getCommand();
}
