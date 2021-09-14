package top.cliffside.Pool;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

/**
 * @author cliffside
 * @date 2021-09-14 21:26
 */
public class NettyTest {
    public static void main(String[] args) {
        PooledByteBufAllocator pooledByteBufAllocator = new PooledByteBufAllocator();

        ByteBuf buffer = pooledByteBufAllocator.buffer(112);
    }
}
