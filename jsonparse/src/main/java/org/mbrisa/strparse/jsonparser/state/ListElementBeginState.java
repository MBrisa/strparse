package org.mbrisa.strparse.jsonparser.state;

import org.mbrisa.strparse.Analyser;
import org.mbrisa.strparse.CharAction;
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

	private final CharResolverSet resolvers;
	
	public ListElementBeginState(Analyser analyser) {
		super(analyser);
		this.resolvers = c -> {
			switch(c){
			case '"' : 
				return charResolverOnString(true);
			case '\'' : 
				return charResolverOnString(false);
			case '{' :  
				return new CharResolver() {
					@Override
					public State resolveNextState() {
						return new MapKeyBeginState(ListElementBeginState.this.getAnalyser());
					}
					@Override
					public CharAction resolveCharAction() {
						return MapCreation.getInstance();
					}
				};
			case '[' : // list as element
				return State.keepState(ListCreation.getInstance());
//				new CharResolver() {
//					@Override
//					public State resolveNextState() {
//						return ;
//					}
//					@Override
//					public CharAction resolveCharAction() {
//						return ListCreation.getInstance();
//					}
//				};
			case ']' : //empty list
				return new CharResolver() {
					@Override
					public State resolveNextState() {
						return new ListOverState(ListElementBeginState.this.getAnalyser());
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
				return new CharResolver(){
					@Override
					public CharAction resolveCharAction() {
						return (bs,cc) ->{
							bca.action(bs,cc);
							DataCollectionAction.getInstance().action(bs,cc);
						};
					}
					@Override
					public State resolveNextState() {
						return new BareListElementState(ListElementBeginState.this.getAnalyser());
					}
				};
			}
		};
	}
	
	private CharResolver charResolverOnString(boolean isDoubleQuote){
		return new CharResolver(){
			@Override
			public CharAction resolveCharAction() {
				return StringBuilderCreation.getInstance();
			}
			@Override
			public State resolveNextState() {
				return new StringOnListElementState(ListElementBeginState.this.getAnalyser(),isDoubleQuote);
			}
		};
	}
	
	@Override
	public CharResolverSet resolvers() {
		return resolvers;
	}
}
