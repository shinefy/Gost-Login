package zz.pseas.ghost.login.beijing.shebao;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * @Auther: 11582
 * @Date: 2019/5/16 17:04
 * @Description:
 * <p>
        使用这种方案，需要引入common-lang3的jar包
        <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-lang3</artifactId>
        <version>3.6</version>
        </dependency>
 * </p>
 */
public class TimerTask {

    public static void useScheduledThreadPoolExecutorImplTimedTask(List<SbRefreshThread> sbRefreshThreadList){
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(
                1, new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(false).build());
        sbRefreshThreadList.forEach(m->{
            // 第一个参数是任务，第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间,第四个参数是时间单位
            scheduledThreadPoolExecutor.scheduleAtFixedRate(m, 5L, 10L, TimeUnit.SECONDS);
        });

    }

    public static void main(String[] args) {

//        useScheduledThreadPoolExecutorImplTimedTask(new SbRefreshThread("aa"));
    }
}
