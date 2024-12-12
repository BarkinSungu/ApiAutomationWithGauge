User Creation Tests
==========================

| firstName  | lastName   | username   | password   |
|            | doe        | dojj       | 123456Aa*  |
| jane       |            | dojj       | 123456Aa*  |
| jane       | doe        |            | 123456Aa*  |
| jane       | doe        | dojj       |            |
| jane1      | doe        | dojj       | 123456Aa*  |
| jane*      | doe        | dojj       | 123456Aa*  |
| jane       | doe1       | dojj       | 123456Aa*  |
| jane       | doe*       | dojj       | 123456Aa*  |
| jane       | doe        | dojj*      | 123456Aa*  |
| j          | doe        | dojj       | 123456Aa*  |
| janejanejanejanejanejanejanejanejanejanejanejanejane      | doe        | dojj       | 123456Aa*  |
| jane       | d          | dojj       | 123456Aa*  |
| jane       | doedoedoedoedoedoedoedoedoedoedoedoedoedoedoedoedoedoe       | dojj       | 123456Aa*  |
| jane       | doe        | d          | 123456Aa*  |
| jane       | doe        | dojj*dojj*dojj*      | 123456Aa*  |


Create a new user with valid user
---------------------------
* Send a POST request to create a user with firstName "jane", lastName "doe", username "doejj" and password "123456Aa*"
* Verify the user creation response contains ID

Create a new user with invalid user
----------------------------

* Send a POST request to create a user with firstName <firstName>, lastName <lastName>, username <username> and password <password>
* Verify the user creation error