package fr.houseofcode.dap.server.rma.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;

/**
 * @author rma.
 * 5 juil. 2019
 *
 */
@Controller
public class GoogleAccount {

    /**
     * LOG4J.
     */
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Attribute of type String that allow to use the literal userkey in the class.
     */
    private static final String USER_KEY = "userKey";

    /**
     * Constant first char of userKey.
     */
    private static final int SENSIBLE_DATA_FIRST_CHAR = 0;

    /**
     * Constant last char of userKey.
     */
    private static final int SENSIBLE_DATA_LAST_CHAR = 5;

    /**
     * Add a Google account (user will be prompt to connect and accept required
     * access).
     * @param userKey  the user to store Data
     * @param request the HTTP request
     * @param session the HTTP session
     * @return the view to Display (on Error)
     * @throws ServletException exception
     * @throws GeneralSecurityException exception
     */
    @GetMapping("/account/add/{userKey}")
    public String addAccount(@PathVariable final String userKey, final HttpServletRequest request,
                             final HttpSession session) throws  GeneralSecurityException {
        LOG.debug("Ajout d'un compte google avec déclenchement possible d'exception :"
                + "GeneralSecurityException");
        String response = "errorOccurs";
        GoogleAuthorizationCodeFlow flow;
        Credential credential = null;

        try {
            flow = Utils.getFlow();
            credential = flow.loadCredential(userKey);
            if (credential != null && credential.getAccessToken() != null) {
                response = "AccountAlreadyAdded";
            } else {
                // redirect to the authorization flow
                 AuthorizationCodeRequestUrl authorizationUrl =
                        flow.newAuthorizationUrl();
                authorizationUrl.setRedirectUri(buildRedirectUri(request,
                        "/oAuth2Callback"));

                session.setAttribute(USER_KEY, userKey);
                response = "redirect:" + authorizationUrl.build();

            }
        } catch (IOException e) {
            LOG.error("Error while loading credential (or Google Flow)", e);
        }

        return response;
    }

    /**
     * Handle the Google response.
     * @param request The HTTP Request
     * @param code The (encoded) code use by Google (token, expirationDate,...)
     * @param session the HTTP Session
     * @throws ServletException When Google account
     * @throws GeneralSecurityException exception
     * @return the view to display
     */
    @GetMapping("/oAuth2Callback")
    public String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
                                final HttpSession session) throws ServletException, GeneralSecurityException {
        LOG.debug("Auth2 callback handler avec déclenchement possible d'exceptions (IOException "
                + "ou GeneralSecurityException");
         String decodedCode = extracCode(request);
         String redirectUri = buildRedirectUri(request, "/oAuth2Callback");
         String userKey = getuserKey(session);

        try {
             GoogleAuthorizationCodeFlow flow = Utils.getFlow();
             TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();
             Credential credential = flow.createAndStoreCredential(response, userKey);

            if (null == credential || null == credential.getAccessToken()) {
                LOG.warn("Trying to store a NULL AccessToken for user : {}.", userKey);
            }

            if (LOG.isDebugEnabled() && null != credential && null != credential.getAccessToken()) {
                LOG.debug("New user credential stored with userKey : {}. partial AccessToken : {}.",
                        userKey, credential.getAccessToken()
                                .substring(SENSIBLE_DATA_FIRST_CHAR, SENSIBLE_DATA_LAST_CHAR));
            }

        } catch (IOException e) {
            LOG.error("Exception while trying to store user Credential", e);
            throw new ServletException("Error while trying to conenct Google Account");
        }
        return "redirect:/";
    }

    /**
     * Retrieve the User ID in Session.
     * @param session the HTTP Session
     * @return the current User Id in Session
     * @throws ServletException if no User Id in session
     */
    private String getuserKey(final HttpSession session) throws ServletException {
        LOG.debug("Accès à la userkey avec déclenchement possible d'exceptions (ServletException)");
        String userKey = null;

        if (null != session && null != session.getAttribute(USER_KEY)) {
            userKey = (String) session.getAttribute(USER_KEY);
        }

        if (null == userKey) {
            LOG.error("userKey in Session is NULL in Callback");
            throw new ServletException("Error when trying to add Google acocunt : userKey is NULL is User Session");
        }

        return userKey;
    }

    /**
     * Extract OAuth2 Google code (from URL) and decode it.
     * @param request the HTTP request to extract OAuth2 code
     * @return the decoded code
     * @throws ServletException if the code cannot be decoded
     */
    private String extracCode(final HttpServletRequest request) throws ServletException {
         LOG.debug("Extractio code google Auth2"
                 + " avec déclenchement possible "
                 + "d'exceptions (ServletException)");
         StringBuffer buf = request.getRequestURL();

         if (null != request.getQueryString()) {
            buf.append('?').append(request.getQueryString());
        }

         AuthorizationCodeResponseUrl responseUrl = new AuthorizationCodeResponseUrl(buf.toString());
         String decodeCode = responseUrl.getCode();

        if (decodeCode == null) {
            throw new MissingServletRequestParameterException("code", "String");
        }

        if (null != responseUrl.getError()) {
            LOG.error("Error when trying to add Google account : {}.", responseUrl.getError());
            throw new ServletException("Error when trying to add Google account");
        }

        return decodeCode;
    }

    /**
     * Build a current host (and port) absolute URL.
     * @param req         The current HTTP request to extract schema, host, port
     *                    informations
     * @param destination the "path" to the resource
     * @return an absolute URI
     */
    protected String buildRedirectUri(final HttpServletRequest req, final String destination) {
        LOG.info("building redirect URI for authorization");
        GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath(destination);
        return url.build();
    }

}
