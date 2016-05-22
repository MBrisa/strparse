package org.mbrisa.strparse;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mbrisa.strparse.common.builder.NumberBuilder;

public class NumberBuilderTest {
	
	@Test
	public void go(){
		char[] s;
		NumberBuilder builder;
		
		s = "123".toCharArray();
		builder = new NumberBuilder();
		for(char c : s){
			builder.builder(c);
		}
		assertEquals(123,builder.complete());
		
		
		s = "12345678987654321".toCharArray();
		builder = new NumberBuilder();
		for(char c : s){
			builder.builder(c);
		}
		assertEquals(12345678987654321L,builder.complete());
		
		s = "123.456".toCharArray();
		builder = new NumberBuilder();
		for(char c : s){
			builder.builder(c);
		}
		assertEquals(123.456,builder.complete());
		
		s = "-123.456".toCharArray();
		builder = new NumberBuilder();
		for(char c : s){
			builder.builder(c);
		}
		assertEquals(-123.456,builder.complete());
	}

}
