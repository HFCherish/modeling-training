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
	14. post 201: -15:
	16. get 200: - 10:
        1. basic info - 15:
	    1. can paginate - 5:
	    1. get according to month - 10:
	    1. contains communication_type -5:
	    1. contains fee
	        1. can calculate when free charge -10:
	        1. can calculate according to communication type (local/internal/international) -15:
1. /users/uid/balance:
    1. get 404 (not current user) - 10:
    1. get 200:
        1. contains remained money - 5:
        1. contains right remained data info - 15:
        1. contains right remained call minutes info - 15:
        1. contains right remained messages info - 15:
        
<!--18. /users/uid/package_purchases:-->
	<!--19. post 201 -10:-->
	<!--20. post 400 -15:-->
	<!--21. get 404 -5:-->
	<!--22. get 200 - 15:-->
<!--23. /users/uid/top_ups:-->
	<!--19. post 201 -10:-->
	<!--20. post 400 -15:-->
	<!--21. get 404 -5:-->
	<!--22. get 200 - 15:-->
<!--23. /products:-->
	<!--24. get 200 -15:--> 