package zz.pseas.ghost.login.beijing.shebao;

import org.openqa.selenium.WebDriver;

import java.util.Date;
import java.util.List;

/**
 * @Auther: 11582
 * @Date: 2019/5/16 16:14
 * @Description:
 */
public class SbRefreshThread implements Runnable {
    private String threadName;
    private SbCookie sbCookie;

    public SbRefreshThread(String threadName,SbCookie sbCookie) {
        this.threadName = threadName;
        this.sbCookie=sbCookie;
        System.out.println("Creating " +  threadName );
    }

    @Override
    public void run() {
        try {
            System.err.println(new Date());
            sbCookie.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        //SbRefreshThread a=new SbRefreshThread("a");
    }
}
