package ua.martishyn.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="demo")
public class MainController {
    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/add")
    public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email){
        Person createdPerson = new Person();
        createdPerson.setName(name);
        createdPerson.setName(email);
        personRepository.save(createdPerson);
        return "Saved";
    }
    @GetMapping("/add")
    public @ResponseBody Iterable<Person> getAllPersons(){
        return personRepository.findAll();
    }


}
