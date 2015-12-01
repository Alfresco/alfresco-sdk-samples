 # Database Content Storage
 
  MySQL BLOB storage has been implemented.
 
  To try out storing file content as BLOBs in a database do this:
  
  1) Clean out all previous content, index, and metadata:
  
     all-in-one$ rm -rf alf_data_dev
   
  2) Uncomment the following bean to override default fileContentStore (located in service-context.xml)
  
      <bean id="fileContentStore" class="org.alfresco.tutorial.contentstore.DbContentStore">
          <property name="databaseAdapter" ref="org.alfresco.tutorial.content.store.databaseAdapter" />
      </bean>

   
  2) Create the following table to hold the BLOBs:
 
     mysql> create database alfresco_content;
     Query OK, 1 row affected (0.01 sec)
    
     mysql> grant all on alfresco_content.* to 'alfresco'@'localhost' identified by '1234' with grant option;
     Query OK, 0 rows affected (0.00 sec)
    
     mysql> grant all on alfresco_content.* to 'alfresco'@'localhost.localdomain' identified by '1234' with grant option;
     Query OK, 0 rows affected (0.00 sec)
     mysql> exit
    
     $ mysql -u alfresco -p
     Enter password:
     Welcome to the MySQL monitor....
    
     mysql> use alfresco_content;
     Database changed
     mysql> create table alfresco_files ( url varchar(256) not null, file longblob ) ENGINE=InnoDB;
     Query OK, 0 rows affected (0.01 sec)
    
     mysql> desc alfresco_files;
     +-------+-------------+------+-----+---------+-------+
     | Field | Type        | Null | Key | Default | Extra |
     +-------+-------------+------+-----+---------+-------+
     | url   | varchar(256)| NO   |     | NULL    |       |
     | file  | longblob    | YES  |     | NULL    |       |
     +-------+-------------+------+-----+---------+-------+
     2 rows in set (0.01 sec)
 
 LONGBLOB can contain up to 4GB
 
 Check the max allowed packet size = max upload size for file
 
     mysql> show variables like 'max_allowed_packet';
     +--------------------+----------+
     | Variable_name      | Value    |
     +--------------------+----------+
     | max_allowed_packet | 16777216 |
     +--------------------+----------+
     1 row in set (0.00 sec)
 
 Debug logging is turned on by default for the DB Content Store, to turn off see:
 
     all-in-one/repo/src/main/resources/alfresco/extension/dev-log4j.properties