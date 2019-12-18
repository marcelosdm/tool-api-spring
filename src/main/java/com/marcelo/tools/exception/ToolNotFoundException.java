package com.marcelo.tools.exception;

public class ToolNotFoundException extends Exception {

	private Long toolId;
	
	public ToolNotFoundException(long toolId) {
		super(String.format("Tool not found with id: %s", toolId));
	}
	
}
