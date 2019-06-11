/**
 *
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
 */
package zz.pseas.ghost.login.beijing.shebao;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import zz.pseas.ghost.utils.BrowserUtil;
import zz.pseas.ghost.utils.ImgUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
* @date 2016年9月14日 下午9:26:00 
* @version   
* @since JDK 1.8  
*/
public class SimpleSbLogin {
	public static void main(String[] args) throws IOException {
		
		String jduserNmae="102000011256216";
		String jdpassWord="pb12345678";
		
		SbCookie cookieParamSupplier = new SbCookie();
		cookieParamSupplier.init();
		
		WebDriver browser = cookieParamSupplier.getBrowser();

/*	browser.findElement(By.name("username")).clear();
		browser.findElement(By.name("username"))
		.sendKeys(jduserNmae);*/

/*		//判断页面是否加载完
		boolean displayed=false;
		while(!displayed){
//			WebElement linkUsername = browser.findElement(By.xpath("//a[contains(text(),password)]"));
			WebElement linkUsername = browser.findElement(By.xpath("//input[@id='popButton2']"));
			displayed = linkUsername.isDisplayed();
		}*/
		browser.findElement(By.id("popButton2")).click();
		System.out.println(123);

	/*	browser.findElement(By.id("UserPwd"))
				.sendKeys(jduserNmae);*/

		System.out.println(123);

		browser.findElement(By.className("ziyedwyhdldiv4")).click();




	/*	WebElement authCode = browser.findElement(By.id("authcode"));
		if(authCode.isDisplayed()){
			byte[] bytes = BrowserUtil.captureScreenShotById(browser, "JD_Verification1");
			
			BufferedImage im = ImgUtil.bytesToBImage(bytes);
			ImageIO.write(im, "jpg", new File("d:/jd.jpg"));
			
			browser.findElement(By.id("authcode")).clear();
			browser.findElement(By.id("authcode"))
			.sendKeys("YYEU");
		}*/

		//new Select(browser.findElement(By.ById.xpath("//select[@id='UserList']"))).selectByValue("806000117381566/5019201803037453");
		/*new Select(browser.findElement(By.ById.xpath("//select[@id='UserList']"))).selectByValue(jduserNmae);
		browser.findElement(By.id("UserPwd")).clear();
		browser.findElement(By.id("UserPwd")).sendKeys(jdpassWord);*/

/*
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		browser.findElement(By.className("ziyedwyhdldiv4")).click();
		boolean result = false;
		while(!result){
			result=cookieParamSupplier.cookiesReady();
		}*/

/*browser.findElement(new By.ByLinkText("社会保险网上申报"))
		.click();
		cookieParamSupplier.cookiesReady();*/

		// 关闭浏览器
//		cookieParamSupplier.close();

		/*for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			cookieParamSupplier.refresh();
		}*/
	}

/*
	public static void main(String[] args) {
		SbCookie login = login("102080002927971/5019201609051050","111111");
		SbCookie login1 = login("102000011256216/011412005407","pb12345678");
		SbRefreshThread loginThread = new SbRefreshThread("loginThread", login);
		SbRefreshThread login1Thread = new SbRefreshThread("login1Thread", login1);
		List<SbRefreshThread> sbRefreshThreadList=new ArrayList();
		TimerTask.useScheduledThreadPoolExecutorImplTimedTask(sbRefreshThreadList);


	}
*/

	public static SbCookie login(String username,String password){
		SbCookie cookieParamSupplier = new SbCookie();
		cookieParamSupplier.init();
		WebDriver browser = cookieParamSupplier.getBrowser();
		//判断页面是否加载完
		boolean displayed=false;
		while(!displayed){
//			WebElement linkUsername = browser.findElement(By.xpath("//a[contains(text(),password)]"));
			WebElement linkUsername = browser.findElement(By.xpath("//input[@id='UserPwd']"));
			displayed = linkUsername.isDisplayed();
		}
		//使用原来登录方式
		browser.findElement(By.className("popButton2")).click();

		//选择key
		new Select(browser.findElement(By.ById.xpath("//select[@id='UserList']"))).selectByValue(username);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//输入密码
		browser.findElement(By.id("UserPwd")).clear();
		browser.findElement(By.id("UserPwd")).sendKeys(password);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//登录
		browser.findElement(By.className("ziyedwyhdldiv4")).click();
		boolean result = false;
		while(!result){
			result=cookieParamSupplier.cookiesReady();
		}

		/*browser.findElement(new By.ByLinkText("社会保险网上申报"))
		.click();
		cookieParamSupplier.cookiesReady();*/
		// 关闭浏览器
//		cookieParamSupplier.close();
		return cookieParamSupplier;
	}



}
