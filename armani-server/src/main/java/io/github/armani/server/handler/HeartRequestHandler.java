package io.github.armani.server.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.armani.common.protocol.packet.request.PingRequestPacket;
import io.github.armani.common.protocol.packet.response.PongResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 响应客户端发来的心跳报文
 *
 */
@ChannelHandler.Sharable
public class HeartRequestHandler extends SimpleChannelInboundHandler<PingRequestPacket> {

	public static final HeartRequestHandler INSTANCE = new HeartRequestHandler();

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, PingRequestPacket msg) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("接收到客户端心跳报文：{}", msg.toJSON());
		}
		ctx.channel().writeAndFlush(PongResponsePacket.INSTANCE);

		if (LOG.isDebugEnabled()) {
			LOG.debug("已响应客户端心跳报文：{}", PongResponsePacket.INSTANCE.toJSON());
		}
	}

	private static final Logger LOG = LoggerFactory.getLogger(HeartRequestHandler.class);
}
