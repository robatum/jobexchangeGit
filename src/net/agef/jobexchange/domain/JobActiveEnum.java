package net.agef.jobexchange.domain;

import java.io.Serializable;

import net.agef.jobexchange.exceptions.EnumValueNotFoundException;

public enum JobActiveEnum implements Serializable {
	ALL("ALL"),
	ACTIVE("ACTIVE"),
	ENDED("ENDED");

	private final String value;

	JobActiveEnum(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static JobActiveEnum fromValue(String v) throws EnumValueNotFoundException {
		if (v != null) {
			v = v.trim();
			if (v.equals("") || v.equals("-1"))
				return null;

			for (JobActiveEnum c : JobActiveEnum.values()) {
				if (c.value.equals(v.toUpperCase())) {
					return c;
				}
			}
		}
		System.out.println("Unable to Parse JobActiveEnum: " + v);
		throw new EnumValueNotFoundException();
	}
}
