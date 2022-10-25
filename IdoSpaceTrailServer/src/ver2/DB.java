package ver2;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * Document : DB - מחלקת שירות שקבלת חיבור למסד נתונים Date : 1/9/2019 Author :
 * Ilan Perets(ilanperets@gmail.com)
 */
public class DB
{
    /**
     * הפעולה מחזירה חיבור למסד נתנונים רצוי
     *
     * @param dbPath הנתיב המלא למסד הנתונים עבורו רוצים חיבור
     * @return nullמחזיר חיבור אם קיים מסד נתונים ואין בעיות אחרת מחזיר
     */
    public static Connection getConnection(String dbPath)
    {
        String dbUserName = "";
        String dbPassword = "";
        String dbFilePath = "";
        Connection dbCon = null;

        try
        {
            // for run in developing mode 
            // -------------------------------------
            Path path = Paths.get(DB.class.getResource(dbPath).toURI());
            dbFilePath = path.toString().replace("build\\classes", "src");

            // for run in stand alone jar, remove the comment from the nex line
            // ----------------------------------------------------------------
            //dbFilePath = new File("mydb.accdb").getPath();
            
            String dbURL = "jdbc:ucanaccess://" + dbFilePath;
            dbCon = DriverManager.getConnection(dbURL, dbUserName, dbPassword);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error DB Connection!", JOptionPane.ERROR_MESSAGE);
        }

        return dbCon;
    }

    public static ResultSet runSqlQuery(String sqlQuery) throws SQLException
    {
        Connection con = getConnection("/assets/mydb.accdb");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sqlQuery);

        st.close();
        con.close();

        return rs;
    }

    public static Boolean isUserExists(String un, String pw) 
    {
        Boolean result = null;
        try
        {
            String query = "SELECT * FROM users WHERE un='" + un + "' AND pw='" + pw + "'";
            //System.out.println("query="+query);
            ResultSet rs = DB.runSqlQuery(query);
            result = rs.next();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        return result;
    }
}
