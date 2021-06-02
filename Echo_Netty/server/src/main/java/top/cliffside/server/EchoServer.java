package top.cliffside.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author cliffside
 * @date 2021-05-19 12:57
 */
public class EchoServer {

    private static Log log = LogFactory.getLog(EchoServerHandler.class);
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
//        if (args.length!=1){
//            log.info("Usage : " + EchoServer.class.getSimpleName() + "<port>");
//            return;
//        };

        System.out.println("start ing ");
        log.info("server starting.....");
        //设置端口值（如果端口参数的格式不正确，则抛出一个NumberFormatException）
        //int port = Integer.parseInt(args[0]);

        //调用服务器的 start()方法
        new EchoServer(20000).start();
    }

    public void start() throws InterruptedException {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        // (1) 创建EventLoopGroup
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            //(2) 创建ServerBootstrap
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group)
                    //(3) 指定所使用的 NIO 传输 Channel
                    .channel(NioServerSocketChannel.class)
                    //(4) 使用指定的端口设置套接字地址
                    .localAddress(new InetSocketAddress(port))
                    //(5) 添加一个EchoServerHandler到于Channel的 ChannelPipeline
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //EchoServerHandler 被标注为@Shareable，
                            // 所以我们可以总是使用同样的实例
                            //这里对于所有的客户端连接来说，
                            // 都会使用同一个 EchoServerHandler，因为其被标注为@Sharable，
                            ch.pipeline().addLast(serverHandler);
                        }
                    });
            //(6) 异步地绑定服务器；调用 sync()方法阻塞等待直到绑定完成
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            log.info(EchoServer.class.getName() +
                    " started and listening for connections on " + channelFuture.channel().localAddress());
            //(7) 获取 Channel 的CloseFuture，并且阻塞当前线程直到它完成
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //(8) 关闭 EventLoopGroup，释放所有的资源
            group.shutdownGracefully().sync();
        }
    }


}
