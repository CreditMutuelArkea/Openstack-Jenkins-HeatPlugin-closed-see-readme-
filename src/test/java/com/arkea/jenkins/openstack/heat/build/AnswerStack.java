package com.arkea.jenkins.openstack.heat.build;

import java.io.Serializable;
import java.util.ArrayList;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.openstack4j.openstack.heat.domain.HeatStack;

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
public class AnswerStack implements Answer<Object>, Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<HeatStack> stacks;
	private int counter = 0;

	public AnswerStack(ArrayList<HeatStack> stacks) {
		this.stacks = stacks;
	}

	@Override
	public Object answer(InvocationOnMock invocation) throws Throwable {
		if (counter > stacks.size()) {
			throw new IllegalArgumentException("Size list stacks overloaded !");
		}
		counter++;
		return stacks.get(counter - 1);
	}

}
