package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class NIOServer {
    public static void main(String[] args) throws Exception {

        // create a new server socket channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // create a new selector
        Selector selector = Selector.open();

        // configure the server socket channel
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        serverSocketChannel.configureBlocking(false);

        // server socket channel cares about the request accept event
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            // continue if there are no events ready
            if (selector.select(1000) == 0) {
                System.out.println("Server is running, no connections");
                continue;
            }

            Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();

            while(selectionKeyIterator.hasNext()) {
                SelectionKey key = selectionKeyIterator.next();

                if (key.isValid() && key.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    // bind buffer for reading contents in socket channel
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                } else if (key.isValid() && key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);

                    System.out.println("From Client : " + new String(buffer.array()));
                }

                selectionKeyIterator.remove();
            }
        }
    }
}
