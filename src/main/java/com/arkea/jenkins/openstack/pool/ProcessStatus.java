package com.arkea.jenkins.openstack.pool;

import java.text.DateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openstack4j.model.heat.Event;
import org.openstack4j.model.heat.Stack;

import com.arkea.jenkins.openstack.client.OpenStack4jClient;
import com.arkea.jenkins.openstack.exception.utils.ExceptionUtils;
import com.arkea.jenkins.openstack.heat.configuration.TimersOS;
import com.arkea.jenkins.openstack.heat.i18n.Messages;
import com.arkea.jenkins.openstack.log.ConsoleLogger;

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
 *         Class check the status of a process on openstack via a loop
 * 
 */
public class ProcessStatus {

	private static EnumSet<StackStatus> failed = EnumSet.of(
			StackStatus.DELETE_FAILED, StackStatus.CREATE_FAILED,
			StackStatus.UNDEFINED, StackStatus.FAILED, StackStatus.TIMEOUT);

	/**
	 * Check a status of an operation openstack via a loop
	 * 
	 * @param status
	 *            status to check
	 * @param client
	 *            client to access at OpenStack
	 * @param cLog
	 *            logger
	 * @param originalStack
	 *            stack to test
	 * @param timersOS
	 *            timeout
	 * @return a boolean result for the action
	 */
	public static boolean checkStackStatus(StackStatus status,
			OpenStack4jClient client, ConsoleLogger cLog, Stack originalStack,
			TimersOS timersOS) {

		DateFormat dateFormat = DateFormat.getDateTimeInstance(
				DateFormat.MEDIUM, DateFormat.MEDIUM);

		boolean rtn = true;
		try {
			long startTime = System.currentTimeMillis();
			boolean taskComplete = false;

			cLog.logInfo(Messages.operation_begin(dateFormat.format(new Date(
					startTime))));
			while (!taskComplete
					&& System.currentTimeMillis() - startTime < timersOS
							.getTimeoutProcessInMS()) {

				// Wait to succeed the command because the caller launches the
				// action on OS
				if (!taskComplete) {
					Thread.sleep(timersOS.getPollingStatusInMS());
				}

				Stack stack = client.getDetails(originalStack.getName(),
						originalStack.getId());
				cLog.logInfo(getTime(System.currentTimeMillis() - startTime)
						+ Messages.stack_waiting(originalStack.getName(),
								stack.getStatus()));
				if (!stack.getStackStatusReason().contains("started")) {
					StackStatus stackStatus = StackStatus.getEnum(stack
							.getStatus());
					cLog.logInfo(stack.getStackStatusReason());
					if (stackStatus.equals(status)) {
						taskComplete = true;
					} else if (failed.contains(stackStatus)) {
						cLog.logError(Messages.status_error(status,
								originalStack.getName()));
						cLog.logError(stack.getStackStatusReason());
						taskComplete = true;
						rtn = false;
						if (cLog.isDebug()) {
							printEvents(stack, client, cLog);
						}
					}
				}
			}
			if (System.currentTimeMillis() - startTime > timersOS
					.getTimeoutProcessInMS()) {
				cLog.logError(Messages.operation_timeout(originalStack
						.getName()));
				rtn = false;
			}
			long finished = System.currentTimeMillis();
			cLog.logInfo(Messages.operation_finished(dateFormat
					.format(new Date(finished))));
			cLog.logInfo(Messages.operation_duration(getTime(finished
					- startTime)));
		} catch (Exception e) {
			cLog.logError(Messages.stack_failed(originalStack.getName())
					+ ExceptionUtils.getStackTrace(e));
			rtn = false;
		}
		return rtn;

	}

	private static String getTime(long time) {
		return String.format(
				"%d min, %d sec",
				TimeUnit.MILLISECONDS.toMinutes(time),
				TimeUnit.MILLISECONDS.toSeconds(time)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
								.toMinutes(time)));
	}

	private static void printEvents(Stack stack, OpenStack4jClient client,
			ConsoleLogger cLog) {
		try {
			List<? extends Event> events = client.getEvents(stack);
			for (Event event : events) {
				cLog.logDebug(Messages.event_detail(event.getResourceName(),
						event.getResourceStatus(), event.getReason()));
			}
		} catch (Exception e) {
			cLog.logError(Messages.event_failed(stack.getName())
					+ e.getMessage() + ExceptionUtils.getStackTrace(e));
		}
	}
}
