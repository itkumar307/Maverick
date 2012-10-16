package com.maveric.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;




public class SUtil {
public static boolean hasRegularExpressionMatch(String regularExpression, String value){
		if(value == null || regularExpression == null){
	    	
			return false;
		}
	    Pattern p;
		try
		{
		    p = Pattern.compile (regularExpression);
		}
		catch (PatternSyntaxException e)
		{
		    
		    return false;
		} 
	    Matcher m = p.matcher (value);
	    return m.find ();		
	}
}