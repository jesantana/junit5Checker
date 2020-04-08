package com.plugin;

import static java.lang.Math.max;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.apache.maven.plugin.logging.Log;

import lombok.SneakyThrows;

public class StatusPrinter {

    
	private static int MAX_LINE_WIDTH = 200;
	
	static void printStatus(Log log, JunitStatus status) {
		getPayoutBox(status)
			.forEach(current -> log.info(current));
		
    }
	
	@SneakyThrows
	static void writeToFile(String filePath, JunitStatus status) {
		File file = new File(filePath);
	    
	    PrintWriter writer = new PrintWriter(file, "UTF-8");
	    
	    getPayoutBox(status)
	    	.forEach(current -> writer.println(current));
	    
	    writer.close();
    }
	
	
	public static List<String> getPayoutBox(JunitStatus status) {
		List<String> lines = new ArrayList<String>(Arrays.asList("NUMBER OF TESTS MIGRATED TO JUNIT 5: " + status.getNumberOfJunit5(),
				   "Number of tests in junit 4: " + status.getNumberOfJunit4(),
				   "Number of tests mixed: " + status.getNumberOfMixed(),
				   "Number of files inside test folder that don't contain a test : " + status.getNumberOfWeirdFiles()));
		
		if(!status.getMixedFiles().isEmpty()) {
			lines.add("");
			lines.add("Mixed Files:");
			lines.addAll(status.getMixedFiles());
		}
		
		return getBox(lines);
	}
	
	public static List<String> getBox(List<String> strings) {
	    
	    List<String> result = new ArrayList<String>(strings.size() + 4);
	    
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
	    return sb.append(fill(' ', max(0, len - str.length()))).toString();
	}

}
