package netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/*
1. SimpleChannelInboundHandler 继承了 ChannelInboundHandlerAdapter
2. HttpObject 客户端和服务器互相通讯的数据 封装成 HttpObject
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject httpObject) throws Exception {
        if (httpObject instanceof HttpRequest) {
            System.out.println("httpObject 类型: " + httpObject.getClass().getSimpleName());
            System.out.println("客户端地址 : " + ctx.channel().remoteAddress());

            // filter request
            HttpRequest request = (HttpRequest) httpObject;
            URI uri = new URI(request.uri());
            if (uri.getPath().equals("/favicon.ico")) {
                System.out.println("Filter the path for /favicon.ico");
                return;
            }

            System.out.println("Pipeline hashcode : " + ctx.pipeline().hashCode());
            System.out.println("Handler hashcode : " + this.hashCode());
            // 回复信息 to browser
            ByteBuf buf = Unpooled.copiedBuffer("hello client" , CharsetUtil.UTF_8);
            DefaultFullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
            resp.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            resp.headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());

            // not "ctx.channel.writeAndFlush(buf)", since the client is expecting a http response
           ctx.channel().writeAndFlush(resp);
        }
    }

    // 处理异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("New exception caught : " + cause.getMessage());
        ctx.channel().close();
    }
}
