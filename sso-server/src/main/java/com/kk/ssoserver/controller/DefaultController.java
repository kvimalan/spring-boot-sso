package com.kk.ssoserver.controller;

import com.kk.ssoserver.LoginUtils;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class DefaultController {

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request, HttpSession httpSession) throws Exception {
        DefaultSavedRequest defaultSavedRequest = LoginUtils.getSavedClientRequest(httpSession);

        //Request not invoked from client application, hence redirect to client url
        if(defaultSavedRequest == null){
            String redirectUri = LoginUtils.getRedirectURI(request);
            if(redirectUri!=null)
              return "redirect:"+redirectUri;
            else
                throw new Exception("Invalid Request(URL)!. Please access from valid url of an application.");
        }

        LoginUtils.addAttributesToModel(model, defaultSavedRequest);
        return "login";
    }
}
