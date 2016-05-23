package org.mbrisa.strparse;

public abstract class State {

	private BuilderStack builderStack;
	

	public State() {
	}
	
	
	public BuilderStack getBuilderStack(){
		return builderStack;
	}
	
	public void setBuilderStack(BuilderStack builderStack) {
		this.builderStack = builderStack;
	}
	
	
	/**
	 * 根据指定的字符返回对应的 CharResolver ，如果参数不属于 State 的操作范围则返回 null
	 * @param c
	 * @return
	 */
	public abstract StateAction action(char c);
	
	
	protected static StateAction keepState(CharAction ca){
//		TODO not to new
		return new StateAction() {
			@Override
			public State resolveNextState() {
				return null;
			}
			@Override
			public CharAction resolveCharAction() {
				return ca;
			}
		};
	}

}
