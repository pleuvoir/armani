package io.github.armani.server;

import io.github.armani.common.protocol.PacketCodec;
import io.github.armani.common.protocol.packet.Packet;
import io.github.armani.common.protocol.packet.request.ChatMessageRequestPacket;
import io.github.armani.common.protocol.packet.request.LoginRequestPacket;
import io.github.armani.common.protocol.packet.response.ChatMessageResponsetPacket;
import io.github.armani.common.protocol.packet.response.LoginResponsePacket;
import io.github.armani.common.utils.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerHandler.class);

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

                LoginUtil.markLogin(ctx.channel());

            } else {
                String reason = "就是不让你上";
                LOGGER.info("登录失败，原因：{}", reason);
                loginResponsePacket.setReason(reason);
            }

            this.reply(ctx, loginResponsePacket);

        } else if (packet instanceof ChatMessageRequestPacket) {

            if (!LoginUtil.isLogin(ctx.channel())) {
                this.reply(ctx,  ChatMessageResponsetPacket.builder().message("小老弟你怎么回事没登录啊..").build());
                return;
            }

            ChatMessageRequestPacket messagePacket = (ChatMessageRequestPacket) packet;

            LOGGER.info("收到新消息：{}", messagePacket.getMessage());

            ChatMessageResponsetPacket replyPacket = ChatMessageResponsetPacket.builder()
                    .message("【来自大鹅的回复】：".concat(messagePacket.getMessage())).build();

            this.reply(ctx, replyPacket);
        }
    }


    private void reply(ChannelHandlerContext ctx, Packet packet) {
        ByteBuf byteBuf = PacketCodec.INSTANCE.encode(ctx.alloc(), packet);

        ctx.channel().writeAndFlush(byteBuf);
    }
}
