/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Book;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thomas
 */
public class BookDTO {
    private Long id;
    private String author;
    private String title;

    public BookDTO(Book book) {
        this.id = book.getId();
        this.author = book.getAuthor();
        this.title = book.getTitle();
    }

    public BookDTO() {
    }

    public Long getId() {
        return id;
    }
    

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public static List<BookDTO> createList(List<Book> books){
        List<BookDTO> bookDTOs = new ArrayList();
        books.stream().map((book)->new BookDTO(book)).forEachOrdered(bookDTOs::add);
        return bookDTOs;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
}
