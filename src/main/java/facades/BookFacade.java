/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.BookDTO;
import entities.Book;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author thomas
 */
public class BookFacade {

    private static BookFacade instance;
    private static EntityManagerFactory emf;

    public static BookFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BookFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //TODO Remove/Change this before use
    public long getBookCount() {
        EntityManager em = getEntityManager();
        try {
            long renameMeCount = (long) em.createQuery("SELECT COUNT(r) FROM Book r").getSingleResult();
            return renameMeCount;
        } finally {
            em.close();
        }
    }

    //TODO Remove/Change this before use
    public BookDTO getBook(long id) {
        EntityManager em = getEntityManager();
        try {
            Book book = em.find(Book.class, id);
            return new BookDTO(book);
        } finally {
            em.close();
        }
    }

    //TODO Remove/Change this before use
    public List<BookDTO> getAllBook() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Book> q = em.createNamedQuery("Book.findAll", Book.class);
            return BookDTO.createList(q.getResultList());
        } finally {
            em.close();
        }
    }

    //TODO Remove/Change this before use
    public BookDTO create(BookDTO bookDTO) {
        EntityManager em = getEntityManager();
        try {
        Book book = new Book(bookDTO.getAuthor(), bookDTO.getTitle());
        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();
            System.out.println("CREATED: "+book);
        return new BookDTO(book);
        } finally  {
            em.close();
        }
        
    }

    //TODO Remove/Change this before use
    public BookDTO update(BookDTO bookDTO) throws Exception {
        EntityManager em = getEntityManager();
        Book book = em.find(Book.class, bookDTO.getId());
        if (book != null) {
            book = new Book(bookDTO.getId(), bookDTO.getAuthor(), bookDTO.getTitle());
            em.getTransaction().begin();
            em.merge(book);
            em.getTransaction().commit();
            return new BookDTO(book);
        }
        else {
        throw new Exception("No such book");
        }
    }

    public Long delete(Long id) {
        EntityManager em = getEntityManager();
        Book renameMe = em.find(Book.class, id);
        em.getTransaction().begin();
        em.remove(renameMe);
        em.getTransaction().commit();
        return renameMe.getId();
    }

    public static void main(String[] args) {
        EntityManagerFactory EMF = Persistence.createEntityManagerFactory("pu");
        BookFacade fe = getFacade(EMF);
        BookDTO book = new BookDTO(new Book("Jan Peter", "Best adventures"));
        BookDTO created = fe.create(book);
        fe.getAllBook().forEach((element) -> System.out.println(element));
        book.setAuthor("totally changed");
        try {
            System.out.println(fe.update(created));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("deleted object with id: " + fe.delete(created.getId()));
    }
}
