package io.github.armani.server;

import io.github.armani.common.protocol.packet.request.LoginRequestPacket;
import io.github.armani.common.protocol.packet.response.LoginResponsePacket;
import io.github.armani.common.utils.AttributeKeyConst;
import io.github.armani.common.utils.SessionMember;
import io.github.armani.common.utils.SessionUtil;
import io.netty.channel.Channel;
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

        final Channel channel = ctx.channel();
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();

        String userId = login.getUserId();
        String username = login.getUsername();
        if (userId.startsWith("1")) {

            LOGGER.info("登录成功，欢迎帅气的[{}]，当前登录用户数：{}", username, online.incrementAndGet());

            channel.attr(AttributeKeyConst.USER_ID).set(userId);

            SessionUtil.bindSessionMember(SessionMember.builder().userId(userId).username(username).build(), channel);

            loginResponsePacket.setReason("登录成功，欢迎帅气的你");
            loginResponsePacket.setSuccess(true);
            channel.writeAndFlush(loginResponsePacket);

        } else {
            String reason = "就是不让你上";
            LOGGER.info("登录失败，原因：{}", reason);
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason(reason);
            //最后会由加码器加码
            channel.writeAndFlush(loginResponsePacket);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionMember member = SessionUtil.getMember(ctx.channel().attr(AttributeKeyConst.USER_ID).get());
        LOGGER.info("[{}]下线了，当前登录用户数：{}", member.getUsername(), online.decrementAndGet());
    }
}
