package de.joerghoh.aem.core.site;

import java.util.List;

import org.apache.sling.api.resource.Resource;

/**
 * A country site
 * 
 * A country site represents a site for a specific country. A country site consists of a number
 * of properties and a list of 1 or more LanguageSites.
 * 
 * A countrySite object can be adapted from a Resource or page:
 * 
 * <code>
 * CountrySite countrySite = mypage.adaptTo(CountrySite.class);
 * </code>
 * 
 * or 
 * 
 * <code>
 * CountrySite countrySite = someResource.adaptTo(CountrySite.class);
 * </code>
 * 
 *
 */


public interface CountrySite {
    
    /**
     * Return the country code for the country (typically an ISO code)
     * @return the country code
     */
    public String getCountryCode();
    
    /**
     * Return the full country name; in case this name is not provided, an internal
     * fallback to the ISO code is provided.
     * @return the country name
     */
    public String getCountryName();
    
    
    /** 
     * Get a list of all languageSites below that country
     * @return the country list
     */
    public List<LanguageSite> getLanguageSites();

}
