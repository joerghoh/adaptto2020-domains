package de.joerghoh.aem.core.site.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.day.cq.wcm.api.Page;

import de.joerghoh.aem.core.site.LanguageSite;
import de.joerghoh.aem.core.site.impl.LanguageSiteAdapter;
import de.joerghoh.aem.core.site.impl.LanguageSiteImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class LanguageSiteTest {


    public final AemContext context = new AemContext();
    
    
    @BeforeEach
    public void setup() {
        context.load().json(this.getClass().getResourceAsStream("CountryLanguageSitesTest.json"), "/content");
        context.registerService(new LanguageSiteAdapter());  
    }
 
    @Test
    public void testSingleNodesAsLanguageNode() {
        Resource p = context.resourceResolver().getResource("/content/us/en/page");
        assertNotNull(p);
        LanguageSite ls = p.adaptTo(LanguageSite.class);
        assertNotNull(ls);
        assertEquals("/content/us/en",ls.getResource().getPath());
        assertEquals("en", ls.getLanguageCode());
    }
    
    @Test
    public void testSingleNodesWithJcrLanguageAsLanguageNode() {
        Resource p = context.resourceResolver().getResource("/content/us/es/page");
        assertNotNull(p);
        LanguageSite ls = p.adaptTo(LanguageSite.class);
        assertNotNull(ls);
        assertEquals("/content/us/es",ls.getResource().getPath());
        assertEquals("es", ls.getLanguageCode());
        assertEquals("espanol", ls.getLanguageName());
    }
    
    
    @Test
    public void testPagesAsLanguageNode() {
        Resource r = context.resourceResolver().getResource("/content/ch/de/page");
        assertNotNull(r);
        LanguageSite ls = r.adaptTo(LanguageSite.class);
        assertNotNull(ls);
        assertEquals("/content/ch/de",ls.getResource().getPath());
        assertEquals("de", ls.getLanguageCode());
        assertEquals("Schweiz",ls.getCountryName());
        
        Page p = context.resourceResolver().getResource("/content/ch/de/page").adaptTo(Page.class);
        assertNotNull(p);
        assertEquals("de",p.adaptTo(LanguageSite.class).getLanguageCode());
        assertEquals("/content/ch/de",ls.getResource().getPath());
        
    }
    
    @Test
    public void testCallOnLanguageSiteRoot() {
        Resource r = context.resourceResolver().getResource("/content/ch/de");
        LanguageSite ls = r.adaptTo(LanguageSite.class);
        assertEquals("de", ls.getLanguageCode());
        
        Page p = context.resourceResolver().getResource("/content/ch/de").adaptTo(Page.class);
        LanguageSite ls1 = p.adaptTo(LanguageSite.class);
        assertEquals("de", ls1.getLanguageCode());
        
        // test from a component on the LS page
        Resource c = context.resourceResolver().getResource("/content/ch/de/page");
        assertNotNull(c);
        LanguageSite lsc = c.adaptTo(LanguageSite.class);
        assertNotNull(lsc);
        assertEquals("/content/ch/de",lsc.getResource().getPath());
        assertEquals("de", lsc.getLanguageCode());
        
    }
    
    
    
    
}
