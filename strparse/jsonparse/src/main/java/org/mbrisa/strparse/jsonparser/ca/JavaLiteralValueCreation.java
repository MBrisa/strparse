package org.mbrisa.strparse.jsonparser.ca;

import org.mbrisa.strparse.DataBuilder;
import org.mbrisa.strparse.common.ca.BuilderCreationAction;
import org.mbrisa.strparse.jsonparser.builder.JavaLiteralValueBuilder;

public class JavaLiteralValueCreation extends BuilderCreationAction{
	
	private final static JavaLiteralValueCreation INSTANCE = new JavaLiteralValueCreation();
	
	private JavaLiteralValueCreation() {
	}

	@Override
	protected DataBuilder createBuilder() {
		return new JavaLiteralValueBuilder();
	}	

	public static JavaLiteralValueCreation getInstance(){
		return INSTANCE;
	}

}
