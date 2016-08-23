package com.arkea.jenkins.openstack.operations;

import hudson.EnvVars;
import hudson.model.EnvironmentContributingAction;
import hudson.model.InvisibleAction;
import hudson.model.AbstractBuild;

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
 *         Class action to push a variable in the context for to retrive
 *         it between the different steps of a build
 */
public class PublishEnvVar extends InvisibleAction implements
		EnvironmentContributingAction {

	private String key;

	private String value;

	public PublishEnvVar(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public void buildEnvVars(AbstractBuild<?, ?> build, EnvVars env) {
		env.put(key, value);
	}

}
