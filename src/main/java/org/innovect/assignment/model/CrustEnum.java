package org.innovect.assignment.model;

public enum CrustEnum {

	NEW_HAND_TOSSED("New hand tossed"), WHEAT_THIN_CRUST("Wheat thin crust"), CHEESE_BURST("Cheese Burst"),
	FRESH_PAN_PIZZA("Fresh pan pizza");

	private final String crust;

	private CrustEnum(String category) {
		this.crust = category;
	}

	public String getCategory() {
		return this.crust;
	}
}
