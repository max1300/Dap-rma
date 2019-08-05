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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
     * @return access to constant LOG.
     */
    private static final Logger LOG = LogManager.getLogger();

    /**
     * @return constant first char of userKey.
     */
    private static final int SENSIBLE_DATA_FIRST_CHAR = 0;

    /**
     * @return constant last char of userKey.
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

    @RequestMapping("/account/add/{userKey}")
    public String addAccount(@PathVariable final String userKey,
            final HttpServletRequest request, final HttpSession session)
                    throws ServletException, GeneralSecurityException {

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
                final AuthorizationCodeRequestUrl authorizationUrl =
                        flow.newAuthorizationUrl();
                authorizationUrl.setRedirectUri(buildRedirectUri(request,
                        "/oAuth2Callback"));
                // store userKey in session for CallBack Access
                session.setAttribute("userKey", userKey);
                //TODO bam by Djer |API Google| Sauvegarde le "loginName"
                //ici en session pour l'utiliser dans le oAuth2Callback
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
     * @return the view to display
     * @throws ServletException When Google account
     * could not be connected to DaP.
     * @throws GeneralSecurityException exception
     */
    @RequestMapping("/oAuth2Callback")
    public String oAuthCallback(@RequestParam final String code,
            final HttpServletRequest request, final HttpSession session)
                    throws ServletException, GeneralSecurityException {
        final String decodedCode = extracCode(request);

        final String redirectUri = buildRedirectUri(request, "/oAuth2Callback");

        final String userKey = getuserKey(session);
        try {
            final GoogleAuthorizationCodeFlow flow = Utils.getFlow();
            final TokenResponse response = flow.newTokenRequest(decodedCode)
                    .setRedirectUri(redirectUri).execute();

            final Credential credential =
                    flow.createAndStoreCredential(response, userKey);
            if (null == credential || null == credential.getAccessToken()) {
                LOG.warn("Trying to store a NULL AccessToken for user : "
                        + userKey);
            }

            if (LOG.isDebugEnabled()) {
                if (null != credential && null != credential.getAccessToken()) {
                    LOG.debug("New user credential stored with userKey : "
                            + userKey + "partial AccessToken : "
                            + credential.getAccessToken()
                            .substring(SENSIBLE_DATA_FIRST_CHAR,
                                    SENSIBLE_DATA_LAST_CHAR));
                }
            }
            // onSuccess(request, resp, credential);
        } catch (IOException e) {
            LOG.error("Exception while trying to store user Credential", e);
            throw new ServletException("Error while "
                    + "trying to conenct Google Account");
        }

        return "redirect:/";
    }

    /**
     * retrieve the User ID in Session.
     * @param session the HTTP Session
     * @return the current User Id in Session
     * @throws ServletException if no User Id in session
     */
    private String getuserKey(final HttpSession session)
            throws ServletException {
        String userKey = null;
        if (null != session && null != session.getAttribute("userKey")) {
            userKey = (String) session.getAttribute("userKey");
        }

        if (null == userKey) {
            LOG.error("userKey in Session is NULL in Callback");
            throw new ServletException("Error when trying"
                    + " to add Google acocunt : userKey is NULL "
                    + "is User Session");
        }
        return userKey;
    }

    /**
     * Extract OAuth2 Google code (from URL) and decode it.
     * @param request the HTTP request to extract OAuth2 code
     * @return the decoded code
     * @throws ServletException if the code cannot be decoded
     */
    private String extracCode(final HttpServletRequest request)
            throws ServletException {
        final StringBuffer buf = request.getRequestURL();
        if (null != request.getQueryString()) {
            buf.append('?').append(request.getQueryString());
        }
        final AuthorizationCodeResponseUrl responseUrl =
                new AuthorizationCodeResponseUrl(buf.toString());
        final String decodeCode = responseUrl.getCode();

        if (decodeCode == null) {
            throw new MissingServletRequestParameterException("code", "String");
        }

        if (null != responseUrl.getError()) {
            LOG.error("Error when trying to add "
                    + "Google acocunt : " + responseUrl.getError());
            throw new ServletException("Error when trying "
                    + "to add Google acocunt");
            // onError(request, resp, responseUrl);
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
    protected String buildRedirectUri(final HttpServletRequest req,
            final String destination) {
        final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath(destination);
        return url.build();
    }

}
