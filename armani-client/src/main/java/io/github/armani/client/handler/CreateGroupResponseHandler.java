package io.github.armani.client.handler;

import io.github.armani.common.protocol.packet.response.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;


public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateGroupResponsePacket.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket msg) throws Exception {
        LOGGER.info("接收到群聊创建通知，{}", msg.toJSON());

        LOGGER.info("群组编号：{}，群里的小伙伴：{}", msg.getGroupId(), Arrays.asList(msg.getUserNameList()));
    }
}