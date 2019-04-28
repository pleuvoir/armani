package io.github.armani.common.protocol;

import io.github.armani.common.protocol.packet.Packet;
import io.github.armani.common.protocol.packet.PacketFactory;
import io.github.armani.common.protocol.serialize.SerializeFactory;
import io.github.armani.common.protocol.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;


/**
 * Packet编解码器
 */
public class PacketCodec {

    public static final PacketCodec INSTANCE = new PacketCodec();

    /**
     * 魔法数字
     */
    private static final int MAGIC_NUMBER = 9527;


    /**
     * 序列化
     */
    public ByteBuf encode(ByteBufAllocator allocator, Packet packet) {
        //序列化JAVA对象，这是实际发送的数据内容
        final byte[] bytes = SerializeFactory.DEFAULT.serialize(packet);
        //一个分配器
        final ByteBuf buffer = allocator.buffer();

        // 魔数
        buffer.writeInt(MAGIC_NUMBER);
        //指令
        buffer.writeByte(packet.getCommand());
        //序列化算法
        buffer.writeByte(SerializeFactory.DEFAULT.getSerializerAlgorithm());
        //长度位
        buffer.writeInt(bytes.length);
        // 内容
        buffer.writeBytes(bytes);
        return buffer;
    }

    public Packet decode(ByteBuf byteBuf) {

        //跳过前4个字节（一个Int）的魔术，实际上应该校验一下
        byteBuf.skipBytes(4);

        //指令
        byte command = byteBuf.readByte();
        //序列化算法
        byte algorithmCode = byteBuf.readByte();
        //长度位
        int length = byteBuf.readInt();
        //读取数据
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        //获取指令对应的实体类
        final Class<? extends Packet> packetClazz = PacketFactory.get(command);

        //获取序列化器进行反序列化
        final Serializer serializer = SerializeFactory.get(algorithmCode);

        Packet packet = serializer.deserialize(packetClazz, bytes);

        return packet;
    }

}
