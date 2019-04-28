package io.github.armani.server;

import io.github.armani.common.protocol.PacketCodec;
import io.github.armani.common.protocol.packet.Packet;
import io.github.armani.common.protocol.packet.request.LoginRequestPacket;
import io.github.armani.common.protocol.packet.response.LoginResponsePacket;
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

            LoginRequestPacket login = (LoginRequestPacket) packet;

            LOGGER.info("处理登录请求。入参：{}", login.toJSON());

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            if (login.getUsername().equals("pleuvoir") && login.getPassword().equals("数字电路")) {
                LOGGER.info("登录成功，欢迎帅气的你");
                loginResponsePacket.setSuccess(true);
            } else {
                String reason = "就是不让你上";
                LOGGER.info("登录失败，原因：{}", reason);
                loginResponsePacket.setReason(reason);
            }
            ByteBuf byteBuf = PacketCodec.INSTANCE.encode(ctx.alloc(), loginResponsePacket);

            ctx.channel().writeAndFlush(byteBuf);

        }
    }
}
