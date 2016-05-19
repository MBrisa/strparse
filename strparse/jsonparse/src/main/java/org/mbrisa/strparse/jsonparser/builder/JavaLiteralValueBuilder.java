package org.mbrisa.strparse.jsonparser.builder;

import org.mbrisa.strparse.DataBuilder;
import org.mbrisa.strparse.InvalidStringException;

public class JavaLiteralValueBuilder extends DataBuilder {
	
	private CharCheck cc;
	
	public JavaLiteralValueBuilder() {
		cc = new LiteralCheckResovler();
	}
	
	@Override
	public void builder(Object ob) {
		cc.validate((char)ob);
	}
	
	@Override
	public Object complete() {
		return cc.complete();
	}
	
	private static interface CharCheck{
		void validate(char c);
		Object complete();
	}
	
	private class LiteralCheckResovler implements CharCheck{
		@Override
		public void validate(char c) {
			CharCheck literalCheck;
			switch(c){
			case 't' :
				literalCheck = new LiteralTrue();
				break;
			case 'f' :
				literalCheck = new LiteralFalse();
				break;
			case 'n' : 
				literalCheck = new LiteralNull();
				break;
			default :
				throw new InvalidStringException();
			}
			JavaLiteralValueBuilder.this.cc = literalCheck;
		}
		@Override
		public Object complete() {
			throw new InvalidStringException();
		}
	}
	
	
	private abstract static class LiteralCheck implements CharCheck{
		private final char[] literalCharWithoutHead;
		private int index = 0;
		
		public LiteralCheck() {
			literalCharWithoutHead = literalCharWithoutHead();
		}
		
		@Override
		public void validate(char c) {
			if(literalCharWithoutHead[index++] != c)
				throw new InvalidStringException();
		}
		
		@Override
		public Object complete(){
			if(this.isComplete()){
				return this.literalValue();
			}
			throw new InvalidStringException();
		}
		
		protected abstract Object literalValue();
		
		private boolean isComplete(){
			return index == literalCharWithoutHead.length;
		}
		
		protected abstract char[] literalCharWithoutHead();
	}
	
	private static class LiteralTrue extends LiteralCheck{
		@Override
		protected char[] literalCharWithoutHead() {
			return new char[]{'r','u','e'};
		}
		
		@Override
		protected Object literalValue() {
			return true;
		}
	}
	
	private static class LiteralFalse extends LiteralCheck{
		@Override
		protected char[] literalCharWithoutHead() {
			return new char[]{'a','l','s','e'};
		}
		@Override
		protected Object literalValue() {
			return false;
		}
	}
	
	private static class LiteralNull extends LiteralCheck{
		@Override
		protected char[] literalCharWithoutHead() {
			return new char[]{'u','l','l'};
		}
		@Override
		protected Object literalValue() {
			return null;
		}
	}
	
	
}
