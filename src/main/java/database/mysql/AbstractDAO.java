package database.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Centralizes shared database operations
 * @author remideboer, gerke de boer, Michael Oosterhout
 *
 */
public abstract class AbstractDAO {
	//CONSTANTEN
	protected final static String TABEL_GEBRUIKER = "gebruiker";
	protected final static String KOLOM_INLOGNAAM = "inlognaam";
	protected final static String KOLOM_ROL = "rol";
	protected final static String KOLOM_WACHTWOORD = "wachtwoord";
	protected final static String NIKS_OPGEHAALD = "Geen record opgehaald uit database" ;

	protected DBAccess dBaccess;

	public AbstractDAO(DBAccess dBaccess) {
		this.dBaccess = dBaccess;
	}
	
	/**
	 * Builds a prepared Statement from the sql string. A DAO should used this to
	 * fill the parameters.
	 * 
	 * @param sql,
	 *            the SQl query
	 */
	protected PreparedStatement getStatement(String sql) throws SQLException {
		return dBaccess.getConnection().prepareStatement(sql);
	}

	/**
	 * Executes a prepared statement without result. Used for insert, update and
	 * delete statements.
	 * @param preparedStatement,
	 *            the prepared statement filled by a DAO
	 */
	protected void executeManipulatePreparedStatement(PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.executeUpdate();
	}

	/**
	 * Executes a prepared statement with result. Used for select statements.
	 * 
	 * @param preparedStatement,
	 *            the prepared statement filled by a DAO
	 */
	protected ResultSet executeSelectPreparedStatement(PreparedStatement preparedStatement) throws SQLException {
		return preparedStatement.executeQuery();
	}

	/**
	 * Executes a prepared statement with result to get a generated key.
	 * 
	 * @param sql,
	 *            the SQL query
	 */
	protected PreparedStatement getStatementWithKey(String sql) throws SQLException {
		return dBaccess.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	}

	protected int executeInsertPreparedStatement(PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.executeUpdate();
		ResultSet resultSet = preparedStatement.getGeneratedKeys();
		int gegenereerdeSleutel = 0;
		while (resultSet.next()) {
			gegenereerdeSleutel = resultSet.getInt(1);
		}
		return gegenereerdeSleutel;
	}

	/** CODE WERKT NIET
	 * Geeft een "SELECT * FROM"-query terug als string.
	 * parameters: tabel-naam, te checken kolomnaam, attribuut-waarde .
	 */
	protected String getSelectAllQuery(String table, String columnName){
		return String.format("SELECT * FROM %s WHERE %s = ?",table, columnName);
	}


}
