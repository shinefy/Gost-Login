package zz.pseas.ghost.login.beijing.shebao.timer;

import org.apache.commons.collections.map.HashedMap;
import org.quartz.*;
import zz.pseas.ghost.login.beijing.shebao.SbCookie;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static zz.pseas.ghost.login.beijing.shebao.SimpleSbLogin.login;

/**
 * @Auther: 11582
 * @Date: 2019/5/17 10:45
 * @Description:
 */
public class SbLoginJob implements Job {



    //这里是第二种获取jobDataMap中的值的方法
    private Map<String,String> loginMap;
    private Map<String,String> loginParamGlobal;
    private Map<String,SbCookie> sbCookieMap;
 /*   private long firstDate;
    private Boolean isLogin;

    public Boolean getLogin() {
        return isLogin;
    }

    public void setLogin(Boolean login) {
        isLogin = login;
    }
*/
/*    public long getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(long firstDate) {
        this.firstDate = firstDate;
    }*/

    public Map<String, String> getLoginParamGlobal() {
        return loginParamGlobal;
    }

    public void setLoginParamGlobal(Map<String, String> loginParamGlobal) {
        this.loginParamGlobal = loginParamGlobal;
    }

    public Map<String, String> getLoginMap() {
        return loginMap;
    }

    public void setLoginMap(Map<String, String> loginMap) {
        this.loginMap = loginMap;
    }

    public Map<String, SbCookie> getSbCookieMap() {
        return sbCookieMap;
    }

    public void setSbCookieMap(Map<String, SbCookie> sbCookieMap) {
        this.sbCookieMap = sbCookieMap;
    }

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        long firstDate= Long.parseLong(loginParamGlobal.get("firstDate"));
        Boolean isLogin= Boolean.valueOf(loginParamGlobal.get("isLogin"));
        System.out.println(firstDate+"***************"+isLogin);

        //打印当前的执行时间 例如 2017-11-22 00:00:00
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("初始时间是***"+firstDate+"**********登录状态"+isLogin+"来到定时任务现在的时间是："+ sf.format(date));

        long nowDate = Instant.now().getEpochSecond();
        //如果开始到现在时间大于一个小时 则重新登录
        if(nowDate-firstDate>1*60*60){
            System.out.println("来到登录方法");
            firstDate=nowDate;
            loginParamGlobal.put("firstDate",nowDate+"");
            Map<String,SbCookie> map= new HashMap();
            for(Map.Entry<String, String> entry : loginMap.entrySet()){
                try {
                    String key=entry.getKey();
                    String value=entry.getValue();
                    System.out.println("Key = " + key + ", Value = " + value);
                    /*if(sbCookieMap!=null){
                        SbCookie sbCookie = sbCookieMap.get(key);
                        if(sbCookie!=null){
                            sbCookie.close();
                            sbCookieMap.put(key ,null);
                        }
                    }
                    SbCookie login = login(key,value);
                    System.out.println("Key = " + key + ", Value = " + value+"************登录成功");
                    sbCookieMap.put(key ,login);*/
                        LoginThread loginThread=new LoginThread(key, value,sbCookieMap);
                        loginThread.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            isLogin=true;
            loginParamGlobal.put("isLogin",true+"");
        }else{
            //登录成功
            if(isLogin){
                System.out.println("开始刷新浏览器");
                //刷新浏览器
                 for (SbCookie sbCookie : sbCookieMap.values()) {
                    sbCookie.refresh();
                }
            }

        }



     }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        long epochSecond = Instant.now().getEpochSecond();
        System.out.println(Instant.now().getEpochSecond());
    }


}
