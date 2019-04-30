package io.github.armani.common.protocol.packet;

import io.github.armani.common.protocol.packet.request.ChatMessageRequestPacket;
import io.github.armani.common.protocol.packet.request.CreateGroupRequestPacket;
import io.github.armani.common.protocol.packet.request.GroupMessageRequestPacket;
import io.github.armani.common.protocol.packet.request.LoginRequestPacket;
import io.github.armani.common.protocol.packet.response.ChatMessageResponsetPacket;
import io.github.armani.common.protocol.packet.response.CreateGroupResponsePacket;
import io.github.armani.common.protocol.packet.response.GroupMessageResponsetPacket;
import io.github.armani.common.protocol.packet.response.LoginResponsePacket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PacketFactory {

    private static Map<Byte, Class<? extends Packet>> packetTable;

    static {
        packetTable = new ConcurrentHashMap<>();

        init(LoginRequestPacket.class, LoginResponsePacket.class);
        init(ChatMessageRequestPacket.class, ChatMessageResponsetPacket.class);
        init(CreateGroupRequestPacket.class, CreateGroupResponsePacket.class);
        init(GroupMessageRequestPacket.class, GroupMessageResponsetPacket.class);
    }


    private static void init(Class<? extends Packet> requestClazz, Class<? extends Packet> responseClazz) {
        try {
            packetTable.put(requestClazz.newInstance().getCommand(), requestClazz);
            packetTable.put(responseClazz.newInstance().getCommand(), responseClazz);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public static Class<? extends Packet> get(Byte command) {
        return packetTable.get(command);
    }

}
