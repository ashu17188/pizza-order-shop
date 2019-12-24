package org.innovect.assignment.model;

public enum PizzaInfoCategoryEnum {
	VEGETARIAN_PIZZA("Vegetarian"), NON_VEGETARIAN("Non Vegetarian");

	private final String category;

	private PizzaInfoCategoryEnum(String category) {
		this.category = category;
	}

	public String getCategory() {
		return this.category;
	}
}
