package de.joerghoh.aem.core.site.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import com.day.cq.wcm.api.Page;

import de.joerghoh.aem.core.site.CountrySite;
import de.joerghoh.aem.core.site.LanguageSite;

public class CountrySiteImpl implements CountrySite {
    
    private static final String COUNTRY_SITE_ROOT_IDENTIFIER = "countrySite";
    
    protected static final String COUNTRY_CODE_PROPERTY = "countryCode";
    protected static final String COUNTRY_NAME_PROPERTY = "countryName";
    
    Resource self;
    String countryCode;
    String countryName;
    
    public static boolean isCountrySiteRoot (Resource r) {
        Page page = r.adaptTo(Page.class);
        if (page == null) {
            return Optional.ofNullable(r.getValueMap())
                .map(vm -> vm.get(COUNTRY_SITE_ROOT_IDENTIFIER))
                .isPresent();
        } else {
            return isCountrySiteRoot(page);
        }
    }
    
    
    public static boolean isCountrySiteRoot (Page page) {
        return Optional.ofNullable(page.getProperties())
                .filter(vm -> vm.containsKey(COUNTRY_SITE_ROOT_IDENTIFIER))
                .isPresent();
    }
    
    
    public CountrySiteImpl(Resource resource) {
        self = resource;
        ValueMap vm = resource.getValueMap();
        countryCode = vm.get(COUNTRY_CODE_PROPERTY, String.class);
        if (StringUtils.isEmpty(countryCode)) {
            countryCode = self.getName().toLowerCase();
        }
        countryName = vm.get(COUNTRY_NAME_PROPERTY, String.class);
        if (StringUtils.isEmpty(countryName)) {
            countryName = countryCode;
        }
    }
    
    public String getCountryCode() {
        return countryCode;
    }
    
    public String getCountryName() {
        return countryName;
    }
    
    public Resource getResource() {
        return self;
    }
    
    
    public List<LanguageSite> getLanguageSites() {
        
        List<LanguageSite> ls = new ArrayList<>();
        
        self.listChildren().forEachRemaining(c -> {
            if (LanguageSiteImpl.isLanguageSiteRoot(c)) {
                ls.add(new LanguageSiteImpl(c));
            }
        });
        return ls;
    }
    
    

}
