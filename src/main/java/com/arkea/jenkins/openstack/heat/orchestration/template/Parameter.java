package com.arkea.jenkins.openstack.heat.orchestration.template;

import java.util.List;

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
 *         Class represents a bean parameter
 * 
 */
public class Parameter {

	/**
	 * Properties
	 */
	private String name;
	private Type type;
	private String label = "";
	private String description = "";
	private Object defaultValue = new String("");
	private boolean hidden;
	private String value = "";
	private List<AbstractConstraint> constraints = null;

	public Parameter(String name, Type type) {
		this.name = name;
		this.type = type;
	}

	public Parameter(String name, Type type, String label,
			String description, Object defaultValue,
			boolean hidden, String value) {
		this.name = name;
		this.type = type;
		this.label = label;
		this.description = description;
		this.defaultValue = defaultValue;
		this.hidden = hidden;
		this.value = value;
	}

	public Parameter(String name, Type type, String label,
			String description, Object defaultValue,
			boolean hidden, String value,
			List<AbstractConstraint> constraints) {
		this.name = name;
		this.type = type;
		this.label = label;
		this.description = description;
		this.defaultValue = defaultValue;
		this.hidden = hidden;
		this.value = value;
		this.constraints = constraints;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public Type getType() {
		return type;
	}

	public String getLabel() {
		return label;
	}

	public String getDescription() {
		return description;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public boolean isHidden() {
		return hidden;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setConstraints(List<AbstractConstraint> constraints) {
		this.constraints = constraints;
	}

	public List<AbstractConstraint> getConstraints() {
		return constraints;
	}
}
