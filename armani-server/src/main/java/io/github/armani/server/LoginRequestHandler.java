package io.github.armani.server;

import io.github.armani.common.protocol.packet.request.LoginRequestPacket;
import io.github.armani.common.protocol.packet.response.LoginResponsePacket;
import io.github.armani.common.utils.LoginUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

/**
 * SimpleChannelInboundHandler 不用再去各种if(是不是本次要处理的包) else(传递给下一个处理器)判断。<br>
 * 并且会自动传递给下一个对应的处理器以及自动释放内存
 */
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRequestHandler.class);

    private AtomicLong online = new AtomicLong();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket login) throws Exception {
        LOGGER.info("处理登录请求。入参：{}", login.toJSON());

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();

        if (login.getUsername().equals("pleuvoir") && login.getPassword().equals("数字电路")) {
            LOGGER.info("登录成功，欢迎帅气的你，当前登录用户数：{}", online.incrementAndGet());
            LoginUtil.markLogin(ctx.channel());

            loginResponsePacket.setReason("登录成功，欢迎帅气的你");
            loginResponsePacket.setSuccess(true);
            ctx.channel().writeAndFlush(loginResponsePacket);

        } else {
            String reason = "就是不让你上";
            LOGGER.info("登录失败，原因：{}", reason);
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason(reason);
            //最后会由加码器加码
            ctx.channel().writeAndFlush(loginResponsePacket);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("用户下线了，当前登录用户数：{}", online.decrementAndGet());
    }
}
