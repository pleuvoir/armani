package io.github.armani.common.codec;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.armani.common.protocol.PacketCodecHelper;
import io.github.armani.common.protocol.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

@ChannelHandler.Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet> {

	public static final PacketCodecHandler INSTANCE = new PacketCodecHandler();

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) {
		Packet packet = PacketCodecHelper.INSTANCE.decode(byteBuf);
		out.add(packet);
		LOG.debug("解码成功。{}", packet);
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> out) {
		ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
		PacketCodecHelper.INSTANCE.encode(byteBuf, packet);
		out.add(byteBuf);
		LOG.debug("编码成功。{}", packet);
	}
	
	private static final Logger LOG = LoggerFactory.getLogger(PacketCodecHandler.class);
}