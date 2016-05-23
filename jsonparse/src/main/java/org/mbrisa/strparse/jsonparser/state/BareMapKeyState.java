package org.mbrisa.strparse.jsonparser.state;

import org.mbrisa.strparse.State;

public class BareMapKeyState extends CollectBareValueState {
	
	public BareMapKeyState() {
		super();
	}
	
	@Override
	protected State afterWhitespaceState() {
		return new MapKeyOverState();
	}
	
}
