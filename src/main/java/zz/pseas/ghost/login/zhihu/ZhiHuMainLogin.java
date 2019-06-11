/** 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * 
 */
package zz.pseas.ghost.login.zhihu;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**   
* @date 2016年9月14日 下午9:26:00 
* @version   
* @since JDK 1.8  
*/
/**
 * 模拟登录知乎
 */
public class ZhiHuMainLogin {
    //知乎首页
    final private static String INDEX_URL = "https://www.zhihu.com";
    //邮箱登录地址
    final private static String EMAIL_LOGIN_URL = "https://www.zhihu.com/login/email";
    //手机号码登录地址
    final private static String PHONENUM_LOGIN_URL = "https://www.zhihu.com/login/phone_num";
    //登录验证码地址
    final private static String YZM_URL = "https://www.zhihu.com/captcha.gif?type=login";

    private CloseableHttpClient httpClient;
    private HttpClientContext httpClientContext;

    /**
     * 初始化CloseableHttpClient、HttpClientContext并设置Cookie策略
     */
    public ZhiHuMainLogin(){
        RequestConfig globalConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
                .build();
        httpClient = HttpClients.custom()
                .setDefaultRequestConfig(globalConfig)
                .build();

        httpClientContext = HttpClientContext.create();
        Registry<CookieSpecProvider> registry = RegistryBuilder
                .<CookieSpecProvider> create()
                .register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
                .register(CookieSpecs.BROWSER_COMPATIBILITY,
                        new BrowserCompatSpecFactory()).build();
        httpClientContext.setCookieSpecRegistry(registry);
    }
    /**
     *
     * @param emailOrPhoneNum 邮箱或手机号码
     * @param pwd 密码
     * @return
     */
    public boolean login(String emailOrPhoneNum,
                         String pwd){
        String yzm = null;
        String loginState = null;
        HttpGet getRequest = new HttpGet(INDEX_URL);
        HttpClientUtil.getWebPage(httpClient, httpClientContext, getRequest, "utf-8", false);
        HttpPost request = null;
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        if(emailOrPhoneNum.contains("@")){
            //通过邮箱登录
            request = new HttpPost(EMAIL_LOGIN_URL);
            formParams.add(new BasicNameValuePair("email", emailOrPhoneNum));
        }
        else {
            //通过手机号码登录
            request = new HttpPost(PHONENUM_LOGIN_URL);
            formParams.add(new BasicNameValuePair("phone_num", emailOrPhoneNum));
        }
        yzm = zhCaptcha(httpClient, httpClientContext, YZM_URL);
        formParams.add(new BasicNameValuePair("captcha", yzm));
        formParams.add(new BasicNameValuePair("_xsrf", ""));//这个参数可以不用
        formParams.add(new BasicNameValuePair("password", pwd));
        formParams.add(new BasicNameValuePair("remember_me", "true"));
        UrlEncodedFormEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(formParams, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        request.setEntity(entity);
        loginState = HttpClientUtil.getWebPage(httpClient, httpClientContext, request, "utf-8", false);//登录
        JSONObject jo = new JSONObject(loginState);
        if(jo.get("r").toString().equals("0")){
            System.out.println("登录知乎成功");
            getRequest = new HttpGet("https://www.zhihu.com");
            /**
             * 访问首页
             */
            HttpClientUtil.getWebPage(httpClient, httpClientContext,getRequest, "utf-8", false);
            return true;
        }else{
            throw new RuntimeException(HttpClientUtil.decodeUnicode(loginState));
        }
    }
    /**
     * 下载验证码至本地，并手动输入验证码//
     * @return
     */
    public String zhCaptcha(CloseableHttpClient httpClient,HttpClientContext context, String url){
        String verificationCodePath = System.getProperty("user.home");
        //下载验证码至本地
        HttpClientUtil.downloadFile(httpClient, context, url, verificationCodePath, "/yzm.jpg",true);
        System.out.println("请输入 " + verificationCodePath + "\\zhihu_captcha01.jpg 下的验证码：");
        Scanner sc = new Scanner(System.in);
        String yzm = sc.nextLine();
        return yzm;
    }
    public static void main(String[] args){
        ZhiHuMainLogin modelLogin = new ZhiHuMainLogin();
        modelLogin.login("邮箱或手机号码", "密码");
    }
}
