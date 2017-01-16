package mnotes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import static mnotes.mNConsts.NOTEITEM_HTML_TEMPLATE;

public class Note {

    int Id;
    Date takenDate;
    Date lastUpdated;
    String title;
    String contentFile;

    public static final String NOTEITEMTABLE = "NotesTable";

    Note() {
        Id = -1;
        takenDate = new Date();
        lastUpdated = new Date();
    }

    public static ArrayList<Note> getNotes() {

        try {
            ArrayList<Note> items = new ArrayList<>();

            ResultSet set = Util.getStatement().executeQuery(
                    "select * from " + NOTEITEMTABLE + ";"
            );
            while (set.next()) {

                Note item = new Note();

                item.Id = set.getInt("Id");
                item.title = set.getString("title");
                item.takenDate = set.getDate("takenDate");
                item.lastUpdated = set.getDate("lastUpdated");
                item.contentFile = set.getString("contentFile");

                items.add(item);
            }
            return items;
        } catch (SQLException ex) {
            Logger.getLogger(ToDoItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void createDB() {

        Statement stmt = Util.getStatement();

        try {
            String sql
                    = "create table " + NOTEITEMTABLE + " ("
                    + "Id               int             primary key auto_increment,"
                    + "takenDate        date            not null,"
                    + "lastUpdated      date            not null,"
                    + "title            varchar(200)    not null,"
                    + "contentFile       varchar(1024)   not null"
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

            String td = Util.toSQLDateString(takenDate);
            String ud = Util.toSQLDateString(lastUpdated);

            String sql;

            if (Id != -1) {
                sql = "update " + NOTEITEMTABLE
                        + " set "
                        + "takenDate='" + td + "', "
                        + "lastUpdated='" + ud + "', "
                        + " where Id=" + Id
                        + ";";
            } else {
                sql
                        = "insert into " + NOTEITEMTABLE
                        + " (takenDate, lastUpdated, title, contentFile) values ("
                        + "\'" + td + "\',"
                        + "\'" + ud + "\',"
                        + "\"" + title + "\","
                        + "\"" + contentFile + "\""
                        + ");";
            }
            stmt.executeUpdate(sql);
            Id = Util.getLastSQLid(stmt, "Id", NOTEITEMTABLE);
            Util.sop(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Note.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    String getHTMLPath() {

        String cHTML = Util.getHTMLfromMML(contentFile);
        String HTML = String.format(NOTEITEM_HTML_TEMPLATE, title, takenDate, lastUpdated, cHTML);
        return Util.createHTMLfile(HTML, contentFile);

    }

    void DeleteFromDB() {
        try {
            String sql = "delete from " + NOTEITEMTABLE + " where Id=" + Id + ";";
            Util.getStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Note.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
