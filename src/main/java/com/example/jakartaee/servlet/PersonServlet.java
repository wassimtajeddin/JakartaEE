package com.example.jakartaee.servlet;

import com.example.jakartaee.entity.Person;
import com.example.jakartaee.repository.PersonRepository;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="personServlet", value = "/people/*")
public class PersonServlet extends HttpServlet {

    @Inject
    PersonRepository repository;

    @Inject
    Jsonb jsonb;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Get path
        String path = req.getPathInfo();
        if( path == null || path.equals("/")) {
            resp.setContentType("text/html");

            PrintWriter out = resp.getWriter();
            out.println("<html><body>");

            out.println("<h1>" + "Persons" + "</h1>");
            out.println("<h1>" + path + "</h1>");
            for (Person person : repository.findAll())
                out.println("<div>" + person.getId() + ":" + person.getName() + "</div>");
            out.println("</body></html>");
        }
        else {
            int id = Integer.parseInt(path.substring(1));
            var person = repository.findOne(id);
            resp.setContentType("application/json");
            if( person.isPresent()) {
                PrintWriter out = resp.getWriter();
                out.print(jsonb.toJson(person));
            }
            else {
                resp.setContentType("text/html");
                resp.sendError(404);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }

        //  Jsonb jsonb = JsonbBuilder.create();

        Person person = jsonb.fromJson(jb.toString(), Person.class);

        repository.insertPerson(person);
    }
}
