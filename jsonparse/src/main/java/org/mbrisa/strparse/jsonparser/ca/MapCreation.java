package org.mbrisa.strparse.jsonparser.ca;

import org.mbrisa.strparse.DataBuilder;
import org.mbrisa.strparse.common.ca.BuilderCreationAction;
import org.mbrisa.strparse.jsonparser.builder.MapBuilder;

public class MapCreation extends BuilderCreationAction{
	
	private final static MapCreation INSTANCE = new MapCreation();
	
	private MapCreation() {
	}

	@Override
	protected DataBuilder createBuilder() {
		return new MapBuilder();
	}	

	public static MapCreation getInstance(){
		return INSTANCE;
	}

}
