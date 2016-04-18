package com.tutorialspoint.test;
   
import com.tutorialspoint.stateful.LibraryStatefulSessionBeanRemote;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
 
public class EJBTester3 {
 
   BufferedReader brConsoleReader = null; 
   Properties props;
   InitialContext ctx;
   {
      props = new Properties();
      try {
         props.load(new FileInputStream("jndi.properties"));
      } catch (IOException ex) {
         ex.printStackTrace();
      }
      try {
         ctx = new InitialContext(props);            
      } catch (NamingException ex) {
         ex.printStackTrace();
      }
      brConsoleReader = 
      new BufferedReader(new InputStreamReader(System.in));
   }
   
   public static void main(String[] args) {
 
      EJBTester3 ejbTester3 = new EJBTester3();
 
      ejbTester3.testStatefulEjb();
   }
   
   private void showGUI(){
      System.out.println("**********************");
      System.out.println("Welcome to Book Store");
      System.out.println("**********************");
      System.out.print("Options \n1. Add Book\n2. Exit \nEnter Choice: ");
   }
   
   private void testStatefulEjb(){
 
      try {
         int choice = 1; 
 
         LibraryStatefulSessionBeanRemote libraryBean =
         (LibraryStatefulSessionBeanRemote)ctx.lookup("LibraryStatefulSessionBean/remote");
 
         while (choice != 2) {
            String bookName;
            showGUI();
            String strChoice = brConsoleReader.readLine();
            choice = Integer.parseInt(strChoice);
            if (choice == 1) {
               System.out.print("Enter book name: ");
               bookName = brConsoleReader.readLine();
               libraryBean.addBook(bookName);          
            } else if (choice == 2) {
               break;
            }
         }
 
       List<String> booksList = libraryBean.getBooks();
         System.out.println("Book(s) entered so far: " + booksList.size());
         for (int i = 0; i < booksList.size(); ++i) {
         System.out.println((i+1)+". " + booksList.get(i));
         }      
         LibraryStatefulSessionBeanRemote libraryBean1 = 
            (LibraryStatefulSessionBeanRemote)ctx.lookup("LibraryStatefulSessionBean/remote");
         List<String> booksList1 = libraryBean1.getBooks();
         System.out.println(
            "***Using second lookup to get library stateful object***");
         System.out.println(
            "Book(s) entered so far: " + booksList1.size());
         for (int i = 0; i < booksList1.size(); ++i) {
            System.out.println((i+1)+". " + booksList1.get(i));
         }		 
      } catch (Exception e) {
         System.out.println(e.getMessage());
         e.printStackTrace();
      }finally {
         try {
            if(brConsoleReader !=null){
               brConsoleReader.close();
            }
         } catch (IOException ex) {
            System.out.println(ex.getMessage());
         }
      }
   }
}
