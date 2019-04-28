package io.github.armani.server;

import io.github.armani.common.protocol.PacketCodec;
import io.github.armani.common.protocol.packet.Packet;
import io.github.armani.common.protocol.packet.request.LoginRequestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        LOGGER.info("新的客户端来了");
        ByteBuf message = (ByteBuf) msg;
        final Packet packet = PacketCodec.INSTANCE.decode(message);

        if (packet instanceof LoginRequestPacket) {
            LOGGER.info("处理登录请求。入参：{}");
        }

    }
}
