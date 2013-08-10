/*
 * Copyright (C) 2005-2010 Alfresco Software Limited.
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
package org.alfresco.sample;

import javax.faces.context.FacesContext;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.bean.dialog.BaseDialogBean;
import org.alfresco.web.bean.repository.Repository;

/**
 * Bean implementation for the "Add Aspect Dialog"
 * 
 * @author gavinc
 */
public class AddAspectDialog extends BaseDialogBean
{
   protected String aspect;
   
   @Override
   protected String finishImpl(FacesContext context, String outcome) throws Exception
   {
      // get the space the action will apply to
      NodeRef nodeRef = this.browseBean.getActionSpace().getNodeRef();
      
      // resolve the fully qualified aspect name
      QName aspectToAdd = Repository.resolveToQName(this.aspect);
      
      // add the aspect to the space
      this.getNodeService().addAspect(nodeRef, aspectToAdd, null);
      
      // return the default outcome
      return outcome;
   }

   @Override
   public boolean getFinishButtonDisabled()
   {
      return false;
   }

   public String getAspect()
   {
      return aspect;
   }

   public void setAspect(String aspect)
   {
      this.aspect = aspect;
   }
}
