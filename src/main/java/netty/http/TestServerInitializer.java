package netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 加入一个http的编解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        pipeline.addLast(TestHttpServerHandler.class.getSimpleName(), new TestHttpServerHandler());
    }
}
