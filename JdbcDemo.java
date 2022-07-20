//STEP 1. Import required packages

import java.sql.*;
import java.util.Scanner;


public class JdbcDemo {

//Set JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
static final String DB_URL = "jdbc:mysql://localhost/Library?allowPublicKeyRetrieval=true&useSSL=false";
   
//  Database credentials
   static final String USER = "user1";// add your user 
   static final String PASS = "Password@123";// add password

   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;

// STEP 2. Connecting to the Database
   try{
      //STEP 2a: Register JDBC driver
      Class.forName(JDBC_DRIVER);
      //STEP 2b: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);
      // STEP 2c: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      Scanner s = new Scanner(System.in);

      main_menu(stmt,s);
      s.close();
      stmt.close();
      conn.close();
	}catch(SQLException se){    	 //Handle errors for JDBC
      	se.printStackTrace();
   	}catch(Exception e){        	//Handle errors for Class.forName
      e.printStackTrace();
   }finally{				//finally block used to close resources
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }					//end finally try
   }					//end try
   System.out.println("End of Code");
}	
static void main_menu(Statement stmt , Scanner s)
{
   System.out.println("Welcome to Library management system");
   System.out.println("Login as a :");
   System.out.println("1. Admin");
   System.out.println("2. Student");
   System.out.println("3. Librarian");
   System.out.println("4. EXIT");
   System.out.print("\n\n Please Enter your choice : ");
   int c = Integer.parseInt(s.nextLine());
   clearScreen();
   switch(c)
   {

      case 2:
      student_menu(stmt, s);
      break;
      case 4:
      System.exit(0);
      break;
      case 1:
      authenticate_super_admin(stmt,s);
      break;
      case 3:
      authenticate_librarian(stmt, s);
      break;
      default:
      System.out.println("Please Enter valid choice");
      

   }
}
static void authenticate_librarian(Statement stmt,Scanner s)
{
   System.out.println("Enter user_id:");
   String user_id = s.nextLine();
   System.out.println("Enter password");
   String password = s.nextLine();
   String st = "SELECT * from librarian";
   ResultSet r = null;
   boolean authenticated = false;
   try {
      r = stmt.executeQuery(st);

   } catch (SQLException se) {
      se.printStackTrace();
   } catch (Exception e) {
       e.printStackTrace();
   }
   try
   {
      while (r.next())
      {
         if(password.equals(r.getString("librarian_password") )&& user_id.equals(r.getString("librarian_id")))
         {
            authenticated = true;
            librarian_menu(stmt,s);
            return;

         }
      }
      if(authenticated == false)
      {
         System.out.println("Entered credentials are invalid do you want to try it again Y/N?");
         char h = s.nextLine().charAt(0);
         if(h == 'y' || h == 'Y')
         {
            authenticate_librarian(stmt, s);
         }
      }

   }catch (SQLException se) {
      se.printStackTrace();
   } catch (Exception e) {
       e.printStackTrace();
   }
   return;

}
static void authenticate_super_admin(Statement stmt,Scanner s)
{
   System.out.println("Enter user_id:");
   String user_id = s.nextLine();
   System.out.println("Enter password");
   String password = s.nextLine();
   String st = "SELECT * from super_admin";
   ResultSet r = null;
   boolean authenticated = false;
   try {
      r = stmt.executeQuery(st);

   } catch (SQLException se) {
      se.printStackTrace();
   } catch (Exception e) {
       e.printStackTrace();
   }
   try
   {
      while (r.next())
      {
         if(password.equals(r.getString("super_admin_password")) && user_id .equals(r.getString("super_admin_id")))
         {
            authenticated = true;
            super_admin_menu(stmt,s);
            return;

         }
      }
      if(authenticated == false)
      {
         System.out.println("Entered credentials are invalid do you want to try it again Y/N?");
         char h = s.nextLine().charAt(0);
         if(h == 'Y' || h == 'y')
         {
            authenticate_super_admin(stmt, s);
         }
      }

   }catch (SQLException se) {
      se.printStackTrace();
   } catch (Exception e) {
       e.printStackTrace();
   }

   return;
}
static void librarian_menu(Statement stmt,Scanner s)
{
   
   System.out.println("Please Enter your choice");
   System.out.println("1. Add a book");
   System.out.println("2. Delete a book");
   System.out.println("3. list of available books");
   System.out.println("4. list of books");
   System.out.println("5. Issue a book to a student");
   System.out.println("6. Return a book");
   System.out.println("7. EXIT");
   System.out.print("\n\n Please Enter your choice : ");
   // int i = s.nextInt();
   Integer i = Integer.parseInt(s.nextLine());
   switch(i)
   {
      case 1: 
      add_book(stmt,s);
      break;
      case 2:
      delete_book(stmt,s);
      break;
      case 3:
      list_of_all_books(stmt, s, true);
      break;
      case 4:
      list_of_all_books(stmt, s, false);
      break;
      case 5:
      issue_book(stmt,s);
      break;
      case 6:
      return_book(stmt,s);
      break;
      case 7:
      System.exit(0);
      break;
      default:
      System.out.println("Please enter a valid choice..");
   }
   librarian_menu(stmt, s);
   return;

}
static void super_admin_menu(Statement stmt,Scanner s)
{
   
System.out.println("Hello Super_admin");
System.out.println("Here are the operations you can choose");
System.out.println("1. Add a student");
System.out.println("2. Delete a student");
System.out.println("3. Add a Librarian");
System.out.println("4. Delete a Librarian");
System.out.println("5. List of students");
System.out.println("6. List of Librarians");  
System.out.println("7. EXIT");
Integer i = Integer.parseInt(s.nextLine());
switch(i)
{
   case 1:
   add_student(stmt,s);
   break;
   case 2:
   delete_student(stmt,s);
   break;
   case 3:
   add_librarian(stmt,s);
   break;
   case 4:
   delete_librarian(stmt,s);
   break;
   case 5:
   list_of_all_students(stmt,s);
   break;
   case 6:
   list_of_all_librarians(stmt,s);
   break;
   case 7:
   System.exit(0);
   break;
   default:
   System.out.println("Please enter a valid choice"); 
}
super_admin_menu(stmt, s);
}
static void add_book(Statement stmt,Scanner s)
{
   try{
      clearScreen();
      System.out.println("Please Enter following details of the student:");
      System.out.println("Enter book id:");
      String id = s.nextLine();
      System.out.println("Enter book name");
      String name = s.nextLine();
      System.out.println("Enter author name");
      String author = s.nextLine();
      System.out.println("Enter publication year");
      Integer p_year = Integer.parseInt(s.nextLine());
      String st = String.format("INSERT INTO book VALUES('%s','%s','%s','%d',NULL)", id,name,author,p_year);
      int r = 0;
      try{
         r = stmt.executeUpdate(st);
      }catch(SQLException se){}catch(Exception e){}
      if(r != 0)
      {
         System.out.println("Book added successfully");
      }
      else 
      {
         System.out.println("Error Occured!! Please try again");
      } 
   }catch(Exception e ){}
   return;
}
static void delete_book(Statement stmt,Scanner s)
{
   try{
      clearScreen();
      System.out.println("Please Enter following details of the student:");
      System.out.println("Enter book id:");
      String id = s.nextLine();
      String st = String.format("DELETE from book where book_id = '%s'",id);
      int r = 0;
      try{
         r = stmt.executeUpdate(st);
      }catch(SQLException se){}catch(Exception e){}
      if(r != 0)
      {
         System.out.println("Book deleted successfully");
      }
      else 
      {
         System.out.println("Error Occured!! Please try again");
      } 
   }catch(Exception e ){}

}
static boolean books ;
static void issue_book(Statement stmt, Scanner s)
{
   clearScreen();
   System.out.println("Enter student id");
   String id = s.nextLine();
   System.out.println("Enter book id");
   String b_id = s.nextLine();
   String st = String.format("UPDATE book SET issuer = '%s' where book_id = '%s'", id,b_id);
   String st2 = String.format("UPDATE student SET book_id = '%s' where roll_number = '%s'",b_id,id);
   int r1 = 0;
   try{
      r1 = stmt.executeUpdate(st2);
   }catch(SQLException se){}catch(Exception e){}

   int r = 0;
   try{
      r = stmt.executeUpdate(st);
   }catch(SQLException se){}catch(Exception e){}
   if(r == 0 || r1 == 0)
   System.out.println("Something went wrong.. Try again");
   else
   System.out.println("Book issued successfully");

}
static void return_book(Statement stmt,Scanner s)
{
   clearScreen();
   System.out.println("Enter book id");
   String b_id = s.nextLine();
   System.out.println("Enter student id");
   String id = s.nextLine();
   String st = String.format("UPDATE book SET issuer = NULL where book_id = '%s'",b_id);
   String st2 = String.format("UPDATE student SET book_id = NULL where roll_number = '%s'",id);
   int r1 = 0;
   try
   {
      r1 = stmt.executeUpdate(st2);
   }catch(SQLException se){}catch(Exception e){}
   int r = 0;
   try{
      r = stmt.executeUpdate(st);
   }catch(SQLException se){}catch(Exception e){}
   if(r == 0 || r1 == 0)
   System.out.println("Something went wrong.. Try again");
   else
   System.out.println("Book returned ..!");
}
static void add_student(Statement stmt, Scanner s)
{
   
   try{
      clearScreen();
      System.out.println("Please Enter following details of the student:");
      System.out.println("Enter student id:");
      String id = s.nextLine();
      System.out.println("Enter student name");
      String name = s.nextLine();
      String st = String.format("INSERT INTO student VALUES('%s','%s',NULL)", id,name);
      int r = 0;
      try{
      r = stmt.executeUpdate(st);
      }catch(SQLException se){}catch(Exception e){}
      if(r != 0)
      {
         System.out.println("Student added successfully");
      }
      else 
      {
         System.out.println("Error Occured!! Please try again");
      } 

   }catch(Exception e ){}
}
static void delete_student(Statement stmt,Scanner s)
{
   try{
      clearScreen();
      System.out.println("Enter id of the student :");
      String id = s.nextLine();
      String st = String.format("DELETE from student where roll_number = '%s'", id);
      int r  = 0;
      try
      {
         r = stmt.executeUpdate(st);
         
      }catch(SQLException se){}catch(Exception e){}
      if(r != 0)
         {
            System.out.println("Student deleted Successfully");
            return;
         }
         else
         {
            System.out.println("Error Occured!! Please try again");
         }
   }catch(Exception e){}
}
static void add_librarian(Statement stmt,Scanner s)
{
   try{
      clearScreen();
      System.out.println("Please enter the following details of tha librarian");
      System.out.println("Enter id of the librarian");
      String l_id = s.nextLine();
      System.out.println("Enter name of the librarian");
      String l_name = s.nextLine();
      System.out.println("Enter password of the librarian");
      String l_pass = s.nextLine();
      String st = String.format("INSERT INTO librarian VALUES('%s','%s','%s')", l_id,l_name,l_pass);
      int r = 0;
      try{
         r = stmt.executeUpdate(st);
      }catch(SQLException se){}catch(Exception e){}
      if(r != 0)
      {
         System.out.println("Librarian added Successfully");
      }
      else
      {
         System.out.println("Error Occured!! Please try again");
      }
   }catch(Exception e){}
}
static void delete_librarian(Statement stmt, Scanner s)
{
   try{
      clearScreen();
      System.out.println("Please enter the following details of tha librarian");
      System.out.println("Enter id of the librarian");
      String l_id = s.nextLine();
      String st = String.format("DELETE from librarian where librarian_id = '%s'", l_id);
      int r  = 0;
      try
      {
         r = stmt.executeUpdate(st);
         
      }catch(SQLException se){
      }catch(Exception e){}
      if(r != 0)
         {
            System.out.println("Librarian deleted Successfully");
            return;
         }
         else
         {
            System.out.println("Error Occured!! Please try again");
         }
   }catch(Exception e){}
}
static void list_of_all_students(Statement stmt, Scanner s)
{
   try{
 
      String st = "SELECT * from student";
      ResultSet r = null;
      try {
         r = stmt.executeQuery(st);
   
      } catch (SQLException se) {} catch (Exception e){}
      System.out.println("Here is the List of students:");
      while(r.next() != false)
      {
         System.out.println("ID :" + r.getString("roll_number"));
         System.out.println("Name: " +r.getString("full_name"));
      }
   }catch(Exception e){}
}
static void list_of_all_librarians(Statement stmt, Scanner s)
{
   try{
      clearScreen();
      String st = "SELECT * from librarian";
      ResultSet r = null;
      try 
      {
         r = stmt.executeQuery(st);
   
      } catch (SQLException se) {} catch (Exception e) {}
      System.out.println("Here is the List of librarians :");
      while(r.next() != false)
      {
         System.out.println("ID :" + r.getString("librarian_id"));
         System.out.println("Name: " +r.getString("librarian_name"));
      }
   }catch(Exception e){}
}
static void student_menu(Statement stmt, Scanner s)
{

   System.out.println("Please enter your choice");
   System.out.println("1. List of all books");
   System.out.println("2.EXIT");
   int i = Integer.parseInt(s.nextLine());

   switch(i)
   {
      case 1:
      list_of_all_books(stmt,s,true);
      break;
      case 2:
      System.exit(0);
      break;
      default: 
      System.out.println("Please Enter valid choice");
   }
   //clearScreen();
   student_menu(stmt, s);
}
static void clearScreen() {
   System.out.println("\033[H\033[J");
   System.out.flush();
}	
static void list_of_all_books(Statement stmt,Scanner s,boolean availablity)
{
   
   String st= "select * from book";
   ResultSet r = null;
   try {
      r = stmt.executeQuery(st);

   } catch (SQLException se) {
      //se.printStackTrace();
   } catch (Exception e) {
       //e.printStackTrace();
   }

   try 
   {
      System.out.println("List of books are:\n");
      while (r.next() != false)
      {
         String id = r.getString("book_id");
         String name = r.getString("book_name");
         String author = r.getString("book_author");
         int p_year = r.getInt("publication_year");
         String issuer = r.getString("issuer");
         if(!availablity)
         {
            System.out.println("ID : " + id);
            System.out.println("Book Name: " + name);
            System.out.println("Author : " + author);
            System.out.println("Publication year : " + p_year);
            System.out.println("");
            books = true;
         }
         else
         {
            if(issuer == null)
            {
               System.out.println("ID : " + id);
               System.out.println("Book Name: " + name);
               System.out.println("Author : " + author);
               System.out.println("Publication year : " + p_year);
               System.out.println("");
               books = true;
            }
         }
      }
      if(books == false)
      {
         System.out.println("No books are available at the moment");
      }
      r.close();
   }
   catch(SQLException se)
   {
      se.printStackTrace();
   }

}
}					
