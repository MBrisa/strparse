package org.mbrisa.strparse.jsonparser.state;

import org.mbrisa.strparse.CharAction;
import org.mbrisa.strparse.StateAction;
import org.mbrisa.strparse.State;
import org.mbrisa.strparse.common.ca.DoCompleteAction;

public class StringOnMapKeyState extends InStringState {
	
	private static final StringOnMapKeyState SINGLE_QUOTE = new StringOnMapKeyState(false);
	private static final StringOnMapKeyState DOUBLE_QUOTE = new StringOnMapKeyState(true);
	
	private StringOnMapKeyState(boolean isDobuleQuote) {
		super(isDobuleQuote);
	}
	
	@Override
	protected StateAction stringEndResolver(){
		return new StateAction() {
			@Override
			public State resolveNextState() {
				return new MapKeyOverState();
			}
			@Override
			public CharAction resolveCharAction() {
				return DoCompleteAction.getInstance();
			}
		};
	}
	
	public static StringOnMapKeyState getInstance(boolean isDobuleQuote){
		return isDobuleQuote ? DOUBLE_QUOTE : SINGLE_QUOTE;
	}
	
}
