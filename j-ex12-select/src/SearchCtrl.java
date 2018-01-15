import java.util.Vector;

public class SearchCtrl {
    private SearchDB db;
    private String table_name = "";

    public SearchCtrl(String db_name) {
        db = new SearchDB(db_name);
    }

    public void setSearchedTableName(String table_name) {
        this.table_name = table_name;
        db.setSearchedTableName(table_name);
    }

    public void setColumnName(String[] column_name) {
        db.setColumnName(column_name);
    }

    public void getAllTuple(Vector<String> data[]) {
        db.executeQuery("select * from " + this.table_name);
        db.getDataFromDB(data);
    }

    public void getTupleBySearch(String condition, Vector<String> data[]) {
        db.executeQuery("select * from " + this.table_name + " where " + condition);
        db.getDataFromDB(data);
    }

    public int getDBDataNum() {
        return db.getDataNum();
    }

    public int getSearchedDataNum() {
        return db.getSeachedDataNum();
    }
}
