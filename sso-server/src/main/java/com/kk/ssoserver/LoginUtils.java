package com.kk.ssoserver;

import groovy.util.logging.Slf4j;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.StringTokenizer;

/**
 * @author Kuriakose V
 * @since 03/12/19.
 */
@Slf4j
public class LoginUtils {
    public static String SPRING_SECURITY_SAVED_REQUEST = "SPRING_SECURITY_SAVED_REQUEST";
    public static String REDIRECT_URI = "redirect_uri";
    public static String QUERY_PARAMETER_DELIMETER = "&";

    public static String getRedirectURI(HttpServletRequest request) {
        return request.getParameter(REDIRECT_URI);
    }

    public static DefaultSavedRequest getSavedClientRequest(HttpSession httpSession) {
        DefaultSavedRequest defaultSavedRequest = null;
         Object object = httpSession.getAttribute(SPRING_SECURITY_SAVED_REQUEST);
         if(object!=null)
             defaultSavedRequest = (DefaultSavedRequest)object;

         return defaultSavedRequest;
    }

    /**
     *
     *  If  defaultSavedRequest.getQueryString()  = client_id=portal&redirect_uri=http://localhost:8081/app1/login&response_type=code&state=X0B8zr
     *  Model will be constructed as key - value pair, for eg: model.addAttribute("client_id", "portal")
     *
     *
     * @param model
     * @param defaultSavedRequest
     */

    public static void addAttributesToModel(Model model, DefaultSavedRequest defaultSavedRequest) {
        StringTokenizer queryTokens = new StringTokenizer(defaultSavedRequest.getQueryString(), QUERY_PARAMETER_DELIMETER);
        if(queryTokens!=null && queryTokens.countTokens()!=0){
            while(queryTokens.hasMoreTokens()){
                String token = queryTokens.nextToken();
                    String[] keyValue = token.split("=");
                    model.addAttribute(keyValue[0], keyValue[1]);
                }
            }
      }


    public static String getRedirectURIFromSavedRequest(DefaultSavedRequest defaultSavedRequest) {
        String redirectUri = null;
       StringTokenizer queryTokens = new StringTokenizer(defaultSavedRequest.getQueryString(), QUERY_PARAMETER_DELIMETER);
       if(queryTokens!=null && queryTokens.countTokens()!=0){
           while(queryTokens.hasMoreTokens()){
               String token = queryTokens.nextToken();
               if(token.contains(REDIRECT_URI)){
                   redirectUri = token.split("=")[1];
                   return redirectUri;
               }
           }
       }

       return redirectUri;
    }
}
