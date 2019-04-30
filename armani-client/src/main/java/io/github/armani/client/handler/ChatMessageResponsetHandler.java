package io.github.armani.client.handler;

import io.github.armani.common.protocol.packet.response.ChatMessageResponsetPacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class ChatMessageResponsetHandler extends SimpleChannelInboundHandler<ChatMessageResponsetPacket> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatMessageResponsetHandler.class);

    public static final ChatMessageResponsetHandler INSTANCE = new ChatMessageResponsetHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatMessageResponsetPacket messagePacket) throws Exception {

        LOGGER.info("客户端收到新消息：{}", messagePacket.getMessage());
    }
}
