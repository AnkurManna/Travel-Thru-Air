package controllers;
import models.DealModel;
import utils.Graph;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
public class DealController {
	
	private static Connection con;
	public DealController()
	{
		/*con=null; 
		 String url = "jdbc:mysql://localhost:3306/test";
		 String uname="root";
		 String pass ="qwerty";
	     try{  
	            Class.forName("com.mysql.jdbc.Driver");  
	            //To get connection object 
	            con=DriverManager.getConnection(url,uname,pass);  
	        }catch(Exception e){System.out.println(e);}  */
	}
	
	public static int save(DealModel d,Connection con){  
        int status=0;  
        try{  
        	
            PreparedStatement ps=con.prepareStatement("INSERT INTO flights (ArrivalCity,DepartureCity,Cost,StartTime,EndTime,IsSpecial) VALUES(?,?,?,?,?,?)");  
            ps.setString(1,d.getArrivalCity());  
            ps.setString(2,d.getDepartureCity());  
            ps.setInt(3,d.getCost());  
            ps.setTime(4,Time.valueOf(d.getStartTime()));  
            ps.setTime(5,Time.valueOf(d.getEndTime()));  
           
            ps.setInt(6, d.getIsSpecial());
            
              
            status=ps.executeUpdate();  
              
           // con.close();  
        }catch(Exception ex){ex.printStackTrace();}  
          
        return status;  
    }  
	
	 public static int update(DealModel d,Connection con){  
	        int status=0;  
	        
	        try{  
	            
	            PreparedStatement ps=con.prepareStatement(  
	                         "update flights set ArrivalCity=?,DepartureCity=?,Cost=?,StartTime=?,EndTime=?,IsSpecial=? where id=?");  
	            ps.setString(1,d.getArrivalCity());  
	            ps.setString(2,d.getDepartureCity());  
	            ps.setInt(3,d.getCost());  
	            ps.setTime(4,Time.valueOf(d.getStartTime()));  
	            ps.setTime(5,Time.valueOf(d.getEndTime()));  
	            ps.setInt(6, d.getIsSpecial());
	            ps.setInt(7, d.getId());
	              
	            status=ps.executeUpdate();  
	              
	           // con.close();  
	        }catch(Exception ex){ex.printStackTrace();}  
	          
	        return status;  
	    }  
	   
	 public static int delete(int id,Connection con){  
	        int status=0;  
	        try{  
	            
	            PreparedStatement ps=con.prepareStatement("delete from flights where id=?");  
	            ps.setInt(1,id);  
	            status=ps.executeUpdate();  
	              
	            //con.close();  
	        }catch(Exception e){e.printStackTrace();}  
	          
	        return status;  
	    }  
	 
	   public static DealModel getDealModelById(int id,Connection con){  
	        DealModel d=new DealModel();  
	        
	       
		
	          
	        try{  
	            //Connection con=EmpDao.getConnection();  
	        	System.out.println(id);
	            PreparedStatement ps=con.prepareStatement("select * from flights where id=? ");  
	            ps.setInt(1,id);  
	        	//System.out.println(ps);

	            ResultSet rs=ps.executeQuery();  
	            rs.next();
	          {  
	            	//System.out.println(rs.getString("ArrivalCity"));
	                d.setArrivalCity(rs.getString("ArrivalCity"));  
	                d.setDepartureCity(rs.getString("DepartureCity"));  
	                d.setCost(rs.getInt("Cost"));
	                d.setStartTime(rs.getTime("StartTime").toString());  
	                d.setEndTime(rs.getTime("EndTime").toString());  
	         
	                d.setId(id);
	            }  
	            //con.close();  
	        }catch(Exception ex){ex.printStackTrace();
        	System.out.println(ex);

	        }  
	          
	        return d;  
	    }  
	   
	   public static ArrayList<DealModel> getAllDealModels(Connection con){  
		   ArrayList<DealModel> arr = new ArrayList<DealModel>(); 
		   
		   
	          
	        try{  
	            //Connection con=EmpDao.getConnection();  
	            PreparedStatement ps=con.prepareStatement("select * from flights where IsSpecial=1 and EndTime between current_time() and   '23:59:59' ");  
	            ResultSet rs=ps.executeQuery();  
	            while(rs.next()){  
	                DealModel d=new DealModel();  
	                d.setId(rs.getInt(1));  
	                d.setArrivalCity(rs.getString(2));  
	                d.setDepartureCity(rs.getString(3));  
	                d.setCost(rs.getInt(4));  
	                d.setStartTime(rs.getTime(5).toString());  
	                d.setEndTime(rs.getTime(6).toString());   
	                d.setIsSpecial(1);
	                arr.add(d);  
	            }  
	            //con.close();  
	        }catch(Exception e){e.printStackTrace();}  
	        
	
	          
	        return arr;  
	    }  
	   
	   
	      public static ArrayList<ArrayList<DealModel>> search(String start,String end,Connection con)
	      {
	    	  ArrayList<DealModel> allDeal = getAllDealModels(con);
	    	  
	    	  HashMap<String,Integer> city = new HashMap<String, Integer>();
	    	  int x = 0;
	    	  for(int i=0;i<allDeal.size();i++)
	    	  {
	    		  if(!city.containsKey(allDeal.get(i).getDepartureCity()))
    			  {
    			  city.put(allDeal.get(i).getDepartureCity(), x);
    			  x++;
    			  }
	    		  if(!city.containsKey(allDeal.get(i).getArrivalCity()))
    			  {
    			  city.put(allDeal.get(i).getArrivalCity(), x);
    			  x++;
    			  }
	    	  }
	    	  for (Entry<String, Integer> entry : city.entrySet())
	              System.out.println("Key = " + entry.getKey() +
	                               ", Value = " + entry.getValue());
	    	  
	    	  int[][] arr = new int[x+5][x+5];
	    	  
	    	  
	    	  
	    	  Graph g = new Graph(x);
	    	  for(int i=0;i<allDeal.size();i++)
	    	  {
	    		  int u = (city.get(allDeal.get(i).getDepartureCity()));
	    		  int v = (city.get(allDeal.get(i).getArrivalCity()));
	    		  arr[u][v] = i;
	    		  g.addEdge(city.get(allDeal.get(i).getDepartureCity()), city.get(allDeal.get(i).getArrivalCity()));
	    	  }
	    	  g.printAllPaths(city.get(start), city.get(end));
	    	 
	    	  ArrayList<ArrayList<Integer>>paths = g.paths;
	    	  for (int i=0;i<paths.size();i++)
	    		  System.out.println(paths.get(i));
	    	  
	    	  ArrayList<ArrayList<DealModel>> ans = new ArrayList<ArrayList<DealModel>>();
	    	  for(int i=0;i<paths.size();i++)
	    	  {
	    		  ArrayList <DealModel> cur = new ArrayList<DealModel>();
	    		  
	    		  for(int j=1;j<paths.get(i).size();j++)
	    		  {
	    			  
	    			  cur.add(allDeal.get(arr[paths.get(i).get(j-1)][paths.get(i).get(j)]));
	    		  }
	    		  
	    		  ans.add(cur);
	    		  
	    	  }
	    	  
	    	  return ans;
	      }
	   
	
	
}
