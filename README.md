# Spring Boot : Single Sign-on 

### What to configure?

In the app1 & app2 update the required configuration:

##### application.yml

  oauth2:
    client:
      clientId: portal
      clientSecret: secret
      accessTokenUri: http://localhost:8080/sso-server/oauth/token #sso server details
      userAuthorizationUri: http://localhost:8080/sso-server/oauth/authorize #sso server authorize end point
    resource:
      userInfoUri: http://localhost:8080/sso-server/user/me

###  How to run?

Step 1) Start/Run the app1 (App1Application.java)

Step 2) Start/Run the app2 (App2Application.java)

Step 3) Start/Run the sso-server (SsoServerApplication.java)

Step 4) Open the browser and point it to - http://localhost:8081/app1
         - Will be redirect to login page(sso-server)
         - Enter username & password as kk/kk
         - You will be logged in and redirected to app1
         
Step 5) Open the browser(new tab) and point it to - http://localhost:8082/app2
           - User would be automatically logged in with asking for credentials - That's SSO for you.
           
Step 6) To access database :http://localhost:8080/sso-server/console
         



