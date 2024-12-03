package ctrl;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Start
 */
@WebServlet({ "/Osap", "/Osap/*" })
public class Osap extends HttpServlet {

	private String appName = "";

	private double principle, interest, fixedInterest;
	private int period, gracePeriod;
	private String grace;
	private double gracePeriodInterest = 0;

	// Context constants
	private static final String CONTEXT_APP_NAME = "appName";
	private static final String CONTEXT_PRINCIPLE = "principle";
	private static final String CONTEXT_PERIOD = "period";
	private static final String CONTEXT_INTEREST = "interest";
	private static final String CONTEXT_FIXEDINTEREST = "fixedInterest";
	private static final String CONTEXT_GRACE = "grace";
	private static final String CONTEXT_GRACEPERIOD = "gracePeriod";
	
	// Constants for DOM attributes
	private static final String CHECKBOX_ON = "on";
	private static final String CHECKBOX_OFF = "off";

	// App Parameters / Attributes
	private static final String PARAM_PRINCIPLE = CONTEXT_PRINCIPLE;
	private static final String PARAM_PERIOD = CONTEXT_PERIOD;
	private static final String PARAM_INTEREST = CONTEXT_INTEREST;
	private static final String PARAM_PAYMENT = "payment";
	private static final String PARAM_OUT = "out";
	private static final String PARAM_RESULT = "result";
	private static final String PARAM_GRACE = CONTEXT_GRACE;
	private static final String PARAM_GRACEINTEREST = "graceInterest";
	

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Osap() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		ServletContext context = getServletContext();
		appName = context.getInitParameter(CONTEXT_APP_NAME);
		try {
			fixedInterest = Double.parseDouble(context
					.getInitParameter(CONTEXT_FIXEDINTEREST)) / 100.0f;
		} catch (Exception e) {

		}
	}

	

		/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//String target = "/UI.html";
		String target = "";
		request.setAttribute(CONTEXT_APP_NAME, appName);

		Writer resOut = response.getWriter();
		//get parameters, from query, session or context
        this.parseQueryString(request);
		String monthlyPaymentFormatted =  calculate(principle, interest, period);

		String clientURI = request.getRequestURI();
		
		if (clientURI.indexOf("/Osap/Lab1") > 0) {//if this is lab 1 return Lab 1 results
	    this.lab1(request, resOut);
		resOut.write(textView(principle, interest, period, monthlyPaymentFormatted));	
			
		}
		else {//otherwise do Lab 2 and so on
        
		if (request.getParameter(PARAM_OUT) != null
				&& request.getParameter(PARAM_OUT).equals("html")) {
			request.setAttribute(PARAM_PAYMENT, monthlyPaymentFormatted);
			System.out.println(monthlyPaymentFormatted);

			request.setAttribute(PARAM_RESULT, true);
			target = "/ResultsView.jsp";
			request.getRequestDispatcher(target).forward(request, response);

		}
		if (request.getParameter(PARAM_OUT) != null
				&& request.getParameter(PARAM_OUT).equals("text")) {
			resOut.write(textView(principle, interest, period, monthlyPaymentFormatted));	
		}
		
		if (request.getParameter(PARAM_OUT) == null) {
			resOut.write(textView(principle, interest, period, monthlyPaymentFormatted));	
		}
		}
		
		}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		    this.doGet(request, response);
	}
	
	private String textView( Double principle, Double interest, int period, String payment) {
		String textView= "---- Monthly payments ----" + "\n"+ "Based on Principal=" +
	    principle + " Period=" + period + " Interest=" + interest + "\n" +
		"Monthly payments: " + payment + "\n";
       
		return textView;
		
	}
	
	 /*  parse query parameters
	 */
	
	private void parseQueryString(HttpServletRequest request) {
		
		//just in case, get the attributes from the session or context..
		
		if (request.getSession().getAttribute(PARAM_PRINCIPLE) != null) {
			principle = (double) request.getSession().getAttribute(
					PARAM_PRINCIPLE);
		} else {
			principle = Double.parseDouble(this.getServletContext()
					.getInitParameter(CONTEXT_PRINCIPLE));
		}

		if (request.getSession().getAttribute(PARAM_PERIOD) != null) {
			period = (int) request.getSession().getAttribute(PARAM_PERIOD);
		} else {
			period = Integer.parseInt(this.getServletContext()
					.getInitParameter(CONTEXT_PERIOD));
		}

		if (request.getSession().getAttribute(PARAM_INTEREST) != null) {
			interest = (double) request.getSession().getAttribute(
					PARAM_INTEREST);
		} else {
			interest = Double.parseDouble(this.getServletContext()
					.getInitParameter(CONTEXT_INTEREST));
		}

		if (request.getSession().getAttribute(PARAM_GRACE) != null) {
			grace = (String) request.getSession().getAttribute(PARAM_GRACE);
		} else {
			grace = this.getServletContext().getInitParameter(CONTEXT_GRACE);
		}

		gracePeriod = Integer.parseInt(this.getServletContext()
				.getInitParameter(CONTEXT_GRACEPERIOD));
		gracePeriodInterest = 0;
		
		//try to get the parameters from the request and save in session;
		//if param not in the query string,  fall on session ones
		
		if (request.getParameter(PARAM_PRINCIPLE) != null) {
			try {
				principle = Double.parseDouble(request
						.getParameter(PARAM_PRINCIPLE));
				request.getSession().setAttribute(PARAM_PRINCIPLE,
						principle);
			} catch (Exception e) {

			}
		}

		if (request.getParameter(PARAM_PERIOD) != null) {
			try {
				period = Integer.parseInt(request
						.getParameter(PARAM_PERIOD));
				request.getSession().setAttribute(PARAM_PERIOD, period);
			} catch (Exception e) {

			}
		}

		if (request.getParameter(PARAM_INTEREST) != null) {
			// Convert input value as percentage value
			try {
				interest = (Double.parseDouble(request
						.getParameter(PARAM_INTEREST)));
				request.getSession().setAttribute(PARAM_INTEREST, interest);
			} catch (Exception e) {

			}
		}

		if (request.getParameter(PARAM_GRACE) != null) {
			// Convert input value as percentage value
			try {
				grace = request.getParameter(PARAM_GRACE);
				request.getSession().setAttribute(PARAM_GRACE, grace);
			} catch (Exception e) {

			}
		} else {
			grace = CHECKBOX_OFF;
			request.getSession().setAttribute(PARAM_GRACE, grace);
		}

		if (grace.equals(CHECKBOX_ON)) {
			gracePeriodInterest = (principle * (interest + fixedInterest) / 12)
					* (gracePeriod);
		}
		request.setAttribute(PARAM_GRACE, grace);
		request.setAttribute(PARAM_GRACEINTEREST, gracePeriodInterest);


	}

	private String calculate(Double p, Double i, int t) {
	
		Double monthlyPayment = ((principle)
				* (interest) / 12/100)
				/ (1 - (Math.pow((1 + ((interest) / 12/100)),
						-period)));
		String monthlyPaymentFormatted = String.format("%.1f", monthlyPayment);
		return monthlyPaymentFormatted;	
		
	}
	
	private void lab1(HttpServletRequest request, Writer resOut)throws ServletException, IOException {
		
		resOut.write("Hello, World!\n");

		// The Networking Layer
		String clientIP = request.getRemoteAddr();
		int clientPort = request.getRemotePort();

		resOut.write("Client IP: " + clientIP + "\n");
		resOut.write("Client Port: " + clientPort + "\n");

		// Simple IP filtering
		if (clientIP.equals("0:0:0:0:0:0:0:1")) {
			resOut.write("This IP has been flagged!" + "\n");
		}

		// The HTTP Layer
		String clientProtocol = request.getProtocol();
		String clientMethod = request.getMethod();

		resOut.write("Client Protocol: " + clientProtocol + "\n");
		resOut.write("Client Method: " + clientMethod + "\n");
		
		String clientQueryString = request.getQueryString();
		
		resOut.write("Query String: " + clientQueryString + "\n");
		
		String foo = request.getParameter("foo");
		
		resOut.write("Query Param foo=" + foo + "\n");
		
		// The URL
		
		String clientURI = request.getRequestURI();
		String clientServletPath = request.getPathInfo();


		
		resOut.write("Request URI : " + clientURI + "\n");
		resOut.write("Request Servlet Path : " + clientServletPath + "\n");

		
	}

		
		
	

}
