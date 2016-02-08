package sk.upjs.ics.opiela.kopr.server.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import sk.upjs.ics.opiela.kopr.server.BeanFactory;
import sk.upjs.ics.opiela.kopr.server.Recept;

public class DatabazoveReceptyDao implements ReceptyDao {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final ReceptRowMapper receptRowMapper = new ReceptRowMapper();

    private final IngrediencieDao ingrediencieDao = BeanFactory.INSTANCE.getIngrediencieDao();

    public DatabazoveReceptyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplate);
    }

    @Override
    public void pridaj(Recept recept) {
        Map<String, Object> insertMap = new HashMap<>();
        insertMap.put("uuid", recept.getUuid().toString());
        insertMap.put("nazovReceptu", recept.getNazovReceptu());
        insertMap.put("menoAutora", recept.getMenoAutora());
        insertMap.put("postupPripravy", recept.getPostupPripravy());
        Map<String, String> ingrediencie = recept.getIngrediencie();

        int pocitadlo = 0;
        for (Map.Entry<String, String> ingrediencieDvojica : ingrediencie.entrySet()) {
            String ingrediencia = ingrediencieDvojica.getKey().trim();
            String mnozstvo = ingrediencieDvojica.getValue();

            ingrediencia = ingrediencia.replace("\"", "\\\"");

            Long idIngrediencie = ingrediencieDao.hladajIdIngredienciePodlaNazvu(ingrediencia);

            if (idIngrediencie == null) {
                idIngrediencie = ingrediencieDao.pridaj(ingrediencia);
            }

            insertMap.put(String.valueOf(pocitadlo), idIngrediencie);
            insertMap.put(String.valueOf(pocitadlo + 1), mnozstvo);

            pocitadlo += 2;
        }

        String sql = "INSERT INTO recepty (uuid, nazovReceptu, menoAutora, postupPripravy)\n"
                + "VALUES (:uuid, :nazovReceptu, :menoAutora, :postupPripravy)";

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(insertMap));

        if (pocitadlo >= 2) {
            StringBuilder sql2 = new StringBuilder("INSERT INTO ingrediencieRecepty "
                    + "(uuidReceptu, idIngrediencie, mnozstvo) VALUES ");
            for (int i = 0; i < pocitadlo - 2; i = i + 2) {
                sql2.append("(:uuid, :")
                        .append(i)
                        .append(", :")
                        .append(i + 1)
                        .append("), ");
            }
            sql2.append("(:uuid, :")
                    .append(pocitadlo - 2)
                    .append(", :")
                    .append(pocitadlo - 1)
                    .append(")");
            namedParameterJdbcTemplate.update(sql2.toString(), new MapSqlParameterSource(insertMap));
        }
    }

    @Override
    public void aktualizuj(Recept recept) {
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("uuid", recept.getUuid().toString());
        updateMap.put("nazovReceptu", recept.getNazovReceptu());
        updateMap.put("menoAutora", recept.getMenoAutora());
        updateMap.put("postupPripravy", recept.getPostupPripravy());
        Map<String, String> ingrediencie = recept.getIngrediencie();

        int pocitadlo = 0;
        for (Map.Entry<String, String> ingrediencieDvojica : ingrediencie.entrySet()) {
            String ingrediencia = ingrediencieDvojica.getKey().trim();
            String mnozstvo = ingrediencieDvojica.getValue();

            ingrediencia = ingrediencia.replace("\"", "\\\"");

            Long idIngrediencie = ingrediencieDao.hladajIdIngredienciePodlaNazvu(ingrediencia);

            if (idIngrediencie == null) {
                idIngrediencie = ingrediencieDao.pridaj(ingrediencia);
            }

            updateMap.put(String.valueOf(pocitadlo), idIngrediencie);
            updateMap.put(String.valueOf(pocitadlo + 1), mnozstvo);

            pocitadlo += 2;
        }

        String sql = "UPDATE recepty "
                + "SET nazovReceptu = :nazovReceptu, menoAutora = :menoAutora, postupPripravy = :postupPripravy "
                + "WHERE uuid = :uuid";

        String sql2 = "DELETE FROM ingrediencieRecepty WHERE uuidReceptu = ?";

        StringBuilder sql3 = new StringBuilder("INSERT INTO ingrediencieRecepty "
                + "(uuidReceptu, idIngrediencie, mnozstvo) VALUES ");
        for (int i = 0; i < pocitadlo - 2; i = i + 2) {
            sql3.append("(:uuid, :")
                    .append(i)
                    .append(", :")
                    .append(i + 1)
                    .append("), ");
        }
        sql3.append("(:uuid, :")
                .append(pocitadlo - 2)
                .append(", :")
                .append(pocitadlo - 1)
                .append(")");

        namedParameterJdbcTemplate.update(sql, updateMap);
        jdbcTemplate.update(sql2, recept.getUuid().toString());
        namedParameterJdbcTemplate.update(sql3.toString(), updateMap);
    }

    @Override
    public void odstran(UUID uuid) {
        String sql = "DELETE FROM recepty WHERE uuid = ?";
        jdbcTemplate.update(sql, uuid.toString());

        String sql2 = "DELETE FROM ingrediencieRecepty WHERE uuidReceptu = ?";
        jdbcTemplate.update(sql2, uuid.toString());
    }

    @Override
    public List<Recept> dajVsetky() {
        return jdbcTemplate.query(SQLQueries.SELECT_ALL_RECEPT, receptRowMapper);
    }

    @Override
    public Recept dajPodlaUUID(UUID uuid) {
        return jdbcTemplate.queryForObject(SQLQueries.SELECT_RECEPT_BY_UUID, receptRowMapper, uuid.toString());
    }

}
