package org.mbrisa.strparse.jsonparser.state;

import org.mbrisa.strparse.Analyser;
import org.mbrisa.strparse.State;

public class BareMapValueState extends CollectBareValueState {
	
	public BareMapValueState(Analyser analyser) {
		super(analyser);
	}
	
	@Override
	protected State afterWhitespaceState() {
		return new MapValueOverState(this.getAnalyser());
	}
	

}
