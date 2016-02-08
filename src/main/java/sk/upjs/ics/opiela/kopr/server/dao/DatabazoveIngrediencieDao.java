package sk.upjs.ics.opiela.kopr.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import sk.upjs.ics.opiela.kopr.server.Recept;

public class DatabazoveIngrediencieDao implements IngrediencieDao {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final IngrediencieResultSetExtractor ingrediencieResultSetExtractor = new IngrediencieResultSetExtractor();

    public DatabazoveIngrediencieDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public Long pridaj(String nazov) {
        Map<String, Object> insertMap = new HashMap<>();
        nazov = nazov.replace("\"", "\\\"").trim();

        insertMap.put("nazov", nazov);

        String sql = "INSERT INTO ingrediencie (nazov)\n"
                + "VALUES(:nazov)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(insertMap), keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public Map<String, String> dajIngredienciePodlaReceptu(Recept recept) {
        return jdbcTemplate.query(SQLQueries.SELECT_INGREDIENCIE_BY_RECEPT, ingrediencieResultSetExtractor, recept.getUuid().toString());
    }

    @Override
    public Long hladajIdIngredienciePodlaNazvu(String nazov) {
        List<Long> id = jdbcTemplate.query(SQLQueries.SELECT_ID_INGREDIENCIE_BY_NAZOV, new RowMapper<Long>() {

            @Override
            public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getLong("id");
            }
        }, nazov);

        if (id.size() == 1) {
            return id.get(0);
        } else {
            return null;
        }
    }

}
