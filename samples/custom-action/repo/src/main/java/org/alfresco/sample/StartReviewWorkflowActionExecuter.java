package org.alfresco.sample;

import java.io.Serializable;
import java.util.*;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.repo.workflow.WorkflowModel;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ActionService;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.cmr.workflow.WorkflowDefinition;
import org.alfresco.service.cmr.workflow.WorkflowPath;
import org.alfresco.service.cmr.workflow.WorkflowService;
import org.alfresco.service.namespace.QName;
import org.apache.log4j.Logger;

public class StartReviewWorkflowActionExecuter extends ActionExecuterAbstractBase {
    public static final String NAME = "start-review-workflow";

    public static final String REVIEW_WORKFLOW_NAME = "activiti$activitiReview";
    private Logger logger = Logger.getLogger(StartReviewWorkflowActionExecuter.class);

    private WorkflowService workflowService;
    private NodeService nodeService;
    private PersonService personService;

    @Override
    protected void executeImpl(Action action, NodeRef actionedUponNodeRef) {

        // Lookup the creator of the document
        // The workflow will be assigned to this user
        String creator = (String) nodeService.getProperty(actionedUponNodeRef, ContentModel.PROP_CREATOR);

        // Check if the workflow exists
        WorkflowDefinition workflow = workflowService.getDefinitionByName(REVIEW_WORKFLOW_NAME);
        if (workflow == null) {
            logger.debug("Workflow " + REVIEW_WORKFLOW_NAME + " doesn't exist.");
            // TODO: Make error message?
            action.setParameterValue(ActionExecuterAbstractBase.PARAM_RESULT, "failure");
        }

        // Set the workflow package
        // this contains the files within the workflow
        NodeRef workflowPackage = workflowService.createPackage(null);
        ChildAssociationRef childAssoc = nodeService.getPrimaryParent(actionedUponNodeRef);
        nodeService.addChild(workflowPackage, actionedUponNodeRef, WorkflowModel.ASSOC_PACKAGE_CONTAINS, childAssoc.getQName());


        // Set the parameters for the workflow
        Map<QName, Serializable> parameters = new HashMap<QName, Serializable>();
        parameters.put(WorkflowModel.ASSOC_PACKAGE, workflowPackage);


        parameters.put(WorkflowModel.ASSOC_ASSIGNEE, personService.getPerson(creator));

        String documentName = (String) nodeService.getProperty(actionedUponNodeRef, ContentModel.PROP_NAME);

        // Add description
        parameters.put(WorkflowModel.PROP_WORKFLOW_DESCRIPTION, "Review the document: " + documentName);

        WorkflowPath workflowPath = workflowService.startWorkflow(workflow.getId(), parameters);
        action.setParameterValue(ActionExecuterAbstractBase.PARAM_RESULT, "success");


    }

    @Override
    protected void addParameterDefinitions(List<ParameterDefinition> paramList) {
    }

    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    public void setWorkflowService(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }
}
