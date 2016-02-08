package sk.upjs.ics.opiela.kopr.server.dao;

import java.util.Map;
import sk.upjs.ics.opiela.kopr.server.Recept;

public interface IngrediencieDao {

    public Long pridaj(String nazov);

    public Map<String, String> dajIngredienciePodlaReceptu(Recept recept);

    public Long hladajIdIngredienciePodlaNazvu(String nazov);
}
