package org.mbrisa.strparse.jsonparser.builder;

import java.util.Arrays;

import org.mbrisa.strparse.DataBuilder;
import org.mbrisa.strparse.InvalidStringException;

public class JavaIdentifyBuilder extends DataBuilder {
	
	private final String[] javaKeywords_ordered = {
			"abstract",
			"assert",
			"boolean",
			"break",
			"byte",
			"case",
			"catch",
			"char",
			"class",
			"const",
			"continue",
			"default",
			"do",
			"double",
			"else",
			"enum",
			"extends",
			"false", // Reserved word for literal value
			"final",
			"finally",
			"float",
			"for",
			"goto",
			"if",
			"implements",
			"import",
			"instanceof",
			"int",
			"interface",
			"long",
			"native",
			"new",
			"null",  // Reserved word for literal value
			"package",
			"private",
			"protected",
			"public",
			"return",
			"short",
			"static",
			"strictfp",
			"super",
			"switch",
			"synchronized",
			"this",
			"throw",
			"throws",
			"transient",
			"true", // Reserved word for literal value
			"try",
			"void",
			"volatile",
			"while"
	};
	private java.lang.StringBuilder builder = new java.lang.StringBuilder(); 
	private CharCheck cc;
	
	public JavaIdentifyBuilder() {
		cc = c -> {
			boolean invalid = Character.isJavaIdentifierStart(c);
			this.cc = cr -> {
				return Character.isJavaIdentifierPart(cr);
			};
			return invalid;
		};
	}
	
	@Override
	public void builder(Object ob) {
		char c = (char)ob;
		if(!cc.isValid(c)){
			throw new InvalidStringException();
		}
		this.builder.append(c);
	}
	
	@Override
	public Object complete() {
		String result = builder.toString();
		if(Arrays.binarySearch(javaKeywords_ordered, result) < 0){
			return result;
		}
		throw new InvalidStringException();
	}
	
	private interface CharCheck{
		boolean isValid(char c);
	}
	
	
}
