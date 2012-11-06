JDF JBoss Stacks Client API
===========================

What is it?
-----------

This a client for JBoss Stacks YAML file. 

How to use this API ?
---------------------

If you need to convert the stack.yaml file to Java Objects add the following dependency to your project

        <dependency>
            <groupId>org.jboss.jdf</groupId>
            <artifactId>stacks-client</artifactId>
            <version>1.0.1-SNAPSHOT</version>
        </dependency>
        
Then you can use the stacks-client API:        

        StacksClient stacksClient = new StacksClient();
        Stacks stacks = stacksClient.getStacks();
        
When you get the Stacks the stacks-client will retrieve the Stacks.yaml file from its repository and cache it locally.
The cache will be updated once a day (24 hours since last update)

With the stacks object, you can navigate on the stacks graph. For more information take a look at the [Stacks format diagram](https://raw.github.com/jboss-jdf/jdf-stack/1.0.0.Final/fileformat.png)

Examples:

        // Get The Available Archetypes
        stacks.getAvailableArchetypes();
        
        // Get all BOMs from a Runtime 
        stacks.getAvailableRuntimes().get(0).getBoms();
        
        //Get the Download URL from the Recommended Runtime from a Major Release (AS7 for example)
        stacks.getMajorReleases().get(0).getRecommendedRuntime().getDownloadUrl(); 

        //Get the BOM name of the Default BOM from a Runtime
        stacks.getAvailableRuntimes().get(0).getDefaultBom().getBom().getName(); 


Advances JBoss Stacks Client configuration
------------------------------------------

The stacks-client can be configured through the `StacksClientConfiguration` interface.

You can implement this class and pass it on the constructor of the StacksClient

Example:

        MyConfiguration myConfig = new MyConfiguration();
        StacksClient stacksClient = new StacksClient(myConfig);

If no configuration is supplied, StacksClient will use 'DefaultStacksClientConfiguration' class with de following values:

- url: The URL of stacks yaml file repository. 

  You can use JVM jdf.stacks.client.repo System property to set the url repo value. Ex.: java -Djdf.stacks.client.repo <YourMainClass> 

- proxyHost: Proxy host to use. Default: null
- proxyPort: Proxy port to use. Default: null
- proxyUser: Proxy user to use. If proxy uses Authenticantion. Default: null
- proxyPassword: Proxy password to use. If proxy uses Authenticantion. Default: null

- online: Flag that indicates if the system should be used online/offline. Default: true
- cacheRefreshPeriodSeconds: Time in seconds to keep the cache information: Default to 86400 (24 hours)


At any moment will can retrieve the information by calling:

        stacksClient.getActualConfiguration();
        
    
Displaying the Stacks messages on your UI
-----------------------------------------

If you need to get the messages from stacks-client you can provide your own implementation of 'StacksClientMessages' interface and use it on the StacksClient constructor.

Example:

        MyConfiguration myConfig = new MyConfiguration();
        MyMessageWay myMsg = new MyMessageWay();
        StacksClient stacksClient = new StacksClient(myConfig, myMsg);
        
               
