package org.mbrisa.strparse.jsonparser.state;

import org.mbrisa.strparse.Analyser;
import org.mbrisa.strparse.State;

public class BareListElementState extends CollectBareValueState {
	
	
	public BareListElementState(Analyser analyser) {
		super(analyser);
	}

	
	@Override
	protected State afterWhitespaceState() {
		return new ListElementOverState(BareListElementState.this.getAnalyser());
	}
	
}
