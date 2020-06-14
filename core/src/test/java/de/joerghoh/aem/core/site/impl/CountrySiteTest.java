package de.joerghoh.aem.core.site.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.day.cq.wcm.api.Page;

import de.joerghoh.aem.core.site.CountrySite;
import de.joerghoh.aem.core.site.LanguageSite;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class CountrySiteTest {
    
    public final AemContext context = new AemContext();
    
    @BeforeEach
    public void setup() {
        context.load().json(this.getClass().getResourceAsStream("CountryLanguageSitesTest.json"), "/content");
        context.registerService(new LanguageSiteAdapter());  
        context.registerService(new CountrySiteAdapter());
    }
    
    @Test
    public void testWithNonPageAsCountrySites() {
        Page p = context.resourceResolver().getResource("/content/ch/de/page").adaptTo(Page.class);
        assertNotNull(p);
        LanguageSite lsde = p.adaptTo(LanguageSite.class);
        assertNotNull(lsde);
        CountrySite csch = lsde.getCountrySite().get();
        assertNotNull(csch);
        assertEquals("ch",csch.getResource().getName());
        assertEquals("Switzerland",csch.getCountryName());
        assertEquals("ch",csch.getCountryCode());
        
    }
    
    @Test
    public void testWithPageAsCountrySites() {
        Page p = context.resourceResolver().getResource("/content/us/en/page").adaptTo(Page.class);
        assertNotNull(p);
        LanguageSite lsde = p.adaptTo(LanguageSite.class);
        assertNotNull(lsde);
        CountrySite csch = lsde.getCountrySite().get();
        assertNotNull(csch);
        assertEquals("us",csch.getCountryName());
        assertEquals("us",csch.getCountryCode());
        
    }
    
    @Test
    public void testCountrySite() {
        LanguageSite lsde = context.resourceResolver().getResource("/content/us/en").adaptTo(LanguageSite.class);
        assertNotNull(lsde);
        CountrySite csch = lsde.getCountrySite().get();
        
        List<LanguageSite> languageSites = csch.getLanguageSites();
        assertEquals(2,languageSites.size());
        
    }
    

}
