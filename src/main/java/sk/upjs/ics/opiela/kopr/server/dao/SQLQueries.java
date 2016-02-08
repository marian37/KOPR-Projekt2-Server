package sk.upjs.ics.opiela.kopr.server.dao;

public class SQLQueries {

    public static final String SELECT_ALL_RECEPT = "SELECT \n"
            + "    uuid,\n"
            + "    nazovReceptu,\n"
            + "    menoAutora,\n"
            + "    postupPripravy\n"
            + "FROM recepty";

    public static final String SELECT_RECEPT_BY_UUID = SELECT_ALL_RECEPT + " WHERE uuid = ?";

    public static final String SELECT_INGREDIENCIE_BY_RECEPT = "SELECT \n"
            + "    nazov,\n"
            + "    mnozstvo\n"
            + "FROM ingrediencie\n"
            + "JOIN ingrediencieRecepty ON ingrediencie.id = ingrediencieRecepty.idIngrediencie\n"
            + "WHERE uuidReceptu = ?";

    public static final String SELECT_ID_INGREDIENCIE_BY_NAZOV = "SELECT \n"
            + "    id\n"
            + "FROM ingrediencie\n"
            + "WHERE nazov = ?";
}
