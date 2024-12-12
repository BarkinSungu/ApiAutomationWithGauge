Switch Activity Tests
==========================
|isActive|
|true    |
|false   |

Switch activity
---------------------------
* Send a POST request to create a user with firstName "jane", lastName "doe", username "doejj" and password "123456Aa*"
* Save userId as "userId"
* Send a PATCH request to switch activity <isActive> by id "userId"
* Verify the switch activity <isActive> by id "userId" response