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
 *         This class is a bean describing a "length" type constraint
 * 
 */

public class LengthConstraint extends AbstractConstraint {

	private int minLength;
	private int maxLength;

	LengthConstraint() {
		super(ConstraintType.length);
	}

	LengthConstraint(int min, int max) {
		this();
		this.minLength = min;
		this.maxLength = max;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	@Override
	public boolean checkConstraint(Parameter parameter) {
		int length = parameter.getValue().length() == 0 ? ((String) parameter
				.getDefaultValue()).length() : parameter.getValue().length();
		if (length >= minLength && length <= maxLength) {
			return true;
		} else {
			return false;
		}
	}
}
