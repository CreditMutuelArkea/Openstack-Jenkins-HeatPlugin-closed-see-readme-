package com.arkea.jenkins.openstack.heat.orchestration.template.constraints;

import com.arkea.jenkins.openstack.heat.orchestration.template.Parameter;
import com.arkea.jenkins.openstack.heat.orchestration.template.utils.ConstraintTypeMapperUtils;

/**
 * @author Credit Mutuel Arkea
 * 
 *         Copyright 2015 Credit Mutuel Arkea
 *
 *         Licensed under the Apache License, Version 2.0 (the "License");
 *         you may not use this file except in compliance with the License.
 *         You may obtain a copy of the License at
 * 
 *         http://www.apache.org/licenses/LICENSE-2.0
 * 
 *         Unless required by applicable law or agreed to in writing, software
 *         distributed under the License is distributed on an "AS IS" BASIS,
 *         WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *         implied.
 *         See the License for the specific language governing permissions and
 *         limitations under the License.
 * 
 *         This class is a generic bean representing a yaml constraint on a
 *         parameter
 *
 */
public abstract class AbstractConstraint {

	private ConstraintType type;
	private String description;

	AbstractConstraint() {
	}

	AbstractConstraint(ConstraintType type) {
		this.type = type;
	}

	AbstractConstraint(String strType, String description) {
		this.type = ConstraintTypeMapperUtils
				.getContraintTypeFromString(strType);
		this.description = description;
	}

	public ConstraintType getType() {
		return type;
	}

	public void setType(ConstraintType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public abstract boolean checkConstraint(Parameter parameter);
}
