package org.mbrisa.strparse.common.ca;

import org.mbrisa.strparse.BuilderStack;
import org.mbrisa.strparse.CharAction;

public class DataCollectionAction implements CharAction {
	
	private static final DataCollectionAction INSTANCE = new DataCollectionAction();
	
	private DataCollectionAction() {
	}

	@Override
	public void action(BuilderStack bs,char c) {
		bs.peek().builder(c);
	}

	public static DataCollectionAction getInstance() {
		return INSTANCE;
	}

}
