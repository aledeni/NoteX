This application developed during the software engineering exam at university aims to allow registered users on the app to share university notes in PDF format for free, while unregistered users will only be able to search for other users and download the notes, without being able to upload your own.
The application was developed using Eclipse Enterprise and Tomcat as web server. Furthermore, I used a MySQL relational database for persistent data management and Hibernate for data mapping between Java and the database.

How to run:
1) Open Eclipse Enterprise
2) Start Tomcat Server
3) Click "Add Buildfiles" in the "Ant" section and select the file ant/build.xml
4) Double-click on "09a.deploy.war" to deploy the application as a WAR file
5) Now open your browser and the application is ready at "http://localhost:8080/NoteX/"
