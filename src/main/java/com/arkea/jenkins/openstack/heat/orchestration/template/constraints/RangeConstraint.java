package com.arkea.jenkins.openstack.heat.orchestration.template.constraints;

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
 *         This class is a bean describing a "range" type constraint
 *
 */

public class RangeConstraint extends AbstractConstraint {

	private double minRange;
	private double maxRange;

	RangeConstraint() {
		super(ConstraintType.range);
	}

	RangeConstraint(double min, double max) {
		this();
		this.minRange = min;
		this.maxRange = max;
	}

	public double getMinRange() {
		return minRange;
	}

	public void setMinRange(double minRange) {
		this.minRange = minRange;
	}

	public double getMaxRange() {
		return maxRange;
	}

	public void setMaxRange(double maxRange) {
		this.maxRange = maxRange;
	}

	@Override
	public boolean checkConstraint(Parameter parameter) {
		if (!Strings.isNullOrEmpty(parameter.getValue())) {
			double value = Double.valueOf(parameter.getValue());
			return (value >= minRange && value <= maxRange);
		} else if (!Strings.isNullOrEmpty((String) parameter.getDefaultValue())) {
			double value = Double.valueOf((String) parameter.getDefaultValue());
			return (value >= minRange && value <= maxRange);
		} else {
			return false;
		}
	}
}
