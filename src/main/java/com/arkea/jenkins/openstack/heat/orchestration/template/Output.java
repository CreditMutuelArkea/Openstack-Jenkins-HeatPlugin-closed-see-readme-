package com.arkea.jenkins.openstack.heat.orchestration.template;

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
 *         Class represents a bean output
 * 
 */
public class Output {

	/**
	 * Properties
	 */
	private String name;
	private String description;
	private String value = "";

	public Output(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public Output(String name, String description,
			String value) {
		this.name = name;
		this.description = description;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
