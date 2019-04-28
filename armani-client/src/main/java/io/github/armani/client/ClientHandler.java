package io.github.armani.client;

import io.github.armani.common.protocol.PacketCodec;
import io.github.armani.common.protocol.packet.Packet;
import io.github.armani.common.protocol.packet.request.LoginRequestPacket;
import io.github.armani.common.protocol.packet.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClientHandler extends ChannelInboundHandlerAdapter {

    public static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);

    /**
     * 一连接成功就发起登录
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("客户端开始登陆 ..");

        LoginRequestPacket requestPacket = LoginRequestPacket.builder()
                .userId("100000000000645")
                .username("pleuvoir")
                .password("数字电路").build();

        ByteBuf byteBuf = PacketCodec.INSTANCE.encode(ctx.alloc(), requestPacket);
        ctx.channel().writeAndFlush(byteBuf);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        final Packet packet = PacketCodec.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginResponsePacket) {

            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            LOGGER.info("客户端收到登录响应消息：{}", loginResponsePacket.toJSON());

            if (loginResponsePacket.isSuccess()) {
                LOGGER.info("客户端登录成功");
            } else {
                LOGGER.info("客户端失败，原因：{}", loginResponsePacket.getReason());
            }
        }
    }
}
