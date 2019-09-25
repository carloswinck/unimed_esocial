package br.com.unimedcuritiba.core.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import br.com.visionnaire.core.util.CustomDate;

public class JDBCUtil
{

	protected final static Logger log = Logger.getLogger(JDBCUtil.class);

	/**
	 * Cria uma datasource para o Jndi informado
	 *
	 * @param jndiName
	 * @return
	 * @throws NamingException
	 */
	public static DataSource createDatasource (final String jndiName) throws NamingException
	{

		if (jndiName != null)
		{
			// Initialize datasource
			log.debug("Conectando no DataSource. lookup :" + jndiName);
			try
			{
				return (DataSource)new InitialContext().lookup(jndiName);
			}
			catch (final NamingException e)
			{
				log.error(e.getLocalizedMessage(), e);
				throw e;
			}
		}
		return null;
	}

	/**
	 * Returns a connection to JNDI Datasource.
	 *
	 * @param jndiName
	 * @return Connection to datasource
	 * @throws NamingException
	 * @throws SQLException
	 */
	public static Connection createConnection (final String jndiName) throws NamingException, SQLException
	{

		Connection connection = null;
		try
		{
			if (jndiName != null)
			{
				// Initialize datasource
				final DataSource datasource = createDatasource(jndiName);

				if (datasource != null)
				{
					// Retrieve a connection from datasource
					log.debug("Obtendo Conex�o do DataSource");
					connection = datasource.getConnection();
					connection.setAutoCommit(false);
				}
			}
		}
		catch (final NamingException e)
		{
			throw e;
		}
		catch (final SQLException e)
		{
			throw e;
		}

		if (connection == null)
		{
			log.warn("Connection is null");
		}

		return connection;
	}

	/**
	 * Seta nulo ao index informado
	 *
	 * @param st
	 * @param index
	 * @throws SQLException
	 */
	public final static void setNullParameter (final PreparedStatement st, final int index) throws SQLException
	{
		try
		{
			// tenta inserir parametro numero
			st.setNull(index, Types.NUMERIC);
		}
		catch (final Exception e)
		{
			// senao der certo.. tenta varchar
			log.debug("Nao foi possivel setar nulo em type NUMERIC", e);
			log.debug("Tentando inserir parametro VARCHAR no index: " + index);
			st.setNull(index, Types.VARCHAR);
		}
	}

	/**
	 * Seta parametro no statement
	 *
	 * @param st
	 * @param value
	 * @param index
	 * @throws SQLException
	 */
	@SuppressWarnings("boxing")
	public final static void setParameterStatement (final PreparedStatement st, final Object value, final int index) throws SQLException
	{
		if (st != null)
		{
			if (value != null)
			{
				if (value instanceof Byte)
				{
					final Byte x = (Byte)value;
					st.setByte(index, x);
				}
				else if (value instanceof Short)
				{
					final Short x = (Short)value;
					st.setShort(index, x);
				}
				else if (value instanceof Integer)
				{
					final Integer x = (Integer)value;
					st.setInt(index, x);
				}
				else if (value instanceof Long)
				{
					final Long x = (Long)value;
					st.setLong(index, x);
				}
				else if (value instanceof Float)
				{
					final Float x = (Float)value;
					st.setFloat(index, x);
				}
				else if (value instanceof Double)
				{
					final Double x = (Double)value;
					st.setDouble(index, x);
				}
				else if (value instanceof Character)
				{
					final Character x = (Character)value;
					st.setString(index, String.valueOf(x));
				}
				else if (value instanceof String)
				{
					final String x = (String)value;
					st.setString(index, x);
				}
				else if (value instanceof Boolean)
				{
					final Boolean x = (Boolean)value;
					st.setBoolean(index, x);
				}
				else if (value instanceof BigDecimal)
				{
					final BigDecimal x = (BigDecimal)value;
					st.setBigDecimal(index, x);
				}
				else if (value instanceof Timestamp)
				{
					final Timestamp x = (Timestamp)value;
					st.setTimestamp(index, x);
				}
				else if (value instanceof java.sql.Date)
				{
					final java.sql.Date x = (java.sql.Date)value;
					st.setDate(index, x);
				}
				else if (value instanceof java.util.Date)
				{
					final java.util.Date date = (java.util.Date)value;
					final java.sql.Date x = new java.sql.Date(date.getTime());
					st.setDate(index, x);
				}
				else if (value instanceof Calendar)
				{
					final java.sql.Date x = CustomDate.toSqlDate((Calendar)value);
					st.setDate(index, x);
				}
				else
				{
					log.warn("Nao foi possivel encontrar tipo de parametro: " + value);
					log.warn("Setando nulo ao parametro da posicao: " + index);
					setNullParameter(st, index);
				}
			}
			else
			{
				setNullParameter(st, index);
			}
		}
	}

	/**
	 * Fecha conex�o JDBC
	 *
	 * @param connection
	 */
	public static void closeConnection (Connection connection)
	{
		try
		{
			if (connection != null)
			{
				connection.close();
			}
		}
		catch (final SQLException e)
		{
			// ok
		}
		connection = null;
	}

	/**
	 * Fecha o statement JDBC
	 *
	 * @param statement
	 */
	public final static void closeStatement (Statement statement)
	{
		try
		{
			if (statement != null)
			{
				statement.close();
			}
		}
		catch (final SQLException e)
		{
			// Ok
		}
		finally
		{
			statement = null;
		}
	}

	/**
	 * Fecha o ResultSet JDBC
	 *
	 * @param statement
	 */
	public final static void closeResultSet (ResultSet rs)
	{
		try
		{
			if (rs != null)
			{
				rs.close();
			}
		}
		catch (final SQLException e)
		{
			// Ok
		}
		finally
		{
			rs = null;
		}
	}

	/**
	 * Fecha a conex�o
	 * 
	 * @param connection
	 * @param statement
	 * @param rs
	 */
	public final static void close (final Connection connection, final Statement statement, final ResultSet rs)
	{
		closeResultSet(rs);
		closeStatement(statement);
		closeConnection(connection);
	}
}
