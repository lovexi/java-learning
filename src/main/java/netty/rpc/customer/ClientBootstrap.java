package netty.rpc.customer;

import netty.rpc.netty.NettyClient;
import netty.rpc.provider.HelloService;
import netty.rpc.provider.HelloServiceImpl;

public class ClientBootstrap {

    public static final String providerName = "HelloService#";

    public static void main(String[] args) {
        NettyClient customer = new NettyClient();

        // here is the proxy customer we received, but the object HelloService itself
        HelloService service = (HelloService) customer.getBean(HelloServiceImpl.class, providerName);

        String res = service.hello("Hello rpc");
        System.out.println("Calling output : " + res);

    }
}
