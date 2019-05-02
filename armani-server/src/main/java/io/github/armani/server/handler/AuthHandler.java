package io.github.armani.server.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.armani.common.utils.AttributeKeyConst;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 这个类因为没有具体的对象，所以一定会进来，处理完成一定要传递给下一个处理器
 */
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {

	public static final AuthHandler INSTANCE = new AuthHandler();

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		boolean notNull = ctx.channel().attr(AttributeKeyConst.SESSION_MEMBER).get().isNotNull();
		if (notNull) {
			LOG.info("用户已登录，权限认证通过，此连接后续不再效验。");
			ctx.pipeline().remove(this);
			ctx.fireChannelRead(msg);
		} else {
			ctx.channel().close();
		}
	}

	private static final Logger LOG = LoggerFactory.getLogger(AuthHandler.class);
}
