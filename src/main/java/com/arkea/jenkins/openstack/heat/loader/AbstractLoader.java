package com.arkea.jenkins.openstack.heat.loader;

import hudson.DescriptorExtensionList;
import hudson.ExtensionPoint;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.Descriptor.FormException;
import jenkins.model.Jenkins;

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
 *         Abstract class define how to load the Heat Orchestration
 *         Template (HOT)
 */
public abstract class AbstractLoader implements Describable<AbstractLoader>,
		ExtensionPoint {

	/**
	 * Method to retrieve the template's name from a source
	 *
	 * @return the array of the template's name
	 */
	public abstract String[] getHots();

	/**
	 * Method to retrieve the env's name from a source
	 *
	 * @return the array of the env's name
	 */
	public abstract String[] getEnvs();

	/**
	 * Method to get the Default full Env filename with extension
	 *
	 * @return the Default Env filename with extension
	 */
	public abstract String getDefaultEnvFileName();

	/**
	 * Method to get the content of a specific Env File
	 *
	 * @param envName
	 *            unique identifier associated with Env File
	 * @return the content from the Env File
	 */
	public abstract String getEnv(String envName);

	/**
	 * Method to get the content of a specific Heat Orchestration Template (HOT)
	 *
	 * @param hotName
	 *            unique identifier associated with a Heat Orchestration
	 *            Template (HOT)
	 * @return the content from the Heat Orchestration Template (HOT)
	 */
	public abstract String getHot(String hotName);

	/**
	 * Method to check the differents inputs
	 * 
	 * @return true if no errors
	 * 
	 * @throws FormException
	 *             exception for the first formfield doesn't set
	 */
	public abstract boolean checkData() throws FormException;

	/**
	 * @param hotName
	 *            unique identifier associated with a Heat Orchestration
	 *            Template (HOT)
	 * @return the fullname equal pathname is a local template or url is a http
	 *         template
	 */
	public abstract String getFullPathHot(String hotName);

	/**
	 * @param envName
	 *            unique identifier associated with an env Heat Orchestration
	 *            Template (HOT)
	 * @return the fullname equal pathname is a local template or url is a http
	 *         template
	 */
	public abstract String getFullPathEnv(String envName);

	/**
	 * @return the list of loaders
	 */
	public static DescriptorExtensionList<AbstractLoader, AbstractLoaderDescriptor> all() {
		return Jenkins.getInstance()
				.<AbstractLoader, AbstractLoaderDescriptor> getDescriptorList(
						AbstractLoader.class);
	}

	@Override
	public AbstractLoaderDescriptor getDescriptor() {
		return (AbstractLoaderDescriptor) Jenkins.getInstance().getDescriptor(
				getClass());
	}

	public abstract static class AbstractLoaderDescriptor extends
			Descriptor<AbstractLoader> {
		protected AbstractLoaderDescriptor(
				Class<? extends AbstractLoader> clazz) {
			super(clazz);
		}

		protected AbstractLoaderDescriptor() {
		}
	}

}
