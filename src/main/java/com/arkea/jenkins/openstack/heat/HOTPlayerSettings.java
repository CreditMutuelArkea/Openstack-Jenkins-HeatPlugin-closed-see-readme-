package com.arkea.jenkins.openstack.heat;

import hudson.Extension;

import java.util.ArrayList;
import java.util.List;

import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;

import com.arkea.jenkins.openstack.exception.utils.FormExceptionUtils;
import com.arkea.jenkins.openstack.heat.configuration.ProjectOS;
import com.arkea.jenkins.openstack.heat.configuration.TimersOS;
import com.arkea.jenkins.openstack.heat.i18n.Messages;
import com.arkea.jenkins.openstack.heat.loader.AbstractLoader;

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
 *         Class to configure the loader in the global configuration jenkins
 * 
 */
@Extension
public class HOTPlayerSettings extends GlobalConfiguration {

	/**
	 * Variable to configure the loader
	 */
	private AbstractLoader loader;

	/**
	 * Variables to configure the differents times
	 */
	private TimersOS timersOS = new TimersOS();

	/**
	 * List Project Openstack connection configuration
	 */
	private List<ProjectOS> projects = new ArrayList<ProjectOS>();

	public HOTPlayerSettings() {
		load();
	}

	@Override
	public boolean configure(StaplerRequest req, JSONObject formData)
			throws FormException {
		// Reset list project for the bindJSON
		projects = new ArrayList<ProjectOS>();
		req.bindJSON(this, formData);
		checkData();
		save();
		return true;
	}

	public AbstractLoader getLoader() {
		return loader;
	}

	public void setLoader(AbstractLoader loader) {
		this.loader = loader;
	}

	public long getPollingStatus() {
		return timersOS.getPollingStatus();
	}

	public void setPollingStatus(long pollingStatus) {
		timersOS.setPollingStatus(pollingStatus);
	}

	public long getTimeoutProcess() {
		return timersOS.getTimeoutProcess();
	}

	public void setTimeoutProcess(long timeoutProcess) {
		timersOS.setTimeoutProcess(timeoutProcess);
	}

	public TimersOS getTimersOS() {
		return timersOS;
	}

	public List<ProjectOS> getProjects() {
		return projects;
	}

	public void setProjects(List<ProjectOS> projects) {
		this.projects = projects;
	}

	/**
	 * Method to check the differents inputs configuration
	 * 
	 * @return true if no errors
	 * 
	 * @throws FormException
	 *             the first formfield doesn't set
	 */
	public boolean checkData() throws FormException {

		if (loader != null) {
			loader.checkData();
		} else {
			throw FormExceptionUtils.getFormException(Messages.loader_label(),
					Messages.loader_name());
		}
		if (timersOS.getPollingStatus() <= 0) {
			throw FormExceptionUtils.getFormException(
					Messages.pollingStatus_label(),
					Messages.pollingStatus_name());
		} else if (timersOS.getTimeoutProcess() <= 0) {
			throw FormExceptionUtils.getFormException(
					Messages.timeoutProcess_label(),
					Messages.timeoutProcess_name());
		} else if (projects.isEmpty()) {
			throw FormExceptionUtils.getFormException(
					Messages.projectConfiguration_label(),
					Messages.projectConfiguration_name());
		} else {
			for (ProjectOS project : projects) {
				project.checkData();
			}
		}

		return true;
	}

}