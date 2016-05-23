package org.mbrisa.strparse.jsonparser.state;

import org.mbrisa.strparse.CharAction;
import org.mbrisa.strparse.StateAction;
import org.mbrisa.strparse.State;
import org.mbrisa.strparse.common.ca.DoCompleteAction;

public class StringOnMapValueState extends InStringState {
	
	private static final StringOnMapValueState SINGLE_QUOTE = new StringOnMapValueState(false);
	private static final StringOnMapValueState DOUBLE_QUOTE = new StringOnMapValueState(true);
	
	private StringOnMapValueState(boolean isDobuleQuote) {
		super(isDobuleQuote);
	}
	
	@Override
	protected StateAction stringEndResolver(){
		return new StateAction() {
			@Override
			public State resolveNextState() {
				return new MapValueOverState();
			}
			@Override
			public CharAction resolveCharAction() {
				return DoCompleteAction.getInstance();
			}
		};
	}
	
	public static final StringOnMapValueState getInstance(boolean isDobuleQuote){
		return isDobuleQuote ? DOUBLE_QUOTE :  SINGLE_QUOTE;
	}

}
