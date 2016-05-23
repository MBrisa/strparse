package org.mbrisa.strparse.jsonparser.state;

import org.mbrisa.strparse.CharAction;
import org.mbrisa.strparse.StateAction;
import org.mbrisa.strparse.State;
import org.mbrisa.strparse.common.ca.DoCompleteAction;

public class StringOnListElementState extends InStringState {
	
	private static final StringOnListElementState SINGLE_QUOTE = new StringOnListElementState(false);
	private static final StringOnListElementState DOUBLE_QUOTE = new StringOnListElementState(true);
	
	private StringOnListElementState(boolean isDobuleQuote) {
		super(isDobuleQuote);
	}
	
	@Override
	protected StateAction stringEndResolver(){
		return new StateAction() {
			@Override
			public State resolveNextState() {
				return new ListElementOverState();
			}
			@Override
			public CharAction resolveCharAction() {
				return DoCompleteAction.getInstance();
			}
		};
	}
	
	public static StringOnListElementState getInstance(boolean isDobuleQuote){
		return isDobuleQuote ? DOUBLE_QUOTE : SINGLE_QUOTE;
	}
	
}
