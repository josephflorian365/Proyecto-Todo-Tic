package pe.todotic.bookstoreapi_s2.web;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.todotic.bookstoreapi_s2.model.Book;
import pe.todotic.bookstoreapi_s2.repository.BookRepository;
import pe.todotic.bookstoreapi_s2.web.dto.BookDTO;

import java.time.LocalDateTime;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    // TODO 9: inyectar la dependencia mediante una @
    @Autowired
    private BookRepository bookRepository;

    /**
     * Devuelve la lista completa de libros
     * Retorna el status OK: 200
     * Ej.: GET http://localhost:9090/api/books
     */
    @GetMapping("/list")
    List<Book> list() {
        // TODO 10: retornar la lista completa de libros
        return bookRepository.findAll();
    }

    /**
     * Devuelve un libro por su ID, en caso contrario
     * lanza EntityNotFoundException.
     * Retorna el status OK: 200
     * Ej.: GET http://localhost:9090/api/books/1
     */
    @GetMapping("/{id}")
    Book get(@PathVariable Integer id) {
        // TODO 11: retornar un libro por su id, en caso que no lo encuentre lanzar EntityNotFoundException.
        return bookRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Crea un libro a partir del cuerpo
     * de la solicitud HTTP y retorna
     * el libro creado.
     * Retorna el status CREATED: 201
     * Ej.: POST http://localhost:9090/api/books
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Book create(@RequestBody BookDTO bookDTO) {
        Book book = new ModelMapper().map(bookDTO, Book.class);
        // TODO 12: persistir en la BD y retornar.
        return bookRepository.save(book);
    }

    /**
     * Actualiza un libro por su ID, a partir
     * del cuerpo de la solicitud HTTP.
     * Si el libro no es encontrado se lanza EntityNotFoundException.
     * Retorna el status OK: 200.
     * Ej.: PUT http://localhost:9090/api/books/1
     */
    @PutMapping("/{id}")
    Book update(
            @PathVariable Integer id,
            @RequestBody BookDTO bookDTO
    ) {
        // TODO 13: buscar un libro por su id, en caso que no lo encuentre lanzar EntityNotFoundException.
        Book book = bookRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);

//        book.setTitle(form.getTitle());
//        book.setSlug(form.getSlug());
//        book.setPrice(form.getPrice());
//        book.setDesc(form.getDesc());
//        book.setUpdatedAt(LocalDateTime.now());
        new ModelMapper().map(bookDTO, book);
        // TODO 14: actualizar el libro en la BD
        return bookRepository.save(book);
    }

    /**
     * Elimina un libro por su ID.
     * Si el libro no es encontrado se lanza EntityNotFoundException.
     * Retorna el status NO_CONTENT: 204
     * Ej.: DELETE http://localhost:9090/api/books/1
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        // TODO 15: buscar un libro por su id, en caso que no lo encuentre lanzar EntityNotFoundException.
        Book book = bookRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);

        // TODO 16: eliminar el libro de la BD
        bookRepository.delete(book);
    }

    /**
     * Devuelve la lista de libros de forma paginada.
     * El cliente puede enviar los parámetros page, size, sort,... en la URL
     * para configurar la página solicitada.
     * Si el cliente no envía ningún parámetro para la paginación,
     * se toma la configuración por defecto.
     * Retorna el status OK: 200
     * Ej.: GET http://localhost:9090/api/books?page=0&size=2&sort=createdAt,desc
     *
     * @param pageable la configuración de paginación que captura los parámetros como: page, size y sort
     */
    @GetMapping
    Page<Book> paginate(
            /* TODO 18: sobreescribir la config. por defecto:
             *   ordenar por el título de forma ascendente
             *   y con un tamaño de 5 elementos por página. */
            @PageableDefault(sort = "title", direction = Sort.Direction.ASC, size = 5) Pageable pageable
    ) {
        // TODO 17: retornar la lista de libros de forma paginada
        return bookRepository.findAll(pageable);
    }

}
