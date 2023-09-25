# spring-boot-2.7-oauth2-adfs-resource-server
## Introduction
This code is about how to set up an ADFS (Active Directory Federation Services) server to work with a Spring Boot Resource Server using the Implicit Grant Flow. The Implicit Grant Flow is a OAuth 2.0 flow that is suitable for browser-based or mobile applications where the client-side JavaScript code can directly obtain an ID token from the identity provider, in this case, ADFS. We will focus on the first step of this process - setting up ADFS for this specific flow.

## Setting Up ADFS for Implicit Grant Flow:
1. Open ADFS Management Console Add Application Group:
Log in to your ADFS server and open the ADFS Management Console.
On left menu under "Application Groups", right click and click "Add Application Group..."
2. Add Application Group:
When "Add Application Group Wizad" appear, select "Server application accessing a web API" and given some Name then click "Next".
3. On "Server application" step add Redirect URI for example "http://localhost:8080/info" and keep "Client Identifier" we will use it in later step.
