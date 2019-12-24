package org.innovect.assignment.model;

public enum AdditionalStuffCategoryEnum {

	VEG_TOPPINGS("Vegetarian Pizza"), NON_VEG_TOPPINGS("Non Vegetarian"), CRUST("crust"), SIDES("sides"),
	MISCELLANEOUS("miscellaneous");

	private final String category;

	private AdditionalStuffCategoryEnum(String category) {
		this.category = category;
	}

	public String getCategory() {
		return this.category;
	}
}
