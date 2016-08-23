package com.arkea.jenkins.openstack.heat.orchestration.template.utils;

import com.arkea.jenkins.openstack.heat.orchestration.template.constraints.ConstraintType;

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
 *         This class is a mapper from Json to Java conversion for a yaml
 *         constraint
 * 
 */
public class ConstraintTypeMapperUtils {

	/**
	 * Mapping constraint type
	 * 
	 * @param str
	 *            the constraint type to map
	 * @return
	 *         the Java Constraint Type
	 */
	public static ConstraintType getContraintTypeFromString(String str) {
		ConstraintType res = ConstraintType.length;

		switch (str) {
		case "Allowed_pattern":
		case "allowed_pattern":
			res = ConstraintType.allowed_pattern;
			break;
		case "Allowed_values":
		case "allowed_values":
			res = ConstraintType.allowed_values;
			break;
		case "Range":
		case "range":
			res = ConstraintType.range;
			break;
		case "Custom_constraint":
		case "custom_constraint":
			res = ConstraintType.custom_constraint;
			break;
		}

		return res;
	}

}
