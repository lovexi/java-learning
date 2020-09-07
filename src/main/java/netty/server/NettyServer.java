package netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        // create Boss Group
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // create Worker Group
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // configure server side
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup) // bind parentGroup and childGroup
                    .channel(NioServerSocketChannel.class) // Use NioServerSocketChannel to use as channel connections
                    .option(ChannelOption.SO_BACKLOG, 128) // Set connection number for thread queue
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // Set alive connection status for child group
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new NettyServerHandler());
                        }
                    }); // Set pipeline handler for eventLoops in worker group

            System.out.println("Server is ready to serve requests.");
            // bind port and set as sync mode
            ChannelFuture cf = bootstrap.bind(6666).sync();

            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("监听端口 6666 成功");
                    } else {
                        System.out.println("监听端口 6666 失败");
                    }
                }
            });

            // close channel (异步）
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
