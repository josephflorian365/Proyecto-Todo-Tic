package pe.todotic.bookstoreapi_s2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.todotic.bookstoreapi_s2.model.Book;

// TODO 8: convertir la interface en un repositorio para la entidad Book
public interface BookRepository extends JpaRepository<Book, Integer> {
}
