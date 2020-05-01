package com.bookstore.admin.controllers;

import com.bookstore.admin.domain.Book;
import com.bookstore.admin.domain.User;
import com.bookstore.admin.exceptions.StorageFileNotFoundException;
import com.bookstore.admin.service.BookService;
import com.bookstore.admin.service.StorageService;
import com.bookstore.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/book")
public class BookController {

    private final StorageService storageService;

    @Autowired
    public BookController(StorageService storageService) {
        this.storageService = storageService;
    }

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @GetMapping("/addUpdateBook")
    public String getAddBookForm(Model model, @PathParam("id") Long id) {
        if (id == null) {
            model.addAttribute("type", "create");
            model.addAttribute("book", new Book());
        } else {
            Book book = bookService.findBookById(id);
            model.addAttribute("type", "update");
            model.addAttribute("book", book);
        }
        return "addUpdateBook";
    }

    @PostMapping("/addUpdateBook")
    public String addBook(@ModelAttribute("book") Book book) {
            if(!book.getBookImage().isEmpty()){
                book.setBookImageName(book.getBookImage().getOriginalFilename());
                bookService.save(book);
                storageService.store(book.getBookImage(), book.getId());
            }else{
                //todo: need to put book image update and delete code.
                bookService.save(book);
            }
        return "redirect:bookList";
    }

    @GetMapping("/bookList")
    public String bookListPage(Model model) {
        List<Book> bookList = bookService.finAll();
        model.addAttribute("bookList", bookList);
        /*
        method to create resource uri

        model.addAttribute("books", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(BookController.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));*/
        return "bookList";
    }

    @GetMapping("/bookImage/{bookName:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String bookName) {
        Resource file = storageService.loadAsResource(bookName);

        //code to create link for image download.
		/*return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);*/

        return ResponseEntity.ok().header(
                "attachment; bookName=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/bookInfo")
    public String bookInfo(Model model, @PathParam("id") Long id) {
        model.addAttribute("book", bookService.findBookById(id));
        return "bookInfo";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
