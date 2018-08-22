Introduction:
The purpose of this project is to satisfy the use cases provided in the document "/receivables/Coding Challenge - C1 FS Tech.pdf"

About the Project:
Go through the document "" available in "/deliverables/SkillProjectDocument.pdf"

Step - 1:

Project Setup:

Prerequisite:
Install Latest Java
Install Docker. Make sure it is up and running
Install IDE like Intellij
Install Robo 3T
Install JMeter

Step 2:

Download Git project from the following github locations:

The below project is responsible for spinning up mongo in docker (local)
git clone https://github.com/maheshkumar-sr/skill-dev-tools

The below project have source code for skills microservices java api project
git clone https://github.com/maheshkumar-sr/skill-service

Step 3:
Open skill-dev-tools
In terminal tab, run the below command
docker-compose up -d

After that check if docker image is available for mongo either through Kitematic or by below command
docker ps

Step-4
Go to Application.java and run. Now stop it and edit the Application Configuration.
Make sure to include Program Argument parameter as --spring.profiles.active=local

Step-5:
Run the Project.

Step-6:
Swagger-ui acts as the front end. Hence, launch the below browser url.
http://localhost:8080/swagger-ui.html

Step-7:

Following service will be displayed.

POST: /api/skills/addSkill
POST: /api/skills/importSkillsResources
GET: /api/skills/searchSkill
GET: /api/skills/searchSkillByFilters

More information is provided in the SkillProjectDocument.pdf

Step-8:
Upload resoureload1.csv and resourceload2.csv using the import API

Step-9:
Configure JMeter and collect the reports for 10-20 concurrent users

Step-10:
Jenkins build script provided to provision pipelines for lower environments upto stage






