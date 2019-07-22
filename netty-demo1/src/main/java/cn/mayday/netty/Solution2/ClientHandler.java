package cn.mayday.netty.Solution2;


import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class ClientHandler extends ChannelHandlerAdapter {


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        System.out.println("handlerAdded");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        System.out.println("handlerRemoved");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // ByteBuf byteBuf = (ByteBuf) msg;
        String byteBuf = (String) msg;
        try {
            // System.out.println(byteBuf.toString(CharsetUtil.US_ASCII));
            System.out.println(byteBuf);
        } finally {
            // byteBuf.release();
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
