package org.mbrisa.strparse.jsonparser.ca;

import org.mbrisa.strparse.DataBuilder;
import org.mbrisa.strparse.common.ca.BuilderCreationAction;
import org.mbrisa.strparse.jsonparser.builder.JavaIdentifyBuilder;

public class JavaIdentifyCreation extends BuilderCreationAction {
	
	private static final JavaIdentifyCreation INSTANCE = new JavaIdentifyCreation();
	
	public static final JavaIdentifyCreation getInstance(){
		return INSTANCE;
	}
	
	private JavaIdentifyCreation() {
	}

	@Override
	protected DataBuilder createBuilder() {
		return new JavaIdentifyBuilder();
	}

}
