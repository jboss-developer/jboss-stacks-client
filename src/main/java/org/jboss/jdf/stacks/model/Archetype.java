package org.jboss.jdf.stacks.model;

public interface Archetype {

    public String getId() ;
    
    public String getName() ;
    
    public String getDescription() ;

    public String getGroupId() ;

    public String getArtifactId() ;
    
    public String getRecommendedVersion();
        
    public String getRepositoryURL() ;
    
    public Archetype getBlank() ;

    	
}
