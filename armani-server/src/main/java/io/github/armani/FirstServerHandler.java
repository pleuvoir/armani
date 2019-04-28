package io.github.armani;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirstServerHandler.class);
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf message = (ByteBuf) msg;
        LOGGER.info("服务端收到消息：{}", message.toString(CharsetUtil.UTF_8));

        //分配一个buffer  ctx.alloc()这是一个ByteBuf 的内存管理器
        ByteBuf buffer = ctx.alloc().buffer();

        String response = "欢迎使用鸭子陪聊录，当前系统版本：" + ctx.channel().attr(AttributeKey.valueOf("server-version"));

        buffer.writeBytes(response.getBytes(CharsetUtil.UTF_8));

        ctx.channel().writeAndFlush(buffer);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("新的客户端来了 ^Hello Armani Client^");
    }
}
