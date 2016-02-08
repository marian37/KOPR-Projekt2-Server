package sk.upjs.ics.opiela.kopr.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;
import sk.upjs.ics.opiela.kopr.server.BeanFactory;
import sk.upjs.ics.opiela.kopr.server.Recept;

public class ReceptRowMapper implements RowMapper<Recept> {

    private final IngrediencieDao ingrediencieDao = BeanFactory.INSTANCE.getIngrediencieDao();

    @Override
    public Recept mapRow(ResultSet rs, int rowNum) throws SQLException {
        Recept recept = new Recept();

        recept.setUuid(UUID.fromString(rs.getString("uuid")));
        recept.setNazovReceptu(rs.getString("nazovReceptu"));
        recept.setMenoAutora(rs.getString("menoAutora"));
        recept.setPostupPripravy(rs.getString("postupPripravy"));
        recept.setIngrediencie(ingrediencieDao.dajIngredienciePodlaReceptu(recept));

        return recept;
    }

}
