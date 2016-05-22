package org.mbrisa.strparse;

import java.util.LinkedList;

public class BuilderStack {

	
	private final LinkedList<DataBuilder> builderStack = new LinkedList<>();
	
	public void push(DataBuilder builder){
		DataBuilder ldb = builderStack.peek();
		builder.setLastBuilder(ldb);
		builderStack.push(builder);
	}
	
	public DataBuilder pop(){
		DataBuilder db = builderStack.pop();
		db.setLastBuilder(null);
		return db;
	}
	
	public boolean isEmpty(){
		return builderStack.isEmpty();
	}
	
	
	public DataBuilder peek(){
		return builderStack.peek();
	}

}
