package org.mbrisa.strparse.jsonparser.state;

import org.mbrisa.strparse.CharAction;
import org.mbrisa.strparse.StateAction;
import org.mbrisa.strparse.State;
import org.mbrisa.strparse.common.ca.BuilderCreationAction;
import org.mbrisa.strparse.common.ca.DataCollectionAction;
import org.mbrisa.strparse.common.ca.DoCompleteAction;
import org.mbrisa.strparse.common.ca.NumberBuilderCreation;
import org.mbrisa.strparse.common.ca.StringBuilderCreation;
import org.mbrisa.strparse.common.state.IgnoreWhitespaceState;
import org.mbrisa.strparse.jsonparser.ca.JavaIdentifyCreation;
import org.mbrisa.strparse.jsonparser.ca.ListCreation;
import org.mbrisa.strparse.jsonparser.ca.MapCreation;

public class MapKeyBeginState extends IgnoreWhitespaceState {
	
	
	public MapKeyBeginState() {
		super();
	}
	
	private static StateAction charResolverOnString(boolean isDoubleQuote){
		return new StateAction(){
			@Override
			public CharAction resolveCharAction() {
				return StringBuilderCreation.getInstance();
			}
			@Override
			public State resolveNextState() {
				return StringOnMapKeyState.getInstance(isDoubleQuote);
			}
		};
	}

	@Override
	public StateAction actionWithoutWhitespace(char c) {
//		case 1:  "
//		case 2:  '
//		case 3:  123
//		case 4:  abc
//		case 5:  [
		switch(c){
		case '"' : // string as map key
			return charResolverOnString(true);
		case '\'' : 
			return charResolverOnString(false);
		case '[' : // list as key
			return new StateAction() {
				@Override
				public State resolveNextState() {
					return new ListElementBeginState();
				}
				@Override
				public CharAction resolveCharAction() {
					return ListCreation.getInstance();
				}
			};
		case '{' : //map as key
			return State.keepState(MapCreation.getInstance());
		case '}' : // empty map
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
		default : // number or identify as key 
			BuilderCreationAction bca;
			if(c == '-' || c == '+' || (c >= '0' && c <= '9')){
				bca = NumberBuilderCreation.getInstance();
			}else{
				bca = JavaIdentifyCreation.getInstance();
			}
			return new StateAction(){
				@Override
				public CharAction resolveCharAction() {
					return (bs,cc) ->{
						bca.action(bs,cc);
						DataCollectionAction.getInstance().action(bs,cc);
					};
				}
				@Override
				public State resolveNextState() {
					return new BareMapKeyState();
				}
			};
			
		}
	}
	

}
