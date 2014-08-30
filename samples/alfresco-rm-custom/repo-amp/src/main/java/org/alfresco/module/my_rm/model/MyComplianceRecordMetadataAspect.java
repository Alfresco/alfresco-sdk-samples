/*
 * Copyright (C) 2005-2014 Alfresco Software Limited.
 *
 * This file is part of Alfresco
 *
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 */
package org.alfresco.module.my_rm.model;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.Behaviour.NotificationFrequency;
import org.alfresco.repo.policy.annotation.Behaviour;
import org.alfresco.repo.policy.annotation.BehaviourBean;
import org.alfresco.repo.policy.annotation.BehaviourKind;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.alfresco.util.GUID;

/**
 * Behaviour bean for myrm:myComplianceRecordMetadata aspect.
 *
 * @author Roy Wetherall
 */
@BehaviourBean
(
   defaultType="myrm:myComplianceRecordMetaData"
)
public class MyComplianceRecordMetadataAspect implements NodeServicePolicies.OnAddAspectPolicy, MyRMModel
{
	/** node service */
	private NodeService nodeService;

	/**
	 * @param nodeService	node service
	 */
	public void setNodeService(NodeService nodeService)
	{
		this.nodeService = nodeService;
	}

	/**
	 * Behaviour executed at the end of the transation when myrm:myComplianceRecordMetadata aspect
	 * is added.
	 *
	 * @see org.alfresco.repo.node.NodeServicePolicies.OnAddAspectPolicy
	 *      	#onAddAspect(org.alfresco.service.cmr.repository.NodeRef, org.alfresco.service.namespace.QName)
	 */
	@Override
	@Behaviour
	(
       kind=BehaviourKind.CLASS,
       notificationFrequency=NotificationFrequency.TRANSACTION_COMMIT
	)
	public void onAddAspect(NodeRef nodeRef, QName aspectTypeQName)
	{
		// ensure the node hasn't been deleted and isn't pending delete
		if (nodeService.exists(nodeRef) &&
		    !nodeService.hasAspect(nodeRef, ContentModel.ASPECT_PENDING_DELETE))
		{
			// generate the complianceId
			String complianceId = GUID.generate();

			// set the compliance id
			nodeService.setProperty(nodeRef, PROP_MY_COMPLIANCE_ID, complianceId);
		}
	}
}
