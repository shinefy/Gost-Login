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
package zz.pseas.ghost.login;

import zz.pseas.ghost.login.email.Email163Login;

/**   
* @date 2017年1月16日 下午12:21:58 
* @version   
* @since JDK 1.8  
*/
public class Email163LoginTest {

	public static void main(String[] args) {
		
		//没有账号请先自己申请账号//
		String username = "testtest@163.com";
		String password = "testtest";

		String content = Email163Login.login(username, password);

		//输出 "msg":"S_OK" 则代表获取Cookie成功，以后即可利用此Cookie去模拟采集了//
		System.out.println(content);
		
		
	}
}