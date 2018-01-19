import javax.swing.*;
import java.util.Vector;

public class UpdateCtrl {
    private UpdateDB db;
    private String table_name = "";

    public UpdateCtrl(String db_name) {
        db = new UpdateDB(db_name);
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

    public String[] readTextField(JTextField text_field) {
        String text = text_field.getText();
        String content[] = text.split(" ", 0);
        return content;
    }

    public void insertIntoDB(String[] data) {
        System.out.println("length " + data.length);
        String sno = data[0];
        String sname = data[1];
        String address = "null";
        String age = "null";
        int temp_age = 0;
        if (data.length == 3) {
            try {
                temp_age = Integer.parseInt(data[2]);
            } catch (NumberFormatException nfe) {
                address = data[2];
            }
        } else if (data.length == 4) {
            address = data[2];
            age = data[3];
        } else {
            System.out.println("insert error! check the length of element to insert");
        }
        db.executeUpdate("insert into " + table_name + " values('" + sno + "','" + sname + "','" + address + "'," + age + ")");
    }

    public void deleteFromDB(Vector<String> data) {
        if (data.size() < 3) {
            System.out.println("size=" + data.size());
            return;
        }
        String sno = data.get(0);
        String sname = data.get(1);
        String address = "null";
        String age = "null";
        int temp_age = 0;
        if (data.size() == 3) {
            System.out.println("size =" + data.size());
            try {
                temp_age = Integer.parseInt(data.get(2));
                db.executeUpdate("delete from " + table_name + " where sno='" + sno + "' and sname='" + sname + "' and age=" + age);
            } catch (NumberFormatException nfe) {
                address = data.get(2);
                db.executeUpdate("delete from " + table_name + " where sno='" + sno + "' and sname='" + sname + "' and address='" + address + "'");
            }
        } else if (data.size() == 2) {
            db.executeUpdate("delete from " + table_name + " where sno='" + sno + "' and sname='" + sname + "'");
        } else if (data.size() == 4) {
            try {
                address = data.get(2);
            } catch (NullPointerException npe) {
                address = "null";
            }
            try {
                age = data.get(3);
            } catch (NullPointerException npe) {
                age = "null";
            }
            if (address == null) {
                System.out.println("address is null");
                db.executeUpdate("delete from " + table_name + " where sno='" + sno + "' and sname='" + sname + "' and address is null");
            } else if (age == null) {
                System.out.println("age is null");

                db.executeUpdate("delete from " + table_name + " where sno='" + sno + "' and sname='" + sname + "' and age is null");
            } else if (address == null && age == null) {
                System.out.println("address & age is null");
                db.executeUpdate("delete from " + table_name + " where sno='" + sno + "' and sname='" + sname + "' and address is null and age is null");
            } else {
                db.executeUpdate("delete from " + table_name + " where sno='" + sno + "' and sname='" + sname + "' and address='" + address + "' and age=" + age);
            }
        } else {
            System.out.println("insert error! check the length of element to insert");
        }
        System.out.println("delete executed");
    }

    public void correctDBData(String correct_data, String condition, String column_name) {
        if (column_name.equals("age")) {
            db.executeUpdate("update " + table_name + " set " + column_name + "=" + correct_data + " where " + column_name + "=" + condition);
        } else {
            db.executeUpdate("update " + table_name + " set " + column_name + "='" + correct_data + "' where " + column_name + "='" + condition + "'");
        }
    }

    public int getDBDataNum() {
        return db.getDataNum();
    }

    public int getSearchedDataNum() {
        return db.getSeachedDataNum();
    }
}