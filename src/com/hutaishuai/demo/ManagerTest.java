package com.hutaishuai.demo;
import java.time.*;

public class ManagerTest {
    public static void main(String[] args){
        Manager boss = new Manager("Huzong", 8000, 1998, 8, 9);
        boss.setBonus(9000);

        Employee[] staff = new Employee[3];

        staff[0] = boss;
        staff[1] = new Employee("xinzei", 9000, 1999,9,12);
        staff[2] = new Employee("hasaki",900,1999,10,12);

        for (Employee e:staff) {
            System.out.println("name=" + e.getName() + ", Salary" + e.getSalary());
        }
    }
}
