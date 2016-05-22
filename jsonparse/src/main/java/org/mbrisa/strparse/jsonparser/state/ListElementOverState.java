package org.mbrisa.strparse.jsonparser.state;

import org.mbrisa.strparse.Analyser;
import org.mbrisa.strparse.CharAction;
import org.mbrisa.strparse.State;
import org.mbrisa.strparse.common.ca.DoCompleteAction;
import org.mbrisa.strparse.common.ca.LazyCharAction;
import org.mbrisa.strparse.common.state.IgnoreWhitespaceState;

public class ListElementOverState extends IgnoreWhitespaceState {

	private final CharResolverSet resolvers;
	
	public ListElementOverState(Analyser analyser) {
		super(analyser);
		this.resolvers = c -> {
			switch(c){
			case ',' : 
				return new CharResolver(){
					@Override
					public CharAction resolveCharAction() {
						return LazyCharAction.getInstance();
					}
	
					@Override 
					public State resolveNextState() {
						return new ListElementBeginState(ListElementOverState.this.getAnalyser());
					}
				};
			case ']' : 
				return new CharResolver() {
					@Override
					public State resolveNextState() {
						return new ListOverState(ListElementOverState.this.getAnalyser());
					}
					@Override
					public CharAction resolveCharAction() {
						return DoCompleteAction.getInstance();
					}
				};
			default : return null;
			}
		};
	}
	

	@Override
	public CharResolverSet resolvers() {
		return resolvers;
	}

}
