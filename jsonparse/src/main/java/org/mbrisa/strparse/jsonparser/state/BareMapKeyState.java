package org.mbrisa.strparse.jsonparser.state;

import org.mbrisa.strparse.Analyser;
import org.mbrisa.strparse.State;

public class BareMapKeyState extends CollectBareValueState {
	
	public BareMapKeyState(Analyser analyser) {
		super(analyser);
	}
	
	@Override
	protected State afterWhitespaceState() {
		return new MapKeyOverState(this.getAnalyser());
	}
	
}
