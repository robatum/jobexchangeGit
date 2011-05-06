package net.agef.jobexchange.domain;

import java.io.Serializable;

import net.agef.jobexchange.exceptions.EnumValueNotFoundException;

public enum AvailabilityEnum implements Serializable {
	NOW("NOW"),
	TWO_WEEKS("TWO_WEEKS"),
	ONE_MONTH("ONE_MONTH"),
	TWO_MONTH("TWO_MONTH"),
	NEGOTIABLE("NEGOTIABLE");

	private final String value;

	AvailabilityEnum(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static AvailabilityEnum fromValue(String v) throws EnumValueNotFoundException {
		if (v != null) {
			v = v.trim();
			if (v.equals("") || v.equals("-1"))
				return null;

			for (AvailabilityEnum c : AvailabilityEnum.values()) {
				if (c.value.equals(v.toUpperCase())) {
					return c;
				}
			}
		}
		System.out.println("Unable to Parse Availability: " + v);
		throw new EnumValueNotFoundException();
	}
}
