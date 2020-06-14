package de.joerghoh.aem.core.site.impl;

import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Component;

@Component(service = {AdapterFactory.class},

        property = {
                "adaptables=org.apache.sling.api.resource.Resource",
                "adapters=de.joerghoh.aem.core.site.LanguageSite"
        })
public class LanguageSiteAdapter implements AdapterFactory {

    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        
        Resource resource = (Resource) adaptable;
        
        while (!LanguageSiteImpl.isLanguageSiteRoot(resource) && !isRoot(resource)) {
            resource = resource.getParent();
        }
        if (isRoot(resource)) {
            return null;
        }
        return (AdapterType) new LanguageSiteImpl(resource);
         
        
    }

    private boolean isRoot(Resource r) {
        return "/".equals(r.getPath());
    }
    
}
