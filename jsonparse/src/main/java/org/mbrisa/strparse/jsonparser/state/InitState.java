package org.mbrisa.strparse.jsonparser.state;

import org.mbrisa.strparse.Analyser;
import org.mbrisa.strparse.CharAction;
import org.mbrisa.strparse.State;
import org.mbrisa.strparse.common.state.IgnoreWhitespaceState;
import org.mbrisa.strparse.jsonparser.ca.ListCreation;
import org.mbrisa.strparse.jsonparser.ca.MapCreation;

public class InitState extends IgnoreWhitespaceState {
	
	private final CharResolverSet resolverSet ;
	
	public InitState(Analyser analyser) {
		super(analyser);
		resolverSet = c -> {
			switch(c){
			case '{' :
				return new CharResolver() {
					@Override
					public State resolveNextState() {
						return new MapKeyBeginState(InitState.this.getAnalyser());
					}
					@Override
					public CharAction resolveCharAction() {
						return MapCreation.getInstance();
					}
				};
			case '[' :
				return new CharResolver() {
					@Override
					public State resolveNextState() {
						return new ListElementBeginState(InitState.this.getAnalyser());
					}
					@Override
					public CharAction resolveCharAction() {
						return ListCreation.getInstance();
					}
				};
			default : return null;
			}
		};
	}

	@Override
	public CharResolverSet resolvers() {
		return this.resolverSet;
	}
	
}

