package org.mbrisa.strparse.jsonparser.state;

import org.mbrisa.strparse.State;

public class BareListElementState extends CollectBareValueState {
	
	
	public BareListElementState() {
		super();
	}

	
	@Override
	protected State afterWhitespaceState() {
		return new ListElementOverState();
	}
	
}
