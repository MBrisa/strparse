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
		setCurrentState(initState());
	}
	
	public void analyse(char c){
		StateAction cr = this.getCurrentState().action(c);
		State state = cr.resolveNextState();
		if(state != null){
			state.setBuilderStack(this.bs);
			this.setCurrentState(state);
		}
		CharAction ca = cr.resolveCharAction();
		ca.action(this.getBuilderStack(),c);
	}
	
	/**
	 * @return the currentState
	 */
	public State getCurrentState() {
		return currentState;
	}

	/**
	 * @param currentState the currentState to set
	 */
	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public Object complete(){
		DataBuilder builder = bs.pop();
		if(!bs.isEmpty() || builder != globalBuilder){
			throw new InvalidStringException();
		}
		return builder.complete();
	}
	
	/**
	 * @return the BuilderStack
	 */
	public BuilderStack getBuilderStack() {
		return bs;
	}
	
	protected abstract State initState();
	

}
