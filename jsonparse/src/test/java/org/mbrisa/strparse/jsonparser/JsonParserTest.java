package org.mbrisa.strparse.jsonparser;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.mbrisa.strparse.StrParser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonParserTest {
	
	public static void main(String[] args) {
		
	/*case '"' : 
		return charResolverOnString(true);
	case '\'' : 
		return charResolverOnString(false);
	case '{' :  
		return new StateAction() {
			@Override
			public State resolveNextState() {
				return new MapKeyBeginState();
			}
			@Override
			public CharAction resolveCharAction() {
				return MapCreation.getInstance();
			}
		};
	case '[' : // list as element
		return State.keepState(ListCreation.getInstance());
	case ']' : //empty list
		return new StateAction() {
			@Override
			public State resolveNextState() {
				return new ListOverState();
			}
			@Override
			public CharAction resolveCharAction() {
				return DoCompleteAction.getInstance();
			}
		};
	default :
		BuilderCreationAction bca;
		if(c == '-' || c == '+' || (c >= '0' && c <= '9')){*/
		
		
		char[] ca = {'\'','"','\\','[',']','{','}',':',',','-','+','0','9'};
		for(char c : ca){
			System.out.println(c+" > "+(int)c);
		}
		
//		String s; 
//		StrParser parser;
//		Map<?,?> result;
//		s = "{name:'hello'}";
//		parser = new StrParser(s, new JsonAnalyser());
//		result = (Map<?,?>)parser.parse();
//		assertEquals(1,result.size());
//		assertEquals("hello",result.get("name"));
	}
	
	@Test
	public void mapStringOnly(){
		String s; 
		StrParser parser;
		Map<?,?> result;
		
		s = "{\"name\":\"hello\"}";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(1,result.size());
		assertEquals("hello",result.get("name"));
		
		s = " { \"name\" : \"hello\" } ";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(1,result.size());
		assertEquals("hello",result.get("name"));
		
		s = " { \"name\" : \"hello\" , \"age\" : \"20\" } ";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(2,result.size());
		assertEquals("hello",result.get("name"));
		assertEquals("20",result.get("age"));
		
		s = " { \"name\" : \"hello\", \"parent\" : { \"father name\" : \"say hello\" , \"age\" : \"50\" } , \"age\" : \"20\" } ";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(3,result.size());
		assertEquals("hello",result.get("name"));
		assertEquals("20",result.get("age"));
		Map<?,?> parent = (Map<?,?>)result.get("parent");
		assertEquals("say hello",parent.get("father name"));
		assertEquals("50",parent.get("age"));
		
		
		s = " { \"name\" : \"hello\", \"parent\" : { \"father\" : { \"name\" : \"say hello\" , \"age\" : \"50\" } } , \"age\" : \"20\" } ";

		Map<?,?> father;
		
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(3,result.size());
		assertEquals("hello",result.get("name"));
		assertEquals("20",result.get("age"));
		parent = (Map<?,?>)result.get("parent");
		assertEquals(1,parent.size());
		father = (Map<?,?>)parent.get("father");
		assertEquals(2,father.size());
		assertEquals("say hello",father.get("name"));
		assertEquals("50",father.get("age"));
		
		s = " { \"name\" : \"hello\", \"parent\" : { \"father\" : { \"name\" : \"say hello\" , \"age\" : \"50\" },\"mother\" : { \"name\" : \"good\" , \"age\" : \"49\" } } , \"age\" : \"20\" } ";

		Map<?,?> mother;
		
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(3,result.size());
		assertEquals("hello",result.get("name"));
		assertEquals("20",result.get("age"));
		parent = (Map<?,?>)result.get("parent");
		assertEquals(2,parent.size());
		father = (Map<?,?>)parent.get("father");
		assertEquals(2,father.size());
		assertEquals("say hello",father.get("name"));
		assertEquals("50",father.get("age"));
		mother = (Map<?,?>)parent.get("mother");
		assertEquals(2,mother.size());
		assertEquals("good",mother.get("name"));
		assertEquals("49",mother.get("age"));
		
		
		s = "{\"person\" : {\"country\" : {\"name\":\"china\"} }}";
		Map<?,?> person;
		Map<?,?> country;
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(1,result.size());
		person = (Map<?,?>)result.get("person");
		assertEquals(1,person.size());
		country = (Map<?,?>)person.get("country");
		assertEquals(1,country.size());
		assertEquals("china",country.get("name"));
		
		
		s = "{'name':\"hello\"}";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(1,result.size());
		assertEquals("hello",result.get("name"));
		
		s = "{ 'name' : \"hello\" }";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(1,result.size());
		assertEquals("hello",result.get("name"));
		
		s = "{'name':'hello'}";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(1,result.size());
		assertEquals("hello",result.get("name"));
		
		s = "{ 'name' : 'hello' }";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(1,result.size());
		assertEquals("hello",result.get("name"));
		
		s = "{ \"name\" : 'hello' }";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(1,result.size());
		assertEquals("hello",result.get("name"));
		
		s = "{ \"\\\"name\" : '\\\'hello' }";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(1,result.size());
		assertEquals("'hello",result.get("\"name"));
		
//		s = "{ \"\\name\" : '\\\'hello' }";
//		System.out.println(s);
//		parser = new StrParser(s, new JsonAnalyser());
//		result = (Map<?,?>)parser.parse();
//		assertEquals(1,result.size());
//		assertEquals("'hello",result.get("\name"));
		
		s = "{name:'hello'}";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(1,result.size());
		assertEquals("hello",result.get("name"));
		
		s = "{ name : 'hello' }";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(1,result.size());
		assertEquals("hello",result.get("name"));
		
		s = "{ name : 'hel\"lo' }";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(1,result.size());
		assertEquals("hel\"lo",result.get("name"));
	}
	
	@Test
	public void mapNumberValueTest(){
		String s; 
		StrParser parser;
		Map<?,?> result;
		
		s = " { \"age\" : 20} ";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(1,result.size());
		assertEquals(20,result.get("age"));
		
		s = " { \"age\" : 20 } ";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(1,result.size());
		assertEquals(20,result.get("age"));
		s = " { \"age\" : 20 , \"name\" : \"hello\" } ";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(2,result.size());
		assertEquals("hello",result.get("name"));
		assertEquals(20,result.get("age"));
		
		s = " {  \"age\" : 20, \"parent\" : { \"father name\" : \"say hello\" , \"age\" : 50 } ,\"name\" : \"hello\" } ";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(3,result.size());
		assertEquals("hello",result.get("name"));
		assertEquals(20,result.get("age"));
		Map<?,?> parent = (Map<?,?>)result.get("parent");
		assertEquals("say hello",parent.get("father name"));
		assertEquals(50,parent.get("age"));
		
		
		s = "{\"person\" : {\"country\" : {\"anima\":20000000000} }}";
		Map<?,?> person;
		Map<?,?> country;
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(1,result.size());
		person = (Map<?,?>)result.get("person");
		assertEquals(1,person.size());
		country = (Map<?,?>)person.get("country");
		assertEquals(1,country.size());
		assertEquals(20000000000L,country.get("anima"));
	}
	
	@Test
	public void mapNumberKeyTest(){
		String s; 
		StrParser parser;
		Map<?,?> result;
		
		s = " {20:\"20\"} ";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(1,result.size());
		assertEquals("20",result.get(20));
		
		
		s = " { 20  : \"20\" } ";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(1,result.size());
		assertEquals("20",result.get(20));
		
		Map<?,?> ten;
		s = " { 20 : {10 : \"minute\"},1 : \"hour\" } ";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(2,result.size());
		ten = (Map<?,?>)result.get(20);
		assertEquals(1,ten.size());
		assertEquals("minute",ten.get(10));
		assertEquals("hour",result.get(1));
	}
	
	@Test
	public void mapKeyIsJavaIdentify(){
		String s; 
		StrParser parser;
		Map<?,?> result;
		
		s = " {age: \"20\"} ";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(1,result.size());
		assertEquals("20",result.get("age"));
		
		Map<?,?> nameMap;
		s = " { age  : \"20\" , name : {firstName : \"json\",lastName : \"nuknow\"}} ";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(2,result.size());
		assertEquals("20",result.get("age"));
		nameMap = (Map<?,?>)result.get("name");
		assertEquals(2,nameMap.size());
		assertEquals("json",nameMap.get("firstName"));
		assertEquals("nuknow",nameMap.get("lastName"));
	}
	
	@Test
	public void mapValueIsList(){
		String s; 
		StrParser parser;
		Map<?,?> result;
		List<?> list;
		
		s = " {address:[\"ad\",\"bd\",\"cd\"]} ";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(1,result.size());
		list = (List<?>)result.get("address");
		
		assertEquals(3,list.size());
		assertEquals("ad",list.get(0));
		assertEquals("bd",list.get(1));
		assertEquals("cd",list.get(2));
		
		s = " {address:[12,13,14]} ";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(1,result.size());
		list = (List<?>)result.get("address");
		
		assertEquals(3,list.size());
		assertEquals(12,list.get(0));
		assertEquals(13,list.get(1));
		assertEquals(14,list.get(2));
		
		
		s = " {address:[ 12  ,13 , 14 ]} ";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(1,result.size());
		list = (List<?>)result.get("address");
		
		assertEquals(3,list.size());
		assertEquals(12,list.get(0));
		assertEquals(13,list.get(1));
		assertEquals(14,list.get(2));
		
		s = " {address:[ \"12\"  ,13 , 14 ]} ";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(1,result.size());
		list = (List<?>)result.get("address");
		
		assertEquals(3,list.size());
		assertEquals("12",list.get(0));
		assertEquals(13,list.get(1));
		assertEquals(14,list.get(2));
		
		s = " {address:[ 12  ,\"13\" , 14 ]} ";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(1,result.size());
		list = (List<?>)result.get("address");
		
		assertEquals(3,list.size());
		assertEquals(12,list.get(0));
		assertEquals("13",list.get(1));
		assertEquals(14,list.get(2));
		
		s = " {address:[ 12  ,13 , \"14\" ]} ";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		assertEquals(1,result.size());
		list = (List<?>)result.get("address");
		
		assertEquals(3,list.size());
		assertEquals(12,list.get(0));
		assertEquals(13,list.get(1));
		assertEquals("14",list.get(2));
		
		Map<?,?> person;
		
		
		s = "{person : {address:[ 12  ,13 , \"14\" ]} }";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		
		assertEquals(1,result.size());
		person = (Map<?,?>)result.get("person");
		list = (List<?>)person.get("address");
		assertEquals(3,list.size());
		assertEquals(12,list.get(0));
		assertEquals(13,list.get(1));
		assertEquals("14",list.get(2));
		
		s = "{person : {address:[ 12  ,13 , \"14\" ],name:\"json\"} }";
		parser = new StrParser(s, new JsonAnalyser());
		result = (Map<?,?>)parser.parse();
		
		assertEquals(1,result.size());
		person = (Map<?,?>)result.get("person");
		assertEquals("json",person.get("name"));
		list = (List<?>)person.get("address");
		assertEquals(3,list.size());
		assertEquals(12,list.get(0));
		assertEquals(13,list.get(1));
		assertEquals("14",list.get(2));
	}
	
	@Test
	public void simpleArray(){
		String s; 
		StrParser parser;
		List<?> list;
		
		s = " [\"ad\"] ";
		parser = new StrParser(s, new JsonAnalyser());
		list = (List<?>)parser.parse();
		assertEquals(1,list.size());
		assertEquals("ad",list.get(0));
		
		s = " [\"ad\",\"bd\",\"cd\"] ";
		parser = new StrParser(s, new JsonAnalyser());
		list = (List<?>)parser.parse();
		assertEquals(3,list.size());
		assertEquals("ad",list.get(0));
		assertEquals("bd",list.get(1));
		assertEquals("cd",list.get(2));
		
		s = "[ \"ad\" , \"bd\"  , \"cd\" ]";
		parser = new StrParser(s, new JsonAnalyser());
		list = (List<?>)parser.parse();
		assertEquals(3,list.size());
		assertEquals("ad",list.get(0));
		assertEquals("bd",list.get(1));
		assertEquals("cd",list.get(2));
		
		s = "[0]";
		parser = new StrParser(s, new JsonAnalyser());
		list = (List<?>)parser.parse();
		assertEquals(1,list.size());
		assertEquals(0,list.get(0));
		
		s = "[0,1,2]";
		parser = new StrParser(s, new JsonAnalyser());
		list = (List<?>)parser.parse();
		assertEquals(3,list.size());
		assertEquals(0,list.get(0));
		assertEquals(1,list.get(1));
		assertEquals(2,list.get(2));
		
		
		s = " [ 0 , 1  , 2 ] ";
		parser = new StrParser(s, new JsonAnalyser());
		list = (List<?>)parser.parse();
		assertEquals(3,list.size());
		assertEquals(0,list.get(0));
		assertEquals(1,list.get(1));
		assertEquals(2,list.get(2));
		
		s = "['0']";
		parser = new StrParser(s, new JsonAnalyser());
		list = (List<?>)parser.parse();
		assertEquals(1,list.size());
		assertEquals("0",list.get(0));
		
		s = "['0','1','2']";
		parser = new StrParser(s, new JsonAnalyser());
		list = (List<?>)parser.parse();
		assertEquals(3,list.size());
		assertEquals("0",list.get(0));
		assertEquals("1",list.get(1));
		assertEquals("2",list.get(2));
		
		
		s = " [ '0' , '1'  , '2' ] ";
		parser = new StrParser(s, new JsonAnalyser());
		list = (List<?>)parser.parse();
		assertEquals(3,list.size());
		assertEquals("0",list.get(0));
		assertEquals("1",list.get(1));
		assertEquals("2",list.get(2));
		
		s = " [ 0 , '1'  , \"2\" ] ";
		parser = new StrParser(s, new JsonAnalyser());
		list = (List<?>)parser.parse();
		assertEquals(3,list.size());
		assertEquals(0,list.get(0));
		assertEquals("1",list.get(1));
		assertEquals("2",list.get(2));
	}
	
	@Test
	public void compositeObjectInList(){
		String s; 
		StrParser parser;
		Map<?,?> map;
		List<?> list;
		
		s = "[{\"ad\":20},{\"bd\":21,name:'json'},{\"cd\":22}]";
		parser = new StrParser(s, new JsonAnalyser());
		list = (List<?>)parser.parse();
		assertEquals(3,list.size());
		map = (Map<?,?>)list.get(0);
		assertEquals(1,map.size());
		assertEquals(20,map.get("ad"));
		map = (Map<?,?>)list.get(1);
		assertEquals(2,map.size());
		assertEquals(21,map.get("bd"));
		assertEquals("json",map.get("name"));
		map = (Map<?,?>)list.get(2);
		assertEquals(1,map.size());
		assertEquals(22,map.get("cd"));
		
		s = " [ { \"ad\" : 20 } ,  {  \"bd\"  :  21  ,  name  :  'json'  }  , { \"cd\" : 22 } ] ";
		parser = new StrParser(s, new JsonAnalyser());
		list = (List<?>)parser.parse();
		assertEquals(3,list.size());
		map = (Map<?,?>)list.get(0);
		assertEquals(1,map.size());
		assertEquals(20,map.get("ad"));
		map = (Map<?,?>)list.get(1);
		assertEquals(2,map.size());
		assertEquals(21,map.get("bd"));
		assertEquals("json",map.get("name"));
		map = (Map<?,?>)list.get(2);
		assertEquals(1,map.size());
		assertEquals(22,map.get("cd"));
		
		List<?> list2;
		Map<?,?> map2;
		s = "[[12,{name:'hello'}],{\"bd\":21,name:'json'},[\"cd\",22]]";
		parser = new StrParser(s, new JsonAnalyser());
		list = (List<?>)parser.parse();
		assertEquals(3,list.size());
		list2 = (List<?>)list.get(0);
		assertEquals(2,list2.size());
		assertEquals(12,list2.get(0));
		map2 = (Map<?,?>)list2.get(1);
		assertEquals(1,map2.size());
		assertEquals("hello",map2.get("name"));
		
		map = (Map<?,?>)list.get(1);
		assertEquals(2,map.size());
		assertEquals(21,map.get("bd"));
		assertEquals("json",map.get("name"));
		
		list2 = (List<?>)list.get(2);
		assertEquals(2,list2.size());
		assertEquals("cd",list2.get(0));
		assertEquals(22,list2.get(1));
	}
	
	@Test
	public void literalValueTest(){
		String s; 
		StrParser parser;
		Map<?,?> map;
		List<?> list;
		
		s = "{isSuccess:true}";
		parser = new StrParser(s, new JsonAnalyser());
		map = (Map<?,?>)parser.parse();
		assertEquals(1,map.size());
		assertEquals(true,map.get("isSuccess"));
		
		s = " { isSuccess : true , isError :  false  , name : null  } ";
		parser = new StrParser(s, new JsonAnalyser());
		map = (Map<?,?>)parser.parse();
		assertEquals(3,map.size());
		assertEquals(true,map.get("isSuccess"));
		assertEquals(false,map.get("isError"));
		assertEquals(null,map.get("name"));
		
		s = "[true,false,null]";
		parser = new StrParser(s, new JsonAnalyser());
		list = (List<?>)parser.parse();
		assertEquals(3,list.size());
		assertEquals(true,list.get(0));
		assertEquals(false,list.get(1));
		assertEquals(null,list.get(2));
		
		s = " [ true ,  false   , null   ] ";
		parser = new StrParser(s, new JsonAnalyser());
		list = (List<?>)parser.parse();
		assertEquals(3,list.size());
		assertEquals(true,list.get(0));
		assertEquals(false,list.get(1));
		assertEquals(null,list.get(2));
		
		s = " {isSuccess:true,state:[0,true,null,false,10]} ";
		parser = new StrParser(s, new JsonAnalyser());
		map = (Map<?,?>)parser.parse();
		assertEquals(2,map.size());
		assertEquals(true,map.get("isSuccess"));
		
		list = (List<?>)map.get("state");
		assertEquals(5,list.size());
		assertEquals(0,list.get(0));
		assertEquals(true,list.get(1));
		assertEquals(null,list.get(2));
		assertEquals(false,list.get(3));
		assertEquals(10,list.get(4));
	}
	
	@Test
	public void keyIsCompositeObject(){
		String s; 
		StrParser parser;
		Map<?,?> map;
		List<?> listOnValue;
		List<?> listOnKey;
		Map<?,?> key;
		Map<?,?> value;
		
		s = "{{name:'hello',age:20}:{'address':[1,2,3]}}";
		parser = new StrParser(s, new JsonAnalyser());
		map = (Map<?,?>)parser.parse();
		assertEquals(1,map.size());
		for(Entry<?, ?> entry : map.entrySet()){
			key = (Map<?,?>)entry.getKey();
			assertEquals(2,key.size());
			assertEquals("hello",key.get("name"));
			assertEquals(20,key.get("age"));
			value = (Map<?,?>)entry.getValue();
			assertEquals(1,value.size());
			listOnValue = (List<?>)value.get("address");
			assertEquals(3,listOnValue.size());
			assertEquals(1,listOnValue.get(0));
			assertEquals(2,listOnValue.get(1));
			assertEquals(3,listOnValue.get(2));
		}
		
		s = " { { name : 'hello' , age: 20  } : { 'address' : [ 1 , 2 , 3  ] } } ";
		parser = new StrParser(s, new JsonAnalyser());
		map = (Map<?,?>)parser.parse();
		assertEquals(1,map.size());
		for(Entry<?, ?> entry : map.entrySet()){
			key = (Map<?,?>)entry.getKey();
			assertEquals(2,key.size());
			assertEquals("hello",key.get("name"));
			assertEquals(20,key.get("age"));
			value = (Map<?,?>)entry.getValue();
			assertEquals(1,value.size());
			listOnValue = (List<?>)value.get("address");
			assertEquals(3,listOnValue.size());
			assertEquals(1,listOnValue.get(0));
			assertEquals(2,listOnValue.get(1));
			assertEquals(3,listOnValue.get(2));
		}
		
		s = "{[1,\"2\",'3']:{'address':[1,2,3]}}";
		parser = new StrParser(s, new JsonAnalyser());
		map = (Map<?,?>)parser.parse();
		assertEquals(1,map.size());
		for(Entry<?, ?> entry : map.entrySet()){
			listOnKey = (List<?>)entry.getKey();
			assertEquals(3,listOnKey.size());
			assertEquals(1,listOnKey.get(0));
			assertEquals("2",listOnKey.get(1));
			assertEquals("3",listOnKey.get(2));
			value = (Map<?,?>)entry.getValue();
			assertEquals(1,value.size());
			listOnValue = (List<?>)value.get("address");
			assertEquals(3,listOnValue.size());
			assertEquals(1,listOnValue.get(0));
			assertEquals(2,listOnValue.get(1));
			assertEquals(3,listOnValue.get(2));
		}
		
		s = " { [ 1  , \"2\" , '3' ]  : { 'address' : [ 1,  2,   3]}}";
		parser = new StrParser(s, new JsonAnalyser());
		map = (Map<?,?>)parser.parse();
		assertEquals(1,map.size());
		for(Entry<?, ?> entry : map.entrySet()){
			listOnKey = (List<?>)entry.getKey();
			assertEquals(3,listOnKey.size());
			assertEquals(1,listOnKey.get(0));
			assertEquals("2",listOnKey.get(1));
			assertEquals("3",listOnKey.get(2));
			value = (Map<?,?>)entry.getValue();
			assertEquals(1,value.size());
			listOnValue = (List<?>)value.get("address");
			assertEquals(3,listOnValue.size());
			assertEquals(1,listOnValue.get(0));
			assertEquals(2,listOnValue.get(1));
			assertEquals(3,listOnValue.get(2));
		}
	}
	
	@Test
	public void emptyTest(){
		String s; 
		StrParser parser;
		Map<?,?> map;
		List<?> list;
		
		s = "{\"\":\"\"}";
		parser = new StrParser(s, new JsonAnalyser());
		map = (Map<?,?>)parser.parse();
		assertEquals(1,map.size());
		assertEquals("",map.get(""));
		
		s = "{'':\"\"}";
		parser = new StrParser(s, new JsonAnalyser());
		map = (Map<?,?>)parser.parse();
		assertEquals(1,map.size());
		assertEquals("",map.get(""));
		
		s = "{}";
		parser = new StrParser(s, new JsonAnalyser());
		map = (Map<?,?>)parser.parse();
		assertEquals(0,map.size());
		
		s = "{   }";
		parser = new StrParser(s, new JsonAnalyser());
		map = (Map<?,?>)parser.parse();
		assertEquals(0,map.size());
		
		s = "[]";
		parser = new StrParser(s, new JsonAnalyser());
		list = (List<?>)parser.parse();
		assertEquals(0,list.size());
		
		s = "[   ]";
		parser = new StrParser(s, new JsonAnalyser());
		list = (List<?>)parser.parse();
		assertEquals(0,list.size());
	}
	
	@Test
	public void compositeTest() throws Exception{
		String s; 
		StrParser parser;
		Map<?,?> map;
		
		/*{
	        "_id" : {
	                "date" : "2015-04-29T16:00:00Z",
	                "uid" : "20150430132830717728986068582162"
	        },
	        "value" : {
	                "userSessions" : [
	                        {
	                                "sid" : "20150430132830717141250911797396",
	                                "userVisitCount" : 1,
	                                "logined" : false,
	                                "onlineTime" : 0,
	                                "ticket" : 0,
	                                "requests" : [
	                                        {
	                                                "id" : "5541bd3da31040b3ef698ac9",
	                                                "uri" : "/selectCinemaList",
	                                                "query" : {
	                                                        "cinemaId" : "1381"
	                                                },
	                                                "cookie" : {
	                                                        "JSESSIONID" : "549069E5B4617FAF983C2D958883CF53",
	                                                        "D2AD53AF8C4FA943F7C4C778FD729B5C" : "549069E5B4617FAF983C2D958883CF53",
	                                                        "U_cfdc200d54de43f697b6096e1f6bc064_DE" : {
	                                                                "key0" : "1381",
	                                                                "key2" : "0",
	                                                                "key1" : "0"
	                                                        }
	                                                },
	                                                "rtime" : NumberLong("1430371710717"),
	                                                "stime" : "2015-04-30T05:27:25Z",
	                                                "totalTime" : 0,
	                                                "referer" : "-",
	                                                "refererHostName" : "-",
	                                                "refererUri" : "-"
	                                        }
	                                ],
	                                "beginTime" : "2015-04-30T05:27:25Z",
	                                "endTime" : "2015-04-30T05:27:25Z",
	                                "cinemaArray" : [
	                                        "1381"
	                                ],
	                                "accountArray" : [ ],
	                                "pv" : 1
	                        }
	                ],
	                "loginedSession" : 0,
	                "onlineTime" : 0,
	                "ticket" : 0,
	                "sessionCount" : 1,
	                "cinemaArray" : [
	                        "1381"
	                ],
	                "accountArray" : [ ],
	                "pv" : 1,
	                "userVisitCount" : 1,
	                "clinetEnvironment" : {
	                        "browserName" : "QQ Browser Mobile",
	                        "browserVersion" : "5",
	                        "resolution" : "768*976",
	                        "osType" : null,
	                        "osVersion" : null,
	                        "isMobile" : false,
	                        "mobileDeviceModel" : null,
	                        "mobileDeviceFacturer" : null
	                }
	        }
	}*/
		
		s = 	"	{"+
				"	        \"_id\" : {"+
				"	                \"date\" : \"2015-04-29T16:00:00Z\","+
				"	                \"uid\" : \"20150430132830717728986068582162\""+
				"	        },"+
				"	        \"value\" : {"+
				"	                \"userSessions\" : ["+
				"	                        {"+
				"	                                \"sid\" : \"20150430132830717141250911797396\","+
				"	                                \"userVisitCount\" : 1,"+
				"	                                \"logined\" : false,"+
				"	                                \"onlineTime\" : 0,"+
				"	                                \"ticket\" : 0,"+
				"	                                \"requests\" : ["+
				"	                                        {"+
				"	                                                \"id\" : \"5541bd3da31040b3ef698ac9\","+
				"	                                                \"uri\" : \"/selectCinemaList\","+
				"	                                                \"query\" : {"+
				"	                                                        \"cinemaId\" : \"1381\""+
				"	                                                },"+
				"	                                                \"cookie\" : {"+
				"	                                                        \"JSESSIONID\" : \"549069E5B4617FAF983C2D958883CF53\","+
				"	                                                        \"D2AD53AF8C4FA943F7C4C778FD729B5C\" : \"549069E5B4617FAF983C2D958883CF53\","+
				"	                                                        \"U_cfdc200d54de43f697b6096e1f6bc064_DE\" : {"+
				"	                                                                \"key0\" : \"1381\","+
				"	                                                                \"key2\" : \"0\","+
				"	                                                                \"key1\" : \"0\""+
				"	                                                        }"+
				"	                                                },"+
				"	                                                \"rtime\" : \"1430371710717\","+
				"	                                                \"stime\" : \"2015-04-30T05:27:25Z\","+
				"	                                                \"totalTime\" : 0,"+
				"	                                                \"referer\" : \"-\","+
				"	                                                \"refererHostName\" : \"-\","+
				"	                                                \"refererUri\" : \"-\""+
				"	                                        }"+
				"	                                ],"+
				"	                                \"beginTime\" : \"2015-04-30T05:27:25Z\","+
				"	                                \"endTime\" : \"2015-04-30T05:27:25Z\","+
				"	                                \"cinemaArray\" : ["+
				"	                                        \"1381\""+
				"	                                ],"+
				"	                                \"accountArray\" : [ ],"+
				"	                                \"pv\" : 1"+
				"	                        }"+
				"	                ],"+
				"	                \"loginedSession\" : 0,"+
				"	                \"onlineTime\" : 0,"+
				"	                \"ticket\" : 0,"+
				"	                \"sessionCount\" : 1,"+
				"	                \"cinemaArray\" : ["+
				"	                        \"1381\""+
				"	                ],"+
				"	                \"accountArray\" : [ ],"+
				"	                \"pv\" : 1,"+
				"	                \"userVisitCount\" : 1,"+
				"	                \"clinetEnvironment\" : {"+
				"	                        \"browserName\" : \"QQ Browser Mobile\","+
				"	                        \"browserVersion\" : \"5\","+
				"	                        \"resolution\" : \"768*976\","+
				"	                        \"osType\" : null,"+
				"	                        \"osVersion\" : null,"+
				"	                        \"isMobile\" : false,"+
				"	                        \"mobileDeviceModel\" : null,"+
				"	                        \"mobileDeviceFacturer\" : null"+
				"	                }"+
				"	        }"+
				"	}";
		
		parser = new StrParser(s, new JsonAnalyser());
		map = (Map<?,?>)parser.parse();
		assertEquals(2,map.size());
		String parsed = map.toString();
		
		Map<?,?> alimap = convert((JSONObject)JSON.parse(s));
		assertEquals(2,alimap.size());
		assertEquals(alimap.toString(), parsed);
	}
	
	@SuppressWarnings("unchecked")
	private Map<Object,Object> convert(JSONObject jo)throws Exception{
		Field field = JSONObject.class.getDeclaredField("map");
		field.setAccessible(true);
		Map<Object,Object> result = (Map<Object,Object>)field.get(jo);
		for(Entry<Object,Object> entry : result.entrySet()){
			Object value = entry.getValue();
			if(value instanceof JSONObject){
				entry.setValue(convert((JSONObject)value));
			}else if(value instanceof JSONArray){
				entry.setValue(convert((JSONArray)value));
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private List<Object> convert(JSONArray ja)throws Exception{
		Field field = JSONArray.class.getDeclaredField("list");
		field.setAccessible(true);
		List<Object> result = new ArrayList<>();
		for(Object ob : (List<Object>)field.get(ja)){
			if(ob instanceof JSONObject){
				result.add(convert((JSONObject)ob));
			}else if(ob instanceof JSONArray){
				result.add(convert((JSONArray)ob));
			}else{
				result.add(ob);
			}
		}
		return result;
	}

}
