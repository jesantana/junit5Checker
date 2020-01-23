package com.plugin;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.logging.Log;

public class StatusPrinter {
	
	private static int MAX_LINE_WIDTH = 120;
	
	static void printStatus(Log log, JunitStatus status) {
		getBox("NUMBER OF TESTS MIGRATED TO JUNIT 5: " + status.getNumberOfJunit5(),
			   "Number of tests in junit 4: " + status.getNumberOfJunit4(),
			   "Number of tests mixed: " + status.getNumberOfMixed(),
			   "Number of files inside test folder that don't contain a test : " + status.getNumberOfWeirdFiles()
		).forEach(current -> log.info(current));
    }
	
	
	public static List<String> getBox(String... strings) {
	    
	    List<String> result = new ArrayList<String>(strings.length + 4);
	    
	    addBorder(result);
	    
	    for (String str : strings) {
	    	result.add(String.format("///%s///", padString(str, MAX_LINE_WIDTH - 6)));
	    }
	    
	    addBorder(result);
	    
	    return result;
	}
	
	private static void addBorder(List<String> box) {
		String line = fill('/', MAX_LINE_WIDTH);
		
		box.add(line);
	    box.add(line);
	}
	
	
	private static String fill(char ch, int len) {
	    StringBuilder sb = new StringBuilder(len);
	    for (int i = 0; i < len; i++) {
	        sb.append(ch);
	    }
	    return sb.toString();
	}
	
	
	private static String padString(String str, int len) {
	    StringBuilder sb = new StringBuilder(str);
	    return sb.append(fill(' ', len - str.length())).toString();
	}

}
