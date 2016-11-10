# Advanced Bugtracker
Bug tracker application for educational purposes.

## JS Course Study Program
Can be found [here](https://github.com/DioDread/advanced-bugtracker/blob/master/program.md).

## Demo Application Installation
To install application we need following software installed and configured at deployment machine: [MySQL Server](https://dev.mysql.com/downloads/mysql/), [Apache Tomcat Server](https://tomcat.apache.org/download-80.cgi), [Apache Maven](https://maven.apache.org/download.cgi).
In case of absence this software, proceed with links above - download and install it.

After environment getting prepared you need follow next steps to deploy and launch Advanced Bugtracker application:
1. Download application sources from github.
2. Import __schema__. In your downloaded project path you can see schema folder (_./advanced-bugtracker/schema_) here you can find SQL dump of application database schema with some testing data. I advice you to use MySQL Workbench application, steps for it:
  - Open Workbench app.
  - Create empty schema with name: __advanced-bugtracker__ (Use exactly this name! It's important!!!). To do this you need click on "Create new schema in the connected server" icon right under applications main menu. Select cp1251 - default collation for DB. Click apply, next, finish etc.
  - In main menu select: Server -> Data Import
  - In open window find Import Options group, select "Import from Dump Project Folder" and
  then select _./advanced-bugtracker/schema_ from you project with file selection tool.
  - Press Start Import button. If you performed all steps correct, no errors will appear during import process.
  - DB Import is done, you may check it server schema list.
3. Build Application WAR. Application uses __Maven__ as its building toolchain, so yo perform this steps you need maven installed and possible imported in your OS PATH variable.
  - Go to application directory _/path/to/project/advanced-bugtracker_
  - Run ```mvn install``` command to install all projects dependencies.
4. Now after application was built you need to deploy it on application server, to do that just copy apps \*.war archive from projects _target_ directory to Tomcat webapps folder and restart application server.
5. If all previous steps were performed correct, you now able to access application via it's URL: ```http://localhost:<your-tomcat-port>/advanced-bugtracker/```

Application work still in progress, so registration form isn't functional yet, to login user dev/dev or user/user credentials pairs.

Thank you!
