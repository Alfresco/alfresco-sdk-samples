alfresco-sdk
============
© 2013 Alfresco Software, Inc. All Rights Reserved. See license.txt.

This is a fork of the Alfresco SDK from Alfresco SVN rev 53865. The original Alfresco SDK source lives [here](http://svn.alfresco.com/repos/alfresco-open-mirror/alfresco/HEAD/root/projects/sdk).

History
=======
The original purpose of the Alfresco SDK was to be a downloadable resource to make it easier for developers to extend Alfresco with their own customizations. The project included compiled dependencies, source, JavaDoc, and sample projects. All sample projects included Ant-based builds.

If you are looking for the binary distribution of the original SDK, it is currently still distributed with each version of Alfresco. For example, for Alfresco Community Edition 4.2.c you can download the SDK from the [4.2.c file list page](http://wiki.alfresco.com/wiki/Community_file_list_4.2.c).

Goal of this Project
====================
The goal of this new project is to:

 - Remove old sample projects that are no longer relevant
 - Add new sample projects for areas of the platform that may currently be missing
 - Convert all sample projects to builds that leverage the Alfresco Maven SDK
 - Provide a light set of documentation that explains how to use the Alfresco Maven SDK and how to build the sample projects. This should not replace any formal [official documentation](http://docs.alfresco.com) but rather the intent is to be a suitable starting place for developers starting a new Alfresco-based project and that will require some amount of documentation to be included in the SDK.

To-Do
=====
The Alfresco community is encouraged to collaborate on this effort. We would love to hear your ideas on what the SDK should or should not include. Even better, we invite you to roll up your sleaves and make this the SDK that you would want to use.

 1. This source was initially copied as-is from the Alfresco source. It is typically built as part of a larger Alfresco build. So step one is getting the code to build as a standalone project that depends on Maven artifacts rather than the larger build context.

 2. Decide which projects should be removed.

 3. Convert the remaining projects to Maven.

 4. Add missing projects.

 5. Write the docs.
