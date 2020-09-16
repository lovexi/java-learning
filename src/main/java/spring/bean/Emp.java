package spring.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Emp {
    private String ename;
    private String gender;
    private Dept dept;
    private String[] hobbitsArr;
    private List<String> hobbitsList;
    private Map<String, String> experience;
    private Set<String> companies;

    public void setCompanies(Set<String> companies) {
        this.companies = companies;
    }

    public void setHobbitsArr(String[] hobbitsArr) {
        this.hobbitsArr = hobbitsArr;
    }

    public void setHobbitsList(List<String> hobbitsList) {
        this.hobbitsList = hobbitsList;
    }

    public void setExperience(Map<String, String> experience) {
        this.experience = experience;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean5.xml");

        Emp emp = context.getBean("emp", Emp.class);
        System.out.println(emp);
    }

    @Override
    public String toString() {
        return "Emp{" +
                "ename='" + ename + '\'' +
                ", gender='" + gender + '\'' +
                ", dept=" + dept +
                ", hobbitsArr=" + Arrays.toString(hobbitsArr) +
                ", hobbitsList=" + hobbitsList +
                ", experience=" + experience +
                ", companies=" + companies +
                '}';
    }
}
