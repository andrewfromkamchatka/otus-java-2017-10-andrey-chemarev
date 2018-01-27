package ru.chemarev.andrey.jetty;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    private static final String login = "otus";
    private static final String password = "otus";
    private static final String LOGIN_PAGE_TEMPLATE = "login.html";
    private static final String WRONG_LOGIN_PASSWORD_MESSAGE = "Wrong login/password";

    private String getPage(String login, String message) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("login", login == null ? "" : login);
        pageVariables.put("message", message == null ? "" : message);
        return TemplateProcessor.instance().getPage(LOGIN_PAGE_TEMPLATE, pageVariables);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println(getPage(null, null));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestLogin = request.getParameter("login");
        String requestPassword = request.getParameter("password");

        String message = null;

        if (requestLogin != null && requestPassword != null) {
            if (requestLogin.equals(login) && requestPassword.equals(password)) {
                saveToCookie(response, login);
                saveToServlet(request, login);
                saveToSession(request, login);
                redirectToStatistics(response);
            } else {
                message = WRONG_LOGIN_PASSWORD_MESSAGE;
            }
        }

        response.getWriter().println(getPage(requestLogin, message));
    }

    private void redirectToStatistics(HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/statistics");
    }

    private void saveToCookie(HttpServletResponse response, String requestLogin) {
        response.addCookie(new Cookie("login", requestLogin));
    }

    private void saveToServlet(HttpServletRequest request, String login) {
        request.getServletContext().setAttribute("login", login);
    }

    private void saveToSession(HttpServletRequest request, String login) {
        request.getSession().setAttribute("login", login);
    }

}
