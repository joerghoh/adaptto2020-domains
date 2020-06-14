package de.joerghoh.aem.core.site;

import java.util.List;

import org.apache.sling.api.resource.Resource;

public interface CountrySite {
    
    public String getCountryCode();
    
    public String getCountryName();
    
    public Resource getResource();
    
    public List<LanguageSite> getLanguageSites();

}
