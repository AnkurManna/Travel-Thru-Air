package listeners;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class Mylistener
 *
 */
@WebListener
public class Mylistener  implements ServletContextListener{

    /**
     * Default constructor. 
     */
    public Mylistener() {
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see ServletContextEvent#ServletContextEvent(ServletContext)
     */


	/**
     * @see ServletContextAttributeListener#attributeAdded(ServletContextAttributeEvent)
     */
    

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

    public void contextInitialized(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	
    	Connection con=null; 
		 String url = "jdbc:mysql://localhost:3306/test";
		 String uname="root";
		 String pass ="qwerty";
	     try{  
	            Class.forName("com.mysql.jdbc.Driver");  
	            //To get connection object 
	            con=DriverManager.getConnection(url,uname,pass);  
	        }catch(Exception e){System.out.println(e);}  
	     
	     ServletContext ctx=arg0.getServletContext();  
	     ctx.setAttribute("con", con);  
    }
	
}
