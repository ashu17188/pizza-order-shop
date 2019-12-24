package org.innovect.assignment.model;

public enum ToppingsCategoryEnum {

	VEGETARIAN("Vegetarian Pizza"), NON_VEGETARIAN("Non Vegetarian");

	private final String category;

	private ToppingsCategoryEnum(String category) {
		this.category = category;
	}

	public String getCategory() {
		return this.category;
	}
}
