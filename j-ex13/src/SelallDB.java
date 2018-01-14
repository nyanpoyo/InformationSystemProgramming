import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class SelallDB {
    private Connection con;
    private Statement state;
    private ResultSet result;
    private String db_name = "";

    public SelallDB() {
    }

    public void setDataBase(String db_name) {
        this.db_name = db_name;
    }

    public void open() {
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(db_name);
            state = con.createStatement();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void close() {
        try {
            state.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void executeQuery(String sql) {
        try {
            result = state.executeQuery(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void getDataFromTable(String column_name[], Vector<String> data[]) {
        try {
            int i = 0;
            while (result.next()) {
                if (column_name.length != data.length) {
                    break;
                }
                for (int j = 0; j < column_name.length; j++)
                    data[i].add(result.getString(column_name[j]));
                i++;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
