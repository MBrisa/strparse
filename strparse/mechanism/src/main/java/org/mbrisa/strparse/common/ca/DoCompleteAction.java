package org.mbrisa.strparse.common.ca;

import org.mbrisa.strparse.BuilderStack;
import org.mbrisa.strparse.CharAction;
import org.mbrisa.strparse.DataBuilder;

public class DoCompleteAction implements CharAction {

	private static final DoCompleteAction INSTANCE = new DoCompleteAction();
	
	private DoCompleteAction() {
	}
	
	@Override
	public void action(BuilderStack bs,char c) {
		DataBuilder builder = bs.pop();
		Object br = builder.complete();
		bs.peek().builder(br);
	}

	public static final DoCompleteAction getInstance(){
		return INSTANCE;
	}
}
