package org.mbrisa.strparse.common.state;

import org.mbrisa.strparse.CharAction;
import org.mbrisa.strparse.StateAction;
import org.mbrisa.strparse.State;
import org.mbrisa.strparse.common.ca.DataCollectionAction;

public class StringEscapeState extends State {
	
	private final State lastState;
	
	public StringEscapeState(State lastState) {
		super();
		this.lastState = lastState;
	}
	
	@Override
	public StateAction action(char c) {
		return new StateAction() {
			@Override
			public State resolveNextState() {
				return StringEscapeState.this.lastState;
			}
			@Override
			public CharAction resolveCharAction() {
				return DataCollectionAction.getInstance();
			}
		};
	}
}
