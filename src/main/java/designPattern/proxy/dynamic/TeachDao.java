package designPattern.proxy.dynamic;

public class TeachDao implements ITeacherDao {

    @Override
    public void teach() {
        System.out.println("Teacher is teaching now.");
    }
}
