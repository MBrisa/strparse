package org.mbrisa.strparse;

public abstract class Analyser {
	
	private final DataBuilder globalBuilder = new DataBuilder() {
		private Object ob;
		@Override
		public Object complete() {
			return ob;
		}
		@Override
		public void builder(Object ob) {
			this.ob = ob;
		}
	};
	
	private State currentState;
	private final BuilderStack bs;
	

	public Analyser() {
		bs = new BuilderStack();
		bs.push(globalBuilder);
		currentState = initState();
	}
	
	public void analyse(char c){
		CharAction ca = this.currentState.action(c);
		ca.action(this.getBuilderStack(),c);
	}
	
	public Object complete(){
		DataBuilder builder = bs.pop();
		if(!bs.isEmpty() || builder != globalBuilder){
			throw new InvalidStringException();
		}
		return builder.complete();
	}
	
	public void setState(State state){
		this.currentState = state;
	}
	
	/**
	 * @return the BuilderStack
	 */
	public BuilderStack getBuilderStack() {
		return bs;
	}
	
	protected abstract State initState();
	

}
