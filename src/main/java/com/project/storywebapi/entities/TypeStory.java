package com.project.storywebapi.entities;

public enum TypeStory {
	word("word"),
	comic("comic");
	
	public final String label;

	public String getLabel() {
		return label;
	}

	private TypeStory(String label) {
		this.label = label;
	}
}
