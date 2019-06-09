package ru.gsmirnov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.gsmirnov.dao.exception.NoSuchModelException;
import ru.gsmirnov.models.TimeInterval;
import ru.gsmirnov.validate.Validate;
import ru.gsmirnov.validate.impl.ValidateDB;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class TableController extends HttpServlet {
    /**
     * The logger.
     */
    private static final Logger LOG = LogManager.getLogger(StopController.class.getName());

    /**
     * The logic singleton instance.
     */
    private final Validate logic = ValidateDB.getInstanceOf();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            this.prepareResponse(this.logic.findAllIntervals(), resp);
        } catch (NoSuchModelException e) {
            TableController.LOG.error("No such model in database.", e);
        } catch (IOException e) {
            TableController.LOG.error("IO error occurs.", e);
        }
    }

    /**
     * Prepares HTTP-response, puts into it json-array of intervals.
     *
     * @param resp - HTTP response.
     * @throws IOException when PrintWriter error occurs.
     */
    private void prepareResponse(List<TimeInterval> intervals, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/json");
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(mapper.writeValueAsString(intervals));
        writer.flush();
    }
}