package com.arkea.jenkins.openstack.heat.loader;

import hudson.Extension;
import hudson.model.Descriptor.FormException;
import hudson.util.FormValidation;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import com.arkea.jenkins.openstack.exception.utils.FormExceptionUtils;
import com.arkea.jenkins.openstack.heat.i18n.Messages;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

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
 *         Load Heat Orchestration Template (HOT) from a server HTTP in
 *         format JSON
 */
public class LoaderHttpREST extends AbstractLoader {

	/** Logger. */
	private static Logger LOG = Logger
			.getLogger(LoaderHttpREST.class.getName());

	private String urlListHot;

	private String urlDetailHot;

	/**
	 * Files Env activate
	 */
	private boolean checkEnv = false;

	private String urlListEnv;

	private String urlDetailEnv;

	private String defaultEnv;

	@DataBoundConstructor
	public LoaderHttpREST(String urlListHot, String urlDetailHot,
			JSONObject httpRESTEnv) {
		this.urlListHot = urlListHot;
		this.urlDetailHot = urlDetailHot;
		if (httpRESTEnv != null) {
			if (httpRESTEnv instanceof JSONObject) {
				this.checkEnv = true;
				this.urlListEnv = ((JSONObject) httpRESTEnv)
						.getString("urlListEnv");
				this.urlDetailEnv = ((JSONObject) httpRESTEnv)
						.getString("urlDetailEnv");
				this.defaultEnv = ((JSONObject) httpRESTEnv)
						.getString("defaultEnv");
			}
		}
	}

	public String getUrlListHot() {
		return urlListHot;
	}

	public String getUrlDetailHot() {
		return urlDetailHot;
	}

	public boolean isCheckEnv() {
		return checkEnv;
	}

	public String getUrlListEnv() {
		return urlListEnv;
	}

	public String getUrlDetailEnv() {
		return urlDetailEnv;
	}

	public String getDefaultEnv() {
		return defaultEnv;
	}

	// @Override
	public String[] getHots() {
		return LoaderHttpRESTDescriptor.getListFiles(this.urlListHot);
	}

	// @Override
	public String getHot(String hotName) {
		return getFile(this.urlDetailHot, hotName);
	}

	private String getFile(String path, String name) {

		StringBuilder contents = new StringBuilder();

		Scanner scanner = null;
		try {
			scanner = new Scanner(new URL(path + "/" + name).openStream());

			while (scanner.hasNextLine()) {
				contents.append(scanner.nextLine()).append('\n');
			}

		} catch (IOException e) {
			LOG.log(Level.SEVERE, Messages.file_notFound(path + "/" + name),
					e.fillInStackTrace());
			contents = new StringBuilder();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}

		return contents.toString();
	}

	@Override
	public boolean checkData() throws FormException {
		if (Strings.isNullOrEmpty(urlListHot)) {
			throw FormExceptionUtils.getFormException(
					Messages.urlListHot_label(), Messages.urlListHot_name());
		} else if (Strings.isNullOrEmpty(urlDetailHot)) {
			throw FormExceptionUtils
					.getFormException(Messages.urlDetailHot_label(),
							Messages.urlDetailHot_name());
		} else if (isCheckEnv()) {
			if (Strings.isNullOrEmpty(urlListEnv)) {
				throw FormExceptionUtils
						.getFormException(Messages.urlListEnv_label(),
								Messages.urlListEnv_name());
			} else if (Strings.isNullOrEmpty(urlDetailEnv)) {
				throw FormExceptionUtils.getFormException(
						Messages.urlDetailEnv_label(),
						Messages.urlDetailEnv_name());
			}
		}

		return true;
	}

	@Extension
	public static class LoaderHttpRESTDescriptor extends
			AbstractLoaderDescriptor {

		@Override
		public String getDisplayName() {
			return "LoaderHttpREST";
		}

		/**
		 * Test if the url Hot is valid
		 * 
		 * @param urlListHot
		 *            to test
		 * @return the result of the test
		 * @throws IOException
		 *             if the url isn't catched
		 */
		public FormValidation doTestUrlHot(
				@QueryParameter("urlListHot") String urlListHot)
				throws IOException {

			return doTestUrl(urlListHot);
		}

		/**
		 * Test if the url Env is valid
		 * 
		 * @param urlListEnv
		 *            to test
		 * @return the result of the test
		 * @throws IOException
		 *             if the url isn't catched
		 */
		public FormValidation doTestUrlEnv(
				@QueryParameter("urlListEnv") String urlListEnv)
				throws IOException {

			return doTestUrl(urlListEnv);
		}

		/**
		 * Test if the url is valid
		 * 
		 * @param url
		 *            to test
		 * @return the result of the test
		 * @throws IOException
		 */
		private FormValidation doTestUrl(String url) throws IOException {
			if (Strings.isNullOrEmpty(url)) {
				return FormValidation.warning(Messages.input_filled(Messages
						.urlListHot_label()));
			}

			String[] data = getListFiles(url);

			if (data.length == 0) {
				return FormValidation.error(Messages
						.formValidation_errorUrl(url));
			} else {
				return FormValidation.ok(Messages.formValidation_success());
			}
		}

		protected static String[] getListFiles(String url) {
			String[] list = new String[0];
			try {
				list = new ObjectMapper().readValue(new URL(url),
						new TypeReference<String[]>() {
						});
			} catch (IOException e) {
				LOG.log(Level.SEVERE, Messages.file_notFound(url),
						e.fillInStackTrace());
			}
			return list;
		}
	}

	@Override
	public String getFullPathHot(String hotName) {
		return urlDetailHot + "/" + hotName;
	}

	@Override
	public String getFullPathEnv(String envFile) {
		return urlDetailEnv + "/" + envFile;
	}

	@Override
	public String[] getEnvs() {
		return LoaderHttpRESTDescriptor.getListFiles(this.urlListEnv);
	}

	@Override
	public String getEnv(String envName) {
		return getFile(this.urlDetailEnv, envName);
	}

	@Override
	public String getDefaultEnvFileName() {
		return urlDetailEnv + "/" + defaultEnv;
	}
}
