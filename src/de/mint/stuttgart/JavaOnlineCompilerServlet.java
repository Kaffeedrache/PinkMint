package de.mint.stuttgart;

import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;


public class JavaOnlineCompilerServlet extends HttpServlet {


   	@Override
   	public void init() throws ServletException {
   		// Servlet initialization code here
   		super.init();
   	}


      private void printHTMLHeader (PrintWriter out) throws IOException {
         out.println("<html>");
         out.println("<head>");
         out.println("</head>");
         out.println("<body>");
         out.println("<h1>Java Online Compiler</h1>");
      }

      private void printHTMLFooter (PrintWriter out) throws IOException {
         out.println("</body>");
         out.println("</html>");
      }



   	@Override
   	protected void doGet(HttpServletRequest request,
   			HttpServletResponse response) throws ServletException, IOException {

   		// Set response content type
   		response.setContentType("text/html");

   		// Actual logic goes here.
   		PrintWriter out = response.getWriter();

         // Print header
         this.printHTMLHeader(out);


         // Session management
            // Return the existing session if there is one. Create a new session otherwise.
            HttpSession session = request.getSession();
         out.println("<p>(Session ID is " + session.getId() + ")</p>");
         out.println("<p>(Session creation time is " +
               new Date(session.getCreationTime()) + ")</p>");
         out.println("<p>(Session last access time is " +
               new Date(session.getLastAccessedTime()) + ")</p>");
         out.println("<p>(Session max inactive interval  is " +
               session.getMaxInactiveInterval() + " seconds)</p>");

         // Print form

         out.println("<form method=\"Post\" onsubmit=\"test\">");

         out.println("<div>");
         out.println("public class <input type=\"text\" name=\"cls\" size=\"25\" value=\"HalloWelt\"> {<br/>");
         out.println("public static void main (String[] args) { <br/>");
         out.println("<textarea name=\"code\" rows=\"10\" cols=\"80\" autofocus=\"true\">System.out.println(\"Hallo Welt\");</textarea>");
         out.println("}<br/>");
         out.println("}<br/>");
         out.println("</div>");


         out.println("<input type=\"submit\" value=\"Search\">");
         out.println("<div>");
         out.println("<button name=\"task\" value=\"0\">compilieren</button>");
         out.println("<button name=\"task\" value=\"1\">ausf&uuml;hren</button>");
         out.println("<label>Suchbegriff <input name=\"q\"></label>");
         out.println("</div>");
		   out.println("</form>");



         // Print footer
         //this.printHTMLFooter(out);



         String classname = request.getParameter("cls");
         //if (classname != null && classname.length() > -0) {
            out.println("<p>classname = " + classname + " </p>");
         //}

         String code = request.getParameter("code");
         //if (code != null && code.length() > -0) {
            out.println("<p>code = " + code + " </p>");
         //}

         String action = request.getParameter("task");
         //if (action != null && action.length() > -0) {
            out.println("<p>action = " + action + " </p>");
         //}


         MyCompiler c = new MyCompiler();
         c.doCompile(" ", " ");


   	}

   	@Override
   	protected void doPost(HttpServletRequest request,
   			HttpServletResponse response) throws ServletException, IOException {
               doGet(request, response);
   	}

   	@Override
   	public void destroy() {
   		// resource release
   		super.destroy();
   	}



}
