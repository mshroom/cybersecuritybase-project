# cybersecuritybase-project
The task of this course project was to create a web application that has at least five different flaws from the [OWASP top ten list](https://www.owasp.org/images/7/72/OWASP_Top_10-2017_%28en%29.pdf.pdf).

This is a simple Java application where users can save their passwords for different services, so they don't have to remember them. Sounds great? You can try the application by downloading this GitHub project and opening and running it in the Java IDE of your choice. 

## Security flaws

### Flaw 1 (A2 Broken Authentication)

Description: Open [http://localhost:8080/](http://localhost:8080/). You see a login window asking for username and password. Type username "admin" and password "admin". You are now redirected to administrator's home page. You can also type username "donald" and password "duck" which will redirect you to Donald's home page.

How to fix: Don't use default passwords that are weak, common and easy to guess. Don't even allow passwords that are short and weak ("admin", "duck").  Instead, add some validations for the passwords, for example minimum length and compulsory special characters. 

### Flaw 2 (A3 Sensitive Data Exposure)

Description: If you inspect the source code in GitHub, you will see that the accounts mentioned above are created in [CustomUserDetailService class](https://github.com/mshroom/cybersecuritybase-project/blob/master/src/main/java/sec/project/config/CustomUserDetailsService.java). You can see from the code that there is also a user called “mickey” with a password “mouse”. You can now login to any of these accounts.

How to fix: Don't create accounts in the source code so that everyone who has access to the source code can see account details from there. Remove these accounts or at least change the passwords. Add a possibility to safely create accounts in the application. In some situations you could also insert account details directly to the database.

### Flaw 3 (A6 Security Misconfiguration)

Description: Open [http://localhost:8080/](http://localhost:8080/). Login as username “admin” and password “admin”. Press the link “List users”. You can now see all usernames and passwords listed as plain text. 

How to fix: Use password encoding in [SecurityConfiguration class](https://github.com/mshroom/cybersecuritybase-project/blob/master/src/main/java/sec/project/config/SecurityConfiguration.java), so that passwords won’t be saved as plain text. Also, don’t list passwords in this page, not even for administrators. They have no real need for those.

### Flaw 4 (A3 Sensitive Data Exposure)

Description: Open [http://localhost:8080/users](http://localhost:8080/users) without logging in. You can now see all usernames and passwords listed as plain text, although this page is meant to be seen only by the administrator.

How to fix: Restrict access to this page only to administrators by using proper authentication methods in the application. Consider removing the whole page (see previous flaw above).

### Flaw 5 (A5 Broken Access Control)

Description: Open [http://localhost:8080/](http://localhost:8080/). Login as username “donald”,  password “duck”. You are redirected to page [http://localhost:8080/home/2](http://localhost:8080/home/2). Modify the last character of the url to 3 instead of 2. You can now access Mickey’s home page although you didn’t give Mickey’s password.

How to fix: Restrict access to user’s home page only to the user in question by using proper authentication methods in the application. Also, don’t use user id’s in the urls, especially when id’s are just simple numbers. Long, randomized strings would work better but even then it’s not wise to show user id’s in the urls, and in any case you should still use proper authentication.

### Flaw 6 (A5 Broken Access Control)

Description: Open Open [http://localhost:8080/](http://localhost:8080/). Login as username “donald”,  password “duck”. Press link “Password manager.” Insert username “donald” and password “duck” again.  You are redirected to page [http://localhost:8080/passwords/donald](http://localhost:8080/passwords/donald) where you can see your saved passwords and add new passwords. Modify the last characters of the url to "mickey" instead of "donald". You can now access Mickey’s password manager although you didn’t give Mickey’s password.

How to fix: Restrict access to user’s password manager page only to the user in question by using proper authentication methods in the application. Also, don’t use usernames in the urls, because usernames are usually easy to guess and then anyone would see for example from the browsing history, who has been using the application. Even if would you use some hard-to-guess user spesific id’s in the urls, you should still use proper authentication.

### Flaw 7 (A1 Injection)

Description: Open [http://localhost:8080/](http://localhost:8080/). Login as username “donald”, password “duck”. Choose “Password manager”. Insert username “donald” and password “duck” again. You are redirected to page [http://localhost:8080/passwords/donald](http://localhost:8080/passwords/donald) where you can see your saved passwords and add new passwords. Modify the url by adding the string

‘ or ‘1’=’1

in the end of the url. You can now see every user’s passwords listed.

How to fix: In the [PasswordDao class](https://github.com/mshroom/cybersecuritybase-project/blob/master/src/main/java/sec/project/repository/PasswordDao.java), you can see that the method findAllByUsername doesn’t validate user input at all and allows SQL injection. It takes the username from the url and puts it inside the SQL query.  There is a commented method above that is almost similar, but it uses the setString method of PreparedStatement to put the username safely into the SQL query. You should consider using this method instead. Also, you could delete PasswordDao class and use PasswordRepository instead. Another security issue here is that there is no proper authentication (see previous flaw).

### Flaw 8 (A3 Sensitive Data Exposure)

Description: Passwords are saved in file [database.db](https://github.com/mshroom/cybersecuritybase-project/blob/master/database.db) which has “accidentally” been pushed to the GitHub repository at some point. Anyone can download the database from there and easily read its contents. 

How to fix: You should remove the database from GitHub and add it to .gitignore so that it won’t be pushed again. Then you should change the passwords of all accounts that were exposed.

There are probably many more flaws as well because I tried to do the application as badly as possible.
