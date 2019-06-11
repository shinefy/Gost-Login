package zz.pseas.ghost.login.beijing.shebao.timer;

import zz.pseas.ghost.login.beijing.shebao.SbCookie;

import java.util.Date;
import java.util.Map;

import static zz.pseas.ghost.login.beijing.shebao.SimpleSbLogin.login;

/**
 * @Auther: 11582
 * @Date: 2019/5/18 13:52
 * @Description:
 */
public class LoginThread implements Runnable {
    private Thread t;
    private String username;
    private String password;
    Map<String,SbCookie> sbCookieMap;

    public LoginThread(String username, String password, Map<String, SbCookie> sbCookieMap) {
        this.username = username;
        this.password = password;
        this.sbCookieMap = sbCookieMap;
    }

    @Override
    public void run() {
        try {
            System.err.println(new Date());

             if(sbCookieMap!=null){
                SbCookie sbCookie = sbCookieMap.get(username);
                if(sbCookie!=null){
                    sbCookie.close();
                    sbCookieMap.put(username ,null);
                }
              }
            SbCookie login = login(username,password);
            System.out.println("username = " + username + ", Value = " + password+"************登录成功");
            sbCookieMap.put(username ,login);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void start () {
        System.out.println("Starting " +  username );
        if (t == null) {
            t = new Thread (this, username);
            t.start ();
        }
    }

    public static void main(String[] args) {
        //SbRefreshThread a=new SbRefreshThread("a");
    }
}
