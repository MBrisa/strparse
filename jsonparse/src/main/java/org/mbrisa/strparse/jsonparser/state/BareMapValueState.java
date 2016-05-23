package org.mbrisa.strparse.jsonparser.state;

import org.mbrisa.strparse.State;

public class BareMapValueState extends CollectBareValueState {
	
	public BareMapValueState() {
		super();
	}
	
	@Override
	protected State afterWhitespaceState() {
		return new MapValueOverState();
	}
	

}
