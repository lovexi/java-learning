package netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
    // a group to manage different chat user, a singleton event executor to manage all channels
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // executed when the handler is added
    // add current channel into the channelGroup
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);

        // This will send msg to all added channel in the channel group
        channelGroup.writeAndFlush("[Server]" + ctx.channel().remoteAddress() + " joined in the group chat " + date.format(new Date()) + "\n");
        channelGroup.add(ctx.channel());
    }

    // executed when the handler is removed
    // add current channel into the channelGroup
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        // This will send msg to all added channel in the channel group
        channelGroup.writeAndFlush("[Server]" + ctx.channel().remoteAddress() + " left in the group chat\n");
        channelGroup.remove(ctx.channel());
    }

    // mention that one user is online
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        final String onlineMsg = ctx.channel().remoteAddress() + " is online now.";
        System.out.println(onlineMsg);
    }

    // mention that one user is offline
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        final String onlineMsg = ctx.channel().remoteAddress() + " is offline now.";
        System.out.println(onlineMsg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        final Channel channel = channelHandlerContext.channel();

        channelGroup.forEach(ch -> {
            if (channel != ch) {
                ch.writeAndFlush("[Client]" + channel.remoteAddress() + ": " + s + "\n");
            } else {
                ch.writeAndFlush("[Me]:" + s + "\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
