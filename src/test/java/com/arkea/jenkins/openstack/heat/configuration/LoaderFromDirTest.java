package com.arkea.jenkins.openstack.heat.configuration;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.arkea.jenkins.openstack.AbstractTest;
import com.arkea.jenkins.openstack.ToolUtils;
import com.gargoylesoftware.htmlunit.html.HtmlForm;

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
public class LoaderFromDirTest extends AbstractTest {

	@Test
	public void testLoaderFromDirConfiguration() throws Exception {

		// Test LoaderFromDir with parameters-template.yaml
		HtmlForm form = j.createWebClient().goTo("configure")
				.getFormByName("config");

		ToolUtils.getButton(form, "Test Path Hot").click();

		// Time to change the page
		Thread.sleep(1000);

		assertEquals(1, form.getElementsByAttribute("div", "class", "ok")
				.size());

		// Test LoaderFromDir with default.yaml
		ToolUtils.getButton(form, "Test Path Env").click();

		// Time to change the page
		Thread.sleep(1000);

		assertEquals(2, form.getElementsByAttribute("div", "class", "ok")
				.size());

	}

}
