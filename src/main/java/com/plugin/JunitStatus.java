package com.plugin;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class JunitStatus {

	private int numberOfMixed;
	private int numberOfJunit4;
	private int numberOfJunit5;
	private int numberOfWeirdFiles;
	
	public void addJunit4() {
		numberOfJunit4 ++;
	}
	
	public void addJunit5() {
		numberOfJunit5 ++;
	}
	
	public void addWeird() {
		numberOfWeirdFiles ++;
	}
	
	public void addMixed() {
		numberOfMixed++;
	}
}
