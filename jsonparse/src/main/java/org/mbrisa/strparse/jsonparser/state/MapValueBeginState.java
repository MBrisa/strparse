package org.mbrisa.strparse.jsonparser.state;

import org.mbrisa.strparse.Analyser;
import org.mbrisa.strparse.CharAction;
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

	/*
	 : "..."
	 : '...'
	 : {...}
	 : [...]
	 : 123
	 */
	
	private final CharResolverSet resolvers;
	
	public MapValueBeginState(Analyser analyser) {
		super(analyser);
		this.resolvers = c -> {
			switch(c){
			case '"' : 
				return new CharResolver(){
					@Override
					public CharAction resolveCharAction() {
						return StringBuilderCreation.getInstance();
					}
					@Override
					public State resolveNextState() {
						return new StringOnMapValueState(MapValueBeginState.this.getAnalyser(),true);
					}
				};
			case '\'' : 
				return new CharResolver(){
					@Override
					public CharAction resolveCharAction() {
						return StringBuilderCreation.getInstance();
					}
					@Override
					public State resolveNextState() {
						return new StringOnMapValueState(MapValueBeginState.this.getAnalyser(),false);
					}
				};
			case '{' : 
				return new CharResolver(){
					@Override
					public CharAction resolveCharAction() {
						return MapCreation.getInstance();
					}
					@Override
					public State resolveNextState() {
						return new MapKeyBeginState(MapValueBeginState.this.getAnalyser());
					}
				};
			case '[' : 
				return new CharResolver() {
					@Override
					public State resolveNextState() {
						return new ListElementBeginState(MapValueBeginState.this.getAnalyser());
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
				return new CharResolver() {
					@Override
					public State resolveNextState() {
						return new BareMapValueState(MapValueBeginState.this.getAnalyser());
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
		};
	}

	@Override
	public CharResolverSet resolvers() {
		return resolvers;
	}

}
