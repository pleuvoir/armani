package io.github.armani.client.handler;

import io.github.armani.common.protocol.packet.response.LoginResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    public static final Logger LOGGER = LoggerFactory.getLogger(LoginResponseHandler.class);


    public static final LoginResponseHandler INSTANCE = new LoginResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        LOGGER.info("客户端收到登录响应消息：{}", loginResponsePacket.toJSON());

        if (loginResponsePacket.isSuccess()) {
            LOGGER.info("客户端登录成功");
        } else {
            LOGGER.info("客户端失败，原因：{}", loginResponsePacket.getReason());
        }
    }

}
