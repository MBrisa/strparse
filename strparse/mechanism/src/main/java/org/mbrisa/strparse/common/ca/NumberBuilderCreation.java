package org.mbrisa.strparse.common.ca;

import org.mbrisa.strparse.DataBuilder;
import org.mbrisa.strparse.common.builder.NumberBuilder;

public class NumberBuilderCreation extends BuilderCreationAction {

	private static final NumberBuilderCreation INSTANCE = new NumberBuilderCreation();
	
	public static final NumberBuilderCreation getInstance(){
		return INSTANCE;
	}
	
	private NumberBuilderCreation() {
	}
	
	@Override
	protected DataBuilder createBuilder() {
		return new NumberBuilder();
	}

}
