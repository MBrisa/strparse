package org.mbrisa.strparse.jsonparser.state;

import org.mbrisa.strparse.Analyser;
import org.mbrisa.strparse.CharAction;
import org.mbrisa.strparse.State;
import org.mbrisa.strparse.common.ca.DataCollectionAction;
import org.mbrisa.strparse.common.ca.DoCompleteAction;

public abstract class CollectBareValueState extends State {

	private final CharResolverSet resolvers;
	
	public CollectBareValueState(Analyser analyser) {
		super(analyser);
		resolvers = c -> {
			if(Character.isWhitespace(c)){
				return new CharResolver() {
					@Override
					public State resolveNextState() {
						return CollectBareValueState.this.afterWhitespaceState();
					}
					@Override
					public CharAction resolveCharAction() {
						return DoCompleteAction.getInstance();
					}
				};
			}
			CharResolver cr = CollectBareValueState.this.afterWhitespaceState().resolvers().getResolver(c);
			if(cr == null){
				return State.keepState(DataCollectionAction.getInstance());
			}
			return new CharResolver() {
				@Override
				public State resolveNextState() {
					return cr.resolveNextState();
				}
				
				@Override
				public CharAction resolveCharAction() {
					return (bs,c) -> {
						DoCompleteAction.getInstance().action(bs, c);
						cr.resolveCharAction().action(bs, c);
					}; 
				}
			};
		};
	}
	
	@Override
	public CharResolverSet resolvers() {
		return resolvers;
	}
	
	protected abstract State afterWhitespaceState();
	
}