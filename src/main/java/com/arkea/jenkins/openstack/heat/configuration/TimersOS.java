package com.arkea.jenkins.openstack.heat.configuration;

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
 *         Class to manage the differents timers (Pool, Timeout...) used
 *         to interact with OpenStack. The default time is the second.
 *
 */
public class TimersOS {

	private long pollingStatus = 20;
	private long timeoutProcess = 900;

	public long getPollingStatus() {
		return pollingStatus;
	}

	public long getTimeoutProcess() {
		return timeoutProcess;
	}

	public long getPollingStatusInMS() {
		return pollingStatus * 1000;
	}

	public long getTimeoutProcessInMS() {
		return timeoutProcess * 1000;
	}

	public long getTimeoutProcessInMin() {
		return timeoutProcess / 60;
	}

	public void setPollingStatus(long pollingStatus) {
		this.pollingStatus = pollingStatus;
	}

	public void setTimeoutProcess(long timeoutProcess) {
		this.timeoutProcess = timeoutProcess;
	}
}
