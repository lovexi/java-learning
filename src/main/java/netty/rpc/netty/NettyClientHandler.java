package netty.rpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {
    private ChannelHandlerContext ctx;
    private String result;
    private String param;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        notify(); // call the waiting thread
    }

    // will be called by the caller via proxy -> waiting to be notified
    @Override
    public synchronized Object call() throws Exception {
        ctx.writeAndFlush(param);

        wait(); // wait for the result from server
        return result;
    }

    void setParam(String param) {
        this.param = param;
    }
}
