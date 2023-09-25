# spring-boot-2.7-oauth2-adfs-resource-server
## Introduction
This code is about how to set up an ADFS (Active Directory Federation Services) server to work with a Spring Boot Resource Server using the Implicit Grant Flow. The Implicit Grant Flow is a OAuth 2.0 flow that is suitable for browser-based or mobile applications where the client-side JavaScript code can directly obtain an ID token from the identity provider, in this case, ADFS. We will focus on the first step of this process - setting up ADFS for this specific flow.

## Setting Up ADFS:
Let's assume that Active Directory and users are already set on your Active Directory server. Now we need to setting Application Group on AD FS by the following step:
1. Log in to your ADFS server and open the ADFS Management Console. On left menu under "Application Groups", right click and click "Add Application Group..."
2. When "Add Application Group Wizad" appear, select "Server application accessing a web API" and given some Name then click "Next".
3. On "Server application" step add Redirect URI for example "http://localhost:8080/info" and keep "Client Identifier" we will use it in later step. Click "Next".
4. On "Configure Application Credentials" step click check box "Generate a shared secret" and copy to txt somewhere, we will use it in later step. Then click "Next".
5. On "Configure Web API" step, put "http://localhost:8080/resource" to "Identifier" and click "Add".
6. On "Apply Access Control Policy" just select "Permit everyone".
7. On "Configure Application Permissions" make sure that under "Permitted scope" the "openid" check box is selected. Then click "Next".
8. On "Summary" step, just click "Next".

## Request for token using ADFS Implicit Grant Flow:
x
x
x
## Request for token using Authorization code grant flow:
x
x
x
## Request for token using Resource owner password credentials grant flow (Not recommended):
x
x
x

