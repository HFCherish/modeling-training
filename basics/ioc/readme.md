Implement a simple injector in accordance with JSR-330.

# @Inject
1. class injection －15 : 57
    1. refactor to use provider to get instance :8
    1. bind to instance -5: 2 
    2. scope
        1. can set scope as singleton when bind -15: 15
        2. can set scope as singleton by using annotation -15: 8
        3. can set scope as self-defined scope -15: 52
            1. how to get the annotation annotated by scope :40
2. field injection
	3. constructor injection －15 : 22
	    1. can only has one constructor with injection annotation -5 : 10
	4. field injection
	    1. can inject fields of a given instance (injectMembers) -15: 13
	    1. can get an instance with fields injection (getInstance) -10: 6
	5. method injection
	    1. can inject method parameters -15 : 10
    3. Qualified
        1. @name
            1. use key (contains qualifier and toInjectClass) to get binding instance -15: 15
            1. can use @name to qualify the instantiation of fields -15 : 47
            1. can use @name to qualify the instantiation of method parameters -10 : 8
        2. self defined qualifier:
            1. create self defined qualifier -5: 5
            2. use self defined qualifier to qualify field -15: 23
            2. use self defined qualifier to qualify method parameters -10:
