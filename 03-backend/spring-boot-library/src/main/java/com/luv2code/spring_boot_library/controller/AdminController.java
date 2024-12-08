package com.luv2code.spring_boot_library.controller;

import com.luv2code.spring_boot_library.requestmodels.AddBookRequest;
import com.luv2code.spring_boot_library.service.AdminService;
import com.luv2code.spring_boot_library.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @PutMapping("/secure/increase/book/quantity")
    public void increaseBookQuantity(@RequestHeader(value="Authorization") String token,
                                     @RequestParam Long bookId) throws Exception{
        String admin = ExtractJWT.payloadJWTExtraction(token,"\"userType\"");
        if(admin == null || !admin.equals("admin")){
            throw new Exception("Admin page only");
        }
        this.adminService.increaseBook(bookId);
    }

    @PutMapping("/secure/decrease/book/quantity")
    public void decreaseBookQuantity(@RequestHeader(value="Authorization") String token,
                                     @RequestParam Long bookId) throws Exception{
        String admin = ExtractJWT.payloadJWTExtraction(token,"\"userType\"");
        if(admin == null || !admin.equals("admin")){
            throw new Exception("Admin page only");
        }
        this.adminService.decreaseBook(bookId);
    }

    @PostMapping("/secure/add/book")
    public void postBook(@RequestHeader(value="Authorization") String token, @RequestBody AddBookRequest addBookRequest) throws Exception{
        String admin = ExtractJWT.payloadJWTExtraction(token,"\"userType\"");
        if(admin == null || !admin.equals("admin")){
            throw new Exception("Admin page only");
        }
        this.adminService.postBook(addBookRequest);
    }

    @DeleteMapping("/secure/delete/book")
    public void deleteBook(@RequestHeader(value="Authorization") String token,
                           @RequestParam Long bookId) throws Exception{
        String admin = ExtractJWT.payloadJWTExtraction(token,"\"userType\"");
        if(admin == null || !admin.equals("admin")){
            throw new Exception("Admin page only");
        }
        this.adminService.deleteBook(bookId);
    }
}
