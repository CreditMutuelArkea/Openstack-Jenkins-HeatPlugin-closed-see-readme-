package com.arkea.jenkins.openstack.heat.orchestration.template.constraints;

import com.arkea.jenkins.openstack.heat.orchestration.template.Parameter;

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
 *         This class is a bean describing a "custom_constraint" type
 *         custom_constraint
 *
 */
public class CustomConstraint extends AbstractConstraint {

	private String key;

	CustomConstraint() {
		super(ConstraintType.custom_constraint);
	}

	CustomConstraint(String key) {
		this();
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public boolean checkConstraint(Parameter parameter) {
		// Return true because the constraint is check via the heat engine
		// backend
		return true;
	}

}
