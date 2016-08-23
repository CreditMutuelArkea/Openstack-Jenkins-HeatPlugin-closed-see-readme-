package com.arkea.jenkins.openstack.heat.orchestration.template.utils;

import java.util.Map;

import com.arkea.jenkins.openstack.heat.orchestration.template.Parameter;
import com.arkea.jenkins.openstack.heat.orchestration.template.constraints.AbstractConstraint;

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
 *         Class test the differents inputs in the global configuration
 *         for this plugin
 */
public class ParameterUtils {
	
	public static boolean checkContraints(Map<String, Parameter> parameters) {
		for(String key : parameters.keySet()) {
			Parameter parameter = parameters.get(key);
			if(!parameter.getConstraints().isEmpty()) {
				for(AbstractConstraint constraint : parameter.getConstraints()) {
					if(!constraint.checkConstraint(parameter)) {
						return false;
					}
				}
			}
		}
		return true;
	}

}
