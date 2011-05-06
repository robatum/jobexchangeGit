package net.agef.jobexchange.integration;

import net.agef.jobexchange.domain.AbstractUserRole;
import net.agef.jobexchange.domain.AlumniRole;
import net.agef.jobexchange.domain.OrganisationRole;


public enum UserRoleType {

		ALUMNI{
			public String getClassName() {
				return AlumniRole.class.getName();
			} 
			@Override
			public AlumniRole getNewInstance() {
				return new AlumniRole();
			}
		},
		ORGANISATION{
			public String getClassName() {
				return OrganisationRole.class.getName();
			} 
			@Override
			public OrganisationRole getNewInstance() {
				return new OrganisationRole();
			}
		};

		public abstract AbstractUserRole getNewInstance();

		public abstract String getClassName();	
	
}
