package org.innovect.assignment.model;

public enum AdditionalStuffCategoryEnum {

	VEG_TOPPINGS("Veg Toppings"), NON_VEG_TOPPINGS("Non-­Veg Toppings"), CRUST("crust"), SIDES("sides"),
	MISCELLANEOUS("miscellaneous");

	private final String category;

	private AdditionalStuffCategoryEnum(String category) {
		this.category = category;
	}

	public String getCategory() {
		return this.category;
	}
}
