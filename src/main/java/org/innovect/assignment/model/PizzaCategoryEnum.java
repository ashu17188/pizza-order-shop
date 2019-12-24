package org.innovect.assignment.model;

public enum PizzaCategoryEnum {
	VEGETARIAN_PIZZA("Vegetarian"), NON_VEGETARIAN("Non Vegetarian");

	private final String category;

	private PizzaCategoryEnum(String category) {
		this.category = category;
	}

	public String getCategory() {
		return this.category;
	}
}
