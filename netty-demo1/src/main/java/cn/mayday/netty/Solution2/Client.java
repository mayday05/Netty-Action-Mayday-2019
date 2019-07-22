package cn.mayday.netty.Solution2;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * BootStrap和ServerBootstrap类似,不过他是对非服务端的channel而言，比如客户端或者无连接传输模式的channel。
 * 如果你只指定了一个EventLoopGroup，那他就会即作为一个‘boss’线程，也会作为一个‘workder’线程，尽管客户端不需要使用到‘boss’线程。
 * 代替NioServerSocketChannel的是NioSocketChannel,这个类在客户端channel被创建时使用。
 * 不像在使用ServerBootstrap时需要用childOption()方法，因为客户端的SocketChannel没有父channel的概念。
 * 我们用connect()方法代替了bind()方法。
 */
public class Client {

    public static void main(String[] args) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {


                    // 设置以"$"作为分隔符
                    ByteBuf buf = Unpooled.copiedBuffer("$".getBytes());
                    ch.pipeline().
                            // addLast(new DelimiterBasedFrameDecoder(1024, buf)).
                            addLast(new FixedLengthFrameDecoder(10)).
                            addLast(new StringDecoder()).
                            addLast(new ClientHandler());
                }
            });

            ChannelFuture f = b.connect("127.0.0.1", 9999).sync(); // (5)

//            f.channel().writeAndFlush(Unpooled.copiedBuffer("Hello Server".getBytes()));
//
//            f.channel().writeAndFlush(Unpooled.copiedBuffer("Hello Server".getBytes()));
//
//            f.channel().writeAndFlush(Unpooled.copiedBuffer("Hello Server".getBytes()));

            f.channel().writeAndFlush(Unpooled.copiedBuffer("Hello $".getBytes()));
            f.channel().writeAndFlush(Unpooled.copiedBuffer("Hello Server$".getBytes()));
            f.channel().writeAndFlush(Unpooled.copiedBuffer("Hello Server !!!!!$".getBytes()));


            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
