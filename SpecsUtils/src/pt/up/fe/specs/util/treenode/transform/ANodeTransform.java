/**
 * Copyright 2016 SPeCS.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. under the License.
 */

package pt.up.fe.specs.util.treenode.transform;

import java.util.List;
import java.util.stream.Collectors;

import pt.up.fe.specs.util.treenode.TreeNode;

public abstract class ANodeTransform<K extends TreeNode<K>> implements NodeTransform<K> {

    private final String type;
    private final List<K> operands;

    public ANodeTransform(String type, List<K> operands) {
	this.type = type;
	this.operands = operands;
    }

    @Override
    public String getType() {
	return type;
    }

    @Override
    public List<K> getOperands() {
	return operands;
    }

    @Override
    public String toString() {
	return getType() + " " + getOperands().stream().map(node -> Integer.toHexString(node.hashCode()))
		.collect(Collectors.joining(" "));
    }
}
