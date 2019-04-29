package io.github.armani.server;

import io.github.armani.common.protocol.packet.request.LoginRequestPacket;
import io.github.armani.common.protocol.packet.response.LoginResponsePacket;
import io.github.armani.common.utils.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SimpleChannelInboundHandler 不用再去各种if(是不是本次要处理的包) else(传递给下一个处理器)判断。<br>
 * 并且会自动传递给下一个对应的处理器以及自动释放内存
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRequestHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket login) throws Exception {
        LOGGER.info("处理登录请求。入参：{}", login.toJSON());
        if (login.getUsername().equals("pleuvoir") && login.getPassword().equals("数字电路")) {
            LOGGER.info("登录成功，欢迎帅气的你");
            LoginUtil.markLogin(ctx.channel());
        } else {
            String reason = "就是不让你上";
            LOGGER.info("登录失败，原因：{}", reason);

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setReason(reason);

            //最后会由加码器加码
            ctx.channel().writeAndFlush(loginResponsePacket);
        }
    }

}