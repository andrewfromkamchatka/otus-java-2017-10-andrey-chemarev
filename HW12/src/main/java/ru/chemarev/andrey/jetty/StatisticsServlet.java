package ru.chemarev.andrey.jetty;

import ru.chemarev.andrey.cache.CacheEngineImpl;
import ru.chemarev.andrey.core.UserDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StatisticsServlet extends HttpServlet {

    private static final String STATISTICS_PAGE_TEMPLATE = "statistics.html";
    private static final int REFRESH_PERIOD_MS = 5 * 1000;
    private CacheEngineImpl<Long, UserDataSet> cache;

    public StatisticsServlet(CacheEngineImpl<Long, UserDataSet> cache) {
        this.cache = cache;
    }

    private String getPage(String login, int refreshPeriod) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        pageVariables.put("login", login);
        pageVariables.put("refreshPeriod", refreshPeriod);

        pageVariables.put("maxElements", cache.getMaxElements());
        pageVariables.put("lifeTimeMs", cache.getLifeTimeMs());
        pageVariables.put("idleTimeMs", cache.getIdleTimeMs());
        pageVariables.put("isEternal", cache.isEternal());

        pageVariables.put("hitCount", cache.getHitCount());
        pageVariables.put("missCount", cache.getMissCount());


        return TemplateProcessor.instance().getPage(STATISTICS_PAGE_TEMPLATE, pageVariables);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");

        if (login == null) {
            response.sendRedirect("/login");
        } else {
            response.getWriter().println(getPage(login, REFRESH_PERIOD_MS));
        }
    }

}
