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
	    1. can get an instance with fields injection (getInstance) -15:
	5. method injection
    3. Qualified
        1. @name
