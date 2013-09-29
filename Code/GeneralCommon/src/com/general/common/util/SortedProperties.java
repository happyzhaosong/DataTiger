package com.general.common.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

public class SortedProperties extends Properties {

	@Override
	public synchronized Enumeration<Object> keys() {
		Enumeration keysEnum = super.keys();
	     Vector keyList = new Vector();
	     while(keysEnum.hasMoreElements()){
	       keyList.add(keysEnum.nextElement());
	     }
	     Collections.sort(keyList);
	     return keyList.elements();
	}
}
