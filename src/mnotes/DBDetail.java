package mnotes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author mmp
 */
public class DBDetail {
    
    String dbname;
    String dbhost;
    String dbport;
    String dbusername;
    String dbpassword;
    
    DBDetail(String path) {
        
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(path));
            
            dbname = props.getProperty("dbname");
            dbhost    = props.getProperty("dbhost");
            dbport    = props.getProperty("dbport");
            dbusername = props.getProperty("dbusername");
            dbpassword = props.getProperty("dbpassword");
            
            
        } catch (IOException ex) {
            
        }
        
    }
}
