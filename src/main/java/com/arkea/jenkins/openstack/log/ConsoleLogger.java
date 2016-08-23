package com.arkea.jenkins.openstack.log;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Map;

import com.arkea.jenkins.openstack.exception.utils.ExceptionUtils;
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
 *         Class to overload the render of the log
 * 
 */
public class ConsoleLogger {

	private PrintStream printStream;
	private ConsoleAnnotator annotator;
	private boolean debug;

	public ConsoleLogger(PrintStream printStream, String pattern,
			boolean debug) {
		this.annotator = new ConsoleAnnotator(printStream, pattern);
		this.printStream = printStream;
		this.debug = debug;
	}

	public ConsoleAnnotator getAnnotator() {
		return annotator;
	}

	public void logDebug(String message) {
		if (debug) {
			logAnnot(this.annotator.getPattern() + Messages.log_debug(),
					message);
		}
	}

	public void logDebugMap(String title, Map<String, String> map) {
		if (debug) {
			logAnnot(this.annotator.getPattern() + Messages.log_debug(), title);
			for (String key : map.keySet()) {
				logAnnot(this.annotator.getPattern() + Messages.log_debug(),
						Messages.log_map(key, map.get(key)));
			}
		}
	}

	public void logInfo(String message) {
		logAnnot(this.annotator.getPattern() + Messages.log_info(), message);
	}

	public void logWarn(String message) {
		logAnnot(this.annotator.getPattern() + Messages.log_warn(), message);
	}

	public void logError(String message) {
		logAnnot(this.annotator.getPattern() + Messages.log_error(), message);
	}

	public void logAnnot(String prefix, String message) {
		byte[] msg = (prefix + message + "\n").getBytes(Charset
				.defaultCharset());
		try {
			annotator.eol(msg, msg.length);
		} catch (IOException e) {
			printStream.println(Messages.consoleLogger_error() + e.getMessage()
					+ ExceptionUtils.getStackTrace(e));
		}
	}

	public void log(String message) {
		printStream.println(message);
	}

	public boolean isDebug() {
		return debug;
	}
}
