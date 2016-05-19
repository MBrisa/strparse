package org.mbrisa.strparse.common.builder;

import org.mbrisa.strparse.DataBuilder;
import org.mbrisa.strparse.InvalidStringException;

public class NumberBuilder extends DataBuilder {
	
	private Integer[] na = new Integer[58];
	private boolean isNegative = false;
	private int decimalPlaces = 1;
	private long l_result;
	private double d_result;
	
	private NBState state;
	
	public NumberBuilder() {
		this.state = new DefaultNBState();
		int n = 0;
		for(int i = 48; i < 58;i++){
			na[i] = n++;
		}
	}

	@Override
	public void builder(Object ob) {
		state.doBuilder((char)ob);
	}

	@Override
	public Object complete() {
		return this.state.complete();
	}
	
	private interface NBState{
		void doBuilder(char c);
		Number complete();
	}
	
	private class DefaultNBState implements NBState{
		@Override
		public void doBuilder(char c) {
			NumberBuilder.this.state = new IntegerState();
			if(c == '+'){
				return;
			}
			if(c == '-'){
				NumberBuilder.this.isNegative = true;
				return;
			}
			NumberBuilder.this.state.doBuilder(c);
		}
		@Override
		public Number complete() {
			throw new InvalidStringException();
		}
	}
	
	private abstract class NumberState implements NBState{
		@Override
		public void doBuilder(char c) {
			Integer i = na[c];
			if(i == null){
				throw new InvalidStringException();
			}
			doHandler(i);
		}
		
		protected abstract void doHandler(int i);
	}
	
	private class IntegerState extends NumberState{
		
		@Override
		public void doBuilder(char c) {
			if(c == '.'){
				NumberBuilder.this.d_result = NumberBuilder.this.l_result;
				NumberBuilder.this.state = new DecimalState();
				return;
			}
			super.doBuilder(c);
		}
		
		@Override
		protected void doHandler(int i) {
			NumberBuilder.this.l_result = NumberBuilder.this.l_result * 10 + i;
		}
		
		@Override
		public Number complete() {
			long r = isNegative ? (0-l_result) : l_result;
			if(r > Integer.MAX_VALUE || r < Integer.MIN_VALUE){
				return r;
			}
			return new Long(r).intValue();
		}
	}
	
	private class DecimalState extends NumberState{
		
		@Override
		protected void doHandler(int i) {
			NumberBuilder.this.d_result = NumberBuilder.this.d_result + (i / Math.pow(10,decimalPlaces));
			decimalPlaces++;
		}
		
		@Override
		public Number complete() {
			return isNegative ? (0-d_result) : d_result;
		}
	}
	
}
