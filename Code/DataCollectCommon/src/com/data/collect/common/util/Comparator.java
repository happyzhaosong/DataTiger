package com.data.collect.common.util;

public class Comparator implements java.util.Comparator<String> {

	@Override
	public int compare(String str1, String str2) {
		return str1.compareTo(str2);
	}

}
