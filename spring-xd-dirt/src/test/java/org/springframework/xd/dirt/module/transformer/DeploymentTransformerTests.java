/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.xd.dirt.module.transformer;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import org.springframework.xd.dirt.module.ModuleDeploymentRequest;
import org.springframework.xd.dirt.stream.StreamTestSupport;
import org.springframework.xd.dirt.stream.XDStreamParser;


/**
 * 
 * @author Mark Pollack
 */
public class DeploymentTransformerTests extends StreamTestSupport {


	@Test
	public void noopTransformation() {
		List<ModuleDeploymentRequest> mdr = getParser().parse("simple", "source | testprocessor1 | sink");
		DeploymentModel initialModel = new DeploymentModel(mdr);
		DefaultDeploymentTransformer transformer = new DefaultDeploymentTransformer();
		DeploymentManifest deploymentManifest = new DeploymentManifest();
		DeploymentModel theSameModel = transformer.transform(initialModel, deploymentManifest);
		assertEquals("deployment models not equal", theSameModel, initialModel);
	}

	private XDStreamParser getParser() {
		return new XDStreamParser(getModuleDefinitionRepository());
	}

	@Test
	public void tagColocatedModule() {
		List<ModuleDeploymentRequest> mdr = getParser().parse("simple",
				"source | testprocessor1 | testprocessor2 | sink");
		DeploymentModel initialModel = new DeploymentModel(mdr);
		DefaultDeploymentTransformer transformer = new DefaultDeploymentTransformer();

		StreamColocationSpec colocationSpec = new StreamColocationSpec();
		List<String> modulesToColocate = new ArrayList<String>();
		modulesToColocate.add("testprocessor1");
		modulesToColocate.add("testprocessor2");
		colocationSpec.add("group1", modulesToColocate);
		DeploymentManifest deploymentManifest = new DeploymentManifest();
		deploymentManifest.addColocation("simple", colocationSpec);

		// DeploymentManifestBuilder deploymentManifestBuilder = new DeploymentManifestBuilder();

		// deploymentManifestBuilder.forStream("simple").colocate("testprocessor1",
		// "testprocessor2").withLabel("group1");

		DeploymentModel transformedModel = transformer.transform(initialModel, deploymentManifest);

		List<ModuleDeploymentRequest> requests = getDeploymentRequestsWithColocationGroupName("group1",
				transformedModel);

		assertEquals(2, requests.size());
		assertEquals("group1", requests.get(0).getColocationGroupName());
		assertEquals("group1", requests.get(1).getColocationGroupName());


	}

	/**
	 * @param string
	 * @return
	 */
	private List<ModuleDeploymentRequest> getDeploymentRequestsWithColocationGroupName(String colocationGroupName,
			DeploymentModel deploymentModel) {
		List<ModuleDeploymentRequest> matchingModules = new ArrayList<ModuleDeploymentRequest>();
		List<ModuleDeploymentRequest> modulesToSearch = deploymentModel.getDeploymentRequests();
		for (ModuleDeploymentRequest moduleDeploymentRequest : modulesToSearch) {
			if (StringUtils.equals(moduleDeploymentRequest.getColocationGroupName(), colocationGroupName)) {
				matchingModules.add(moduleDeploymentRequest);
			}
		}

		return matchingModules;
	}
}
