package com.arkea.jenkins.openstack.heat.orchestration.template.utils;

import java.util.Comparator;
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
 *         Class utils to manage the outputs from a stack
 * 
 */
public class OutputUtils {

	/**
	 * @return a comprator to sort the outputs in alphabetical order for the
	 *         name output
	 */
	public static Comparator<Map<String, Object>> getComparatorAlphabetical() {
		return new Comparator<Map<String, Object>>() {
			public int compare(Map<String, Object> m1, Map<String, Object> m2) {
				return ((String) m1.get("output_key")).compareTo((String) m2
						.get("output_key"));
			}
		};
	}

}
