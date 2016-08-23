package com.arkea.jenkins.openstack.log;

import com.arkea.jenkins.openstack.heat.i18n.Messages;

import hudson.Extension;
import hudson.MarkupText;
import hudson.console.ConsoleAnnotationDescriptor;
import hudson.console.ConsoleAnnotator;
import hudson.console.ConsoleNote;
import hudson.model.Run;

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
 *         Class to push log in color in the standard output log
 * 
 */
public class HOTConsoleNote extends ConsoleNote<Run<?, ?>> {

	private static final long serialVersionUID = 1L;

	@Override
	public ConsoleAnnotator<Run<?, ?>> annotate(Run<?, ?> context,
			MarkupText text, int charPos) {
		if (text.getText().contains(Messages.log_error())) {
			text.addMarkup(0, text.length(),
					"<span style=\"font-weight: bold; color:red\">", "</span>");
		}
		if (text.getText().contains(Messages.log_warn())) {
			text.addMarkup(0, text.length(), "<span style=\"color:#FF8700\">",
					"</span>");
		}
		if (text.getText().contains(Messages.log_info())) {
			text.addMarkup(0, text.length(), "<span style=\"color:#008BB8\">",
					"</span>");
		}
		if (text.getText().contains(Messages.log_debug())) {
			text.addMarkup(0, text.length(), "<span style=\"color:#D0D0D0\">",
					"</span>");
		}
		return null;
	}

	@Extension
	public static class DescriptorImpl extends
			ConsoleAnnotationDescriptor {

		public String getDisplayName() {
			return "OpenStack HOT Console Note";
		}
	}

}
