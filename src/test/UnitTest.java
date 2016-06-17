package test;

import database.MySQL;
import entity.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


/**
 * Created by Dragon on 6/16/16.
 */

/**
 *  这个设备维护管理系统的实质性操作是数据库操作,在数据库对应表中建立起设备类型记录,设备记录,维修计划记录等等。
 *  因此, 对于形如新建一个设备类型的操作,直接验证操作前后的数据库内容是否符合预期
 *  对于获取某设备信息的操作,验证能否返回符合预期的类实例
 *  对于统计某种设备类型在大型维修商花费了多少工时这样的操作,验证方法返回的工时数是否符合预期
 */


public class UnitTest {
    DeviceType deviceType1 = new DeviceType("#A1", "Haier TV", "Haier TV Set, 15");
    Date date = new Date((new java.util.Date()).getTime());

    public static int getRecordCount(String sql){
        try {
            ResultSet result = MySQL.getStatementInstance().executeQuery(sql);
            result.next();
            return result.getInt(1);
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return -1;
    }

    @Test
    public void testAddEmployee() {
        String sql1 = "select count(*) from employee where name='wang' and gender='M' and age=25;";
        String sql2 = "select count(*) from employee where name='zhang' and gender='F' and age=25;";
        Employee employee1 = new Employee("wang", Gender.M, 25);
        employee1.addEmployee();
        Employee employee2 = new Employee("zhang", Gender.F, 25);
        employee2.addEmployee();

        int countOfEmplpyee1=getRecordCount(sql1);
        assertEquals(true,countOfEmplpyee1>0);
        int countOfEmplpyee2=getRecordCount(sql2);
        assertEquals(true,countOfEmplpyee2>0);

    }

    @Test
    public void testAddDeviceType(){
        String sql1 = "select count(*) from device_type where code='#A1' and name='Haier TV' and description='Haier TV Set, 15';";
        // 顺带验证exist()函数的正确性
        assertEquals(false, deviceType1.exist());
        deviceType1.addDeviceType();
        assertEquals(true, deviceType1.exist());
        int countOfDeviceType1 = getRecordCount(sql1);
        assertEquals(true,countOfDeviceType1>0);
    }

    @Test
    public void testAddDevice(){
        String sql = "select * from device_type where ID = 1";//某条已有的设备类型
        DeviceType deviceTpye = DeviceType.getDeviceTypesFromQuery(sql).get(0);
        Device device = deviceTpye.addDevice(date, "Test Road");
        assertEquals(true, device.exist());
    }

    @Test
    public void testAddPlan(){
        deviceType1.addPlan(5, PlanType.SMALL, "something small");
        deviceType1.addPlan(20, PlanType.MIDDLE, "something mid");
        deviceType1.addPlan(30, PlanType.BIG, "something big");

    }

    @Test
    public void testGeneratePlanSheets() {
    ;
    }

    @Test
    public void testGetWaitPlanSheetsWithinDays(){
        ;
    }

    @Test
    public void testFinishPlanSheet(){
        //finish方法
    }

    @Test
    public void testCancelPlanSheet(){
        //finish方法
    }

    //测试用时的
    @Test
    public void testGet() {
    ;
    }



//    @Test
//    public void testAddDevice(){
//        deviceType1.save();
//        Device device1 = deviceType1.addDevice(date, "Cai Lun Road");
//        Device device2 = deviceType1.addDevice(date, "Zu Chong Zhi Road");
//        Device device3 = deviceType1.addDevice(date, "Zhang Heng Road");
//        System.out.println(device1.exist());
//        System.out.println(device2.exist());
//        System.out.println(device3.exist());
//        assertEquals(true, device1.exist());
//        assertEquals(true, device2.exist());
//        assertEquals(true, device3.exist());
//    }
}

class Initialization{
    public static void initializeTestData() {
        DeviceType deviceType1 = new DeviceType("#A1", "Haier TV", "Haier TV Set, 15");
        System.out.println(deviceType1.exist());
        deviceType1.save();
        System.out.println(deviceType1.exist());
        deviceType1.addPlan(5, PlanType.SMALL, "something small");
        deviceType1.addPlan(20, PlanType.MIDDLE, "something mid");
        deviceType1.addPlan(30, PlanType.BIG, "something big");
        Date date = new Date((new java.util.Date()).getTime());
        Device device1 = deviceType1.addDevice(date, "Cai Lun Road");
        Device device2 = deviceType1.addDevice(date, "Zu Chong Zhi Road");
        Device device3 = deviceType1.addDevice(date, "Zhang Heng Road");
        for (Device device : deviceType1.getDevices()) {
            device.generatePlanSheets();
        }
        DeviceType deviceType2 = new DeviceType("#A2", "Haier Air Machine", "Haier Air Machine, 100W");
        deviceType2.save();
        deviceType2.addPlan(30, PlanType.SMALL, "something small");
        deviceType2.addPlan(40, PlanType.MIDDLE, "something mid");
        deviceType2.addPlan(60, PlanType.BIG, "something big");
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
        for (PlanType planName : PlanType.values()) {
            System.out.println(planName);
            System.out.println(device1.getTotalHoursByPlanType(planName));
            System.out.println(device2.getTotalHoursByPlanType(planName));
            System.out.println(device3.getTotalHoursByPlanType(planName));
        }
    }
}