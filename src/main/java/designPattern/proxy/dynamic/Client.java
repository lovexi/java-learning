package designPattern.proxy.dynamic;

public class Client {
    public static void main(String[] args) {
        ITeacherDao target = new TeachDao();

        ITeacherDao teacher = (ITeacherDao) new ProxyFactory(target).getProxyInstance();

        teacher.teach();
    }
}
