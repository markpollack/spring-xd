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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.xd.dirt.module.ModuleDeploymentRequest;


/**
 * Default implementation of DeploymentTransformer
 * 
 * @author Mark Pollack
 */
public class DefaultDeploymentTransformer implements DeploymentTransformer {

	@Override
	public DeploymentModel transform(DeploymentModel deploymentModel, DeploymentManifest deploymentManifest) {

		DeploymentModel transformedModel = deploymentModel.deepCopy();

		if (!deploymentManifest.hasEmptyStreamColocationSpecMap()) {
			Map<String, StreamColocationSpec> streamCompositionSpecMap = deploymentManifest.getStreamColocationSpecMap();
			for (Map.Entry<String, StreamColocationSpec> entry : streamCompositionSpecMap.entrySet()) {
				String streamName = entry.getKey();
				StreamColocationSpec streamCompositionSpec = entry.getValue();
				tagModulesToColocate(streamName, streamCompositionSpec, transformedModel);
			}

		}
		return transformedModel;
	}

	/**
	 * @param streamName
	 * @param deploymentModel
	 * @return
	 */
	private List<ModuleDeploymentRequest> tagModulesToColocate(String streamName,
			StreamColocationSpec streamCompositionSpec, DeploymentModel deploymentModel) {
		List<ModuleDeploymentRequest> matchingModules = new ArrayList<ModuleDeploymentRequest>();

		List<ModuleDeploymentRequest> modulesToSearch = deploymentModel.getDeploymentRequests();
		for (ModuleDeploymentRequest moduleDeploymentRequest : modulesToSearch) {
			// Does this colocation data apply to this stream?
			if (moduleDeploymentRequest.getGroup().equals(streamName)) {
				Map<String, List<String>> compositionManifestMap = streamCompositionSpec.getColocationManifestMap();
				for (Map.Entry<String, List<String>> entry : compositionManifestMap.entrySet()) {
					String colocationGroupName = entry.getKey();
					List<String> modulesToCollocate = entry.getValue();
					if (modulesToCollocate.contains(moduleDeploymentRequest.getModule())) {
						moduleDeploymentRequest.setColocationGroupName(colocationGroupName);
					}

				}
			}
		}
		return null;
	}
}
