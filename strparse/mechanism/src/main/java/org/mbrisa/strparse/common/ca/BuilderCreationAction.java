package org.mbrisa.strparse.common.ca;

import org.mbrisa.strparse.BuilderStack;
import org.mbrisa.strparse.CharAction;
import org.mbrisa.strparse.DataBuilder;

public abstract class BuilderCreationAction implements CharAction {

	@Override
	public void action(BuilderStack bs,char c) {
		bs.push(createBuilder());
	}
	
	protected abstract DataBuilder createBuilder();

}
