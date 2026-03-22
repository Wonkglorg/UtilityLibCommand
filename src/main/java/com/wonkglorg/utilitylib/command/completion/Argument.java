package com.wonkglorg.utilitylib.command.completion;

import java.util.Set;
import java.util.function.Supplier;

public class Argument{
	private String argumentName;
	private boolean required;
	private Supplier<Set<String>> suggestions;
	
	public Argument(String argumentName, boolean required, Supplier<Set<String>> suggestions) {
		this.argumentName = argumentName;
		this.required = required;
		this.suggestions = suggestions;
	}
	
	public Argument(String argumentName, boolean required) {
		this.argumentName = argumentName;
		this.required = required;
		suggestions = Set::of;
	}
	
	public String getArgumentName() {
		return argumentName;
	}
	
	public boolean isRequired() {
		return required;
	}
	
	public Set<String> getSuggestions() {
		return suggestions.get();
	}
}
