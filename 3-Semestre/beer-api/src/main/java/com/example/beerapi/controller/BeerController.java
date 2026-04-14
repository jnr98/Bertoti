package com.example.beerapi.controller;

import com.example.beerapi.model.Beer;
import com.example.beerapi.repository.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/beer")
@CrossOrigin(origins = "*")
public class BeerController {

    @Autowired
    private BeerRepository repository;

    // ===== LISTAR TODAS =====
    @GetMapping
    public List<Beer> listar() {
        return repository.findAll();
    }

    // ===== CRIAR =====
    @PostMapping
    public Beer criar(@RequestBody Beer beer) {
        return repository.save(beer);
    }

    // ===== ATUALIZAR =====
    @PutMapping("/{id}")
    public Beer atualizar(@PathVariable Long id, @RequestBody Beer beer) {
        beer.setId(id);
        return repository.save(beer);
    }

    // ===== DELETAR =====
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        repository.deleteById(id);
    }

}