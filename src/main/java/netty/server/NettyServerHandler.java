package netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 自定义一个handler 需要继承netty规定好的某个handlerAdapter
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    // 读取客户端发送来的数据
    /*
    1. ChannelHandlerContext ctx: 上下文对象，pipeline, channel, address
    2. Object msg: 客户端发送的数据
     */
    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {

        // 1 用户自定义任务
        ctx.channel().eventLoop().execute(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(10 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("Hello, client in channelRead method", CharsetUtil.UTF_8));
                } catch (Exception e) {

                }
            }
        });

//        System.out.println("Current thread id : " + Thread.currentThread().getName());
//        System.out.println("Server ctx = " + ctx);
//        // 将msg 转化成bytebuf
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.println("Client msg = " + buf.toString(CharsetUtil.UTF_8));
//        System.out.println("Client address: "  + ctx.channel().remoteAddress().toString());
    }

    // 数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello, client.", CharsetUtil.UTF_8));
    }

    // 处理异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
