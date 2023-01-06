package MyPack;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class ControllerServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        processRequest(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        processRequest(request, response);
    }
    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response) throws ServletException, IOException {
        String userAction = request.getParameter("action");
		if(userAction.equals("ViewAllFilms"))
			ViewAllFilms(request, response);
		else if(userAction.equals("ViewFilmById"))
			response.sendRedirect("EnterId.jsp");
		else if(userAction.equals("ViewFilm"))
			ViewFilmByID(request,response);
		else if(userAction.equals("InsertFilm"))
			response.sendRedirect("Insert.jsp");
		else if(userAction.equals("Inserted"))
			Inserted(request,response);
		else if(userAction.equals("UpdateFilm"))
			response.sendRedirect("UpdateFilm.jsp");
		else if(userAction.equals("Updated"))
			Updated(request,response);
		else if(userAction.equals("DeleteFilm"))
			response.sendRedirect("DeleteFilm.jsp");
		else if(userAction.equals("DeletedFilm"))
			DeletedFilm(request,response);
		else if(userAction.equals("SearchFilm"))
			response.sendRedirect("SearchFilm.jsp");
		else if(userAction.equals("SearchedFilm"))
			SearchedFilm(request,response);
    }
	
    private void ViewAllFilms(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            FilmDAO searchall = new FilmDAO();
            ArrayList FilmList = searchall.getAllFilms();
            request.setAttribute("list", FilmList);
            RequestDispatcher rd = request.getRequestDispatcher("ShowFilms.jsp");
            rd.forward(request,response);
        }catch (Exception e) {
            System.out.println(e);
        }
    }
	private void ViewFilmByID(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
			String ID = request.getParameter("filmid");
			int id = Integer.parseInt(ID);
        try{
            FilmDAO search = new FilmDAO();
            Film film = search.getFilmByID(id);
            request.setAttribute("film", film);
            RequestDispatcher rd = request.getRequestDispatcher("ShowOneFilm.jsp");
            rd.forward(request,response);
        }catch (Exception e) {
            System.out.println(e);
        }
    }
	private void Inserted(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
			PrintWriter out = response.getWriter();
			String ID = request.getParameter("filmid");
			int id = Integer.parseInt(ID);
			String title = request.getParameter("filmtitle");
			String year = request.getParameter("filmyear");
			int Year = Integer.parseInt(year);
			String director = request.getParameter("filmdir");
			String stars = request.getParameter("filmstars");
			String review = request.getParameter("filmrev");
        try{
            FilmDAO Insert = new FilmDAO();
			Film insert = new Film();
			insert.setId(id);
			insert.setTitle(title);
			insert.setYear(Year);
			insert.setDirector(director);
			insert.setStars(stars);
			insert.setReview(review);
            Insert.insertFilm(insert);
            out.println("Inserted");
        }catch (Exception e) {
            System.out.println(e);
        }
    }
	
	private void Updated(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
			PrintWriter out = response.getWriter();
			String OldID = request.getParameter("id");
			int oldid = Integer.parseInt(OldID);
			String ID = request.getParameter("filmid");
			int newid = Integer.parseInt(ID);
			String title = request.getParameter("filmtitle");
			String year = request.getParameter("filmyear");
			int Year = Integer.parseInt(year);
			String director = request.getParameter("filmdir");
			String stars = request.getParameter("filmstars");
			String review = request.getParameter("filmrev");
        try{
            FilmDAO Update = new FilmDAO();
			Film film = new Film();
			film.setId(newid);
			film.setTitle(title);
			film.setYear(Year);
			film.setDirector(director);
			film.setStars(stars);
			film.setReview(review);
            Update.updateFilm(film,oldid);
            out.println("Updated");
        }catch (Exception e) {
            System.out.println(e);
        }
    }
	private void DeletedFilm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
			PrintWriter out = response.getWriter();	
			String ID = request.getParameter("filmid");
			int id = Integer.parseInt(ID);
        try{
            FilmDAO delete = new FilmDAO();
            delete.deleteFilm(id);
             out.println("Deleted");
        }catch (Exception e) {
            System.out.println(e);
        }
    }
	
	private void SearchedFilm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
			PrintWriter out = response.getWriter();	
			String str = request.getParameter("string");
			
        try{
            FilmDAO search = new FilmDAO();
            Collection FilmList = search.searchFilms(str);
            request.setAttribute("list", FilmList);
            RequestDispatcher rd = request.getRequestDispatcher("ShowFilms.jsp");
            rd.forward(request,response);
        }catch (Exception e) {
            System.out.println(e);
        }
    }
}