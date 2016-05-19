package org.mbrisa.strparse.jsonparser.ca;

import org.mbrisa.strparse.DataBuilder;
import org.mbrisa.strparse.common.ca.BuilderCreationAction;
import org.mbrisa.strparse.jsonparser.builder.ListBuilder;

public class ListCreation extends BuilderCreationAction {

private final static ListCreation INSTANCE = new ListCreation();
	
	private ListCreation() {
	}

	public static ListCreation getInstance(){
		return INSTANCE;
	}
	
	@Override
	protected DataBuilder createBuilder() {
		return new ListBuilder();
	}

}
