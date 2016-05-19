package org.mbrisa.strparse.jsonparser.builder;

import java.util.HashMap;
import java.util.Map;

import org.mbrisa.strparse.DataBuilder;

public class MapBuilder extends DataBuilder {
	
	private Map<Object,Object> map = new HashMap<>();
	private Object key = null;
	private KVProgress progress = KVProgress.EXCEPT_KEY;
	
	@Override
	public void builder(Object ob) {
		if(key == null){
			key = ob;
			progress = KVProgress.EXCEPT_VALUE;
			return;
		}
		progress = KVProgress.EXCEPT_KEY;
		map.put(key, ob);
		key = null;
	}

	@Override
	public Object complete() {
		return map;
	}
	
	public KVProgress getProgress(){
		return this.progress;
	}
	
	public static enum KVProgress{
		EXCEPT_KEY,EXCEPT_VALUE
	}

}
