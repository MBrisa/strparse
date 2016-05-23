package org.mbrisa.strparse.jsonparser.state;

import org.mbrisa.strparse.CharAction;
import org.mbrisa.strparse.StateAction;
import org.mbrisa.strparse.DataBuilder;
import org.mbrisa.strparse.InvalidStringException;
import org.mbrisa.strparse.State;
import org.mbrisa.strparse.common.ca.DoCompleteAction;
import org.mbrisa.strparse.common.ca.LazyCharAction;
import org.mbrisa.strparse.common.state.IgnoreWhitespaceState;
import org.mbrisa.strparse.jsonparser.builder.ListBuilder;
import org.mbrisa.strparse.jsonparser.builder.MapBuilder;

public class MapOverState extends IgnoreWhitespaceState {

	public MapOverState() {
		super();
	}

	@Override
	public StateAction actionWithoutWhitespace(char c) {
		DataBuilder builder = getBuilderStack().peek();
		switch(c){
		case ':' : // as other map key
			if(builder instanceof MapBuilder && ((MapBuilder)builder).getProgress() == MapBuilder.KVProgress.EXCEPT_VALUE){
				return new StateAction() {
					@Override
					public State resolveNextState() {
						return new MapValueBeginState();
					}
					@Override
					public CharAction resolveCharAction() {
						return LazyCharAction.getInstance();
					}
				};
			}
		case ',' : //as other map value or a array element
			State state;
			if(builder instanceof MapBuilder){
				state = new MapKeyBeginState();
			}else if(builder instanceof ListBuilder){
				state = new ListElementBeginState();
			}else{
				throw new InvalidStringException();
			}
			return new StateAction() {
				@Override
				public State resolveNextState() {
					return state;
				}
				@Override
				public CharAction resolveCharAction() {
					return LazyCharAction.getInstance();
				}
			};
		case '}' : // as other map value,and that map will complete
			return State.keepState(DoCompleteAction.getInstance());
		case ']' : // as a array element
			return new StateAction() {
				@Override
				public State resolveNextState() {
					return new ListOverState();
				}
				@Override
				public CharAction resolveCharAction() {
					return DoCompleteAction.getInstance();
				}
			};
		default : return null;
		}
	}
}
