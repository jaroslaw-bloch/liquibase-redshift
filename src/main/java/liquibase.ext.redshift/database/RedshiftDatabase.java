package liquibase.ext.redshift.database;

import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.database.core.PostgresDatabase;
import liquibase.exception.DatabaseException;
import liquibase.servicelocator.PrioritizedService;
import liquibase.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RedshiftDatabase extends PostgresDatabase {

    private Set<String> redshiftReservedWords = new HashSet<String>();

    public RedshiftDatabase() {
        super.setCurrentDateTimeFunction("GETDATE()");

        redshiftReservedWords.addAll(Arrays.asList("AES128",
                "AES256",
                "ALL",
                "ALLOWOVERWRITE",
                "ANALYSE",
                "ANALYZE",
                "AND",
                "ANY",
                "ARRAY",
                "AS",
                "ASC",
                "AUTHORIZATION",
                "BACKUP",
                "BETWEEN",
                "BINARY",
                "BLANKSASNULL",
                "BOTH",
                "BYTEDICT",
                "CASE",
                "CAST",
                "CHECK",
                "COLLATE",
                "COLUMN",
                "CONSTRAINT",
                "CREATE",
                "CREDENTIALS",
                "CROSS",
                "CURRENT_DATE",
                "CURRENT_TIME",
                "CURRENT_TIMESTAMP",
                "CURRENT_USER",
                "CURRENT_USER_ID",
                "DEFAULT",
                "DEFERRABLE",
                "DEFLATE",
                "DEFRAG",
                "DELTA",
                "DELTA32K",
                "DESC",
                "DISABLE",
                "DISTINCT",
                "DO",
                "ELSE",
                "EMPTYASNULL",
                "ENABLE",
                "ENCODE",
                "ENCRYPT     ",
                "ENCRYPTION",
                "END",
                "EXCEPT",
                "EXPLICIT",
                "FALSE",
                "FOR",
                "FOREIGN",
                "FREEZE",
                "FROM",
                "FULL",
                "GLOBALDICT256",
                "GLOBALDICT64K",
                "GRANT",
                "GROUP",
                "GZIP",
                "HAVING",
                "IDENTITY",
                "IGNORE",
                "ILIKE",
                "IN",
                "INITIALLY",
                "INNER",
                "INTERSECT",
                "INTO",
                "IS",
                "ISNULL",
                "JOIN",
                "LEADING",
                "LEFT",
                "LIKE",
                "LIMIT",
                "LOCALTIME",
                "LOCALTIMESTAMP",
                "LUN",
                "LUNS",
                "LZO",
                "LZOP",
                "MINUS",
                "MOSTLY13",
                "MOSTLY32",
                "MOSTLY8",
                "NATURAL",
                "NEW",
                "NOT",
                "NOTNULL",
                "NULL",
                "NULLS",
                "OFF",
                "OFFLINE",
                "OFFSET",
                "OLD",
                "ON",
                "ONLY",
                "OPEN",
                "OR",
                "ORDER",
                "OUTER",
                "OVERLAPS",
                "PARALLEL",
                "PARTITION",
                "PERCENT",
                "PLACING",
                "PRIMARY",
                "RAW",
                "READRATIO",
                "RECOVER",
                "REFERENCES",
                "REJECTLOG",
                "RESORT",
                "RESTORE",
                "RIGHT",
                "SELECT",
                "SESSION_USER",
                "SIMILAR",
                "SOME",
                "SYSDATE",
                "SYSTEM",
                "TABLE",
                "TAG",
                "TDES",
                "TEXT255",
                "TEXT32K",
                "THEN",
                "TO",
                "TOP",
                "TRAILING",
                "TRUE",
                "TRUNCATECOLUMNS",
                "UNION",
                "UNIQUE",
                "USER",
                "USING",
                "VERBOSE",
                "WALLET",
                "WHEN",
                "WHERE",
                "WITH",
                "WITHOUT"));
    }

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException {
        return StringUtils.trimToEmpty(System.getProperty("liquibase.ext.redshift.force")).equalsIgnoreCase("true")
                || conn.getURL().contains("redshift")
                || conn.getURL().contains(":5439");
    }

    @Override
    public String getShortName() {
        return "redshift";
    }

    @Override
    protected String getDefaultDatabaseProductName() {
        return "Redshift";
    }

    @Override
    public boolean isReservedWord(String tableName) {
        if (super.isReservedWord(tableName)) {
            return true;
        }

        return redshiftReservedWords.contains(tableName.toUpperCase());

    }

    @Override
    public String getCurrentDateTimeFunction() {
        return "GETDATE()";
    }

    @Override
    public int getPriority() {
        return PrioritizedService.PRIORITY_DATABASE;
    }
    /**
     * The PostgresDatabase implementation is not supported by redshift.
     * This method return public. TODO : support any schema by executing a query instead (current_schema())
     *  @see PostgresDatabase#getConnectionSchemaName()
     *  @see AbstractJdbcDatabase#getConnectionSchemaName()
     *  @see liquibase.database.AbstractJdbcDatabase#getConnectionSchemaNameCallStatement()
     * @return
     */
    @Override
    protected String getConnectionSchemaName() {
        return "public";
    }
}
