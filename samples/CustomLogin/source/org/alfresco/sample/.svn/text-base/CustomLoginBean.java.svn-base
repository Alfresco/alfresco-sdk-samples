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

import java.util.Date;

import org.alfresco.web.bean.LoginBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CustomLoginBean extends LoginBean
{
   private static final Log logger = LogFactory.getLog(CustomLoginBean.class);

   @Override
   public String login()
   {
      String outcome = super.login();
      
      // log to the console who logged in and when
      String username = this.getUsername();
      if (username == null)
      {
         username = "Guest";
      }
      
      logger.info(username + " has logged in at " + new Date());
      
      return outcome;
   }

   @Override
   public String logout()
   {
      String outcome = super.logout();
      
      // log to the console who logged out and when
      String username = this.getUsername();
      if (username == null)
      {
         username = "Guest";
      }
      
      logger.info(username + " logged out at " + new Date());
      
      return outcome;
   }
}
