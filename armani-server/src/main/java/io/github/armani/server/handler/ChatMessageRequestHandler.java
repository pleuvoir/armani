package io.github.armani.server.handler;

import io.github.armani.common.protocol.packet.request.ChatMessageRequestPacket;
import io.github.armani.common.protocol.packet.response.ChatMessageResponsetPacket;
import io.github.armani.common.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SimpleChannelInboundHandler 不用再去各种if(是不是本次要处理的包) else(传递给下一个处理器)判断。<br>
 * 并且会自动传递给下一个对应的处理器以及自动释放内存
 */
public class ChatMessageRequestHandler extends SimpleChannelInboundHandler<ChatMessageRequestPacket> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatMessageRequestHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatMessageRequestPacket chat) throws Exception {

        if (!SessionUtil.isLogin(chat.getFromUserId())) {
            ctx.channel().writeAndFlush(ChatMessageResponsetPacket.builder().message("小老弟你怎么回事没登录啊..").build());
            return;
        }

        if (chat.getFromUserId().equals(chat.getToUserId())) {
            ctx.channel().writeAndFlush(ChatMessageResponsetPacket.builder().message("不能给自己发消息").build());
            return;
        }


        LOGGER.info("收到新消息：{}", chat.toJSON());

        ChatMessageResponsetPacket replyPacket = new ChatMessageResponsetPacket();
        String toUserId = chat.getToUserId();

        if (SessionUtil.isLogin(toUserId)) {
            Channel channel = SessionUtil.getChannel(toUserId);
            replyPacket.setMessage(chat.getMessage());
            channel.writeAndFlush(replyPacket); //发给接收方的通道

        } else {
            LOGGER.info("{}不在线，消息丢弃。", toUserId);
        }
    }

}
