package org.mbrisa.strparse.common.builder;

import org.mbrisa.strparse.DataBuilder;

public class StringBuilder extends DataBuilder {
	
	private java.lang.StringBuilder builder = new java.lang.StringBuilder(); 

	@Override
	public void builder(Object ob) {
		this.builder.append((char)ob);
	}
	
	@Override
	public Object complete() {
		return builder.toString();
	}
}
