package net.agef.jobexchange.domain;

import java.io.Serializable;

import net.agef.jobexchange.exceptions.EnumValueNotFoundException;

public enum WorkTypeEnum implements Serializable {
	INTERNSHIP_TRAINEE("INTERNSHIP_TRAINEE"),
	PHD("PHD"),
	FREELANCE("FREELANCE"),
	SHORT_TERM("SHORT_TERM"),
	TEMPORARY_WORK("TEMPORARY_WORK"),
	LONG_TERM("LONG_TERM");
	
	private final String value;

	WorkTypeEnum(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static WorkTypeEnum fromValue(String v) throws EnumValueNotFoundException {
		if (v != null) {
			v = v.trim();
			if (v.equals("") || v.equals("-1"))
				return null;

			for (WorkTypeEnum c : WorkTypeEnum.values()) {
				if (c.value.equals(v.toUpperCase())) {
					return c;
				}
			}
		}
		System.out.println("Unable to Parse WorkType: " + v);
		throw new EnumValueNotFoundException();
	}
}
