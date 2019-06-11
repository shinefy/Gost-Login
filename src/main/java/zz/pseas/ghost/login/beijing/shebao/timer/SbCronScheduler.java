package zz.pseas.ghost.login.beijing.shebao.timer;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.AnnualCalendar;
import org.quartz.impl.calendar.MonthlyCalendar;
import zz.pseas.ghost.login.beijing.shebao.SbCookie;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: 11582
 * @Date: 2019/5/17 10:46
 * @Description:
 */
public class SbCronScheduler {
    public static void main(String[] args) throws SchedulerException, InterruptedException {
        Map<String,String> login=new HashMap<String, String>();
        Map<String,String> loginParamGlobal=new HashMap<String, String>();
        login.put("102080002927971","111111");
//        login.put("102000011256216","pb12345678");
        loginParamGlobal.put("isLogin","false");
        loginParamGlobal.put("firstDate","1");
        startSchduler(login,loginParamGlobal);
    }

    public static void startSchduler(Map<String, String> loginMap,Map<String, String> loginParamGlobal){
    String a="0";
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //传递登录参数
            JobDataMap loginJobData=new JobDataMap();
            loginJobData.put("loginMap",loginMap);
            loginJobData.put("loginParamGlobal",loginParamGlobal);
            loginJobData.put("sbCookieMap",new HashMap<String, String> ());

            //创建一个JobDetail的实例，将该实例与HelloJob绑定
            JobDetail jobDetail = JobBuilder.newJob(SbLoginJob.class)
                    .withIdentity("cronJob")
//                    .usingJobData("message","hello myJob1") //加入属性到jobDataMap
//                    .usingJobData("FloatJobValue",8.88f) //加入属性到jobDataMap
                    .usingJobData(loginJobData)
//                    .usingJobData("isLogin",false)
//                    .usingJobData("firstDate",1L)
                    .build();


//            AnnualCalendar holidays = new AnnualCalendar();
//            GregorianCalendar nationalDay = new GregorianCalendar(2017, 10, 27);  // 排除今天的时间2017年11月27日（月份是从0～11的）
//            holidays.setDayExcluded(nationalDay,true); //排除的日期，如果为false则为包含*/

        /*    //排除每月1-4号
            MonthlyCalendar monthlyDay=new MonthlyCalendar();
            monthlyDay.setDayExcluded(1,true);
            monthlyDay.setDayExcluded(2,true);
            monthlyDay.setDayExcluded(3,true);
            monthlyDay.setDayExcluded(4,true);

            //创建Scheduler实例
            StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = stdSchedulerFactory.getScheduler();
            //向Scheduler注册日历
//            scheduler.addCalendar("holidays",holidays,false,false);
            scheduler.addCalendar("monthlyDay",monthlyDay,false,false);

            Trigger simpleTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("zhlTrigger")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1*1*60).repeatForever()) //每一秒执行一次job
                    .modifiedByCalendar("holidays")   //将我们设置好的Calander与trigger绑定
                    .modifiedByCalendar("monthlyDay")
                    .build();
            //让trigger应用指定的日历规则
            //scheduler.scheduleJob(jobDetail,simpleTrigger);
            System.out.println("现在的时间 ："+sf.format(new Date()));
            System.out.println("最近的一次执行时间 ："+sf.format(scheduler.scheduleJob(jobDetail,simpleTrigger))); //scheduler与jobDetail、trigger绑定，并打印出最近一次执行的事件
            scheduler.start();*/

            //cronTrigger
            //每月5号到最后一天 的6点到22点 每两分钟触发任务
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("cronTrigger").withSchedule(CronScheduleBuilder.cronSchedule("0 */2 7-21 5-31 * ?")).build();
            //Scheduler实例
            StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = stdSchedulerFactory.getScheduler();
            scheduler.start();
            scheduler.scheduleJob(jobDetail,cronTrigger);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
