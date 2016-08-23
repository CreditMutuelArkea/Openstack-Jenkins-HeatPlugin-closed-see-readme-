package com.arkea.jenkins.openstack.heat.orchestration.template.utils;

import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

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
 *         Class utils to transform a JSONObject to a Bundle
 * 
 */
public class BundleMapperUtils {

	@SuppressWarnings("unchecked")
	public static Bundle getBundleFromJson(String data) {

		JSONObject json = JSONObject.fromObject(data);

		// Properties globals
		Bundle bundle = new Bundle(json.getString("hotName"),
				json.getString("name"), json.getBoolean("exist"),
				json.getBoolean("debug"));

		// Parameters
		Map<String, Parameter> params = new TreeMap<String, Parameter>();
		Map<String, Object> parameters = json.getJSONObject("parameters");

		for (String parameter : parameters.keySet()) {
			Map<String, Object> properties = (Map<String, Object>) parameters
					.get(parameter);
			Parameter param = new Parameter(
					(String) properties.get("name"),
					TypeMapperUtils.getType((String) properties.get("type")),
					(String) properties.get("label"),
					(String) properties.get("description"),
					properties.get("defaultValue"),
					(boolean) properties.get("hidden"),
					(String) properties.get("value"),
					ConstraintUtils.getContraintsFromJSONParameter(properties));
			params.put(parameter, param);
		}

		bundle.setParameters(params);

		// Outputs
		Map<String, Output> exits = new TreeMap<String, Output>();
		Map<String, Object> outputs = json.getJSONObject("outputs");

		for (String output : outputs.keySet()) {
			Map<String, Object> properties = (Map<String, Object>) outputs
					.get(output);
			Output exit = new Output((String) properties.get("name"),
					(String) properties.get("description"),
					(String) properties.get("value"));
			exits.put(output, exit);
		}

		bundle.setOutputs(exits);

		return bundle;
	}

}
