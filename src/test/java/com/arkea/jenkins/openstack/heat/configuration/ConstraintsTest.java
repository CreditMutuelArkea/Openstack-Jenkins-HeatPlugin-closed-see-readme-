package com.arkea.jenkins.openstack.heat.configuration;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;

import org.junit.Test;

import com.arkea.jenkins.openstack.AbstractTest;
import com.arkea.jenkins.openstack.heat.HOTPlayerSettings;
import com.arkea.jenkins.openstack.heat.orchestration.template.Bundle;
import com.arkea.jenkins.openstack.heat.orchestration.template.utils.BundleMapperUtils;
import com.arkea.jenkins.openstack.heat.orchestration.template.utils.HOTMapperUtils;
import com.arkea.jenkins.openstack.heat.orchestration.template.utils.ParameterUtils;

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
 *         Class test the differents inputs in the global configuration
 *         for this plugin
 */
public class ConstraintsTest extends AbstractTest {

	@Test
	public void testConstraints() throws Exception {

		String hotName = "parameters-template.yaml";
		String body = ((HOTPlayerSettings) Jenkins.getInstance().getDescriptor(
				HOTPlayerSettings.class)).getLoader().getHot(hotName);
		JSONObject json = JSONObject.fromObject(HOTMapperUtils
				.getBundleFromHOT(hotName, body));
		Bundle bundle = BundleMapperUtils.getBundleFromJson(json.toString());

		// testString contraints
		assertFalse(
				"String too short ?",
				bundle.getParameters()
						.get("testString")
						.getConstraints()
						.get(0)
						.checkConstraint(
								bundle.getParameters().get("testString")));
		bundle.getParameters().get("testString")
				.setValue("testtesttesttesttest");
		assertFalse(
				"String too long ?",
				bundle.getParameters()
						.get("testString")
						.getConstraints()
						.get(0)
						.checkConstraint(
								bundle.getParameters().get("testString")));
		bundle.getParameters().get("testString").setValue("testtesttesttest");
		assertTrue(
				"String constraints correct ?",
				bundle.getParameters()
						.get("testString")
						.getConstraints()
						.get(0)
						.checkConstraint(
								bundle.getParameters().get("testString")));

		// testNumber contraints
		assertFalse(
				"Number too big ?",
				bundle.getParameters()
						.get("testNumber")
						.getConstraints()
						.get(0)
						.checkConstraint(
								bundle.getParameters().get("testNumber")));
		bundle.getParameters().get("testNumber").setValue("-2.3");
		assertFalse(
				"Number too small ?",
				bundle.getParameters()
						.get("testNumber")
						.getConstraints()
						.get(0)
						.checkConstraint(
								bundle.getParameters().get("testNumber")));
		bundle.getParameters().get("testNumber").setValue("0.75");
		assertTrue(
				"Number constraints correct ?",
				bundle.getParameters()
						.get("testNumber")
						.getConstraints()
						.get(0)
						.checkConstraint(
								bundle.getParameters().get("testNumber")));

		// testBoolean contraints
		assertFalse(
				"Boolean is not true ?",
				bundle.getParameters()
						.get("testBoolean")
						.getConstraints()
						.get(0)
						.checkConstraint(
								bundle.getParameters().get("testBoolean")));
		bundle.getParameters().get("testBoolean").setValue("false");
		assertTrue(
				"Boolean is false ?",
				bundle.getParameters()
						.get("testBoolean")
						.getConstraints()
						.get(0)
						.checkConstraint(
								bundle.getParameters().get("testBoolean")));
		bundle.getParameters().get("testBoolean").setValue("true");
		assertTrue(
				"Boolean is true ?",
				bundle.getParameters()
						.get("testBoolean")
						.getConstraints()
						.get(0)
						.checkConstraint(
								bundle.getParameters().get("testBoolean")));

		// testConstraints contraints
		assertTrue("Constraints begins begin by UpperCase ?", bundle
				.getParameters().get("testConstraints").getConstraints().get(0)
				.checkConstraint(bundle.getParameters().get("testConstraints")));
		assertFalse(
				"Constraints is too short ?",
				bundle.getParameters()
						.get("testConstraints")
						.getConstraints()
						.get(1)
						.checkConstraint(
								bundle.getParameters().get("testConstraints")));
		bundle.getParameters().get("testConstraints").setValue("testtesttest");
		assertFalse("Constraints has not UpperCase ?", bundle.getParameters()
				.get("testConstraints").getConstraints().get(0)
				.checkConstraint(bundle.getParameters().get("testConstraints")));
		assertTrue("Constraints has the good length  ?", bundle.getParameters()
				.get("testConstraints").getConstraints().get(1)
				.checkConstraint(bundle.getParameters().get("testConstraints")));
		bundle.getParameters().get("testConstraints").setValue("Testtesttest");
		assertTrue("Contraints UpperCase is true ?", bundle.getParameters()
				.get("testConstraints").getConstraints().get(0)
				.checkConstraint(bundle.getParameters().get("testConstraints")));
		assertTrue(
				"Contraints Length is true ?",
				bundle.getParameters()
						.get("testConstraints")
						.getConstraints()
						.get(0)
						.checkConstraint(
								bundle.getParameters().get("testConstraints")));

		// testJson custom contraint
		assertTrue("Custom constraint return always true ?", bundle
				.getParameters().get("testJson").getConstraints().get(0)
				.checkConstraint(bundle.getParameters().get("testJson")));

		// Check all constraints
		assertTrue("All the constraints are OK ?",
				ParameterUtils.checkContraints(bundle.getParameters()));
		bundle.getParameters().get("testConstraints").setValue("testtesttest");
		assertFalse("All the constraints are not OK ?",
				ParameterUtils.checkContraints(bundle.getParameters()));

	}

}
