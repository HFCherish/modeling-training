1. /users/uid
	3. get 404 not exists - 10 : 15
	3. get 404 current user is not uid- 10 : 18
	2. get 200 - 15 : 8
3. /users/uid/calls
	5. post 404 - 5: 10
	5. post 400 - 10: 30
	4. post 201 - 15: 30
	    1. modify the user & target balance -15:
	5. get 404 - 5: 4
	6. get 200 - 15: 
	    1. basic info :26
	    1. paginated - 15:
	    1. show fee info - 15:
	    1. show call_type info - 15:
	    1. show communication_type info -15:
7. /users/uid/messages:
	8. post 201 - 15:
	9. post 404 - 5:
	11. get 200 - 15:
	12. get 404 - 5:
13. /users/uid/data_accesses:
	14. post 201: -15:
	15. post 404: -5:
	16. get 200: - 10:
	17. get 404 -5:
18. /users/uid/package_purchases:
	19. post 201 -10:
	20. post 400 -15:
	21. get 404 -5:
	22. get 200 - 15:
23. /users/uid/top_ups:
	19. post 201 -10:
	20. post 400 -15:
	21. get 404 -5:
	22. get 200 - 15:
23. /products:
	24. get 200 -15: 