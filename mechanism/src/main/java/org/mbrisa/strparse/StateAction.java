package org.mbrisa.strparse;

public interface StateAction {
	
	CharAction resolveCharAction();
	
	State resolveNextState();
}