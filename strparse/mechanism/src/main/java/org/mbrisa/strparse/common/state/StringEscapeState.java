package org.mbrisa.strparse.common.state;

import org.mbrisa.strparse.CharAction;
import org.mbrisa.strparse.State;
import org.mbrisa.strparse.common.ca.DataCollectionAction;

public class StringEscapeState extends State {
	
	private final CharResolverSet resolvers;
	
	public StringEscapeState(State lastState) {
		super(lastState.getAnalyser());
		this.resolvers = c -> {
			return new CharResolver() {
				@Override
				public State resolveNextState() {
					return lastState;
				}
				@Override
				public CharAction resolveCharAction() {
					return DataCollectionAction.getInstance();
				}
			};
		};
	}
	
	@Override
	public CharResolverSet resolvers() {
		return resolvers;
	}
}
