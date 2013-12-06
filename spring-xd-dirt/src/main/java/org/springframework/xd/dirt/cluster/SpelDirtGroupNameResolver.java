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

package org.springframework.xd.dirt.cluster;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.Assert;


/**
 * Evaluate the node attributes using a Spel expression to classify the node into a group.
 * 
 * The context for the evalution is the Map<String, Object> present in the node's ResourceOffer
 * 
 * @author Mark Pollack
 */
public class SpelDirtGroupNameResolver implements DirtGroupNameResolver {

	private final Log logger = LogFactory.getLog(this.getClass());

	private ExpressionParser parser = new SpelExpressionParser();

	private final String expression;

	private final String groupName;

	/**
	 * Create the resolver with the Spel expression and group name.
	 * 
	 * @param expression Spel expression, e.g. groupName == 'pepsi'
	 * @param groupName group name to assign if the expression evaluates to true, cannot be null
	 */
	public SpelDirtGroupNameResolver(String expression, String groupName) {
		Assert.notNull(expression, "expression cannot be null");
		Assert.notNull(groupName, "groupName cannot be null");
		this.expression = expression;
		this.groupName = groupName;
	}

	@Override
	public String resolve(DirtContainerNode node) {
		boolean matched = false;
		try {
			Expression exp = parser.parseExpression(expression);
			EvaluationContext context = new StandardEvaluationContext(node.getResourceOffer().getAttributes());
			matched = exp.getValue(context, Boolean.class);
		}
		catch (Exception e) {
			logger.error("Could not resolve container node to group. Expression = [" + expression + "], Context = "
					+ node.getResourceOffer().getAttributes());
		}
		if (matched) {
			return groupName;
		}
		return null;
	}

}
