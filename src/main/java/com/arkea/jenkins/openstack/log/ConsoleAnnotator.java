package com.arkea.jenkins.openstack.log;

import hudson.console.LineTransformationOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

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
 *         Class to intercept the write of log in the standart output
 * 
 */
public class ConsoleAnnotator extends LineTransformationOutputStream {

	private OutputStream out;
	private String pattern;

	public ConsoleAnnotator(OutputStream out, String pattern) {
		this.out = out;
		this.pattern = pattern;
	}

	public String getPattern() {
		return pattern;
	}

	@Override
	protected void eol(byte[] b, int len) throws IOException {
		String line = Charset.defaultCharset()
				.decode(ByteBuffer.wrap(b, 0, len)).toString();
		if (line.startsWith(this.pattern)) {
			new HOTConsoleNote().encodeTo(out);
		}
		out.write(b, 0, len);
	}

	@Override
	public void close() throws IOException {
		super.close();
		out.close();
	}

}
