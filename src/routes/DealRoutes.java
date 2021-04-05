package routes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
/**
 * Servlet implementation class testServlet
 */
import java.io.*; 
import java.util.*; 
import java.sql.*;

import models.DealModel;
import controllers.DealController;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DealRoutes
 */
@WebServlet("/DealRoutes")
public class DealRoutes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DealRoutes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		System.out.println("in DO GET");
		
		//response. addHeader("Access-Control-Allow-Origin", "*"); 

		
		
		System.out.println("kk");
	

		 ServletContext ctx=getServletContext();  
	     Connection con=(Connection)ctx.getAttribute("con");  
        
        if(request.getParameter("op").equals("get_by_id"))
        {
        	System.out.print("get_by_id");
        	DealModel res = new DealModel();
        	int idx = Integer.parseInt(request.getParameter("Id"));
        	res = DealController.getDealModelById(idx,con);
        	if(res==null)
        		System.out.println("NULL");
        	//res = DealController.getDealModelById(idx);
        	
        	ObjectMapper Obj = new ObjectMapper();
            
            try {
     
                // get Oraganisation object as a json string
                String jsonStr = Obj.writeValueAsString(res);
     
                // Displaying JSON String
                response.getWriter().write(jsonStr);
            }
            catch (IOException e) {
	            e.printStackTrace();
	        }
        }
        else if(request.getParameter("op").equals("get_all"))
        {
        	System.out.print("getting_all");
        	ArrayList<DealModel> arr = new ArrayList<DealModel>();
        			arr = DealController.getAllDealModels(con);
        	
        	
        	ObjectMapper Obj = new ObjectMapper();
            
            try {
     
                // get Oraganisation object as a json string
                String jsonStr = Obj.writeValueAsString(arr);
     
                // Displaying JSON String
                response.getWriter().write(jsonStr);
            }
            catch (IOException e) {
	            e.printStackTrace();
	        }
            
        	
        }
        else if(request.getParameter("op").equals("delete"))
        {
        	System.out.print("deleting");
        	DealController.delete(Integer.parseInt(request.getParameter("Id")),con);
        }
        else if(request.getParameter("op").equals("search"))
        {
        	System.out.print("in search");
        	System.out.println(request.getParameter("start"));
        	System.out.println(request.getParameter("end"));
        	ArrayList<ArrayList<DealModel>> paths = DealController.search(request.getParameter("start"),request.getParameter("end"),con);
        		ObjectMapper Obj = new ObjectMapper();
		            
            try {
     
                // get Oraganisation object as a json string
                String jsonStr = Obj.writeValueAsString(paths);
     
                // Displaying JSON String
                response.getWriter().write(jsonStr);
            }
            catch (IOException e) {
	            e.printStackTrace();
	        }
        }
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("in DO POST");
		//response. addHeader("Access-Control-Allow-Origin", "*"); 

		/*String test = request.getReader().lines().collect(Collectors.joining());
		System.out.print(test);
		System.out.println("post3");
		String body="";
		for(int i=0;i<test.length();i++)
		{
			if(test.charAt(i)!='{'&&test.charAt(i)!='}'&&test.charAt(i)!='\n'&&test.charAt(i)!='\r'&&test.charAt(i)!=' '&&test.charAt(i)!='"')
				body += test.charAt(i);
		}
		*/
		 Map<String, String> mp 
         = new HashMap<String, String>(); 
        
        
       /* String[] arrOfStr = body.split(",", 8); 
        for (int i=0;i<arrOfStr.length;i++)
        {
        	String[] h = arrOfStr[i].split(":",2);
        	
        	
        		mp.put(h[0], h[1]);
            	System.out.println(arrOfStr[i]);
            	System.out.println(h[0]);
            	System.out.println(h[1]);
            	System.out.print("hey");
            	System.out.print(mp.get(h[0]));
            	System.out.print("hi");
        	
        	
        }*/
		/* ServletContext context=request.getServletContext();  
		mp = (Map<String, String>) context.getAttribute("mp");   */
		   
	        HttpSession session=request.getSession(false);  
	        //String n=(String)session.getAttribute("uname");  
		
		mp = (Map<String, String>) session.getAttribute("mp");  
        DealModel d = new DealModel();
        DealController dc = new DealController();
    	d.setArrivalCity(mp.get("ArrivalCity"));
    	d.setDepartureCity(mp.get("DepartureCity"));
    	d.setCost(Integer.parseInt(mp.get("Cost")));
    	d.setStartTime(mp.get("StartTime"));
    	d.setEndTime(mp.get("EndTime"));
    	d.setIsSpecial(Integer.parseInt(mp.get("IsSpecial")));
    	if(mp.get("Id")!=null)
    	d.setId(Integer.parseInt(mp.get("Id")));
    	
    	 ServletContext ctx=getServletContext();  
	     Connection con=(Connection)ctx.getAttribute("con");  
    	
        if(mp.get("op").equals("create"))
        {
        	System.out.print("Saving");
        	DealController.save(d,con);
        }
        else if(mp.get("op").equals("update"))
        {
        	System.out.print("updating");
        	DealController.update(d,con);
        }
        
        
        System.out.print(mp.get("ArrivalCity"));
       
	}

}
