package org.mbrisa.strparse.common.state;

import org.mbrisa.strparse.StateAction;
import org.mbrisa.strparse.State;
import org.mbrisa.strparse.common.ca.LazyCharAction;

public abstract class IgnoreWhitespaceState extends State {

	public IgnoreWhitespaceState() {
		super();
	}

	@Override
	public StateAction action(char c) {
		if(Character.isWhitespace(c)){
			return State.keepState(LazyCharAction.getInstance());
		}
		return this.actionWithoutWhitespace(c);
	}
	
	protected abstract StateAction actionWithoutWhitespace(char c);
	
}
