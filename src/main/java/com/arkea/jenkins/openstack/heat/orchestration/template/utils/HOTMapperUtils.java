package com.arkea.jenkins.openstack.heat.orchestration.template.utils;

import java.util.Map;
import java.util.TreeMap;

import org.yaml.snakeyaml.Yaml;

import com.arkea.jenkins.openstack.heat.orchestration.template.Bundle;
import com.arkea.jenkins.openstack.heat.orchestration.template.Output;
import com.arkea.jenkins.openstack.heat.orchestration.template.Parameter;
import com.arkea.jenkins.openstack.heat.orchestration.template.constraints.ConstraintUtils;

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
 *         Class utils to parse the HOT
 * 
 */
public class HOTMapperUtils {

	/**
	 * @param hotName
	 *            name HOT
	 * @param hot
	 *            the contents
	 * @return the bundle
	 */
	@SuppressWarnings("unchecked")
	public static Bundle getBundleFromHOT(String hotName, String hot) {

		Bundle stack = new Bundle(hotName, "", false, false);

		// Find paramaters or outputs in HOT
		if (hot.contains(Constants.PARAMETERS)
				|| hot.contains(Constants.OUTPUTS)) {
			Map<String, Object> hotObjects = (Map<String, Object>) (new Yaml())
					.load(hot);
			if (hot.contains(Constants.PARAMETERS)) {
				stack.setParameters(getParameters(hotObjects));
			}
			if (hot.contains(Constants.OUTPUTS)) {
				stack.setOutputs(getOutputs(hotObjects));
			}
		}

		return stack;

	}

	/**
	 * Transform parameters from HOT to parameters in JAVA
	 * 
	 * @param hotObjects
	 *            the objects parameters content in the HOT
	 * @return the list of parameters
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, Parameter> getParameters(
			Map<String, Object> hotObjects) {
		Map<String, Parameter> params = new TreeMap<String, Parameter>();
		Map<String, Object> parameters = (Map<String, Object>) hotObjects
				.get(Constants.PARAMETERS);
		for (String key : parameters.keySet()) {
			Map<String, Object> data = (Map<String, Object>) parameters
					.get(key);
			params.put(key, populateParameter(key, data));
		}
		return params;
	}

	/**
	 * Create Paramater in JAVA
	 * 
	 * @param name
	 *            name of the parameter
	 * @param properties
	 *            properties of the paramater
	 * @return parameter
	 */
	private static Parameter populateParameter(String name,
			Map<String, Object> properties) {

		Parameter param = new Parameter(name,
				TypeMapperUtils.getType((String) properties.get("type")));

		if (properties.get("label") != null) {
			param.setLabel((String) properties.get("label"));
		}

		if (properties.get("description") != null) {
			param.setDescription((String) properties.get("description"));
		}

		if (properties.get("default") != null) {
			param.setDefaultValue(properties.get("default"));
		}

		if (properties.get("hidden") != null) {
			param.setHidden((boolean) properties.get("hidden"));
		}

		if (properties.get("constraints") != null) {
			param.setConstraints(ConstraintUtils
					.getContraintsToPopulatParameters(properties));
		}

		return param;
	}

	/**
	 * Transform outputs from HOT to outputs in JAVA
	 * 
	 * @param hotObjects
	 *            the objects outputs content in the HOT
	 * @return the list of outputs
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, Output> getOutputs(
			Map<String, Object> hotObjects) {
		Map<String, Output> exits = new TreeMap<String, Output>();
		Map<String, Object> outputs = (Map<String, Object>) hotObjects
				.get(Constants.OUTPUTS);
		for (String key : outputs.keySet()) {
			exits.put(
					key,
					new Output(key, (String) ((Map<String, Object>) outputs
							.get(key)).get("description")));
		}
		return exits;
	}
}
