package io.github.armani.common.codec;

import io.github.armani.common.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


/**
 * Netty为我们提供的特殊入站handler，方便将二进制转换为对象以便在后续的处理器中直接操作对象<br>
 *    并且会自动释放内存
 *
 */
public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(PacketCodec.INSTANCE.decode(in));
    }

}
