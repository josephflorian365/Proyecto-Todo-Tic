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
import pe.todotic.bookstoreapi_s2.model.User;
import pe.todotic.bookstoreapi_s2.repository.BookRepository;
import pe.todotic.bookstoreapi_s2.repository.UserRepository;
import pe.todotic.bookstoreapi_s2.web.dto.BookDTO;
import pe.todotic.bookstoreapi_s2.web.dto.UserDTO;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    // TODO 9: inyectar la dependencia mediante una @
    @Autowired
    private UserRepository userRepository;

    /**
     * Devuelve la lista completa de usuarios
     * Retorna el status OK: 200
     * Ej.: GET http://localhost:9090/api/users
     */
    @GetMapping("/list")
    List<User> list() {
        // TODO 10: retornar la lista completa de libros
        return userRepository.findAll();
    }

    /**
     * Devuelve un usuario por su ID, en caso contrario
     * lanza EntityNotFoundException.
     * Retorna el status OK: 200
     * Ej.: GET http://localhost:9090/api/users/1
     */
    @GetMapping("/{id}")
    User get(@PathVariable Integer id) {
        // TODO 11: retornar un usuario por su id, en caso que no lo encuentre lanzar EntityNotFoundException.
        return userRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Crea un libro a partir del cuerpo
     * de la solicitud HTTP y retorna
     * el libro creado.
     * Retorna el status CREATED: 201
     * Ej.: POST http://localhost:9090/api/users
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    User create(@RequestBody UserDTO userDTO) {
        User user = new ModelMapper().map(userDTO, User.class);
        // TODO 12: persistir en la BD y retornar.
        return userRepository.save(user);
    }

    /**
     * Actualiza un usuario por su ID, a partir
     * del cuerpo de la solicitud HTTP.
     * Si el libro no es encontrado se lanza EntityNotFoundException.
     * Retorna el status OK: 200.
     * Ej.: PUT http://localhost:9090/api/users/1
     */
    @PutMapping("/{id}")
    User update(
            @PathVariable Integer id,
            @RequestBody UserDTO userDTO
    ) {
        // TODO 13: buscar un usuario por su id, en caso que no lo encuentre lanzar EntityNotFoundException.
        User user = userRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);

//        book.setTitle(form.getTitle());
//        book.setSlug(form.getSlug());
//        book.setPrice(form.getPrice());
//        book.setDesc(form.getDesc());
//        book.setUpdatedAt(LocalDateTime.now());
        new ModelMapper().map(userDTO, user);
        // TODO 14: actualizar el usuario en la BD
        return userRepository.save(user);
    }

    /**
     * Elimina un libro por su ID.
     * Si el libro no es encontrado se lanza EntityNotFoundException.
     * Retorna el status NO_CONTENT: 204
     * Ej.: DELETE http://localhost:9090/api/users/1
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        // TODO 15: buscar un libro por su id, en caso que no lo encuentre lanzar EntityNotFoundException.
        User user = userRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);

        // TODO 16: eliminar el libro de la BD
        userRepository.delete(user);
    }

    /**
     * Devuelve la lista de usuarios de forma paginada.
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
    Page<User> paginate(
            /* TODO 18: sobreescribir la config. por defecto:
             *   ordenar por el título de forma ascendente
             *   y con un tamaño de 5 elementos por página. */
            @PageableDefault(sort = "firstName", direction = Sort.Direction.ASC, size = 5) Pageable pageable
    ) {
        // TODO 17: retornar la lista de libros de forma paginada
        return userRepository.findAll(pageable);
    }

    /**
     * Devuelve un usuario por su ID, en caso contrario
     * lanza EntityNotFoundException.
     * Retorna el status OK: 200
     * Ej.: GET http://localhost:9090/api/users/search
     */
    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam("keyword") String keyword) {
        // TODO 11: retornar un usuario por su id, en caso que no lo encuentre lanzar EntityNotFoundException.
        return userRepository.findByFullNameContainingIgnoreCase(keyword);
    }
}
