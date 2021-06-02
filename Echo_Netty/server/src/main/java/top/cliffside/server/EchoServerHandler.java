package top.cliffside.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.nio.charset.StandardCharsets;

/**
 * ChannelHandler，它是一个接口族的父接口，它的实现负责接收并响应事件通知。
 * 在 Netty 应用程序中，所有的数据处理逻辑都包含在这些核心抽象的实现中
 *
 * Echo 服务器会响应传入的消息，所以它需要实现 ChannelInboundHandler 接口，用
 * 来定义响应入站事件的方法。这个简单的应用程序只需要用到少量的这些方法，
 * 所以继承 ChannelInboundHandlerAdapter 类也就足够了，
 * 它提供了 ChannelInboundHandler 的默
 * @author cliffside
 * @date 2021-05-19 11:17
 */

/**
 * 标示一个ChannelHandler 可以被多个 Channel 安全地
 * 共享
 */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    private static Log log = LogFactory.getLog(EchoServerHandler.class);
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf =(ByteBuf) msg;
        log.info("server received :" + byteBuf.toString(StandardCharsets.UTF_8));

        ctx.write(byteBuf);
        //super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将未决消息冲刷到远程节点，并且关闭该 Channel
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
        //super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //打印异常栈跟踪
        cause.printStackTrace();
        //关闭该Channel
        ctx.close();
        //super.exceptionCaught(ctx, cause);
    }
}
