import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdTest {
    private Connection connect;
    private Statement state;
    private ResultSet result;
    private final String column_name[] = {"sno", "sname", "address", "age"};

    UpdTest(String db_name) {
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection(db_name);
            state = connect.createStatement();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void executequery(String sql) {
        try {
            result = state.executeQuery(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void executeUpdate(String sql) {
        try {
            state.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeDB() {
        try {
            state.close();
            connect.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void getDataFromDB() {
        this.executequery("select * from Student");
        try {
            while (result.next()) {
                for (int i = 0; i < column_name.length; i++) {
                    System.out.print(result.getString(column_name[i]) + " ");
                }
                System.out.println("");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String args[]) {
        UpdTest obj = new UpdTest("jdbc:sqlite:/Users/takumi/Downloads/stock3.s3db");
        obj.executeUpdate("insert into Student values('11111','nya-n', 'chiba',55)");
        obj.getDataFromDB();
        obj.executeUpdate("update Student set address='tokyo' where address='chiba'");
        obj.getDataFromDB();
//        obj.executeUpdate("delete from Student where address='tokyo'");
//        obj.getDataFromDB();
        obj.closeDB();
    }
}
