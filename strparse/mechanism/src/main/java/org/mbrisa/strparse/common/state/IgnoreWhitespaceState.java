package org.mbrisa.strparse.common.state;

import org.mbrisa.strparse.Analyser;
import org.mbrisa.strparse.CharAction;
import org.mbrisa.strparse.State;
import org.mbrisa.strparse.common.ca.LazyCharAction;

public abstract class IgnoreWhitespaceState extends State {

	public IgnoreWhitespaceState(Analyser analyser) {
		super(analyser);
	}

	@Override
	public CharAction action(char c) {
		if(Character.isWhitespace(c)){
			return LazyCharAction.getInstance();
		}
		return super.action(c);
	}
	
}
