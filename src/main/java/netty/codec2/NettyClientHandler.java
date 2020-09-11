package netty.codec2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    // 当通道就绪时 该方法会触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int random = new Random().nextInt(3);
        MyDataInfo.Mymessage mymessage = null;

        if (0 == random) { // send student obj
            MyDataInfo.Student student = MyDataInfo.Student.newBuilder().setId(4).setName("Tom").build();
            mymessage = MyDataInfo.Mymessage.newBuilder().setDataType(MyDataInfo.Mymessage.DataType.StudentType)
                    .setStudent(student).build();
        } else { // send worker obj
            MyDataInfo.Worker worker = MyDataInfo.Worker.newBuilder().setAge(40).setName("Jerry").build();
            mymessage = MyDataInfo.Mymessage.newBuilder().setDataType(MyDataInfo.Mymessage.DataType.WorkerType)
                    .setWorker(worker).build();
        }

        ctx.writeAndFlush(mymessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("Response from server :" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("Ip address from server : " + ctx.channel().remoteAddress().toString());
    }

}
