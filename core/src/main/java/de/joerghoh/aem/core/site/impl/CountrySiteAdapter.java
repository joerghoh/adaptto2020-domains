package de.joerghoh.aem.core.site.impl;

import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Component;

@Component(service = {AdapterFactory.class},

property = {
        "adaptables=org.apache.sling.api.resource.Resource",
        "adapters=de.joerghoh.aem.core.site.CountrySite"
})
public class CountrySiteAdapter implements AdapterFactory{

    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        
        Resource resource = (Resource) adaptable;
        
        while (!CountrySiteImpl.isCountrySiteRoot(resource) && isRoot(resource)) {
            resource = resource.getParent();
        }
        if (isRoot(resource)) {
            return null;
        }
        return (AdapterType) new CountrySiteImpl(resource);
         
        
    }

    private boolean isRoot(Resource r) {
        return "/".equals(r.getPath());
    }
    
}
