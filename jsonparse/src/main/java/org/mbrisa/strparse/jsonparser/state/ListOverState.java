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

public class ListOverState extends IgnoreWhitespaceState {

	public ListOverState() {
		super();
	}
	
	@Override
	public StateAction actionWithoutWhitespace(char c) {
		DataBuilder builder = getBuilderStack().peek();
		switch(c){
		case ':' : // array is map key
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
			throw new InvalidStringException();
		case ',' : // as other array element or a map value
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
		case '}' : // as map value
			return new StateAction() {
				@Override
				public State resolveNextState() {
					return new MapOverState();
				}
				@Override
				public CharAction resolveCharAction() {
					return DoCompleteAction.getInstance();
				}
			};
		case ']' : // as other array element,and that array will complete
			return State.keepState(DoCompleteAction.getInstance());
		default : return null;
		}
	}


}
