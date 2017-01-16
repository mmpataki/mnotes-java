package mnotes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Util {

    public static Statement getStatement() {
        try {
            DBDetail dbdt = new DBDetail(mNConsts.DBPROPS);
            String dburl = String.format("jdbc:mysql://%s:%s/%s", dbdt.dbhost, dbdt.dbport, dbdt.dbname);
            Connection conn
                    = DriverManager.getConnection(dburl,
                            dbdt.dbusername, dbdt.dbpassword);
            return conn.createStatement();
        } catch (SQLException ex) {

        }
        return null;
    }

    public static void createNoteDB(String dbName) {

        try {
            DBDetail dbdt = new DBDetail(mNConsts.DBPROPS);
            String dburl = String.format("jdbc:mysql://%s:%s/", dbdt.dbhost, dbdt.dbport);
            Connection conn
                    = DriverManager.getConnection(dburl, dbdt.dbusername, dbdt.dbpassword);
            conn.createStatement().execute("create database " + dbName + ";");
        } catch (SQLException ex) {
            if (!ex.getMessage().contains("exists")) {
                File f = new File(mNConsts.DBPROPS);
                if (f.exists()) {
                    f.delete();
                }
            }
        }
    }

    static void sop(String s) {
        System.out.println(s);
    }

    static String toSQLDateString(Date d) {

        // toString() on Date object prints Like : 
        // dow mon dd hh:mm:ss zzz yyyy
        // 0   1   2     3     4   5
        //mySQL's timestamp format is 
        //yyyy-mm-dd hh:mm:ss   (24 Hours)
        String cs[] = d.toString().split(" ");

        //convert 'Nov' like format to 11
        String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        int i = 0;
        for (i = 0; i < months.length; i++) {
            if (months[i].equals(cs[1])) {
                break;
            }
        }

        return (cs[5] + "-" + (i + 1) + "-" + cs[2] + " " + cs[3]);
    }

    static String getHTMLfromMML(String contentFile) {
        return parseNote(readFile(contentFile));
    }

    public static void create_root_directory() {

        try {
            Files.createDirectories(Paths.get(mNConsts.NotePath));
            Files.createDirectories(Paths.get(mNConsts.ToDoPath));
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String readFile(String path) {
        StringBuilder sb = new StringBuilder();
        try (Scanner s = new Scanner(new BufferedReader(new FileReader(path)))) {

            while (s.hasNextLine()) {
                sb.append(s.nextLine()).append("\n");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sb.toString();
    }

    public static String parseNote(String content) {

        StringBuffer sbuf = new StringBuffer(content);

        // To use '@' in the content use '\@' and for '\' use '\\'
        // 0x00 0x01  => @
        // 0x00 0x02  => \
        
        for (int i = 0; i < sbuf.length()-1; i++) {
            try {
                if (sbuf.charAt(i) == '\\') {
                    switch (sbuf.charAt(i + 1)) {
                        case '@':
                            sbuf.setCharAt((i), (char) 0);
                            sbuf.setCharAt((i + 1), (char) 0);
                            break;
                        case '\\':
                            sbuf.setCharAt((i), (char) 0);
                            sbuf.setCharAt((i + 1), (char) 1);
                            break;
                    }
                }
            } catch (Exception e) {
                //Array Index Out Of Bounds
            }
        }

        content = sbuf.toString();

        content = content.trim().replace("<", "&lt;").replace(">", "&gt;");
        String splits[] = content.split("@");

        StringBuilder sb = new StringBuilder();
        Stack<String> stk = new Stack<>();

        for (int i = 1; i < splits.length; i++) {

            if (splits[i].trim().equals("")) {
                if (stk.empty()) {
                    break;
                }
                handlePop(sb, stk.pop());
                continue;
            }

            if (" \t\n\r".contains("" + splits[i].charAt(0))) {
                handlePop(sb, stk.pop());
                sb.append(splits[i]);
                continue;
            }
            
            /* get the tag & attributes out */
            String tagcomps[];
            int parindex = splits[i].indexOf('(');
            int spaindex = splits[i].split("[ \t\n]")[0].length();

            if (spaindex == -1 || parindex == -1) {
                if (spaindex != -1) {
                    tagcomps = splits[i].split("[ \t\n]", 2);
                } else {
                    tagcomps = splits[i].split("[)]", 2);
                }
            } else if (spaindex > parindex) {
                tagcomps = splits[i].split("[)]", 2);
            } else {
                tagcomps = splits[i].split("[ \t\n]", 2);
            }

            //push the TAG.
            String tagnattr[] = tagcomps[0].split("[(]", 2);

            stk.push(tagnattr[0]);
            handlePush(sb, tagnattr[0]);

            //Now add the tag to markup being built.
            sb.append("<").append(tagnattr[0]);
            if (tagnattr.length > 1 && !mNConsts.customTags.containsKey(tagnattr[0])) {
                tagnattr[1] = tagnattr[1].replace(")", "");
                sb.append(" ").append(tagnattr[1].trim());
            }
            sb.append(">");

            if (tagcomps.length > 1) {
                sb.append(tagcomps[1]);
            }
        }

        while (!stk.empty()) {
            handlePop(sb, stk.pop());
        }

        String toret = sb.toString().replace("\000\000", "@").replace("\000\001", "\\");

        for (String string : mNConsts.customTags.keySet()) {
            toret = toret.replace("<" + string + ">", "").replace("</" + string + ">", "");
        }
        return toret;
    }

    static String createHTMLfile(String HTML, String contentFile) {

        try {
            FileWriter wrt = new FileWriter(contentFile + ".html");
            wrt.write(HTML);
            wrt.flush();
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contentFile + ".html";
    }

    static String createDirectory(String RootPath, String newND) {
        try {
            newND = newND.replace('/', '_');
            newND = newND.replace(' ', '_');
            Files.deleteIfExists(Paths.get(RootPath + "/" + newND));
            Files.createDirectory(Paths.get(RootPath + "/" + newND));
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return RootPath + "/" + newND;
    }

    static ArrayList<String> selectFiles(JFrame frame) {

        JFileChooser fc = new JFileChooser();
        int opt = fc.showOpenDialog(frame);
        ArrayList<String> flist = new ArrayList<>();

        if (opt == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            flist.add(file.getAbsolutePath());
        }
        return flist;
    }

    private static void handlePop(StringBuilder sb, String top) {
        sb.append("</").append(top).append(">");

        if (mNConsts.customTags.containsKey(top)) {
            sb.append(mNConsts.customTags.get(top).closeTag);
        }
    }

    private static void handlePush(StringBuilder sb, String string) {
        if (mNConsts.customTags.containsKey(string)) {
            sb.append(mNConsts.customTags.get(string).openTag);
        }
    }

    static void deleteDirectory(String path) {

        try {

            Path dir = Paths.get(path);
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
                for (Path file : stream) {
                    Files.delete(file);
                }
            } catch (IOException | DirectoryIteratorException x) {
                System.out.println(x);
            }

            Files.deleteIfExists(Paths.get(path));
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static int getLastSQLid(Statement stmt, String id, String TabName) {
        int Id = -1;
        try {
            ResultSet set = stmt.executeQuery("select max(" + id + ") as mId from " + TabName);
            set.next();
            Id = set.getInt("mId");
        } catch (SQLException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Id;
    }

}
