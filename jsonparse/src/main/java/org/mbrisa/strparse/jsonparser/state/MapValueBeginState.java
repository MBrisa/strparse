package org.mbrisa.strparse.jsonparser.state;

import org.mbrisa.strparse.CharAction;
import org.mbrisa.strparse.StateAction;
import org.mbrisa.strparse.State;
import org.mbrisa.strparse.common.ca.BuilderCreationAction;
import org.mbrisa.strparse.common.ca.DataCollectionAction;
import org.mbrisa.strparse.common.ca.NumberBuilderCreation;
import org.mbrisa.strparse.common.ca.StringBuilderCreation;
import org.mbrisa.strparse.common.state.IgnoreWhitespaceState;
import org.mbrisa.strparse.jsonparser.ca.JavaLiteralValueCreation;
import org.mbrisa.strparse.jsonparser.ca.ListCreation;
import org.mbrisa.strparse.jsonparser.ca.MapCreation;

public class MapValueBeginState extends IgnoreWhitespaceState {

	public MapValueBeginState() {
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
				return StringOnMapValueState.getInstance(isDoubleQuote);
			}
		};
	}

	@Override
	public StateAction actionWithoutWhitespace(char c) {
		/*
		 : "..."
		 : '...'
		 : {...}
		 : [...]
		 : 123
		 */
		switch(c){
		case '"' : 
			return charResolverOnString(true);
		case '\'' : 
			return charResolverOnString(false);
		case '{' : 
			return new StateAction(){
				@Override
				public CharAction resolveCharAction() {
					return MapCreation.getInstance();
				}
				@Override
				public State resolveNextState() {
					return new MapKeyBeginState();
				}
			};
		case '[' : 
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
		default : 
			BuilderCreationAction bca;
			if(c == '-' || c == '+' || (c >= '0' && c <= '9')){
				bca = NumberBuilderCreation.getInstance();
			}else{
				bca = JavaLiteralValueCreation.getInstance();
			}
			return new StateAction() {
				@Override
				public State resolveNextState() {
					return new BareMapValueState();
				}
				@Override
				public CharAction resolveCharAction() {
					return (bs,c) -> {
						bca.action(bs,c);
						DataCollectionAction.getInstance().action(bs,c);
					};
				}
			};
		}
	}

}
