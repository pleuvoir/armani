package io.github.armani.server.handler;

import io.github.armani.common.protocol.packet.request.CreateGroupRequestPacket;
import io.github.armani.common.protocol.packet.response.ChatMessageResponsetPacket;
import io.github.armani.common.protocol.packet.response.CreateGroupResponsePacket;
import io.github.armani.common.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.DefaultChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateGroupRequestHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket group) throws Exception {

        String fromUserId = group.getFromUserId();
        LOGGER.info("收到[{}]的创建群聊请求：{}", fromUserId, group.toJSON());
        if (!SessionUtil.isLogin(fromUserId)) {
            ctx.channel().writeAndFlush(ChatMessageResponsetPacket.builder().message("小老弟你怎么回事没登录啊..").build());
            return;
        }

        //聚合发送
        //给每个客户端发送拉群通知  使用通道组操作 给每个通道发消息
        DefaultChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        Set<String> onlineUserNames = new HashSet<>();
        Set<String> onlineUsers = new HashSet<>();


        List<String> userIdList = group.getUserIdList();
        userIdList.add(group.getFromUserId());  //先把自己加进来

        //检查在线的用户拉进来
        userIdList.forEach(userId -> {
            if (SessionUtil.isLogin(userId)) {
                onlineUsers.add(userId);
                onlineUserNames.add(SessionUtil.getMember(userId).getUsername());
                channelGroup.add(SessionUtil.getChannel(userId));
            }
        });

        CreateGroupResponsePacket groupResponsePacket = new CreateGroupResponsePacket();
        String groupId = UUID.randomUUID().toString().replaceAll("-", "");
        groupResponsePacket.setGroupId(groupId);
        groupResponsePacket.setUserIdList(new ArrayList<>(onlineUsers));
        groupResponsePacket.setUserNameList(new ArrayList<>(onlineUserNames));

        //绑定群组
        SessionUtil.bindChannelGroup(groupId, channelGroup);

        channelGroup.writeAndFlush(groupResponsePacket);
    }

}
;