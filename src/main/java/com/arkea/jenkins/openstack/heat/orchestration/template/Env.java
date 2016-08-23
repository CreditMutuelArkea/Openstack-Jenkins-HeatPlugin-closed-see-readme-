package com.arkea.jenkins.openstack.heat.orchestration.template;

import java.util.Map;

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
 *         Class represents a file env
 *
 */
public class Env {

	private Map<String, String> parameter_defaults;
	private Map<String, String> parameters;

	public Env(Map<String, String> parameter_defaults,
			Map<String, String> parameters) {
		this.parameter_defaults = parameter_defaults;
		this.parameters = parameters;
	}

	public Map<String, String> getParameter_defaults() {
		return parameter_defaults;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

}
