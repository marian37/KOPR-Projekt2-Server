package sk.ics.upjs.opiela.kopr.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.Test;
import static org.junit.Assert.*;
import sk.upjs.ics.opiela.kopr.server.Recept;
import sk.upjs.ics.opiela.kopr.server.ReceptResource;

public class ReceptResourceTest {

    private static final int POCET_RECEPTOV = 3;

    private static final int POCET_RECEPTOV_PODLA_KLUCOVYCH_SLOV = 2;

    private static final int POCET_RECEPTOV_PODLA_INGREDIENCII = 1;

    private final ReceptResource receptResource = new ReceptResource(true);

    public ReceptResourceTest() {
    }

    @Test
    public void testDajVsetky() {
        List<Recept> recepty = receptResource.dajVsetky();
        assertEquals(POCET_RECEPTOV, recepty.size());
    }

    @Test
    public void testDajPodlaUUID() {
        Recept vzor = receptResource.dajVsetky().get(0);
        Recept hladany = receptResource.dajPodlaUUID(vzor.getUuid());
        assertEquals(vzor.getUuid(), hladany.getUuid());
        assertEquals(vzor.getNazovReceptu(), hladany.getNazovReceptu());
        assertEquals(vzor.getMenoAutora(), hladany.getMenoAutora());
        assertEquals(vzor.getIngrediencie(), hladany.getIngrediencie());
        assertEquals(vzor.getPostupPripravy(), hladany.getPostupPripravy());
    }

    @Test
    public void testHladajPodlaKlucovychSlov() {
        List<String> klucoveSlova = new ArrayList<>();
        klucoveSlova.add("Recept");
        klucoveSlova.add("vode");
        List<Recept> recepty = receptResource.hladajPodlaKlucovychSlov(klucoveSlova);
        assertEquals(POCET_RECEPTOV_PODLA_KLUCOVYCH_SLOV, recepty.size());
    }

    @Test
    public void testHladajPodlaIngrediencii() {
        List<String> ingrediencie = new ArrayList<>();
        ingrediencie.add("voda");
        ingrediencie.add("sol");
        List<Recept> recepty = receptResource.hladajPodlaIngrediencii(ingrediencie);
        assertEquals(POCET_RECEPTOV_PODLA_INGREDIENCII, recepty.size());
    }

    @Test
    public void testAktualizuj() {
        Recept stary = receptResource.dajVsetky().get(0);

        Recept upraveny = new Recept();
        upraveny.setUuid(stary.getUuid());
        upraveny.setNazovReceptu("Recept");
        upraveny.setMenoAutora("TEST");
        upraveny.setIngrediencie(stary.getIngrediencie());
        upraveny.setPostupPripravy(stary.getPostupPripravy());

        Recept novy = receptResource.aktualizuj(stary.getUuid(), upraveny);

        assertEquals(novy.getUuid(), upraveny.getUuid());
        assertEquals(novy.getNazovReceptu(), upraveny.getNazovReceptu());
        assertEquals(novy.getMenoAutora(), upraveny.getMenoAutora());
        assertEquals(novy.getIngrediencie(), upraveny.getIngrediencie());
        assertEquals(novy.getPostupPripravy(), upraveny.getPostupPripravy());

        receptResource.aktualizuj(novy.getUuid(), stary);
    }

    @Test
    public void testPridajAOdstran() {
        Map<String, String> ingrediencie = new HashMap<>();
        ingrediencie.put("voda", "1 l");
        ingrediencie.put("maslo", "100 g");
        ingrediencie.put("buchty", "4 ks");
        Recept recept = new Recept("ReceptTest", "TESTER", ingrediencie, "Test.");

        List<Recept> recepty = receptResource.dajVsetky();
        int pocet = recepty.size();

        UUID uuid = receptResource.pridaj(recept);

        recepty = receptResource.dajVsetky();
        assertEquals(pocet + 1, recepty.size());

        receptResource.odstran(uuid);

        recepty = receptResource.dajVsetky();
        assertEquals(pocet, recepty.size());
    }

}
