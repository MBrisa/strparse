package org.mbrisa.strparse.jsonparser.state;

import org.mbrisa.strparse.CharAction;
import org.mbrisa.strparse.StateAction;
import org.mbrisa.strparse.State;
import org.mbrisa.strparse.common.state.IgnoreWhitespaceState;
import org.mbrisa.strparse.jsonparser.ca.ListCreation;
import org.mbrisa.strparse.jsonparser.ca.MapCreation;

public class InitState extends IgnoreWhitespaceState {
	
	public InitState() {
		super();
	}

	@Override
	public StateAction actionWithoutWhitespace(char c){
		switch(c){
		case '{' : return new StateAction() {
			@Override
			public State resolveNextState() {
				return new MapKeyBeginState();
			}
			@Override
			public CharAction resolveCharAction() {
				return MapCreation.getInstance();
			}
		};
		case '[' : return new StateAction() {
			@Override
			public State resolveNextState() {
				return new ListElementBeginState();
			}
			@Override
			public CharAction resolveCharAction() {
				return ListCreation.getInstance();
			}
		};
		default : return null;
		}
	}
	
}

