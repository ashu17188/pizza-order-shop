package org.innovect.assignment.model;

public enum AdditionalStuffCategoryEnum {

	MISCELLANEOUS("miscellaneous");

	private final String category;

	private AdditionalStuffCategoryEnum(String category) {
		this.category = category;
	}

	public String getCategory() {
		return this.category;
	}
}
