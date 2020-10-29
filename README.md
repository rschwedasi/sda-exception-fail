1. Run via Gradle run task
2. ```
   curl --location --request POST 'http://localhost:8080/api/ok' \
   --header 'Content-Type: application/json' \
   --data-raw '{
       "payload" : {
            "someString" : "test"
       }
   }'
   ```
   👍 - Worked
3. ```
   curl --location --request POST 'http://localhost:8080/api/fail' \
   --header 'Content-Type: application/json' \
   --data-raw '{
       "nested" : {
           "payload" : {
   
           }
       }
   }'
   ```
   💣 - Watch it die
   

`org.sdase.commons.server.jackson.errors.JerseyValidationExceptionMapper` seems to die at Line 114 trying 
to instantiate a new Type-Object of null 😥
