package io.github.armani.common.protocol.packet;

import io.github.armani.common.protocol.packet.request.ChatMessageRequestPacket;
import io.github.armani.common.protocol.packet.request.LoginRequestPacket;
import io.github.armani.common.protocol.packet.response.ChatMessageResponsetPacket;
import io.github.armani.common.protocol.packet.response.LoginResponsePacket;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PacketFactory {

    private static Map<Byte, Class<? extends Packet>> packetTable;

    static {

        Map<Byte, Class<? extends Packet>> byteClassMap = new ConcurrentHashMap();

        byteClassMap.put(LoginRequestPacket.LOGIN, LoginRequestPacket.class);
        byteClassMap.put(LoginResponsePacket.LOGIN, LoginResponsePacket.class);

        byteClassMap.put(ChatMessageRequestPacket.ONE_2_ONE_CHAT, ChatMessageRequestPacket.class);
        byteClassMap.put(ChatMessageResponsetPacket.ONE_2_ONE_CHAT, ChatMessageResponsetPacket.class);

        packetTable = Collections.unmodifiableMap(byteClassMap);
    }

    public static Class<? extends Packet> get(Byte command) {
        return packetTable.get(command);
    }

}
