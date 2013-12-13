/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.xd.dirt.module;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.xd.module.ModuleType;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * 
 * @author Mark Fisher
 * @author Gary Russell
 * @author Luke Taylor
 * @author Ilayaperumal Gopinathan
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({ @Type(name = "simple", value = ModuleDeploymentRequest.class),
	@Type(name = "composite", value = CompositeModuleDeploymentRequest.class) })
public class ModuleDeploymentRequest implements Comparable<ModuleDeploymentRequest> {

	private volatile String module;

	private volatile String group;

	private volatile String colocationGroupName;

	private volatile String sourceChannelName;

	private volatile String sinkChannelName;

	private volatile int index;

	private volatile ModuleType type = null;

	private final Map<String, String> parameters = new HashMap<String, String>();

	private volatile boolean remove;

	private volatile boolean launch;


	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getGroup() {
		return group;
	}

	public String getColocationGroupName() {
		return colocationGroupName;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public ModuleType getType() {
		return type;
	}

	public void setType(ModuleType type) {
		this.type = type;
	}

	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}

	public void setParameter(String name, String value) {
		this.parameters.put(name, value);
	}

	public Map<String, String> getParameters() {
		return Collections.unmodifiableMap(this.parameters);
	}

	public String getSourceChannelName() {
		return sourceChannelName;
	}

	public void setSourceChannelName(String sourceChannelName) {
		this.sourceChannelName = sourceChannelName;
	}

	public String getSinkChannelName() {
		return sinkChannelName;
	}

	public void setSinkChannelName(String sinkChannelName) {
		this.sinkChannelName = sinkChannelName;
	}

	public boolean isLaunch() {
		return launch;
	}

	public void setLaunch(boolean launch) {
		this.launch = launch;
	}

	/**
	 * Tag this module deployment as belonging to a group.
	 * 
	 * @param colocationGroupName name of the group
	 */
	public void setColocationGroupName(String colocationGroupName) {
		this.colocationGroupName = colocationGroupName;
	}


	@Override
	public String toString() {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(this);
		}
		catch (Exception e) {
			return super.toString();
		}
	}

	public ModuleDeploymentRequest copy() {
		ModuleDeploymentRequest request = new ModuleDeploymentRequest();
		request.setGroup(this.getGroup());
		request.setIndex(this.getIndex());
		request.setLaunch(this.isLaunch());
		request.setModule(this.getModule());
		Map<String, String> params = getParameters();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			request.setParameter(entry.getKey(), entry.getValue());
		}
		request.setRemove(this.isRemove());
		request.setSinkChannelName(this.getSinkChannelName());
		request.setSourceChannelName(this.getSourceChannelName());
		request.setType(this.getType());
		return request;
	}

	@Override
	public int compareTo(ModuleDeploymentRequest o) {
		Assert.notNull(o, "ModuleDeploymentRequest must not be null");
		return Integer.valueOf(this.getIndex()).compareTo(Integer.valueOf(o.getIndex()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colocationGroupName == null) ? 0 : colocationGroupName.hashCode());
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + index;
		result = prime * result + (launch ? 1231 : 1237);
		result = prime * result + ((module == null) ? 0 : module.hashCode());
		result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
		result = prime * result + (remove ? 1231 : 1237);
		result = prime * result + ((sinkChannelName == null) ? 0 : sinkChannelName.hashCode());
		result = prime * result + ((sourceChannelName == null) ? 0 : sourceChannelName.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		ModuleDeploymentRequest other = (ModuleDeploymentRequest) obj;
		if (colocationGroupName == null) {
			if (other.colocationGroupName != null)
				return false;
		}
		else if (!colocationGroupName.equals(other.colocationGroupName))
			return false;
		if (group == null) {
			if (other.group != null)
				return false;
		}
		else if (!group.equals(other.group))
			return false;
		if (index != other.index)
			return false;
		if (launch != other.launch)
			return false;
		if (module == null) {
			if (other.module != null)
				return false;
		}
		else if (!module.equals(other.module))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		}
		else if (!parameters.equals(other.parameters))
			return false;
		if (remove != other.remove)
			return false;
		if (sinkChannelName == null) {
			if (other.sinkChannelName != null)
				return false;
		}
		else if (!sinkChannelName.equals(other.sinkChannelName))
			return false;
		if (sourceChannelName == null) {
			if (other.sourceChannelName != null)
				return false;
		}
		else if (!sourceChannelName.equals(other.sourceChannelName))
			return false;
		if (type != other.type)
			return false;
		return true;
	}


}
