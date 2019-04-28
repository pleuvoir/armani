package io.github.armani.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端启动后向对端发送一个消息，并接收应答
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    public static final Logger LOGGER = LoggerFactory.getLogger(FirstClientHandler.class);

    /**
     * 当连接可用时操作一波 ，向对端发送数据
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        LOGGER.info("客户端连接可用写出数据");

        //分配一个buffer  ctx.alloc()这是一个ByteBuf 的内存管理器
        ByteBuf buffer = ctx.alloc().buffer();

        buffer.writeBytes("你好啊0阿玛尼0 服务端，来了老弟".getBytes(CharsetUtil.UTF_8));

        //冲刷到远程节点，对端，也就是armani-server
        ctx.channel().writeAndFlush(buffer);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf message = (ByteBuf) msg;
        LOGGER.info("客户端收到消息：{}", message.toString(CharsetUtil.UTF_8));
    }
}
