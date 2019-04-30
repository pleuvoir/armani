package io.github.armani.server.handler;

import io.github.armani.common.protocol.packet.request.GroupMessageRequestPacket;
import io.github.armani.common.protocol.packet.response.GroupMessageResponsetPacket;
import io.github.armani.common.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupMessageRequestHandler.class);

    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket group) throws Exception {

        LOGGER.info("收到群消息：{}", group.toJSON());

        ChannelGroup channelGroup = SessionUtil.getChannelGroup(group.getGroupId());

        if (channelGroup == null) {
            ctx.channel().writeAndFlush(GroupMessageResponsetPacket.builder().message("群组信息不存在..").build());
            return;
        }

        GroupMessageResponsetPacket responsetPacket = GroupMessageResponsetPacket.builder().message(group.getMessage()).groupId(group.getGroupId()).build();
        channelGroup.writeAndFlush(responsetPacket);
    }

}