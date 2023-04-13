package com.sigmify.vb.admin.util;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class ReferralCodeGenerator {
	public String createRandomCode(int codeLength) {
		char[] chars="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
		StringBuilder sb=new StringBuilder();
		Random random=new SecureRandom();
		for (int i = 0; i < codeLength; i++) {
			char c=chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output=sb.toString();
		//System.out.println(output);
		return output;		
	}
}
