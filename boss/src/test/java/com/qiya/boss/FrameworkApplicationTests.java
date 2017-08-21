package com.qiya.boss;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = BossApplication.class)
public class FrameworkApplicationTests {
	public static void main(String[] args) {
//		String pattern = "(\\w)(\\s+)([\\.,])";
//		System.out.println("来源：界面新闻    作者：      2017/5/8 7:00:00 ".replaceAll(pattern, "$1$3"));
		// String to be scanned to find the pattern.
//		String line = "来源：界面新闻    作者：邓对义       2017/5/8 7:00:00 ";
//		String pattern = "作者：(.*?)\\s\\D";
//
//		// Create a Pattern object
//		Pattern r = Pattern.compile(pattern);
//
//		// Now create matcher object.
//		Matcher m = r.matcher(line);
//		if (m.find( )) {
//			System.out.println("Found value0: " + m.group(0) );
//			System.out.println("Found value1: " + m.group(1) );
//			System.out.println("Found value2: " + m.group(2) );
//		}else {
//			System.out.println("NO MATCH");
//		}

		String a = "然而现在\u2028它们被私有、被肆意破坏";
		System.out.println(a.length());
		String b = a.replaceAll("\u2028","");
		System.out.println(b.length());
	}
//	@Test
//	public void contextLoads() {
//		String pattern = "(\\w)(\\s+)([\\.,])";
//		System.out.println("来源：界面新闻    作者：      2017/5/8 7:00:00 ".replaceAll(pattern, "$1$3"));
//	}

}
