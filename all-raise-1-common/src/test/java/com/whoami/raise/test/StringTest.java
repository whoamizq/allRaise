package com.whoami.raise.test;

import java.util.UUID;

import org.junit.Test;

public class StringTest {
	
	@Test
	public void testUUID() {
		UUID uuid = UUID.randomUUID();
		String stringUUID = uuid.toString();
		System.out.println(stringUUID);
		System.out.println(stringUUID.replaceAll("-",""));
		System.out.println(stringUUID);
	}
}
