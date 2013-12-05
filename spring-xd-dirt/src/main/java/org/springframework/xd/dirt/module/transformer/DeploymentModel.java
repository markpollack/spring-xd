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

import java.util.List;

import org.springframework.util.Assert;
import org.springframework.xd.dirt.module.ModuleDeploymentRequest;


/**
 * Represents how the jobs and streams will be deployed to the nodes.
 * 
 * 
 * @author Mark Pollack
 */
public class DeploymentModel {

	// TODO - will likely grow in complexity, encapsulate the current list of module deployment requests
	// TODO - the labels for each module are not captured in the deployment request - this will need to be taken into
	// account.

	// ModuleDeploymentRequest was picked over ModuleDefintion since it conceptually 'closer' to the deployment as it
	// has the channel information and the basis for deployment in the current code base.

	private final List<ModuleDeploymentRequest> deploymentRequests;


	public DeploymentModel(List<ModuleDeploymentRequest> deploymentRequests) {
		Assert.notNull(deploymentRequests, "deploymentRequests cannot be null");
		this.deploymentRequests = deploymentRequests;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deploymentRequests == null) ? 0 : deploymentRequests.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeploymentModel other = (DeploymentModel) obj;
		if (deploymentRequests == null) {
			if (other.deploymentRequests != null)
				return false;
		}
		else if (!deploymentRequests.equals(other.deploymentRequests))
			return false;
		return true;
	}

	public List<ModuleDeploymentRequest> getDeploymentRequests() {
		return deploymentRequests;
	}


}
