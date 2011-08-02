package net.agef.jobexchange.domain;

import java.io.Serializable;

import net.agef.jobexchange.exceptions.EnumValueNotFoundException;

public enum WorkTypeEnum implements Serializable {
	INTERNSHIP_TRAINEE("INTERNSHIP_TRAINEE"),
	PHD("PHD"),
	FREELANCE("FREELANCE"),
	SHORT_TERM("SHORT_TERM"),
	TEMPORARY_WORK("TEMPORARY_WORK"),
	LONG_TERM("LONG_TERM"),
	/*Ergaenzungen fuer das IGC Sommer2011 - Beginn - */
	EDITORIAL_OFFICE("EDITORIAL_OFFICE"),
	PRESENTATION("PRESENTATION"),
	FORUMS_MODERATION("FORUMS_MODERATION"),
	COACH("COACH"),
	ONLINE_TUTORING("ONLINE_TUTORING"),
	SEMINAR_LEADER_ASSISTANT("SEMINAR_LEADER_ASSISTANT"),
	EXPERT_CONSULTANT("EXPERT_CONSULTANT"),
	EVALUATION("EVALUATION"),
	RESOURCE_PERSON("RESOURCE_PERSON");
	/*Ergaenzungen fuer das IGC Sommer2011 - Ende - */
	
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
