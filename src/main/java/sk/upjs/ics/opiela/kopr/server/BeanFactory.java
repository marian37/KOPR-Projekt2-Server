package sk.upjs.ics.opiela.kopr.server;

import sk.upjs.ics.opiela.kopr.server.dao.DatabazoveIngrediencieDao;
import sk.upjs.ics.opiela.kopr.server.dao.DatabazoveReceptyDao;
import sk.upjs.ics.opiela.kopr.server.dao.IngrediencieDao;
import sk.upjs.ics.opiela.kopr.server.dao.ReceptyDao;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public enum BeanFactory {

    INSTANCE;

    private JdbcTemplate jdbcTemplate;

    private DataSource dataSource;

    private DataSource dataSourceTest;

    private ReceptyDao receptyDao;

    private IngrediencieDao ingrediencieDao;

    public ReceptyDao getReceptyDao() {
        return getReceptyDao(false);
    }

    public ReceptyDao getReceptyDao(boolean test) {
        if (receptyDao == null) {
            receptyDao = new DatabazoveReceptyDao(jdbcTemplate(test));
        }

        return receptyDao;
    }

    public IngrediencieDao getIngrediencieDao() {
        return getIngrediencieDao(false);
    }

    public IngrediencieDao getIngrediencieDao(boolean test) {
        if (ingrediencieDao == null) {
            ingrediencieDao = new DatabazoveIngrediencieDao(jdbcTemplate(test));
        }

        return ingrediencieDao;
    }

    public JdbcTemplate jdbcTemplate(boolean test) {
        if (this.jdbcTemplate == null) {
            this.jdbcTemplate = new JdbcTemplate(dataSource(test));
        }
        return this.jdbcTemplate;
    }

    private DataSource dataSource(boolean test) {
        if (test) {
            if (this.dataSourceTest == null) {
                MysqlDataSource dataSourceTest = new MysqlDataSource();
                dataSourceTest.setUrl("jdbc:mysql://localhost:3306/receptyTest?zeroDateTimeBehavior=convertToNull");
                dataSourceTest.setUser("marian");
                dataSourceTest.setPassword("heslo");
                this.dataSourceTest = dataSourceTest;
            }

            return this.dataSourceTest;
        } else {
            if (this.dataSource == null) {
                MysqlDataSource dataSource = new MysqlDataSource();
                dataSource.setUrl("jdbc:mysql://localhost:3306/recepty?zeroDateTimeBehavior=convertToNull");
                dataSource.setUser("marian");
                dataSource.setPassword("heslo");
                this.dataSource = dataSource;
            }

            return this.dataSource;
        }
    }
}
