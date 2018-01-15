import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class SearchDB {
    private Connection connection;
    private Statement state;
    private ResultSet result;
    private String searched_table_name = "";
    private String[] column_name;
    private int db_data_num = 0;
    private int searched_data_num = 0;

    public SearchDB(String db_name) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(db_name);
            state = connection.createStatement();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setSearchedTableName(String name) {
        this.searched_table_name = name;
        try {
            this.executeQuery("select * from " + name);
            while (result.next()) {
                this.db_data_num++;
            }
            System.out.println("row " + this.db_data_num);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setColumnName(String[] name) {
        column_name = name;
    }

    public void executeQuery(String sql) {
        try {
            result = state.executeQuery(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int getDataNum() {
        return this.db_data_num;
    }

    public int getSeachedDataNum() {
        return this.searched_data_num;
    }

    public void getDataFromDB(Vector<String> data[]) {
        try {
            int i = 0;
            while (result.next()) {
                for (int j = 0; j < column_name.length; j++) {
                    data[i].add(result.getString(this.column_name[j]));
                }
                i++;
            }
            this.searched_data_num = i;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
