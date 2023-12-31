# spring-boot-2.7-oauth2-adfs-resource-server
## Introduction
This code is about how to set up an ADFS (Active Directory Federation Services) server to work with a Spring Boot Resource Server using the Implicit Grant Flow. The Implicit Grant Flow is a OAuth 2.0 flow that is suitable for browser-based or mobile applications where the client-side JavaScript code can directly obtain an ID token from the identity provider, in this case, ADFS. We will focus on the first step of this process - setting up ADFS for this specific flow.

## Setting Up ADFS:
Let's assume that Active Directory and users are already set on your Active Directory server. Now we need to setting Application Group on AD FS by the following step:
1. Log in to your ADFS server and open the ADFS Management Console. On left menu under "Application Groups", right click and click "Add Application Group..."
2. When "Add Application Group Wizad" appear, select "Server application accessing a web API" and given some Name then click "Next".

![Screenshort1](images/Screenshot%20from%202023-09-26%2013-01-47.png)

3. On "Server application" step add Redirect URI for example "http://localhost:8080/info" and keep "Client Identifier" we will use it in later step. Click "Next".

![Screenshort1](images/Screenshot%20from%202023-09-26%2013-03-00.png)

4. On "Configure Application Credentials" step click check box "Generate a shared secret" and copy to txt somewhere, we will use it in later step. Then click "Next".

![Screenshort1](images/Screenshot%20from%202023-09-26%2013-03-09.png)

5. On "Configure Web API" step, put "http://localhost:8091/resource" to "Identifier" and click "Add".

![Screenshort1](images/Screenshot%20from%202023-09-26%2013-03-35.png)

6. On "Apply Access Control Policy" just select "Permit everyone".

![Screenshort1](images/Screenshot%20from%202023-09-26%2013-03-44.png)

7. On "Configure Application Permissions" make sure that under "Permitted scope" the "openid" check box is selected. Then click "Next".

![Screenshort1](images/Screenshot%20from%202023-09-26%2013-03-52.png)

8. On "Summary" step, just click "Next", then click "Close".

![Screenshort1](images/Screenshot%20from%202023-09-26%2013-04-04.png)

![Screenshort1](images/Screenshot%20from%202023-09-26%2013-04-11.png)

## Request for token using ADFS Implicit Grant Flow:
To generate token from AD FS with ADFS Implicit Grant Flow, put url below to web browser. The browser will redirect to AD FS login page. After login, it will redirect to http://localhost:8080/info with token parameters.
```txt
https://adfs.yourdomain.com/adfs/oauth2/authorize?client_id=<clientid>&response_type=id_token&redirect_uri=http://localhost:8080/info&scope=openid&response_mode=fragment&state=12345&nonce=<generated random nonce>&resource=http://localhost:8091/resource
```
Below are an example for response in json format:
```json
{
    "id_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1Ni...",
    "state": "12345"
}
```
## Request for token using Authorization code grant flow:
### Step 1: Request for code
Open web browser and put link below, it will redirect to AD FS login page.
```txt
https://adfs.yourdomain.com/adfs/oauth2/authorize?
client_id=3a6e27b9-ac5b-4335-bcae-594bc8d31db0
&response_type=code
&redirect_uri=http://localhost:8080/info
&response_mode=query
&resource=http://localhost:8091/resource
&scope=openid
&state=12345
```
After login success, it will redirect to http://localhost:8080/info with parameters below
```txt
http://localhost:8080/info?
code=f-ArOm1rdUCyBVKQMJv-pQ.B7Atfx-72wgHAH...
&state=12345
```
### Step 2: Request for token.
Use the code from previous step and to make post request below:

```bash
curl --location --request POST 'https://adfs.yourdomain.com/adfs/oauth2/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=3a6e27b9-ac5b-4335-bcae-594bc8d31db0' \
--data-urlencode 'code=f-ArOm1rdUCyBVKQMJv-pQ.B7Atfx-72wgHAH...' \
--data-urlencode 'redirect_uri=http://localhost:8080/info' \
--data-urlencode 'grant_type=authorization_code' \
--data-urlencode 'client_secret=xB36vPEJajey-KEOOg3msd...'
```
Response look like below:
```json
{
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSU...",
    "token_type": "bearer",
    "expires_in": 3600,
    "resource": "urn:microsoft:userinfo",
    "refresh_token": "GfiQ6R6Oz0d53Tyhu5vD9BeieO9U-d...",
    "refresh_token_expires_in": 599939,
    "scope": "openid",
    "id_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUz..."
}
```
## Request for token using Resource owner password credentials grant flow (Not recommended):
This flow will not pop up login page. You just send login credential within post command.
```bash
curl --location --request POST 'https://adfs.yourdomain.com/adfs/oauth2/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'scope=openid' \
--data-urlencode 'username=user@adfs.yourdomain.com' \
--data-urlencode 'password=password' \
--data-urlencode 'client_id=5c9db177-cc55-41d6-98db-...' \
--data-urlencode 'client_secret=Y4yk0log7xUD6RA70qns...' \
--data-urlencode 'resource=http://localhost:8080/resource' \
--data-urlencode 'nonce=aserqw3erq243w3423'
```
Response look like below:
```json
{
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSU...",
    "token_type": "bearer",
    "expires_in": 3600,
    "resource": "urn:microsoft:userinfo",
    "refresh_token": "GfiQ6R6Oz0d53Tyhu5vD9BeieO9U-d...",
    "refresh_token_expires_in": 599939,
    "scope": "openid",
    "id_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUz..."
}
```
