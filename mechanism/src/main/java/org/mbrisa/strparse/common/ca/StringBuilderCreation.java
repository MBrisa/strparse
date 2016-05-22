package org.mbrisa.strparse.common.ca;

import org.mbrisa.strparse.DataBuilder;
import org.mbrisa.strparse.common.builder.StringBuilder;

public class StringBuilderCreation extends BuilderCreationAction {
	
	private static final StringBuilderCreation INSTANCE = new StringBuilderCreation();
	
	public static final StringBuilderCreation getInstance(){
		return INSTANCE;
	}
	
	private StringBuilderCreation() {
	}

	@Override
	protected DataBuilder createBuilder() {
		return new StringBuilder();
	}

}
