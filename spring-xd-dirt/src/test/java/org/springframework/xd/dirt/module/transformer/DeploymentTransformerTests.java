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

import java.util.List;

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
		XDStreamParser parser = new XDStreamParser(getModuleDefinitionRepository());
		List<ModuleDeploymentRequest> mdr = parser.parse("simple", "source | testprocessor1 | sink");
		DeploymentModel initialModel = new DeploymentModel(mdr);
		DefaultDeploymentTransformer transformer = new DefaultDeploymentTransformer();
		DeploymentManifest deploymentManifest = new DeploymentManifest();
		DeploymentModel theSameModel = transformer.transform(initialModel, deploymentManifest);
		assertEquals("deployment models not equal", theSameModel, initialModel);
	}
}
