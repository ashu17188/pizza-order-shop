package org.innovect.assignment.data;

import java.util.ArrayList;
import java.util.List;

import org.innovect.assignment.model.AdditionalStuffInfo;

public class AdditionalStuffInfoListData {

	public static List<AdditionalStuffInfo> createAdditionalStuffInfoList() {
		List<AdditionalStuffInfo> additionalStuffInfoList = new ArrayList<>();

		AdditionalStuffInfo additionalStuffInfo1 = new AdditionalStuffInfo("Black olive","Veg Toppings",20.0,40);
		AdditionalStuffInfo additionalStuffInfo2 = new AdditionalStuffInfo("Mushroom","Veg Toppings",30.0,10);


		additionalStuffInfoList.add(additionalStuffInfo1);
		additionalStuffInfoList.add(additionalStuffInfo2);
		return additionalStuffInfoList;
	}
}
