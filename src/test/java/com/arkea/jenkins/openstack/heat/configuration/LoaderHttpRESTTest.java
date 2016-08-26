package com.arkea.jenkins.openstack.heat.configuration;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandler;
import org.mortbay.jetty.handler.ResourceHandler;

import com.arkea.jenkins.openstack.AbstractTest;
import com.arkea.jenkins.openstack.ToolUtils;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;

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
public class LoaderHttpRESTTest extends AbstractTest {

	@Test
	public void testHttpRESTConfiguration() throws Exception {

		// Configuration Server Http REST Hot File
		Server server = new Server(8180);

		ContextHandler context = new ContextHandler();
		context.setContextPath("/testHot");
		ResourceHandler resourceHandler = new ResourceHandler();
		String path = getClass().getResource("/listHots").getPath();
		resourceHandler
				.setResourceBase(path.substring(0, path.lastIndexOf("/")));
		context.setHandler(resourceHandler);

		server.setHandler(context);

		server.start();
		while (!server.isStarted()) {
			Thread.sleep(200);
		}

		// Configuration HttpREST
		HtmlForm form = j.createWebClient().goTo("configure")
				.getFormByName("config");

		HtmlSelect selectLoader = ToolUtils.getSelect(form, "0",
				"LoaderFromDir");
		selectLoader.getOptions().get(1).setSelected(true);
		
		// Time to change the page
		Thread.sleep(1000);
		
		form.getInputByName("httpRESTEnv").click();

		form.getInputByName("_.urlListHot").setValueAttribute(
				"http://localhost:8180/testHot/listHots");
		
		form.getInputByName("_.urlListEnv").setValueAttribute(
				"http://localhost:8180/testHot/listEnvs");

		// Test call url hot
		ToolUtils.getButton(form, "Test Url Hot").click();
		
		// Time to change the page
		Thread.sleep(1000);
		
		assertEquals(1, form.getElementsByAttribute("div", "class", "ok")
				.size());

		// Test call url env
		ToolUtils.getButton(form, "Test Url Env").click();
		
		// Time to change the page
		Thread.sleep(1000);
		
		assertEquals(2, form.getElementsByAttribute("div", "class", "ok")
				.size());

		server.stop();

	}

}
