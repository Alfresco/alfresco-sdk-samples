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
// NOTE: if you change the package location of this class you will need to update the WS_SEURITY_INFO XML as for this example this class
//       doubles as the passwordCAllbackClass
package org.alfresco.sample.webservice;

import java.util.ArrayList;
import java.util.List;

import org.alfresco.webservice.repository.QueryResult;
import org.alfresco.webservice.repository.RepositoryServiceSoapBindingStub;
import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.types.Node;
import org.alfresco.webservice.types.Predicate;
import org.alfresco.webservice.types.Query;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.types.ResultSet;
import org.alfresco.webservice.types.ResultSetRow;
import org.alfresco.webservice.util.AuthenticationUtils;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.WebServiceFactory;

public class Query2 extends SamplesBase
{    
    /**
     * Main method
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        Query2 sample = new Query2();
        List<ContentResult> results = sample.getRankedContent("Web Service Sample Folder", "Alfresco Development Team");
        
        // Output the results for visual inspection
        int iCount = 1;
        for (ContentResult result : results)
        {
            System.out.println("Result " + iCount + ": " + result.toString());
            iCount ++;
        }
    }
    
    /**
     * Get a list of ordered results of documents in the space specified matching the search 
     * text provided.
     * 
     * @param spaceName     the name of the space (immediatly beneth the company home space) to search
     * @param searchValue   the FTS search value
     * @return              list of results
     */
    public List<ContentResult> getRankedContent(String spaceName, String searchValue)
    {
        List<ContentResult> results = new ArrayList<ContentResult>();
        
        try
        {
            AuthenticationUtils.startSession(USERNAME, PASSWORD);
            try
            {            
                RepositoryServiceSoapBindingStub repositoryService = WebServiceFactory.getRepositoryService();       
                
                // Get a reference to the space we have named
                Reference reference = new Reference(STORE, null, "/app:company_home/*[@cm:name=\"" + spaceName + "\"]");
                Predicate predicate = new Predicate(new Reference[]{reference}, null, null);        
                Node[] nodes = repositoryService.get(predicate);
                
                // Create a query object, looking for all items with alfresco in the name of text
                Query query = new Query(
                        Constants.QUERY_LANG_LUCENE, 
                        "+PARENT:\"workspace://SpacesStore/"+ nodes[0].getReference().getUuid() + "\" +TEXT:\"" + searchValue + "\"");
                
                // Execute the query
                QueryResult queryResult = repositoryService.query(STORE, query, false);
                
                // Display the results
                ResultSet resultSet = queryResult.getResultSet();
                ResultSetRow[] rows = resultSet.getRows();
                
                if (rows != null)
                {
                    // Get the infomation from the result set
                    for(ResultSetRow row : rows)
                    {
                        String nodeId = row.getNode().getId();
                        ContentResult contentResult = new ContentResult(nodeId);
                        
                        for (NamedValue namedValue : row.getColumns())
                        {
                            if (namedValue.getName().endsWith(Constants.PROP_CREATED) == true)
                            {
                                contentResult.setCreateDate(namedValue.getValue());
                            }
                            else if (namedValue.getName().endsWith(Constants.PROP_NAME) == true)
                            {
                                contentResult.setName(namedValue.getValue());
                            }
                            else if (namedValue.getName().endsWith(Constants.PROP_DESCRIPTION) == true)
                            {
                                contentResult.setDescription(namedValue.getValue());   
                            }
                            else if (namedValue.getName().endsWith(Constants.PROP_CONTENT) == true)
                            {
                                // We could go to the content service and ask for the content to get the URL but to save time we 
                                // might as well dig the content URL out of the results.                        
                                String contentString = namedValue.getValue();
                                String[] values = contentString.split("[|=]");
                                contentResult.setUrl(values[1]);
                            }
                        }
                        
                        results.add(contentResult);
                    }
                }
            }
            finally
            {            
                // End the session
                AuthenticationUtils.endSession();
            }
        }
        catch (Exception serviceException)
        {
            throw new RuntimeException("Unable to perform search.", serviceException);
        }
        
        return results;
    }
    
    /**
     * Class to contain the information about the result from the query
     */
    public class ContentResult
    {
        private String id;
        private String name;
        private String description;
        private String url;       
        private String createDate;
        
        public ContentResult(String id)
        {
            this.id = id;
        }
        
        public String getCreateDate()
        {
            return createDate;
        }
        
        public void setCreateDate(String createDate)
        {
            this.createDate = createDate;
        }
        
        public String getDescription()
        {
            return description;
        }
        
        public void setDescription(String description)        
        {
            this.description = description;
        }
        
        public String getId()
        {
            return id;
        }

        public String getName()
        {
            return name;
        }
        
        public void setName(String name)
        {
            this.name = name;
        }
        
        public String getUrl()
        {
            return url;
        }
        
        public void setUrl(String url)
        {
            this.url = url;
        }      

        @Override
        public String toString()
        {
            return "id=" + this.id + 
                   "; name=" + this.name + 
                   "; description=" + this.description + 
                   "; created=" + this.createDate + 
                   "; url=" + this.url;
        }
    }
}
