import java.util.Vector;

public class SelallCtrl {
    private SelallDB db;


    public SelallCtrl() {
        db = new SelallDB();
    }

    public void setDataBase(String db_name) {
        db.setDataBase(db_name);
    }

    public void executeQuery(String sql) {
        db.open();
        db.executeQuery(sql);
    }

    public void getDataFromDBTable(String column_name[], Vector<String> data[]) {
        db.getDataFromTable(column_name, data);
        db.close();
    }
}
