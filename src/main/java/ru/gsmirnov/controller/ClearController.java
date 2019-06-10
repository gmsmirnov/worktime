package ru.gsmirnov.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.gsmirnov.Constants;
import ru.gsmirnov.validate.Validate;
import ru.gsmirnov.validate.impl.ValidateDB;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClearController extends HttpServlet {
    /**
     * The logger.
     */
    private static final Logger LOG = LogManager.getLogger(ClearController.class.getName());

    /**
     * The logic singleton instance.
     */
    private final Validate logic = ValidateDB.getInstanceOf();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.logic.emptyTable();
        //req.getRequestDispatcher(Constants.PAGE_JSP_WORKTIME).forward(req, resp);
        req.getRequestDispatcher("worktime.html").forward(req, resp);
    }
}