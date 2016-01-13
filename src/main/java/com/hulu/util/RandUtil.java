package com.hulu.util;

import java.util.Random;

/**
 * 随机相关工具类
 * @author liu.qingliang
 *
 */
public class RandUtil {

	private static final Random rand = new Random();
	
	private static final int[] RAND_NUMS = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
	
	private final static char[] ch_min_word = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	
	private final static char[] ch_max_word = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	
	private final static char[] ch_word = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

	private final static char[] ch_digit_word = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
	
	private static final int  WORDS = 2;
	
	private static final int  MIN_WORDS = 3;
	
	private static final int  MAX_WORDS = 4;
	
	private static final int  MIX = 5;
	
	
	/**
	 * 获取4位随机数
	 * @return
	 */
	public static int getRand4() {
		return getRand(4);
	}
	/**
	 * 获取n位随机数
	 * @param n 随机数的位数（如5，则表示[10000，99999]）
	 * @return
	 */
	public static int getRand(int n) {
		if(n <= 0) {
			return 0;
		} else if(n == 1) {
			return rand.nextInt(10);
		} else if(n == 10) {
			return 1000000000 + getRand(9);
		}
		
		int r = RAND_NUMS[n - 1] + rand.nextInt(RAND_NUMS[n] - RAND_NUMS[n - 1]);
		
		return r;
	}
	/**
	 * 随机获取n位小写英文组成的字符串
	 * @param n
	 * @return
	 */
	public static String getRandLowerCh(int n){
		return getRand(n, MIN_WORDS);
	}
	/**
	 * 随机获取n位大写英文组成的字符串
	 * @param n
	 * @return
	 */
	public static String getRandUpperCh(int n){
		return getRand(n, MAX_WORDS);
	}
	
	/**
	 * 随机获取n位英文字母组成的字符串
	 * @param n
	 * @return
	 */
	public static String getRandCh(int n){
		return getRand(n, WORDS);
	}
	
	/**
	 * 随机获取n位英文字母或数字组成的字符串
	 * @param n
	 * @return
	 */
	public static String getRandString(int n){
		return getRand(n, MIX);
	}
	
	/**
	 * 
	 * @param n 生成随机字符串的位数
	 * @param type 生成随机字符串的类型
	 * @return
	 */
	public static String getRand(int n,int type){
		StringBuilder result = new StringBuilder();
		char[] ch = getCh(type);
		if(ch != null && n > 0){
			for (int i = 0; i < n; i++) {
				result.append(ch[rand.nextInt(ch.length)]);
			}
		}
		return result.toString();
	}
	private static char[] getCh(int type) {
		char[] ch = null;
		if(WORDS == type){
			ch = ch_word;
		}else if(MIN_WORDS == type){
			ch = ch_min_word;
		}else if(MAX_WORDS == type){
			ch = ch_max_word;
		}else if(MIX == type){
			ch = ch_digit_word;
		}
		return ch;
	}
	
}
