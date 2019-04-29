package io.github.armani.client;

import io.github.armani.common.protocol.packet.request.LoginRequestPacket;
import io.github.armani.common.protocol.packet.response.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    public static final Logger LOGGER = LoggerFactory.getLogger(LoginResponseHandler.class);


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

        ctx.channel().writeAndFlush(requestPacket);
    }

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
