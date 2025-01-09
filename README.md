**Card Balance Checker**

Users have requested a website to allow them to check the balance on their account. In this project, you will develop a website that will allow a user to enter their 16-digit Card number (PAN) and return the balance. You will create
the frontend website and the backend api. The data has been provided with existing users, PANs and balances.

**Assumptions:**
* The current user data was provided in a file more or less represented in JSON.  For this development iteration, I decided to include this as a JSON file in the project.  However, to increase performace in both adding data and searching, storage in a database should be looked at as a future improvement.
* This is a very simple API and web page application.  There should be steps to improve the security of this application such as a login page that validates the user and uses some sort of validation with each successive page.
* In a production environment, all PANs should be encrypted before being sent from the client to the API server.
* I did a little bit of validation of the input on the client before sending it to the API server.  Sometimes bad actors could use this to determine characteristics, if not already known about the PAN.  In this instance I decided to do the verification on both the client and server.

**Setup**

I create and ran this application out of my IntelliJ IDE.  With that in mind, I used the following to get to the home page.
http://localhost:63342/Bankapp/static/home.html

In addition, you will see that the API server is running on port 8080 so the URL for the fetch in my home.html file is:
http://localhost:8080/accountBalance/${cardNumber}

You may need to modify these depending on what web/back end server that you use.

**Steps**
1. Download the zip file from https://github.com/ashworth618/BankApp/tree/main under the code button.
2. Unzip the zip file whereever you'd like such as /Documents, then rename the directory from BankApp-main to BankApp.
3. Start IntelliJ, Open the BankApp directory, and select pom.xml then hit open button. Open as a project when asked.
4. On the run menu select Run... then select edit configuration. Hit the + sign and select application.  CLick on box next to Main class and select click OK.  Enter BankApp in Name and press Run.
5. Back at the project frame, open src/main/resources/static then right click on home.html.  Select open in-brwoser-pick your favorite browser.
6. API is running and page is up for use.  Enter a PAN of 1234567890123456 to test.

**Design**

The basic was to use a Java Spring App for the backend API and HTML/Javascript for the front end.  The API steps are as follows:
1. Validate our PAN.  As stated above, there can be a lot more validation done here.  Perhaps the PANs that this company use start with 9999 or only have 14 characters, all of these could be added to the validation.
2. Read in the account data from the file.  I used a POJO that matched the input file provided to hold the format of the provided data.  If the file is not found we return an internal server error so that end user sees that there is an outage and write the error to an error log so that an automated system can detect the issue in the log and pull in someone to resolve the missing data file issue.
3. Loop through the data looking for the PAN, if found, set the HTTPStatus to OK and return the card balance.  If not found, set an error, change the HTTPStatus and return.

**Improvements**
1. Better security - for starters, Login and tokens to verify Authentication and Authorization.  Encryption of the PANs both on client and server side.
2. Use a database instead of files to improved performance of queries.
3. Store the PAN encrypted in the Database.
4. Add unit tests for existing functionality as well as enhancements.

**Other comments**

I added possible improvements as comment to both the front and back end code.  
