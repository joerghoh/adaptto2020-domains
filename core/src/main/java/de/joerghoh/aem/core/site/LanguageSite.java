package de.joerghoh.aem.core.site;

import java.util.Optional;

import org.apache.sling.api.resource.Resource;

import de.joerghoh.aem.core.site.impl.CountrySiteImpl;

public interface LanguageSite {
    
    public String getLanguageCode();
    
    public String getLanguageName();
    
    public String getCountryName();
    
    public Optional<CountrySiteImpl> getCountrySite();
    
    public Resource getResource();

}
