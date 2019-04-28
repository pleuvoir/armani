package io.github.armani.common.protocol.packet;

import io.github.armani.common.protocol.packet.request.LoginRequestPacket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PacketFactory {

    private static final Map<Byte, Class<LoginRequestPacket>> packetTable = new ConcurrentHashMap();


    static {
        packetTable.put(LoginRequestPacket.LOGIN, LoginRequestPacket.class);
    }

    public static Class<LoginRequestPacket> get(Byte command) {
        return packetTable.get(command);
    }

}
