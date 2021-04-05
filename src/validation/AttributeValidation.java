package validation;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.servlet.http.*;  
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class AttributeValidation
 */
@WebFilter("/AttributeValidation")
public class AttributeValidation implements Filter {

    /**
     * Default constructor. 
     */
	static int count=0;  
    public AttributeValidation() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		System.out.println("AttributeValidation Filter HTTP Request: " + request.getMethod());
		String test = request.getReader().lines().collect(Collectors.joining());
		System.out.print(test);
		System.out.println("post3");
		String body="";
		for(int i=0;i<test.length();i++)
		{
			if(test.charAt(i)!='{'&&test.charAt(i)!='}'&&test.charAt(i)!='\n'&&test.charAt(i)!='\r'&&test.charAt(i)!=' '&&test.charAt(i)!='"')
				body += test.charAt(i);
		}
		
		 Map<String, String> mp 
         = new HashMap<String, String>(); 
        
        
    if(request.getMethod().equals("POST"))
    {
    	String regex = "^[A-Za-z]\\w{5,29}$";
    	  
        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
  
  
        String[] arrOfStr = body.split(",", 8); 
        for (int i=0;i<arrOfStr.length;i++)
        {
        	String[] h = arrOfStr[i].split(":",2);
        	
        	if(arrOfStr.length>1)
        	{
        		mp.put(h[0], h[1]);
            	System.out.println(arrOfStr[i]);
            	System.out.println(h[0]);
            	System.out.println(h[1]);
        	}
        }
        //for time validation
        String timePattern = "([0-1]?\\d|2[0-3]):([0-5]?\\d):([0-5]?\\d)";
        Pattern timeregex = Pattern.compile(timePattern);
        Matcher timeMatcher = timeregex.matcher(mp.get("StartTime"));
        if(!timeMatcher.matches())
        	return ;
        else
        	System.out.print("regexmatched");
        timeMatcher = timeregex.matcher(mp.get("EndTime"));
        if(!timeMatcher.matches())
        	return ;
        
        
        		
        String NumPattern = "^-?\\d+$";
        Pattern numregex = Pattern.compile(NumPattern);
        Matcher NumMatcher = numregex.matcher(mp.get("Cost"));
        if(!NumMatcher.matches())
        	return ;
        else
        	System.out.print("regexmatched");
        
        String binPattern = "[0-1]";
        Pattern binregex = Pattern.compile(binPattern);
        Matcher binMatcher = binregex.matcher(mp.get("IsSpecial"));
        
        if(!binMatcher.matches())
        	return ;
        else
        	System.out.print("regexmatched");
    }
  
        
       /* if (request.getMethod().equals("OPTIONS")) {
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }*/
		// pass the request along the filter chain
    /*ServletContext context=request.getServletContext();  
    
    context.setAttribute("mp",mp);  */
    
    HttpSession session=request.getSession();  
    session.setAttribute("mp",mp); 
    
    long before=System.currentTimeMillis();  
		chain.doFilter(request, response);
		count++;
		 long after=System.currentTimeMillis();  
		 
		 System.out.print("Request takes "+(after-before) +"miliseconds ");
		 System.out.println("Total "+count+" requests have been processed");
	}

	

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
