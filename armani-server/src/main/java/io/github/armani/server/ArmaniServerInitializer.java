package io.github.armani.server;

import io.github.armani.common.codec.PacketCodecHandler;
import io.github.armani.common.handler.ArmaniIdleStateHandler;
import io.github.armani.server.handler.AuthHandler;
import io.github.armani.server.handler.ChatMessageRequestHandler;
import io.github.armani.server.handler.CreateGroupRequestHandler;
import io.github.armani.server.handler.GroupMessageRequestHandler;
import io.github.armani.server.handler.HeartRequestHandler;
import io.github.armani.server.handler.LoginRequestHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ArmaniServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
		ch.pipeline().addLast(new ArmaniIdleStateHandler());
		ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 6, 4));
		ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
		ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
		ch.pipeline().addLast(HeartRequestHandler.INSTANCE);
		ch.pipeline().addLast(AuthHandler.INSTANCE);
		ch.pipeline().addLast(ChatMessageRequestHandler.INSTANCE);
		ch.pipeline().addLast(CreateGroupRequestHandler.INSTANCE);
		ch.pipeline().addLast(GroupMessageRequestHandler.INSTANCE);
	}
}
