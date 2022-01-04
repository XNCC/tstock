package com.vbqncc.tstock.tstock.se;

import org.apache.tomcat.jni.Library;
import org.apache.tomcat.jni.Sockaddr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mylist {
    public static void main(String[] args) {
        List<Book> library = new ArrayList<Book>();
        Librarian librarian = new Librarian("ncc", "123456", library);
        Student student = new Student("vbq", "123456");

        Book b1 = new Book("java入门到入土", "技术", 138.8);
        Book b2 = new Book("西游记", "神话", 88);
        Book b3 = new Book("三国演义", "同人", 99);
        Book b4 = new Book("水浒传", "基佬文", 77);
        librarian.add(b1);
        librarian.add(b2);
        librarian.add(b3);
        librarian.add(b4);
        for (int i = 0; i < library.size(); i++) {
            Book book = library.get(i);
            System.out.println(book.getName());
        }
        System.out.println("////////////////////////////////////////");
        student.borrow(librarian, b4);
        for (int i = 0; i < library.size(); i++) {
            Book book = library.get(i);
            System.out.println(book.getName());
        }
        System.out.println("////////////////////////////////");
        student.returnBook(librarian,b4);
        for (int i = 0; i < library.size(); i++) {
            Book book = library.get(i);
            System.out.println(book.getName());
        }
    }

}

class Student {
    private String stunum;
    private String password;

    public String getStunum() {
        return stunum;
    }

    public void setStunum(String stunum) {
        this.stunum = stunum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Student(String stunum, String password) {
        this.stunum = stunum;
        this.password = password;
    }

    public Book borrow(Librarian librarian, Book book) {
        System.out.println(this.stunum + "要向-->" + librarian.getUserrname() + ",借：" + book.getName());
        System.out.println("///////////////////////////");
        librarian.delete(book);
        return book;
    }
    public Book returnBook(Librarian librarian,Book book){
        System.out.println(this.stunum+"要向"+librarian.getUserrname()+"来还"+book.getName());
        System.out.println("///////////////");
        librarian.add(book);
        return book;
    }
}

class Book {
    private String name;
    private String type;
    private double money;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Book(String name, String type, double money) {
        this.name = name;
        this.type = type;
        this.money = money;
    }
}

class Librarian {
    private String userrname;
    private String password;
    private List<Book> library;

    public String getUserrname() {
        return userrname;
    }

    public void setUserrname(String userrname) {
        this.userrname = userrname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Librarian(String userrname, String password, List<Book> library) {
        this.userrname = userrname;
        this.password = password;
        this.library = library;
    }

    public void add(Book book) {
        library.add(book);
    }

    public void delete(Book book) {
        library.remove(book);
    }

    public void update() {

    }

    public Book query(String bookName) {
        return null;
    }


}