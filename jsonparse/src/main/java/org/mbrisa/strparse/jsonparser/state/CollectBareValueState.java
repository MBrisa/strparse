package org.mbrisa.strparse.jsonparser.state;

import org.mbrisa.strparse.CharAction;
import org.mbrisa.strparse.StateAction;
import org.mbrisa.strparse.State;
import org.mbrisa.strparse.common.ca.DataCollectionAction;
import org.mbrisa.strparse.common.ca.DoCompleteAction;

public abstract class CollectBareValueState extends State {

	
	public CollectBareValueState() {
		super();
	}
	
	@Override
	public StateAction action(char c){
		if(Character.isWhitespace(c)){
			return new StateAction() {
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
		StateAction cr = this.afterWhitespaceState().action(c);
		if(cr == null){
			return State.keepState(DataCollectionAction.getInstance());
		}
		return new StateAction() {
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
	}
	
	protected abstract State afterWhitespaceState();
	
}