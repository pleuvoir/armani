package io.github.armani.server.handler;

import io.github.armani.common.utils.AttributeKeyConst;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class AuthHandler extends SimpleChannelInboundHandler<Object> {

    public static final AuthHandler INSTANCE = new AuthHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        boolean notNull = ctx.channel().attr(AttributeKeyConst.SESSION_MEMBER).get().isNotNull();

        if (notNull) {
            ctx.pipeline().remove(this);
        } else {
            ctx.channel().close();
        }
    }
}
