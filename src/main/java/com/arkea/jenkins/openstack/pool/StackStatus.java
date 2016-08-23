package com.arkea.jenkins.openstack.pool;

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
 *         Status possible for a stack
 * 
 */
public enum StackStatus {
	UNKNOWN("UNKNOWN"), FAILED("FAILED"), CREATE_FAILED("CREATE_FAILED"), UNDEFINED(
			"UNDEFINED"), TIMEOUT("TIMEOUT"), DELETE_COMPLETE("DELETE_COMPLETE"), CREATE_COMPLETE(
			"CREATE_COMPLETE"), DELETE_FAILED("DELETE_FAILED"), CREATE_IN_PROGRESS(
			"CREATE_IN_PROGRESS"), DELETE_IN_PROGRESS("DELETE_IN_PROGRESS");

	private String label;

	StackStatus(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return label;
	}

	public static StackStatus getEnum(String status) {
		try {
			return StackStatus.valueOf(status);
		} catch (IllegalArgumentException iae) {
			return StackStatus.UNKNOWN;
		}
	}

}
