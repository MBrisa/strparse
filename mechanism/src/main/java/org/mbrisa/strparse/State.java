package org.mbrisa.strparse;

public abstract class State {

	private final Analyser analyser;
	
	
//	protected final CharResolverSet resolvers;
//	private CharAction ca;
	
//	protected char toAction;
	protected CharResolver cr;
	
	
	public State(Analyser analyser) {
		this.analyser = analyser;
//		this.resolvers = resolvers();
	}
	
	public CharAction action(char c){
//		this.toAction = c;
		this.cr = this.resolvers().getResolver(c);
		State state = cr.resolveNextState();
		if(state != null){
			this.getAnalyser().setState(state);
		}
//		CharAction charAction = cr.resolveCharAction();
//		lastCharAction = charAction;
		return cr.resolveCharAction();
	}
	
	public BuilderStack getBuilderStack(){
		return this.getAnalyser().getBuilderStack();
	}
	
	protected static CharResolver keepState(CharAction ca){
//		TODO not to new
		return new CharResolver() {
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
	
	public abstract CharResolverSet resolvers();
	
	public Analyser getAnalyser() {
		return analyser;
	}

	/**
	 * TODO to inline in State
	 * @author HipiaoMay
	 *
	 */
	protected static interface CharResolverSet {
		/**
		 * 根据指定的字符返回对应的 CharResolver ，如果参数不属于 State 的操作范围则返回 null
		 * @param c
		 * @return 
		 */
		CharResolver getResolver(char c);
	}
	
	protected static interface CharResolver {
		CharAction resolveCharAction();
		State resolveNextState();
	}
	
}
