package io.github.armani.server;

import io.github.armani.common.protocol.packet.request.ChatMessageRequestPacket;
import io.github.armani.common.protocol.packet.response.ChatMessageResponsetPacket;
import io.github.armani.common.utils.LoginUtil;
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


        if (!LoginUtil.isLogin(ctx.channel())) {
            ctx.channel().writeAndFlush(ChatMessageResponsetPacket.builder().message("小老弟你怎么回事没登录啊..").build());
            return;
        }

        LOGGER.info("收到新消息：{}", chat.getMessage());

        ChatMessageResponsetPacket replyPacket = ChatMessageResponsetPacket.builder()
                .message("【来自大鹅的回复】：".concat(chat.getMessage())).build();

        //最后会由加码器加码
        ctx.channel().writeAndFlush(replyPacket);
    }

}
