package org.mbrisa.strparse.jsonparser.state;

import org.mbrisa.strparse.CharAction;
import org.mbrisa.strparse.State;
import org.mbrisa.strparse.StateAction;
import org.mbrisa.strparse.common.ca.DataCollectionAction;
import org.mbrisa.strparse.common.ca.LazyCharAction;
import org.mbrisa.strparse.common.state.StringEscapeState;

public abstract class InStringState extends State {

	private final CharResolverContainer dq_crd = new CharResolverContainer();
	private final CharResolverContainer sq_crd = new CharResolverContainer();
	private final StateAction cr;

	public InStringState(boolean isDobuleQuote) {
		super();
		if(isDobuleQuote){
			dq_crd.setResolver(stringEndResolver());
		}else{
			sq_crd.setResolver(stringEndResolver());
		}
		cr = new StateAction() {
			@Override
			public State resolveNextState() {
				return new StringEscapeState(InStringState.this);
			}
			@Override
			public CharAction resolveCharAction() {
				return LazyCharAction.getInstance();
			}
		};
	}
	
	public StateAction action(char c){
		switch(c){
			case '\\' : return cr;
			case '\'' : return sq_crd.getResolver();
			case '"' :  return dq_crd.getResolver();
			default : return State.keepState(DataCollectionAction.getInstance());
		}
	}
	
	protected abstract StateAction stringEndResolver();
	
	protected static class CharResolverContainer{
		private StateAction cr = State.keepState(DataCollectionAction.getInstance());
		public CharResolverContainer() {
		}
		public StateAction getResolver() {
			return cr;
		}
		public void setResolver(StateAction cr){
			this.cr = cr;
		}
	}

}