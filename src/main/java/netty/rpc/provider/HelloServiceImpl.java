package netty.rpc.provider;

public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String msg) {
        System.out.println("Received the message from client : " + msg);
        if (msg != null) {
            return "Hello Client. I've received the message : " + msg;
        } else {
            return "Hello Client. I've received the message";
        }
    }
}
