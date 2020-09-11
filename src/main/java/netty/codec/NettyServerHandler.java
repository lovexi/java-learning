package netty.codec;

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

        StudentPOJO.Student student = (StudentPOJO.Student) msg;

        System.out.println("Student POJO received : " + student);
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
