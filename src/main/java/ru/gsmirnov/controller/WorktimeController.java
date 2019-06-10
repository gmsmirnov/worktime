package ru.gsmirnov.controller;

import ru.gsmirnov.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WorktimeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("worktime.html").forward(req, resp);
        //req.getRequestDispatcher(Constants.PAGE_JSP_WORKTIME).forward(req, resp);
    }
}