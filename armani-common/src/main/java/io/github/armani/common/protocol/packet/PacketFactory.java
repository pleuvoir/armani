package io.github.armani.common.protocol.packet;

import io.github.armani.common.protocol.packet.request.LoginRequestPacket;
import io.github.armani.common.protocol.packet.response.LoginResponsePacket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PacketFactory {

    private static final Map<Byte, Class<? extends Packet>> packetTable = new ConcurrentHashMap();


    static {
        packetTable.put(LoginRequestPacket.LOGIN, LoginRequestPacket.class);
        packetTable.put(LoginResponsePacket.LOGIN, LoginResponsePacket.class);
    }

    public static Class<? extends Packet> get(Byte command) {
        return packetTable.get(command);
    }

}
