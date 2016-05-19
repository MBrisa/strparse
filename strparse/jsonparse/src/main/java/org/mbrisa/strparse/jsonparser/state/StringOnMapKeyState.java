package org.mbrisa.strparse.jsonparser.state;

import org.mbrisa.strparse.Analyser;
import org.mbrisa.strparse.CharAction;
import org.mbrisa.strparse.State;
import org.mbrisa.strparse.common.ca.DoCompleteAction;

public class StringOnMapKeyState extends InStringState {
	
	
	public StringOnMapKeyState(Analyser analyser,boolean isDobuleQuote) {
		super(analyser,isDobuleQuote);
	}
	
	@Override
	protected CharResolver stringEndResolver(){
		return new CharResolver() {
			@Override
			public State resolveNextState() {
				return new MapKeyOverState(StringOnMapKeyState.this.getAnalyser());
			}
			@Override
			public CharAction resolveCharAction() {
				return DoCompleteAction.getInstance();
			}
		};
	}
	
}
