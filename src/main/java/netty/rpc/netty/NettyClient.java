package netty.rpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient {
    // create thread pool
    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    // this clientHandler will be in the pipeline also run as a thread to process rpc calls
    private static NettyClientHandler clientHandler;

    // protocol definition: providerName + arguments
    public Object getBean(final Class<?> clazz, final String providerName) {
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(),
                (proxy, method, args) -> {
                    if (clientHandler == null) {
                        initClient();
                    }

                    clientHandler.setParam(providerName + method.getName() + "#" + args[0]);

                    return executorService.submit(clientHandler).get();
                });
    }

    private static void initClient() {
        clientHandler = new NettyClientHandler();

        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(clientHandler);
                    }
                });

        try {
            bootstrap.connect("127.0.0.1", 6666).sync();
        } catch (Exception e) {
            System.out.println("Exception message " + e.getMessage());
        }
    }
}
