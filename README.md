# telecom-provider
AND Digital Coding Challenge

A simple spring boot aplication with 3 endpoints:
* /numbers - provide all phone numbers
* /customer/{customerId}/numbers - provide all phone numbers for a given customer (404 if customer does not exist)
* /number/{numberId}/activate - activate a phone number (404 if phone number does not exist)

## Data
I used h2 as a relational database management system. 
Mocked data are inserted in data.sql file.

## Running
Use maven to package a project. Then run .jar file.
