package io.github.armani.client.handler;

import io.github.armani.common.protocol.packet.response.GroupMessageResponsetPacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ChannelHandler.Sharable
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsetPacket> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupMessageResponseHandler.class);

    public static final GroupMessageResponseHandler INSTANCE = new GroupMessageResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsetPacket msg) throws Exception {
        LOGGER.info("收到群消息：{}", msg.toJSON());
    }
}