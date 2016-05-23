package org.mbrisa.strparse.jsonparser.state;

import org.mbrisa.strparse.CharAction;
import org.mbrisa.strparse.StateAction;
import org.mbrisa.strparse.State;
import org.mbrisa.strparse.common.ca.LazyCharAction;
import org.mbrisa.strparse.common.state.IgnoreWhitespaceState;

public class MapKeyOverState extends IgnoreWhitespaceState {
	
	public MapKeyOverState() {
		super();
	}

	@Override
	public StateAction actionWithoutWhitespace(char c) {
		switch(c){
		case ':' : 
			return new StateAction(){
				@Override
				public CharAction resolveCharAction() {
					return LazyCharAction.getInstance();
				}
				@Override
				public State resolveNextState() {
					return new MapValueBeginState();
				}
			};
		default : return null;
		}
	}

}
