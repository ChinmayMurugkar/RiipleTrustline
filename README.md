# Ripple TrustLine
This app allows users to keep track of debts

## Instructions

###Installation
```bash
./mvnw clean install
```

###Run Two instances
 - open two separate windows for bash
 - run profile userA first terminal window and userB in other
 - userA profile runs on port 8080
 - userB profile runs on port 8090
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=<Profile>
./mvnw spring-boot:run -Dspring-boot.run.profiles=userA
./mvnw spring-boot:run -Dspring-boot.run.profiles=userB
```

###Run Two instances
####Testing Curl
```bash
curl --location --request POST 'http://127.0.0.1:8090/user/sendUnits' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
"userName": "userA",
"amount": "10"
}
```
```bash
curl --location --request GET 'http://127.0.0.1:8080/user/getBalance?userName=%22userA%22'
```

###Some preliminary information
PIS :
sendUnits -  Post
receiveUnits - post
checkBalance - Get

Happy Path:
1. [A] Transaction starts [create unique trasnaction Id] -> register state -> "transit" [A] done
2. [A] Keep TTL for trasnaction -> after few min try again  done
3. On Success update balance -> update state[A] - "Done"

1. [B] Trasnaction received -> registered as "transit"
2. [B] update balance
3. mark "complete" send the trasnaction back

Service B goes down
1. [A] received TTL
2. Try again
3. Keep try count [TXN: countId]
4. Throw error back after try count=5

Service B receives same request twice
1. Check for trasnaction already present in trasnaction state table
2. If Yes, return back 400 saying trasnaction already in progress

Service B receives two simultaneous requests from service A
1. can handle through optimistic locking

Datastructres needed

Txn UUID -> sits with Transaction service

UserName | Balance
UserName | ipaddress
Txn UUID | state

###would have liked to implement but didn't have the time to
- Better Logging
- Better testing
- Going in depth of all edge cases and implementing the ones left out after rigours testing.
- separating out services to manage better
- Introducing more formal exception classes and possibly separate custom library that can be reused in various projects.
- Creating proper unit test frame work
- create integration test framework with something like Cucumber
- Setup better way to store environment variables, setting up vault to do so
- Create data access layer library to separate out the repository management in their
- Create Data access layer service to operate on DAL libraries, manage sql thread pools through their. Also, this would help managing various versions of DALs which would be current with the repository in various environments eg: development, Testing, parallel production, production...etc
  We would be able to also batch the SQL queries through the same.
- Create cache annotations and setup caching system on the API eg : spring-boot-starter-cache, @EnableCaching, @Cacheable(), @CacheEvict(),@CachePut()
- Create reactive model for the requests coming in by using : MONO or FLUX
- Create schedulers for example by using Liked blocking queue to manage the requests and avoid over loading of the service
- Introducing thread management system and handling threadpool through it.
- Define API schemas models and apis separately to manage in separate yaml files
- Define separate repository/springboot app to maintain the api versions separate and smooth migrations
- Improve logging in the API through introduction of distributed logging - either as a separate infra pipelines or api generating for monitoring and debugging purposes.
- Add better Authentication for example like Oauth through something like okta, google libraries, etc...
- Improving pom versioning style
- Introducing java code style to maintain uniformity in the development across service.
- Trasnaction service should be separate
- Better satte management system
- Wish had more time to implement a little that mentioned above, especially around testing so would get better results around data checks.




