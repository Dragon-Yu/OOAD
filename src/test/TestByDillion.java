//package test;
//
//import entity.*;
//
//import java.sql.Date;
//import java.util.Random;
//
//import org.junit.Test;
//import static org.junit.Assert.assertEquals;
//
///**
// * Created by Dillion on 6/9/16.
// */
//
//
//class TestDemo {
//    @Test
//    public void testDeviceType() throws Exception {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            System.exit(-1);
//        }
//        DeviceType planType = new DeviceType("#123A", "air mechine", "lalla");
//        planType.save();
//        assertEquals(true,planType.exist());
//    }
//    @Test
//    public void demo(){
//        int a = 1;
//        assertEquals(a,2);
//    }
//}
//
//
//public class TestByDillion {
//    public static void main(String args[]) {
//        DeviceType deviceType1 = new DeviceType("#A1", "Haier TV", "Haier TV Set, 15");
//        System.out.println(deviceType1.exist());
//        deviceType1.save();
//        System.out.println(deviceType1.exist());
//        deviceType1.addPlan(5, PlanName.SMALL, "something small");
//        deviceType1.addPlan(20, PlanName.MIDDLE, "something mid");
//        deviceType1.addPlan(30, PlanName.BIG, "something big");
//        Date date = new Date((new java.util.Date()).getTime());
//        Device device1 = deviceType1.addDevice(date, "Cai Lun Road");
//        Device device2 = deviceType1.addDevice(date, "Zu Chong Zhi Road");
//        Device device3 = deviceType1.addDevice(date, "Zhang Heng Road");
//        for (Device device : deviceType1.getDevices()) {
//            device.generatePlanSheets();
//        }
//        DeviceType deviceType2 = new DeviceType("#A2", "Haier Air Machine", "Haier Air Machine, 100W");
//        deviceType2.save();
//        deviceType2.addPlan(30, PlanName.SMALL, "something small");
//        deviceType2.addPlan(40, PlanName.MIDDLE, "something mid");
//        deviceType2.addPlan(60, PlanName.BIG, "something big");
//        deviceType2.addDevice(date, "1211");
//        deviceType2.addDevice(date, "1201");
//        deviceType2.addDevice(date, "1608");
//        for (Device device : deviceType2.getDevices()) {
//            device.generatePlanSheets();
//        }
//
//        Employee employee1 = new Employee("wang", Gender.M, 25);
//        employee1.save();
//        Employee employee2 = new Employee("zhang", Gender.F, 25);
//        employee1.save();
//
//        for (PlanSheet planSheet : PlanSheet.getWaitPlanSheetsWithinDays(30)) {
//            System.out.println(planSheet.getId());
//            Random random = new Random();
//            if (random.nextBoolean()) {
//                planSheet.finish(planSheet.getShouldDoTime(), employee1.getId(), 1.5f);
//            } else {
//                planSheet.cancel();
//            }
//        }
//        for (PlanName planName : PlanName.values()) {
//            System.out.println(planName);
//            System.out.println(device1.getTotalHoursByPlanName(planName));
//            System.out.println(device2.getTotalHoursByPlanName(planName));
//            System.out.println(device3.getTotalHoursByPlanName(planName));
//      }
//    }
//}
