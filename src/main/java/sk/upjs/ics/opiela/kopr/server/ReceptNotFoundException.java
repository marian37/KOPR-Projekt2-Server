package sk.upjs.ics.opiela.kopr.server;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReceptNotFoundException extends RuntimeException {

    public ReceptNotFoundException(UUID uuid) {
        super("Recept " + uuid + " sa nenasiel.");
    }

}
