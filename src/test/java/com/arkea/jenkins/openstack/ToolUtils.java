package com.arkea.jenkins.openstack;

import java.util.List;

import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
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
public class ToolUtils {
	
	public static HtmlSelect getSelect(HtmlForm form, String value, String label) {
		List<HtmlElement> options = form.getElementsByAttribute("option", "value", value);
		HtmlSelect rtn = null;
		for(HtmlElement option : options) {
			DomNode child = option.getFirstChild();
			if(label.equals(child.getNodeValue())) {
				rtn = (HtmlSelect) option.getParentNode();
				break;
			}
		}
		return rtn;
	}
}
