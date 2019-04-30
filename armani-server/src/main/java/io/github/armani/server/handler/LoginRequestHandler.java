package io.github.armani.server.handler;

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

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    private AtomicLong online = new AtomicLong();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket login) throws Exception {
        LOGGER.info("处理登录请求。入参：{}", login.toJSON());

        final Channel channel = ctx.channel();
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();

        String userId = login.getUserId();
        String username = login.getUsername();

        if (SessionUtil.isLogin(userId)) {
            String reason = userId + "已经登录过了，请勿重复登录!";
            LOGGER.info("登录失败，原因：{}", reason);
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason(reason);
            channel.writeAndFlush(loginResponsePacket);
            return;
        }

        LOGGER.info("[{}]登录验证通过，当前登录用户数：{}", username, online.incrementAndGet());


        SessionMember member = SessionMember.builder().userId(userId).username(username).build();
        channel.attr(AttributeKeyConst.SESSION_MEMBER).set(member);

        SessionUtil.bindSessionMember(member, channel);

        loginResponsePacket.setReason("登录成功，欢迎帅气的[" + userId + "]");
        loginResponsePacket.setSuccess(true);
        channel.writeAndFlush(loginResponsePacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        SessionMember member = ctx.channel().attr(AttributeKeyConst.SESSION_MEMBER).get();
        if (member.isNotNull()) {
            SessionUtil.unbindSessionMember(member, ctx.channel());
            LOGGER.info("[{}]下线了，当前登录用户数：{}", member.getUsername(), online.decrementAndGet());
        } else {
            LOGGER.info("未登陆用户连接断开");
        }
    }
}
