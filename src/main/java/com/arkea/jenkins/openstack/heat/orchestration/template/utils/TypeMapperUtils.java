package com.arkea.jenkins.openstack.heat.orchestration.template.utils;

import com.arkea.jenkins.openstack.heat.orchestration.template.Type;

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
 *         Class utils for the type
 * 
 */
public class TypeMapperUtils {

	/**
	 * Mapping Type
	 * 
	 * @param type
	 *            type to map
	 * @return the Type in Java
	 */
	public static Type getType(String type) {

		Type rtn = Type.String;

		switch (type) {
		case "number":
		case "Number":
			rtn = Type.Number;
			break;
		case "json":
		case "Json":
			rtn = Type.Json;
			break;
		case "comma_delimited_list":
		case "Comma_delimited_list":
			rtn = Type.Comma_delimited_list;
			break;
		case "boolean":
		case "Boolean":
			rtn = Type.Boolean;
			break;
		}
		return rtn;
	}
}
