package org.mbrisa.strparse.jsonparser.builder;

import java.util.ArrayList;
import java.util.List;

import org.mbrisa.strparse.DataBuilder;

public class ListBuilder extends DataBuilder {
	
	private List<Object> result = new ArrayList<>();

	@Override
	public void builder(Object ob) {
		result.add(ob);
	}

	@Override
	public Object complete() {
		return result;
	}

}
