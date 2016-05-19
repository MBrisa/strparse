package org.mbrisa.strparse.common.ca;

import org.mbrisa.strparse.BuilderStack;
import org.mbrisa.strparse.CharAction;

public class LazyCharAction implements CharAction {
	
	private static final LazyCharAction INSTANCE = new LazyCharAction();
	
	private LazyCharAction() {
	}

	@Override
	public void action(BuilderStack bs,char c) {}

	public static final LazyCharAction getInstance(){
		return INSTANCE;
	}
}
