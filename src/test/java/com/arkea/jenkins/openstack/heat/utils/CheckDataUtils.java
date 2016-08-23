package com.arkea.jenkins.openstack.heat.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import hudson.model.Descriptor.FormException;
import net.sf.json.JSONObject;

import com.arkea.jenkins.openstack.heat.HOTPlayerSettings;
import com.arkea.jenkins.openstack.heat.orchestration.template.Output;
import com.arkea.jenkins.openstack.heat.orchestration.template.Parameter;
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
 */
public class CheckDataUtils {

	public static void checkString(Parameter parameter) {
		assertEquals(Type.String, parameter.getType());
		assertEquals("testString", parameter.getName());
		assertEquals("testString", parameter.getLabel());
		assertEquals("test a type string", parameter.getDescription());
		assertEquals("string", parameter.getDefaultValue());
		assertEquals(false, parameter.isHidden());
	}

	public static void checkNumber(Parameter parameter) {
		assertEquals(Type.Number, parameter.getType());
		assertEquals("testNumber", parameter.getName());
		assertEquals("testNumber", parameter.getLabel());
		assertEquals("test a type number", parameter.getDescription());
		assertEquals("33", parameter.getDefaultValue());
		assertEquals(true, parameter.isHidden());
	}

	public static void checkJson(Parameter parameter) {
		assertEquals(Type.Json, parameter.getType());
		assertEquals("testJson", parameter.getName());
		assertEquals("testJson", parameter.getLabel());
		assertEquals("test a type json", parameter.getDescription());
		JSONObject json = new JSONObject();
		json.put("key", "value");
		assertEquals(json, parameter.getDefaultValue());
		assertEquals(false, parameter.isHidden());
	}

	public static void checkBoolean(Parameter parameter) {
		assertEquals(Type.Boolean, parameter.getType());
		assertEquals("testBoolean", parameter.getName());
		assertEquals("testBoolean", parameter.getLabel());
		assertEquals("test a type boolean", parameter.getDescription());
		assertEquals("on", parameter.getDefaultValue());
		assertEquals(true, parameter.isHidden());
	}

	public static void checkCommaDelimitedList(Parameter parameter) {
		assertEquals(Type.Comma_delimited_list, parameter.getType());
		assertEquals("testCommaDelimitedList", parameter.getName());
		assertEquals("testCommaDelimitedList", parameter.getLabel());
		assertEquals("test a type comma_delimited_list",
				parameter.getDescription());
		assertEquals("one, two", parameter.getDefaultValue());
		assertEquals(false, parameter.isHidden());
	}

	public static void checkOutput(Output output) {
		assertEquals("testOutput", output.getName());
		assertEquals("test a value output", output.getDescription());
		assertEquals("", output.getValue());
	}

	public static void checkConstraints(Parameter parameter) {
		assertEquals(Type.String, parameter.getType());
		assertEquals("testConstraint", parameter.getName());
		assertEquals("testConstraint", parameter.getLabel());
		assertEquals("test a type string with constraints",
				parameter.getDescription());
		assertEquals("REC", parameter.getDefaultValue());
		assertNotNull(parameter.getConstraints());
		assertEquals(2, parameter.getConstraints().size());
	}

	public static void checkFormException(HOTPlayerSettings hPS,
			String field) {
		try {
			hPS.checkData();
			assertFalse(false);
		} catch (FormException fe) {
			assertEquals(field, fe.getFormField());
		}
	}

}
