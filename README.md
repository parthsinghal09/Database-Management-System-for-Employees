# Database-Management-System-for-Employees

The program stores employee data and maintains the information about organization hierarchy using a generic Tree data structure. The Databse also supports queries such as fire employee, hire employee, list employees and employee search in a time and space efficient manner using an AVL Tree.

# Folders

- Database Code
  - EmptyTreeException.java => File defines a custom Tree exception in the situation that the orgainzation is empty
  - IllegalIDException.java => File defines a custom exception if an illegal ID is given as input in a query
  - NotEmptyException.java => File defines a custom exception if someone tries to hire owner for a non-empty organozation
  - OrgHierarchyInterface.java => Defines the interface of the program
  - OrgHierarchy.java => Implementation of AVL Tree based Database Management System
  - Makefile - Used for running the program

- Tester 
  - Contains files which test the database system

> Usage - make (while using the makefile one should ensure that the path to the tester files is also provided as tester files are not in the same folder)

or 

> Usage without makefile - 

> javac -d classes/ -cp classes/ EmptyTreeException.java

> javac -d classes/ -cp classes/ IllegalIDException.java

> javac -d classes/ -cp classes/ NotEmptyException.java

> javac -d classes/ -cp classes/ OrgHierarchyInterface.java

> javac -d classes/ -cp classes/ OrgHierarchy.java

> javac -d classes/ -cp classes/ Path to Tester files

> java -cp classes/ Tester
