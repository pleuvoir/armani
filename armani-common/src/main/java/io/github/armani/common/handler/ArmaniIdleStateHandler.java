package io.github.armani.common.handler;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 空闲检测
 *
 */
public class ArmaniIdleStateHandler extends IdleStateHandler {

	private static final int READER_IDLE_TIME = 15;

	public ArmaniIdleStateHandler() {
		super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
	}

	@Override
	protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
		LOGGER.info("{}秒内未读到数据，关闭连接", READER_IDLE_TIME);
		ctx.channel().close();
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(ArmaniIdleStateHandler.class);
}
