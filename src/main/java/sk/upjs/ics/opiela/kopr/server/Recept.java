package sk.upjs.ics.opiela.kopr.server;

import java.util.Map;
import java.util.UUID;

public class Recept {

    private UUID uuid;

    private String nazovReceptu;

    private String menoAutora;

    private Map<String, String> ingrediencie;

    private String postupPripravy;

    public Recept() {
    }

    public Recept(String nazovReceptu, String menoAutora, Map<String, String> ingrediencie, String postupPripravy) {
        this.nazovReceptu = nazovReceptu;
        this.menoAutora = menoAutora;
        this.ingrediencie = ingrediencie;
        this.postupPripravy = postupPripravy;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getNazovReceptu() {
        return nazovReceptu;
    }

    public void setNazovReceptu(String nazovReceptu) {
        this.nazovReceptu = nazovReceptu;
    }

    public String getMenoAutora() {
        return menoAutora;
    }

    public void setMenoAutora(String menoAutora) {
        this.menoAutora = menoAutora;
    }

    public Map<String, String> getIngrediencie() {
        return ingrediencie;
    }

    public void setIngrediencie(Map<String, String> ingrediencie) {
        this.ingrediencie = ingrediencie;
    }

    public String getPostupPripravy() {
        return postupPripravy;
    }

    public void setPostupPripravy(String postupPripravy) {
        this.postupPripravy = postupPripravy;
    }

}
