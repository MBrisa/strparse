package org.mbrisa.strparse.jsonparser.state;

import org.mbrisa.strparse.Analyser;
import org.mbrisa.strparse.CharAction;
import org.mbrisa.strparse.DataBuilder;
import org.mbrisa.strparse.InvalidStringException;
import org.mbrisa.strparse.State;
import org.mbrisa.strparse.common.ca.DoCompleteAction;
import org.mbrisa.strparse.common.ca.LazyCharAction;
import org.mbrisa.strparse.common.state.IgnoreWhitespaceState;
import org.mbrisa.strparse.jsonparser.builder.ListBuilder;
import org.mbrisa.strparse.jsonparser.builder.MapBuilder;

public class ListOverState extends IgnoreWhitespaceState {

	private final CharResolverSet resolvers;
	
	public ListOverState(Analyser analyser) {
		super(analyser);
		this.resolvers = c -> {
			DataBuilder builder = getBuilderStack().peek();
			switch(c){
			case ':' : // array is map key
				if(builder instanceof MapBuilder && ((MapBuilder)builder).getProgress() == MapBuilder.KVProgress.EXCEPT_VALUE){
					return new CharResolver() {
						@Override
						public State resolveNextState() {
							return new MapValueBeginState(ListOverState.this.getAnalyser());
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
					state = new MapKeyBeginState(ListOverState.this.getAnalyser());
				}else if(builder instanceof ListBuilder){
					state = new ListElementBeginState(ListOverState.this.getAnalyser());
				}else{
					throw new InvalidStringException();
				}
				return new CharResolver() {
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
				return new CharResolver() {
					@Override
					public State resolveNextState() {
						return new MapOverState(ListOverState.this.getAnalyser());
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
		};
	}
	
	@Override
	public CharResolverSet resolvers() {
		return resolvers;
	}


}
