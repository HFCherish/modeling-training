# api
1. /users/uid
	3. get 404 not exists - 10 : 15
	3. get 404 current user is not uid- 10 : 18
	2. get 200 - 15 : 8
3. /users/uid/calls
	5. post 404 - 5: 10
	5. post 400 - 10: 30
	4. post 201 - 15: 30
	5. get 404 - 5: 4
	6. get 200 - 15: 
	    1. basic info :26
	    1. paginated - 15: 1
	    1. show call_type info - 5: 4
	    1. show communication_type info -15: 8
	    1. show fee info - 15:  30

-----------
1. can get card info when get user detail
    1. 200, modify result detail - 10 : 10
1. modify post & get calls
    1. modify target info and not check target when post -15 :26
    1. add call type -10 : 1
1. can get call records according to month -15 : 14
    1. add start when create call record - 10: 12
-----------

7. /users/uid/messages:
	9. post 400 - 10: 6
	9. post 404 - 5: 7
	8. post 201 - 15: 17
	11. get 200
	    1. basic info - 15: 
	    1. can paginate - 5:
	    1. get according to month - 10: 18
	    1. contains communication_type -5: 3
	    1. contains fee
	        1. can calculate when free charge -10: 
	        1. can calculate according to communication type (local/internal/international) and send type -15: 
	        1. can calculate according to communication type (local/internal/international) and receiver type -10: 27
13. /users/uid/data_accesses:
	15. post 400: -10: 7
	15. post 404: -5: 4
	14. post 201: -15: 5
	16. get 200: - 10:
        1. basic info - 15:
	    1. can paginate - 5:
	    1. get according to month - 10: 10
	    1. contains communication_type -5: 2
	    1. contains fee
	        1. can calculate when free charge -10:
	        1. can calculate according to communication type (local/internal/international) -15: 12
1. /users/uid/balance:
    1. get 404 (not current user) - 10:
    1. get 200:
        1. contains remained money - 5:
        1. contains right remained data info - 15: 4
        1. contains right remained call minutes info - 15: 3
        1. contains right remained messages info - 15: 17



# infrastructure
1. modify gradle to contains mongo rather than jersey, write a simple test to see mongo is ok to use:
    1. configuration: gradle, database configuration -10: 35 
    1. can save to mongocollection with a simple test object (contains an id and a name) - 15:
        1. save code :24
        1. install mongodb and run it :16
        1. stuck at connection failure :52 (reason: the java mongodb-driver version is in consistent with local installed mongodb server version, and authentication schema between the two is different.)
        1. fix unmarshall bug (orm) :7 (reason: need a non-parameter constructor)
        1. fix assertion error :2 (reason: should rollback after each running)
    1. can get from mongocollection -5: 2
    1. use latest version of mongo-driver, and discard jongo (not support latest mongo-driver)
        1. study mongo-driver 3.4.0 usage (crud, orm) -60: 100
        2. modify the simple test to use mongo-driver 3.4.0
            1. save -15:
            2. get (implement orm by self)-15: 20
            2. get (try to do orm using code registry)-20: 70

1. find user by id
	2. contains right basic info - 15: 17
	3. contains right phone card info -15: 
	    1. contains right locale info -5:
	4. contains right balance info -15:
5. user.saveCallRecord