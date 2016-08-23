package com.arkea.jenkins.openstack.heat.orchestration.template.constraints;

import java.util.ArrayList;

import com.arkea.jenkins.openstack.heat.orchestration.template.Parameter;
import com.google.common.base.Strings;

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
 *         This class is a bean describing an "allowed_values" type constraint
 *
 */
public class AllowedValuesConstraint extends AbstractConstraint {

	private ArrayList<String> allowedValues;

	AllowedValuesConstraint() {
		super(ConstraintType.allowed_values);
	}

	AllowedValuesConstraint(ArrayList<String> allowedValues) {
		this();
		this.allowedValues = allowedValues;
	}

	public ArrayList<String> getAllowedValues() {
		return allowedValues;
	}

	public void setAllowedValues(ArrayList<String> allowedValues) {
		this.allowedValues = allowedValues;
	}

	@Override
	public boolean checkConstraint(Parameter parameter) {
		String testValue = (String) parameter.getDefaultValue();
		if (!Strings.isNullOrEmpty(parameter.getValue())) {
			testValue = parameter.getValue();
		}
		for (int i = 0; i < allowedValues.size(); i++) {
			if (testValue.equals(String.valueOf(allowedValues.get(i)))) {
				return true;
			}
		}
		return false;
	}

}
