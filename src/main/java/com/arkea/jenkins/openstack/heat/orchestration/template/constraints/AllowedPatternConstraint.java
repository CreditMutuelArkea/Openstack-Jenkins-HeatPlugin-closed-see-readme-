package com.arkea.jenkins.openstack.heat.orchestration.template.constraints;

import java.util.regex.Pattern;

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
 *         This class is a bean describing an "allowed_pattern" type constraint
 *
 */
public class AllowedPatternConstraint extends AbstractConstraint {

	private String allowedPattern;

	public AllowedPatternConstraint() {
		super(ConstraintType.allowed_pattern);
	}

	public AllowedPatternConstraint(String pattern) {
		super(ConstraintType.allowed_pattern);
		this.allowedPattern = pattern;
	}

	public String getAllowedPattern() {
		return allowedPattern;
	}

	public void setAllowedPattern(String allowedPattern) {
		this.allowedPattern = allowedPattern;
	}

	@Override
	public boolean checkConstraint(Parameter parameter) {
		Pattern p = Pattern.compile(allowedPattern);
		if (!Strings.isNullOrEmpty(parameter.getValue())) {
			return p.matcher(parameter.getValue()).matches();
		} else if (!Strings.isNullOrEmpty((String) parameter.getDefaultValue())) {
			return p.matcher((String) parameter.getDefaultValue()).matches();
		} else {
			return false;
		}
	}
}
