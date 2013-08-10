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

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.web.app.Application;
import org.alfresco.web.bean.actions.handlers.BaseActionHandler;
import org.alfresco.web.bean.wizard.IWizardBean;

/**
 * Action handler for the "tag" action.
 * 
 * @author gavinc
 */
public class TagActionHandler extends BaseActionHandler
{
   public static final String PROP_TAGS = "tags";
   
   public String getJSPPath()
   {
      return "/jsp/extension/tag.jsp";
   }

   public void prepareForSave(Map<String, Serializable> actionProps,
         Map<String, Serializable> repoProps)
   {
      repoProps.put(TagActionExecuter.PARAM_TAGS, (String)actionProps.get(PROP_TAGS));
   }

   public void prepareForEdit(Map<String, Serializable> actionProps,
         Map<String, Serializable> repoProps)
   {
      actionProps.put(PROP_TAGS, (String)repoProps.get(TagActionExecuter.PARAM_TAGS));
   }

   public String generateSummary(FacesContext context, IWizardBean wizard,
         Map<String, Serializable> actionProps)
   {
      String tags = (String)actionProps.get(PROP_TAGS);
      if (tags == null)
      {
         tags = "";
      }
      
      return MessageFormat.format(Application.getMessage(context, "add_tags"), 
            new Object[] {tags});
   }
}
