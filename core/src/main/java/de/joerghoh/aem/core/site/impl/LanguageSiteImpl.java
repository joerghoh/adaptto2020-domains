package de.joerghoh.aem.core.site.impl;


import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import com.day.cq.wcm.api.Page;

import de.joerghoh.aem.core.site.LanguageSite;

public class LanguageSiteImpl implements LanguageSite {
    
    private static final String LANGUAGE_SITE_ROOT_IDENTIFIER="languageSiteRoot";
    
    Resource selfResource;
    Page selfPage;
    
    String languageCode;
    String languageName;
    
    String countryName;
    
    private static final String LANGUAGE_CODE_PROPERTY = "jcr:language";
    private static final String LANGUAGE_NAME_PROPERTY = "languageName";
    
    CountrySiteImpl countrySite;
    
    
    protected static boolean isLanguageSiteRoot (Page page) {
        return Optional.ofNullable(page.getProperties())
                .filter(vm -> vm.containsKey(LANGUAGE_SITE_ROOT_IDENTIFIER))
                .isPresent();
    }
    
    
    protected static boolean isLanguageSiteRoot(Resource r) {
        Page p = r.adaptTo(Page.class);
        if (p == null) {
            return Optional.ofNullable(r.getValueMap())
                    .map(vm -> vm.get(LANGUAGE_SITE_ROOT_IDENTIFIER))
                    .isPresent();
        } else {
            return isLanguageSiteRoot(p);
        }
    }
    

    protected LanguageSiteImpl(Resource resource) {
        selfResource = resource;
        
        Page page = selfResource.adaptTo(Page.class);
        if (page != null) {
            Resource cr = page.getContentResource();
            if (cr == null) {
                throw new IllegalArgumentException(String.format("Page %s does not have a jcr:content resource",
                        page.getPath()));
            }
            fillData(selfResource, cr);
        } else {
            fillData(selfResource,selfResource);
        }
        
    }
    
    
    private void fillData (Resource invokedOnResource, Resource dataResource) {
        ValueMap vm = dataResource.getValueMap();
        languageCode = vm.get(LANGUAGE_CODE_PROPERTY, String.class);
        if (StringUtils.isEmpty(languageCode)) {
            languageCode = invokedOnResource.getName().toLowerCase();
        }
        languageName = vm.get(LANGUAGE_NAME_PROPERTY, String.class);
        if (StringUtils.isEmpty(languageName)) {
            languageName = languageCode;
        }
        countryName = vm.get(CountrySiteImpl.COUNTRY_NAME_PROPERTY,String.class);
    }
    
    
    public String getLanguageCode() {
        return languageCode;
    }
    
    public String getLanguageName() {
        return languageName;
    }
    
    public Resource getResource() {
        return selfResource;
    }
    
    public String getCountryName() {
        if (!StringUtils.isEmpty(countryName)) {
            return countryName;
        } else {
            return getCountrySite()
                    .map(cs -> cs.getCountryName())
                    .orElse(null);
        }
    }
    
    public Optional<CountrySiteImpl> getCountrySite() {
        
        if (countrySite == null) {
            CountrySiteImpl cs =  Optional.ofNullable(selfResource.getParent())
                    .filter(CountrySiteImpl::isCountrySiteRoot)
                    .map(r -> new CountrySiteImpl(r))
                    .orElse(null);
            countrySite = cs;
        }
        return Optional.ofNullable(countrySite);
        
    }
    
    

}
