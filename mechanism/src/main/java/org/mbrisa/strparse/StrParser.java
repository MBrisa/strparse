package org.mbrisa.strparse;

public class StrParser {

	private final String source;
	private final char[] s_chars;
	private final Analyser analyser;
	
	
	public StrParser(String s,Analyser analyser) {
		this.source = s;
		this.s_chars = this.source.toCharArray();
		this.analyser = analyser;
	}
	
	
	public Object parse(){
		for(char c : s_chars){
			analyser.analyse(c);
		}
		return analyser.complete();
	}

}