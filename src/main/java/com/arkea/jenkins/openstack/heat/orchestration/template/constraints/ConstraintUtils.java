package com.arkea.jenkins.openstack.heat.orchestration.template.constraints;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

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
 *         This class is a utilitary class with mapping methods about yaml
 *         constraints :
 *         - conversion from yaml to Java
 *         - conversion from JSOn to Java
 *
 */
public class ConstraintUtils {

	/**
	 * Build a constraint bean list from JSON constraints part parsing in entry
	 * file.
	 * 
	 * @param constraints
	 *            the constraints list String structure
	 * @return
	 *         the equivalent constraints JAVA list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<AbstractConstraint> getContraintsToPopulatParameters(
			Map<String, Object> properties) {
		List<AbstractConstraint> constraintsList = new ArrayList<AbstractConstraint>();

		if (properties != null) {
			if (properties.get("constraints") != null) {
				if (properties.get("constraints") instanceof ArrayList) {
					ArrayList list = (ArrayList) (properties.get("constraints"));
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i) instanceof Map<?, ?>) {
							String description = "";
							Map<String, Object> jsonConstraint = (Map<String, Object>) list
									.get(i);
							if (jsonConstraint.get("allowed_values") != null) {
								ArrayList<String> allowedValuesList = (ArrayList<String>) jsonConstraint
										.get("allowed_values");
								AllowedValuesConstraint avc = new AllowedValuesConstraint(
										allowedValuesList);
								description = (String) jsonConstraint
										.get("description");
								avc.setDescription(description);
								constraintsList.add(avc);
							} else if (jsonConstraint.get("length") != null) {
								Map<String, Integer> lengthList = (Map<String, Integer>) jsonConstraint
										.get("length");
								LengthConstraint lc = new LengthConstraint(
										lengthList.get("min"),
										lengthList.get("max"));
								description = (String) jsonConstraint
										.get("description");
								lc.setDescription(description);
								constraintsList.add(lc);
							} else if (jsonConstraint.get("range") != null) {
								Map<String, Double> rangeList = (Map<String, Double>) jsonConstraint
										.get("range");
								RangeConstraint rc = new RangeConstraint(
										rangeList.get("min"),
										rangeList.get("max"));
								description = (String) jsonConstraint
										.get("description");
								rc.setDescription(description);
								constraintsList.add(rc);
							} else if (jsonConstraint.get("allowed_pattern") != null) {
								String pattern = (String) jsonConstraint
										.get("allowed_pattern");
								AllowedPatternConstraint apc = new AllowedPatternConstraint(
										pattern);
								description = (String) jsonConstraint
										.get("description");
								apc.setDescription(description);
								constraintsList.add(apc);
							} else if (jsonConstraint.get("custom_constraint") != null) {
								String key = (String) jsonConstraint
										.get("custom_constraint");
								CustomConstraint cc = new CustomConstraint(key);
								description = (String) jsonConstraint
										.get("description");
								cc.setDescription(description);
								constraintsList.add(cc);
							}
						}
					}
				}
			}
		}

		return constraintsList;
	}

	/**
	 * Build a constraint bean list from JSON constraints part parsing in JSON
	 * parameter.
	 * 
	 * @param constraints
	 *            the constraints list JSON structure
	 * @return
	 *         the equivalent constraints JAVA list
	 */
	@SuppressWarnings({ "unchecked" })
	public static List<AbstractConstraint> getContraintsFromJSONParameter(
			Map<String, Object> properties) {
		List<AbstractConstraint> constraintsList = new ArrayList<AbstractConstraint>();

		if (properties != null) {
			if (properties.get("constraints") != null) {
				JSONArray jsonConstraint = JSONArray.fromObject(properties
						.get("constraints"));

				String constraintType = "";
				for (int i = 0; i < jsonConstraint.size(); i++) {
					Map<String, Object> constraintMap = (Map<String, Object>) jsonConstraint
							.get(i);
					constraintType = (String) constraintMap.get("type");
					String description = "";

					switch (constraintType) {
					case "allowed_pattern":
						String pattern = (String) constraintMap
								.get("allowedPattern");
						AllowedPatternConstraint apc = new AllowedPatternConstraint(
								pattern);
						description = (String) constraintMap.get("description");
						apc.setDescription(description);
						constraintsList.add(apc);
						break;
					case "allowed_values":
						ArrayList<String> allowedValuesList = (ArrayList<String>) JSONSerializer
								.toJava(JSONArray.fromObject(constraintMap
										.get("allowedValues")));
						AllowedValuesConstraint avc = new AllowedValuesConstraint(
								allowedValuesList);
						description = (String) constraintMap.get("description");
						avc.setDescription(description);
						constraintsList.add(avc);
						break;
					case "length":
						LengthConstraint lc = new LengthConstraint(
								(Integer) constraintMap.get("minLength"),
								(Integer) constraintMap.get("maxLength"));
						description = (String) constraintMap.get("description");
						lc.setDescription(description);
						constraintsList.add(lc);
						break;
					case "range":
						RangeConstraint rc = new RangeConstraint(
								(Double) constraintMap.get("minRange"),
								(Double) constraintMap.get("maxRange"));
						description = (String) constraintMap.get("description");
						rc.setDescription(description);
						constraintsList.add(rc);
						break;
					case "custom_constraint":
						CustomConstraint cc = new CustomConstraint(
								(String) constraintMap.get("key"));
						description = (String) constraintMap.get("description");
						cc.setDescription(description);
						constraintsList.add(cc);
						break;
					}

					constraintsList.get(constraintsList.size() - 1)
							.setDescription(
									(String) constraintMap.get("description"));
				}
			}
		}

		return constraintsList;
	}

}
