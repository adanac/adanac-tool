package com.adanac.tool.sfunc.task;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;

public class TimerTest2 {

	public static void main(String[] args) {
		Timer timer = new Timer();
		MyTask myTask1 = new MyTask();
		MyTask myTask2 = new MyTask();
		myTask2.setInfo("myTask-info-2");

		timer.schedule(myTask1, 1000, 2000); // 任务1 一秒钟后执行，每两秒执行一次。
		timer.scheduleAtFixedRate(myTask2, 2000, 3000); // 任务2
														// 2秒后开始进行重复的固定速率执行（3秒钟重复一次）

		while (true) {
			try {
				// 用来接收键盘输入的字符串
				byte[] info = new byte[1024];
				int len = System.in.read(info);

				String strInfo = new String(info, 0, len, "GBK");// 从控制台读出信息

				if (strInfo.charAt(strInfo.length() - 1) == ' ') {
					strInfo = strInfo.substring(0, strInfo.length() - 2);
				}

				if (strInfo.startsWith("Cancel-1")) {
					myTask1.cancel();// 退出任务1
					// 其实应该在这里判断myTask2是否也退出了,是的话就应该break.但是因为无法在包外得到
					// myTask2的状态,所以,这里不能做出是否退出循环的判断.
				} else if (strInfo.startsWith("Cancel-2")) {
					myTask2.cancel(); // 退出任务2
				} else if (strInfo.startsWith("Cancel-All")) {
					timer.cancel();// 退出Timer
					break;
				} else {
					// 只对myTask1作出判断,偷个懒^_^
					myTask1.setInfo(strInfo);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	static class MyTask extends java.util.TimerTask {

		String info = "INFO";

		@Override
		public void run() {
			System.out.println(new Date() + "      " + info);
		}

		public String getInfo() {
			return info;
		}

		public void setInfo(String info) {
			this.info = info;
		}
	}

}
