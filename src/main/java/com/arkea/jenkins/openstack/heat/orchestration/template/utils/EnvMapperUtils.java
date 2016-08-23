package com.arkea.jenkins.openstack.heat.orchestration.template.utils;

import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.arkea.jenkins.openstack.heat.orchestration.template.Env;

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
 */
public class EnvMapperUtils {

	@SuppressWarnings("unchecked")
	public static Env getEnv(String yaml) {

		Map<String, String> parameter_defaults = new HashMap<String, String>();
		Map<String, String> parameters = new HashMap<String, String>();

		Map<String, Object> yamlObjects = (Map<String, Object>) (new Yaml())
				.load(yaml);
		if (yaml.contains(Constants.PARAMETER_DEFAULTS)) {
			Map<String, Object> yamlParamDefaults = (Map<String, Object>) yamlObjects
					.get(Constants.PARAMETER_DEFAULTS);
			for (String key : yamlParamDefaults.keySet()) {
				parameter_defaults.put(key, String.valueOf(yamlParamDefaults.get(key)));
			}
		}

		if (yaml.contains(Constants.PARAMETERS)) {
			Map<String, Object> yamlParams = (Map<String, Object>) yamlObjects
					.get(Constants.PARAMETERS);
			for (String key : yamlParams.keySet()) {
				parameters.put(key, String.valueOf(yamlParams.get(key)));
			}
		}

		return new Env(parameter_defaults, parameters);

	}
}
