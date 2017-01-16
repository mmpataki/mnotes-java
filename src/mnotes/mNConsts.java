/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnotes;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mmp
 */
public class mNConsts {

    public static String ToDoPath;
    public static String NotePath;
    public static String RootPath;
    public static String DBPROPS ;
    public static String ModifiersPath;
    
    /*
    public static String codeCloseTag;
    public static String codeOpenTag;
    public static String imgCloseTag;
    public static String imgOpenTag;
    public static String bannerOpenTag;
    public static String bannerCloseTag;
    */
    
    public static String TODOITEM_HTML_TEMPLATE;
    public static String NOTEITEM_HTML_TEMPLATE;
    
    private static final String INITCONFIG = "code :	{\n" +
"		[\n" +
"			<div style=\"\n" +
"				border: solid 1px #ea4916;\n" +
"				margin: 10px; padding: 5px 10px 5px 10px\n" +
"				font-family: 'Courier';\n" +
"				background-color: #ffe5ac; font-size: 10px\">\n" +
"					<pre>\n" +
"		] , [\n" +
"			</pre></div>\n" +
"		]\n" +
"	}\n" +
"\n" +
"img :	{ \n" +
"		[\n" +
"			<div style=\"margin: 5px;\"> \n" +
"		] , [\n" +
"			</div>\n" +
"		]\n" +
"	}\n" +
"\n" +
"banner :{\n" +
"		[\n" +
"			<pre style=\"font-size: 14; padding: 3px; color: white; background-color: purple;\" >\n" +
"		] , [\n" +
"			</pre>\n" +
"		]\n" +
"	}\n" +
"\n" +
"math: 	{\n" +
"		[\n" +
"			<center style=\"padding: 5px; background: #fff08f; margin: 10px\">\n" +
"				<i>\n" +
"		] , [\n" +
"				</i>\n" +
"			</center>\n" +
"		]\n" +
"	}\n" +
"\n" +
"\n" +
"TODOITEM_HTML_TEMPLATE : {\n" +
"	<html>\n" +
"	<body style=\"\n" +
"		border-left: solid %s 20px;\n" +
"		padding:10px;\n" +
"		background-color:#f5f5f5;\">\n" +
"		<h2>%s</h2>\n" +
"		<div>\n" +
"			<b><i>Start: </i></b>%s\n" +
"			<br/>\n" +
"			<b><i>End: </i></b>%s\n" +
"			<hr/>\n" +
"		</div>\n" +
"		<div style=\"padding-left: 10px\">%s</div>\n" +
"	</body>\n" +
"	</html>\n" +
"	}\n" +
"\n" +
"NOTEITEM_HTML_TEMPLATE : {\n" +
"	<html>\n" +
"	<body style=\"padding:10px;background-color:#f5f5f5\">\n" +
"		<h2>%s</h2>\n" +
"		<br/>\n" +
"		<div>\n" +
"			<b><i>Date Taken: </i></b>\n" +
"				%s\n" +
"			<br/>\n" +
"			<b><i>Last updated: </i></b>\n" +
"				%s\n" +
"			<br/>\n" +
"		</div>\n" +
"		<hr/>\n" +
"		<br/>\n" +
"		<br/>\n" +
"		<div style=\"padding-left: 10px\">\n" +
"			%s\n" +
"		</div>\n" +
"	</body>\n" +
"	</html>\n" +
"	}";
    
    
    static HashMap<String, mCustomTag> customTags = new HashMap<>();
    
    
    static void initConsts(String rootPath) {

        RootPath = rootPath;
        NotePath = RootPath + "/Notes";
        ToDoPath = RootPath + "/ToDos";
        DBPROPS = RootPath + "/DB.props";
        ModifiersPath = RootPath + "/noteModifiers.props";
        try {

            File f = new File(ModifiersPath);
            if(!f.exists()) {
                Files.createFile(Paths.get(ModifiersPath));
                
                PrintWriter pw = new PrintWriter(new File(ModifiersPath));
                
                pw.write(INITCONFIG);
                pw.flush();
            }
            
            Scanner sc = new Scanner(f);
            StringBuilder sb = new StringBuilder();

            while (sc.hasNext()) {
                sb.append(sc.nextLine()).append("\n");
            }

            String raw_prop = sb.toString();
            String props[] = raw_prop.split("[}]");

            for (String prop : props) {

                try {

                    String propparts[] = prop.split(":", 2);
                    String value = propparts[1].split("[{]")[1].trim();

                    switch (propparts[0].trim()) {
                        case "NOTEITEM_HTML_TEMPLATE":
                            NOTEITEM_HTML_TEMPLATE = value;
                            break;
                        case "TODOITEM_HTML_TEMPLATE":
                            TODOITEM_HTML_TEMPLATE = value;
                            break;
                        
                        default:
                            
                            String openCloses[] = value.split("\\][ \t\n]*,[ \t\n]*\\[");
                            if(openCloses.length < 2) {
                                break;
                            }
                            openCloses[0] = openCloses[0].substring(openCloses[0].indexOf('[')+1).trim();
                            openCloses[1] = openCloses[1].substring(0, openCloses[1].lastIndexOf(']')).trim();    
                            customTags.put(propparts[0].trim(), new mCustomTag(openCloses[0], openCloses[1]));
                    }
                    
                } catch(Exception e) {
                    
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(mNConsts.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
