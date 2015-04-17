# Alfresco SDK Examples

© 2014 Alfresco Software, Inc. All Rights Reserved. See license.txt.

The goal of this new project is to provide examples on how to extend Alfresco with the Alfresco Maven SDK.

## Prerequisites

If you are new to Alfresco and the Alfresco Maven SDK, you should start by reading [Jeff Potts' tutorial on the subject](http://ecmarchitect.com/alfresco-developer-series-tutorials/maven-sdk/tutorial/tutorial.html).

# Running the examples

All examples are selfcontained with the Alfresco Maven SDK, so you'll be able to get up and running quickly. This means that the only thing you will need is JDK and Maven. The SDK will allow you to start up an embedded Tomcat server with an embedded H2 database, so you will be up and running in no time.

Some examples ties into both Alfresco and Share. In those cases there will be an AMP module for each `repo` will be for the repository tier (Alfresco) and `share` will be for Share.

## Running Alfresco AMP examples (repo)

To run the Alfreco AMP examples you enter the `repo` folder and run the "run.sh" script. Alternatively you will need to adjust the JVM memory settings. This is not needed for Share. Enter the `repo` folder and run the following command:

	export MAVEN_OPTS="-Xms1024m -Xmx4096m -XX:PermSize=1024m"
	mvn integration-test -P amp-to-war

Alfresco will be available on [http://localhost:8080/alfresco](http://localhost:8080/alfresco)

## Running Share AMP examples (share)

To run Share AMP examples you run enter the `share` folder and run the "run.sh" script. Alternatively you run this command:

	mvn integration-test -P amp-to-war
	
Share will be available on [http://localhost:8081/alfresco](http://localhost:8081/alfresco) (notice that the port is 8081).

# Examples

## Custom Model

A very simple example on a custom data model that defines a new type `cme:doc`, which just inherits from `cm:model` and introduces a new field for related `cme:doc` nodes.

In the Share tier it's possible to create, edit and view the custom type.

## Custom Action

A custom action that fires up a review workflow. From Share, the user can execute an action on a node to directly start a review workflow that is assigned to the creator of the node.

## Webscripts

A collection of webscript examples. It provides examples on both Java-backed webscripts, aswell as Javascript-backed webscripts, touches briefly on doing proper REST webscripts with a webscript that allows GET and DELETE on nodes. It also shows how to have the same webscript controller be rendered as both JSON and XML. Finally it shows a webscript in the Share tier.

### Java-backed JSON Webscript

A simple Java-backed webscript that returns JSON directly without a Freemarker template.

URL: [http://localhost:8080/alfresco/service/example-webscripts/java-backed/json-webscript](example-webscripts/java-backed/json-webscript)

### Java-backed Webscript

A normal Java-backed Webscript that uses a Freemarker template to render the model.

URL: [http://localhost:8080/alfresco/service/example-webscripts/java-backed/webscript](example-webscripts/java-backed/webscript)

### Folder list

Java-backed webscript that renders a HTML page with the children of a folder.
The webscript takes a nodeRef as an argument like this: `/example-webscripts/folder/list/{store_type}/{store_id}/{id}`

To reach the webscript, find a nodeRef and access f.x: [http://localhost:8080/alfresco/service/example-webscripts/folder/list/workspace/SpacesStore/e7226383-ce8f-4c8f-97ed-9cdb3a1d6573](http://localhost:8080/alfresco/service/example-webscripts/folder/list/workspace/SpacesStore/e7226383-ce8f-4c8f-97ed-9cdb3a1d6573)

### Javascript-backed webscript

A simple Javascript-backed webscript with a Freemarker template.

URL: [http://localhost:8080/alfresco/service/example-webscripts/javascript-backed/webscript](http://localhost:8080/alfresco/service/example-webscripts/javascript-backed/webscript)

### Javascript-backed JSON webscript

A simple Javascript-backed webscript with a Freemarker template that returns JSON.

URL: [http://localhost:8080/alfresco/service/example-webscripts/javascript-backed/json-webscript](http://localhost:8080/alfresco/service/example-webscripts/javascript-backed/json-webscript)

### Javascript-backed GET & DELETE node webscript

Two webscripts for getting basic node information based on a nodeRef and deleting. This shows how to do webscripts in a proper RESTful way.

Both webscripts assumes the same URL, but responds differently to GET and DELETE requests. Both webscripts assumes the nodeRef like this `/example-webscripts/node/{store_type}/{store_id}/{id}`.

Once you have a nodeRef you can `GET` the node with a URL like this: [http://localhost:8080/alfresco/service/example-webscripts/node/workspace/SpacesStore/5c80da0a-f081-4745-b71a-a164872f1d8b](http://localhost:8080/alfresco/service/example-webscripts/node/workspace/SpacesStore/5c80da0a-f081-4745-b71a-a164872f1d8b)

To delete a node, you could use a tool like cURL like this to perform a DELETE request:

	curl -i -X DELETE --user admin:admin \
	http://localhost:8080/alfresco/service/example-webscripts/node/workspace/SpacesStore/5c80da0a-f081-4745-b71a-a164872f1d8b
	
### Share webscript

In the `share` AMP there is a Javascript-backed webscript that calls the node GET webscript from Alfresco and displays the result.

It assumes the nodeRef assumes the nodeRef as part of the URL like the other example webscripts: `/example-webscripts/node/{store_type}/{store_id}/{id}`

To reach the webscript, find a nodeRef and access like this: [http://localhost:8081/share/page/example-webscripts/node/workspace/SpacesStore/924aec92-8139-439d-9cc5-14fac827378c](http://localhost:8081/share/page/example-webscripts/node/workspace/SpacesStore/924aec92-8139-439d-9cc5-14fac827378c)

## Custom Dashlet

A very simple custom dashlet that can be added to the user dashboard. 