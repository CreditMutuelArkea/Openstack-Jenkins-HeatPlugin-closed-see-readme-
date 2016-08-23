package com.arkea.jenkins.openstack.operations;

import hudson.EnvVars;
import hudson.Util;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.util.VariableResolver;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import com.arkea.jenkins.openstack.exception.utils.ExceptionUtils;
import com.arkea.jenkins.openstack.heat.orchestration.template.Output;
import com.arkea.jenkins.openstack.log.ConsoleLogger;
import com.arkea.jenkins.openstack.heat.i18n.Messages;

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
 *         Class utils to retrieve and push variable between step of the
 *         build
 */
public class EnvVarsUtils {

	private EnvVars env;
	private VariableResolver<String> vr;
	private ConsoleLogger cLog;
	private AbstractBuild<?, ?> build;

	public EnvVarsUtils(AbstractBuild<?, ?> build,
			BuildListener listener, ConsoleLogger cLog) {

		this.cLog = cLog;
		this.build = build;

		vr = build.getBuildVariableResolver();
		try {
			env = build.getEnvironment(listener);
		} catch (IOException | InterruptedException e) {
			cLog.logError(Messages.environment_notFound() + e.getMessage()
					+ ExceptionUtils.getStackTrace(e));
		}
	}

	/**
	 * Tranfrom a list of $VARIABLE in good value
	 * 
	 * @param toResolves
	 *            variables to transform
	 * @return variables resolveds
	 */
	public Map<String, String> getVars(Map<String, String> toResolves) {

		Map<String, String> resolveds = new TreeMap<String, String>();
		for (String key : toResolves.keySet()) {
			resolveds.put(key, this.env.expand(Util.replaceMacro(
					toResolves.get(key), this.vr)));
			if (!toResolves.get(key).equals(resolveds.get(key))) {
				this.cLog.logDebug(Messages.environment_variable(key,
						toResolves.get(key), resolveds.get(key)));
			}
		}

		return resolveds;
	}

	/**
	 * Tranfrom $VARIABLE in good value
	 * 
	 * @param toResolve
	 *            variable to transform
	 * @return varaible resolved
	 */
	public String getVar(String toResolve) {

		return this.env.expand(Util.replaceMacro(toResolve, this.vr));

	}

	/**
	 * 
	 * Put $VARIBLE in context to be disponble at different step of the build
	 * 
	 * @param toPuts
	 *            variables to set
	 * @param outputs
	 *            values possibles
	 */
	public void setVars(Map<String, Output> toPuts,
			Map<String, String> outputs) {

		for (String key : toPuts.keySet()) {
			Output output = toPuts.get(key);
			// If the value starts with $ then if a variable and it presents in
			// the list of possibles values
			if (output.getValue().startsWith("$") && outputs.containsKey(key)) {
				this.cLog.logDebug(Messages.environment_output(
						output.getValue(), outputs.get(key)));
				// Push the variable in the context without the $ in the
				// name
				PublishEnvVar publish = new PublishEnvVar(output
						.getValue().substring(1), outputs.get(key));
				this.build.addAction(publish);
				publish.buildEnvVars(this.build, this.env);
			}
		}
	}
}
