package io.github.armani.common.protocol.packet;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PacketFactory {

    public static final Logger LOG = LoggerFactory.getLogger(PacketFactory.class);

    private static Map<Byte, Class<? extends Packet>> packetTable = new ConcurrentHashMap<>();

    static {

        Reflections packageInfo = new Reflections("io.github.armani.common.protocol.packet");

        packageInfo.getSubTypesOf(Packet.class).forEach(packet -> {
            try {
                final Byte command = packet.newInstance().getCommand();
                LOG.info("加载协议[{}]:{}", command, packet.getCanonicalName());
                packetTable.put(command, packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public static Class<? extends Packet> get(Byte command) {
        return packetTable.get(command);
    }

}
