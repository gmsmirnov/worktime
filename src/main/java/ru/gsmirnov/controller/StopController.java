package ru.gsmirnov.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.gsmirnov.Constants;
import ru.gsmirnov.dao.exception.NullArgumentException;
import ru.gsmirnov.models.TimeInterval;
import ru.gsmirnov.validate.Validate;
import ru.gsmirnov.validate.impl.ValidateDB;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;

public class StopController extends HttpServlet {
    /**
     * The logger.
     */
    private static final Logger LOG = LogManager.getLogger(StopController.class.getName());

    /**
     * The logic singleton instance.
     */
    private final Validate logic = ValidateDB.getInstanceOf();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            if (session.getAttribute(Constants.SESSION_ATTR_IN) != null && (Boolean) session.getAttribute(Constants.SESSION_ATTR_IN)) {
                this.logic.updateTimeInterval((Integer) session.getAttribute(Constants.SESSION_ATTR_CUR_ID),
                        new TimeInterval(new Timestamp((Long) session.getAttribute(Constants.SESSION_ATTR_START)), new Timestamp(System.currentTimeMillis())));
                session.removeAttribute(Constants.SESSION_ATTR_CUR_ID);
                session.removeAttribute(Constants.SESSION_ATTR_START);
                session.setAttribute(Constants.SESSION_ATTR_IN, false);
            }
        } catch (NullArgumentException e) {
            StopController.LOG.error(e.getMessage(), e);
        }
        req.getRequestDispatcher(Constants.PAGE_JSP_WORKTIME).forward(req, resp);
    }
}