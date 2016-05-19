package org.mbrisa.strparse;

public abstract class DataBuilder {
	
	private DataBuilder ldb;
	
	
	public abstract void builder(Object ob) ;

	public abstract Object complete();
	
	void setLastBuilder(DataBuilder ldb){
		this.ldb = ldb;
	}
	
	public DataBuilder getLastBuilder(){
		return this.ldb;
	}
}
