package sk.upjs.ics.opiela.kopr.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class IngrediencieResultSetExtractor implements ResultSetExtractor<Map<String, String>> {
    
    @Override
    public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, String> ingrediencie = new HashMap<>();
        
        while (rs.next()) {
            String nazov = rs.getString("nazov");
            String mnozstvo = rs.getString("mnozstvo");
            ingrediencie.put(nazov, mnozstvo);
        }
        
        return ingrediencie;
    }
    
}
