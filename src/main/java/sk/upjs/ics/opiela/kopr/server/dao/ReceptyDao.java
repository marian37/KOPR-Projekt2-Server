package sk.upjs.ics.opiela.kopr.server.dao;

import java.util.List;
import java.util.UUID;
import sk.upjs.ics.opiela.kopr.server.Recept;

public interface ReceptyDao {

    public void pridaj(Recept recept);

    public void aktualizuj(Recept recept);

    public void odstran(UUID uuid);

    public List<Recept> dajVsetky();

    public Recept dajPodlaUUID(UUID uuid);

}
