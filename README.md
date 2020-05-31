I am glad to introduce my first project here on GitHub.


The aplication allows you to read the books and translate it during the reading (app also contains the mode for showing only English definition for a particular word). You should only change the API in Android application to Oxford Dictionary mode.

Features:
1) Oxford Translate Mode - show English word's definition.
2) Yandex Translate Mode - translate English to Russian.
3) Public library - I added 5 free public books for reading. User can download it with one click.
4) User library - user is able to upload his own book (parse and translate)
5) Translation history - user is able to show his history.
6) HealthCheck - server checks all services every 30 minutes. The android application checks server connection too and if it doesn't work shows technical work popup.

Here you can see an architecture of application. I've used:
1) Postgres DB;
2) Mongo DB - it's not really necessary, because I could use only Postres DB with indexing.
3) Keycloak - same case here for education purpose, it could be simple Spring Security.
4) Docker-compose for fast build in new server (my docker support local windows and linux versions)

Instruction for running server you'll find in server's readme.md
If you want to continue developing this application - you're welcome! I also added TODO list with important things to readme file.

![architecture](readme-resources/architecture.jpg)

After downloading the application you have to create new account in date base (authorization process is the same):

![register](readme-resources/register.png)

After the registration you'll see a guide of using this application:



![guide](readme-resources/guide.png)

Guide explanation: Single click at word during the reading shows you translation of particular word. Hold sentence'll select whole sentence for you.

After reading the guide you will be able to add your own or application's book into your phone:



![addPublicBook](readme-resources/addPublicBook.png)

![addOwnBook](readme-resources/addOwnBook.png)

After the adding books you'll see the main screen:

![mainScreen](readme-resources/mainScreen.png)

And now you are able to select your added book for reading!

![readingMode](readme-resources/readingMode.png)

In a reading mode as I wrote before - you will be able to translate text to Russian.

![translation](readme-resources/translation.png)

After reading your favorite books you will be able to see your searching history:

![historyTranslation](readme-resources/historyTranslation.png)

If server will not be available, user'll see "technical work popup"

![tech-work](readme-resources/tech-work.png)

