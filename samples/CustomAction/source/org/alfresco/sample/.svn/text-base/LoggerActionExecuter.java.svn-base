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

import java.util.List;

import org.alfresco.repo.action.ParameterDefinitionImpl;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.dictionary.DataTypeDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Logger action executer.
 * 
 * This action will log a message to the application log file at the level specified.
 * 
 * @author Roy Wetherall
 */
public class LoggerActionExecuter extends ActionExecuterAbstractBase
{
    /** The logger */
    private static Log logger = LogFactory.getLog("org.alfresco.sample"); 
    
    /** The name of the action */
    public static final String NAME = "logger-action";    
    
    /** The parameter names */
    public static final String PARAM_LOG_MESSAGE = "param-log-message";
    public static final String PARAM_LOG_LEVEL = "param-log-level";
    
    /**
     * This action will take the log message and log it at the provided log level.
     * 
     * If the log level is not provided the default will be INFO.
     * 
     * @see org.alfresco.repo.action.executer.ActionExecuterAbstractBase#executeImpl(org.alfresco.service.cmr.action.Action, org.alfresco.service.cmr.repository.NodeRef)
     */
    @Override
    protected void executeImpl(Action action, NodeRef actionedUponNodeRef)
    {
        // Get the log message parameter
        String logMessage = (String)action.getParameterValue(PARAM_LOG_MESSAGE);
        if (logMessage != null && logMessage.length() != 0)
        {
            // Get the log level (default to INFO)
            LogLevel logLevel = LogLevel.INFO;
            String logLevelParam = (String) action.getParameterValue(PARAM_LOG_LEVEL);
            if (logLevelParam != null && logLevelParam.length() != 0)
            {
                logLevel = LogLevel.valueOf(logLevelParam);
            }
            
            // Log the message based on the log level
            switch (logLevel)
            {
                case DEBUG:
                {
                    logger.debug(logMessage);
                    break;
                }
                case ERROR:
                {
                    logger.error(logMessage);
                    break;
                }
                case FATAL:
                {
                    logger.fatal(logMessage);
                    break;
                }
                case INFO:
                {
                    logger.info(logMessage);
                    break;
                }
                case TRACE:
                {
                    logger.trace(logMessage);
                    break;
                }
                case WARN:
                {
                    logger.warn(logMessage);
                    break;
                }
            }
        }
    }

    /**
     *  @see org.alfresco.repo.action.ParameterizedItemAbstractBase#addParameterDefinitions(java.util.List)
     */
    @Override
    protected void addParameterDefinitions(List<ParameterDefinition> paramList)
    {
        // Specify the parameters
        paramList.add(new ParameterDefinitionImpl(PARAM_LOG_MESSAGE, DataTypeDefinition.TEXT, true, getParamDisplayLabel(PARAM_LOG_MESSAGE)));
        paramList.add(new ParameterDefinitionImpl(PARAM_LOG_LEVEL, DataTypeDefinition.TEXT, false, getParamDisplayLabel(PARAM_LOG_LEVEL)));
    }
    
    /**
     * Helper enum to differentiate log levels
     */
    private enum LogLevel
    {
        DEBUG, ERROR, FATAL, INFO, WARN, TRACE
    }
}
