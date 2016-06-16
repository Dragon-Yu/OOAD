package test;

import com.mysql.jdbc.authentication.MysqlClearPasswordPlugin;
import database.MySQL;
import entity.*;
import java.sql.Date;
import java.util.Random;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by dragon on 16/6/15.
 */
public class UnitTest {
    DeviceType deviceType1 = new DeviceType("#A1", "Haier TV", "Haier TV Set, 15");
    Date date = new Date((new java.util.Date()).getTime());
    //DeviceType deviceType2 = new DeviceType("#A2", "Haier Air Machine", "Haier Air Machine, 100W");
//    @Test
//    public void testCreateWorkDatabase(){
//        MySQL.createWorkDatabase();
//    }
    @Test
    public void testSavaDeviceType(){
        System.out.println(deviceType1.exist());
        assertEquals(false, deviceType1.exist());
        deviceType1.save();
        System.out.println(deviceType1.exist());
        assertEquals(true, deviceType1.exist());
    }
    @Test
    public void testAddPlan(){
        deviceType1.addPlan(5, PlanName.SMALL, "something small");
        deviceType1.addPlan(20, PlanName.MIDDLE, "something mid");
        deviceType1.addPlan(30, PlanName.BIG, "something big");
        //

        assertEquals(true, deviceType1.exist());
    }
    @Test
    public void testAddDevice(){
        Device device1 = deviceType1.addDevice(date, "Cai Lun Road");
        Device device2 = deviceType1.addDevice(date, "Zu Chong Zhi Road");
        Device device3 = deviceType1.addDevice(date, "Zhang Heng Road");
        System.out.println(device1.exist());
        System.out.println(device2.exist());
        System.out.println(device3.exist());
        assertEquals(true, device1.exist());
        assertEquals(true, device2.exist());
        assertEquals(true, device3.exist());
    }
    @Test
    public void testGeneratePlanSheets(){
        for (Device device : deviceType1.getDevices()) {
            device.generatePlanSheets();
        }
    }
}

class Readit {
    public static void main(String args[]) {

        DeviceType deviceType1 = new DeviceType("#A1", "Haier TV", "Haier TV Set, 15");
        System.out.println(deviceType1.exist());
        deviceType1.save();
        System.out.println(deviceType1.exist());//
        deviceType1.addPlan(5, PlanName.SMALL, "something small");
        deviceType1.addPlan(20, PlanName.MIDDLE, "something mid");
        deviceType1.addPlan(30, PlanName.BIG, "something big");
        Date date = new Date((new java.util.Date()).getTime());
        Device device1 = deviceType1.addDevice(date, "Cai Lun Road");
        Device device2 = deviceType1.addDevice(date, "Zu Chong Zhi Road");
        Device device3 = deviceType1.addDevice(date, "Zhang Heng Road");
        for (Device device : deviceType1.getDevices()) {
            device.generatePlanSheets();
        }
        DeviceType deviceType2 = new DeviceType("#A2", "Haier Air Machine", "Haier Air Machine, 100W");
        deviceType2.save();
        deviceType2.addPlan(30, PlanName.SMALL, "something small");
        deviceType2.addPlan(40, PlanName.MIDDLE, "something mid");
        deviceType2.addPlan(60, PlanName.BIG, "something big");
        deviceType2.addDevice(date, "1211");
        deviceType2.addDevice(date, "1201");
        deviceType2.addDevice(date, "1608");
        for (Device device : deviceType2.getDevices()) {
            device.generatePlanSheets();
        }

        Employee employee1 = new Employee("wang", Gender.M, 25);
        employee1.save();
        Employee employee2 = new Employee("zhang", Gender.F, 25);
        employee1.save();

        for (PlanSheet planSheet : PlanSheet.getWaitPlanSheetsWithinDays(30)) {
            System.out.println(planSheet.getId());
            Random random = new Random();
            if (random.nextBoolean()) {
                planSheet.finish(planSheet.getShouldDoTime(), employee1.getId(), 1.5f);
            } else {
                planSheet.cancel();
            }
        }
        for (PlanName planName : PlanName.values()) {
            System.out.println(planName);
            System.out.println(device1.getTotalHoursByPlanName(planName));
            System.out.println(device2.getTotalHoursByPlanName(planName));
            System.out.println(device3.getTotalHoursByPlanName(planName));
        }
    }
}