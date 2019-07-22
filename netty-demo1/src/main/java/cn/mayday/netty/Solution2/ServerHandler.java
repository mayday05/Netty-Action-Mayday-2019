package cn.mayday.netty.Solution2;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/*
 * 处理一个服务器的通道
 */
public class ServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // ByteBuf byteBuf = (ByteBuf) msg;
        String byteBuf = (String) msg;

        // System.out.println(byteBuf.toString(CharsetUtil.US_ASCII));
        System.out.println(byteBuf);

        //ctx.writeAndFlush(Unpooled.copiedBuffer(new String("Hello Client").getBytes()));

        //ctx.writeAndFlush(Unpooled.copiedBuffer(new String("Hello Client").getBytes()));

        //ctx.writeAndFlush(Unpooled.copiedBuffer(new String("Hello Client").getBytes()));

        ctx.writeAndFlush(Unpooled.copiedBuffer(new String("Hello Client$Hello Client2$").getBytes()));


    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //打印异常信息
        cause.printStackTrace();
        ctx.close();
    }
}
