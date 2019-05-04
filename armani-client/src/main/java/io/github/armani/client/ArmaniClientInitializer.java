package io.github.armani.client;

import io.github.armani.client.handler.ChatMessageResponsetHandler;
import io.github.armani.client.handler.CreateGroupResponseHandler;
import io.github.armani.client.handler.GroupMessageResponseHandler;
import io.github.armani.client.handler.HeartBeatTimerHandler;
import io.github.armani.client.handler.LoginResponseHandler;
import io.github.armani.common.codec.PacketCodecHandler;
import io.github.armani.common.handler.ArmaniIdleStateHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ArmaniClientInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		//ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
		ch.pipeline().addLast(new ArmaniIdleStateHandler());
		ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 6, 4));
		ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
		ch.pipeline().addLast(new HeartBeatTimerHandler());
		ch.pipeline().addLast(LoginResponseHandler.INSTANCE);
		ch.pipeline().addLast(ChatMessageResponsetHandler.INSTANCE);
		ch.pipeline().addLast(CreateGroupResponseHandler.INSTANCE);
		ch.pipeline().addLast(GroupMessageResponseHandler.INSTANCE);
	}
}
