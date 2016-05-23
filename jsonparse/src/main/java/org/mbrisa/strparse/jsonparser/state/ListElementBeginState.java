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
import org.mbrisa.strparse.jsonparser.ca.JavaLiteralValueCreation;
import org.mbrisa.strparse.jsonparser.ca.ListCreation;
import org.mbrisa.strparse.jsonparser.ca.MapCreation;

public class ListElementBeginState extends IgnoreWhitespaceState {

	private static final StateAction[] saa = new StateAction[Character.MAX_VALUE+1];
	static{
		StateAction dsa = new StateAction(){
			@Override
			public CharAction resolveCharAction() {
				return (bs,cc) ->{
					JavaLiteralValueCreation.getInstance().action(bs,cc);
					DataCollectionAction.getInstance().action(bs,cc);
				};
			}
			@Override
			public State resolveNextState() {
				return new BareListElementState();
			}
		};
		for(int i = 0;i<saa.length;i++){
			saa[i] = dsa;
		}
		saa['"'] = charResolverOnString(true);
		saa['\''] =  charResolverOnString(false);
		saa['{'] = new StateAction() {
			@Override
			public State resolveNextState() {
				return new MapKeyBeginState();
			}
			@Override
			public CharAction resolveCharAction() {
				return MapCreation.getInstance();
			}
		};
		saa['['] = State.keepState(ListCreation.getInstance());// list as element
		saa[']'] = new StateAction() {
			@Override
			public State resolveNextState() {
				return new ListOverState();
			}
			@Override
			public CharAction resolveCharAction() {
				return DoCompleteAction.getInstance();
			}
		};//empty list
		saa['-'] = saa['+'] = saa['0'] = saa['1'] = saa['2'] = saa['3'] = saa['4'] = saa['5'] = saa['6'] = saa['7'] = saa['8'] = saa['9'] = new StateAction(){
			@Override
			public CharAction resolveCharAction() {
				return (bs,cc) ->{
					NumberBuilderCreation.getInstance().action(bs,cc);
					DataCollectionAction.getInstance().action(bs,cc);
				};
			}
			@Override
			public State resolveNextState() {
				return new BareListElementState();
			}
		};
	}
	
	public ListElementBeginState() {
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
				return StringOnListElementState.getInstance(isDoubleQuote);
			}
		};
	}
	
	@Override
	public StateAction actionWithoutWhitespace(char c) {
			switch(c){
			case '"' : 
				return charResolverOnString(true);
			case '\'' : 
				return charResolverOnString(false);
			case '{' :  
				return new StateAction() {
					@Override
					public State resolveNextState() {
						return new MapKeyBeginState();
					}
					@Override
					public CharAction resolveCharAction() {
						return MapCreation.getInstance();
					}
				};
			case '[' : // list as element
				return State.keepState(ListCreation.getInstance());
			case ']' : //empty list
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
			default :
				BuilderCreationAction bca;
				if(c == '-' || c == '+' || (c >= '0' && c <= '9')){
					bca = NumberBuilderCreation.getInstance();
				}else{
					bca = JavaLiteralValueCreation.getInstance();
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
						return new BareListElementState();
					}
				};
			}
	}
}
