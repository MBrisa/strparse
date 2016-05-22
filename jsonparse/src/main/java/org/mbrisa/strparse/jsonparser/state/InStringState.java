package org.mbrisa.strparse.jsonparser.state;

import org.mbrisa.strparse.Analyser;
import org.mbrisa.strparse.CharAction;
import org.mbrisa.strparse.State;
import org.mbrisa.strparse.common.ca.DataCollectionAction;
import org.mbrisa.strparse.common.ca.LazyCharAction;
import org.mbrisa.strparse.common.state.StringEscapeState;

public abstract class InStringState extends State {

	
	private final CharResolverSet resolvers;

	public InStringState(Analyser analyser,boolean isDobuleQuote) {
		super(analyser);
		CharResolverContainer dq_crd = new CharResolverContainer();
		CharResolverContainer sq_crd = new CharResolverContainer();
		if(isDobuleQuote){
			dq_crd.setResolver(stringEndResolver());
		}else{
			sq_crd.setResolver(stringEndResolver());
		}
		CharResolver cr = new CharResolver() {
			@Override
			public State resolveNextState() {
				return new StringEscapeState(InStringState.this);
			}
			@Override
			public CharAction resolveCharAction() {
				return LazyCharAction.getInstance();
			}
		};
		this.resolvers = c -> {
			switch(c){
			case '\\' : return cr;
			case '\'' : return sq_crd.getResolver();
			case '"' :  return dq_crd.getResolver();
			default : return State.keepState(DataCollectionAction.getInstance());
			}
		};
	}
	
	@Override
	public CharResolverSet resolvers() {
		return resolvers;
	}
	
	
	protected abstract CharResolver stringEndResolver();
	
	protected static class CharResolverContainer{
		private CharResolver cr = State.keepState(DataCollectionAction.getInstance());
		public CharResolverContainer() {
		}
		public CharResolver getResolver() {
			return cr;
		}
		public void setResolver(CharResolver cr){
			this.cr = cr;
		}
	}

}