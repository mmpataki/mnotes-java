package mnotes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import static mnotes.mNConsts.TODOITEM_HTML_TEMPLATE;

public class ToDoItem {

    int Id;
    int priority;
    Date startDate;
    Date endDate;
    String title;
    String contentFile;    //path to the file containing content.

    public static final String TODOITEMTABLE = "ToDoItem";

    public ToDoItem() {
        Id = -1;
    }

    public static void createDB() {

        Statement stmt = Util.getStatement();

        try {
            String sql
                    = "create table " + TODOITEMTABLE + " ("
                    + "Id               int             primary key auto_increment,"
                    + "startDate        date            not null,"
                    + "endDate          date            not null,"
                    + "title            varchar(200)    not null,"
                    + "priority         int             not null,"
                    + "contentFile      varchar(1024)   not null"
                    + ");";
            Util.sop(sql);
            stmt.execute(sql);

        } catch (Exception e) {
            //Don't panic, error is good sometimes.
        }
    }

    public void insertDB() {

        try {
            Statement stmt = Util.getStatement();

            String ss = Util.toSQLDateString(startDate);
            String es = Util.toSQLDateString(endDate);

            String sql;

            if (Id != -1) {

                sql = "update " + TODOITEMTABLE
                        + " set "
                        + "startDate='" + ss + "', "
                        + "endDate='" + es + "', "
                        + "priority=" + priority
                        + " where Id=" + Id
                        + ";";
            } else {
                sql
                        = "insert into " + TODOITEMTABLE
                        + " (startDate, endDate, title, priority, contentFile) values ("
                        + "\'" + ss + "\',"
                        + "\'" + es + "\',"
                        + "\"" + title + "\","
                        + "" + priority + ","
                        + "\"" + contentFile + "\""
                        + ");";
            }
            Util.sop(sql);
            stmt.executeUpdate(sql);
            Id= Util.getLastSQLid(stmt, "Id", TODOITEMTABLE);
            
        } catch (SQLException ex) {
            Logger.getLogger(ToDoItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<ToDoItem> getToDos() {

        try {
            ArrayList<ToDoItem> items = new ArrayList<>();

            ResultSet set = Util.getStatement().executeQuery(
                    "select * from " + TODOITEMTABLE + ";"
            );
            while (set.next()) {

                ToDoItem item = new ToDoItem();

                item.Id = set.getInt("Id");
                item.title = set.getString("title");
                item.startDate = set.getDate("startDate");
                item.endDate = set.getDate("endDate");
                item.priority = set.getInt("priority");

                item.contentFile = set.getString("contentFile");

                items.add(item);
            }
            return items;
        } catch (SQLException ex) {
            Logger.getLogger(ToDoItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getColorFromPriority() {
        String Colors[] = {"green", "#5fd0df", "orange", "red"};
        return Colors[priority];
    }

    public String getHTMLPath() {

        String cHTML = Util.getHTMLfromMML(contentFile);
        String HTML = String.format(TODOITEM_HTML_TEMPLATE, getColorFromPriority(), title, startDate, endDate, cHTML);
        return Util.createHTMLfile(HTML, contentFile);
    }

    void DeleteFromDB() {
        try {
            String sql = "delete from " + TODOITEMTABLE + " where Id=" + Id + ";";
            Util.getStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ToDoItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
