package sk.upjs.ics.opiela.kopr.server;

import sk.upjs.ics.opiela.kopr.server.dao.ReceptyDao;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
@RestController
public class ReceptResource {

    private final ReceptyDao receptyDao;

    public ReceptResource() {
        this.receptyDao = BeanFactory.INSTANCE.getReceptyDao();
    }

    public ReceptResource(boolean test) {
        this.receptyDao = BeanFactory.INSTANCE.getReceptyDao(test);
    }

    @RequestMapping(value = "/recepty", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public UUID pridaj(@RequestBody Recept recept) {
        recept.setUuid(UUID.randomUUID());

        receptyDao.pridaj(recept);

        return recept.getUuid();
    }

    @RequestMapping(value = "/recepty/{uuid}", method = RequestMethod.POST)
    public Recept aktualizuj(@PathVariable UUID uuid, @RequestBody Recept recept) {
        recept.setUuid(uuid);

        receptyDao.aktualizuj(recept);

        return recept;
    }

    @RequestMapping(value = "/recepty/{uuid}", method = RequestMethod.DELETE)
    public void odstran(@PathVariable UUID uuid) {
        receptyDao.odstran(uuid);
    }

    @RequestMapping("/recepty")
    public List<Recept> dajVsetky() {
        return receptyDao.dajVsetky();
    }

    @RequestMapping("/recepty/{uuid}")
    public Recept dajPodlaUUID(@PathVariable UUID uuid) {
        try {
            return receptyDao.dajPodlaUUID(uuid);
        } catch (EmptyResultDataAccessException e) {
            throw new ReceptNotFoundException(uuid);
        }
    }

    @RequestMapping(value = "/recepty/hladaj/klucove-slova", method = RequestMethod.POST)
    public List<Recept> hladajPodlaKlucovychSlov(@RequestBody List<String> klucoveSlova) {
        List<Recept> recepty = this.dajVsetky();
        List<Recept> vyhovujuce = new ArrayList<>();

        for (Recept recept : recepty) {
            boolean vyhovuje = true;
            for (String slovo : klucoveSlova) {
                if (!recept.getNazovReceptu().contains(slovo)
                        && !recept.getMenoAutora().contains(slovo)
                        && !recept.getPostupPripravy().contains(slovo)
                        && !recept.getIngrediencie().containsKey(slovo)
                        && !recept.getIngrediencie().containsValue(slovo)) {
                    vyhovuje = false;
                    break;
                }
            }
            if (vyhovuje) {
                vyhovujuce.add(recept);
            }
        }

        return vyhovujuce;
    }

    @RequestMapping(value = "/recepty/hladaj/ingrediencie", method = RequestMethod.POST)
    public List<Recept> hladajPodlaIngrediencii(@RequestBody List<String> ingrediencie) {
        List<Recept> recepty = this.dajVsetky();
        List<Recept> vyhovujuce = new ArrayList<>();

        for (Recept recept : recepty) {
            boolean vyhovuje = true;
            for (String ingrediencia : ingrediencie) {
                if (!recept.getIngrediencie().containsKey(ingrediencia)) {
                    vyhovuje = false;
                    break;
                }
            }
            if (vyhovuje) {
                vyhovujuce.add(recept);
            }
        }

        return vyhovujuce;
    }
}
