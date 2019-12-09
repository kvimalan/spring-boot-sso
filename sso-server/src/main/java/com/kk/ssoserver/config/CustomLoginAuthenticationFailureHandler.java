package com.kk.ssoserver.config;

import com.kk.ssoserver.LoginUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Kuriakose V
 * @since 09/12/19.
 */
public class CustomLoginAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        DefaultSavedRequest defaultSavedRequest = LoginUtils.getSavedClientRequest(request.getSession());
        String redirecUri = LoginUtils.getRedirectURIFromSavedRequest(defaultSavedRequest);
        setDefaultFailureUrl("/login?error=true&"+LoginUtils.REDIRECT_URI+"="+redirecUri);
        super.onAuthenticationFailure(request, response, exception);
    }
}
