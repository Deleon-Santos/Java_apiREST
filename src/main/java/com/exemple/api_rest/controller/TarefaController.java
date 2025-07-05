
package com.exemple.api_rest.controller;

import com.exemple.api_rest.model.Tarefa;
import com.exemple.api_rest.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus; // Importe HttpStatus

import java.util.List;
import java.util.Optional;






@RestController
@RequestMapping("api_rest/tarefa")
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;

    // Criar uma tarefa
    @PostMapping
    public Tarefa criarTarefa(@RequestBody Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    // Consultar todas as tarefas
    @GetMapping
    public List<Tarefa> listarTarefas() {
        return tarefaRepository.findAll();
    }

    // Consultar tarefa pelo id
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarTarefaPorId(@PathVariable Long id) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        if (tarefa.isPresent()) {
            return ResponseEntity.ok(tarefa.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    // Atualizar tarefa
    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaDetalhes) { // Alterado para ResponseEntity<Object>
        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);

        if (tarefaOptional.isPresent()) {
            Tarefa tarefa = tarefaOptional.get();
            tarefa.setNome(tarefaDetalhes.getNome());
            tarefa.setDataEntrega(tarefaDetalhes.getDataEntrega());
            tarefa.setResponsavel(tarefaDetalhes.getResponsavel());

            Tarefa tarefaAtualizada = tarefaRepository.save(tarefa);
            return ResponseEntity.ok(tarefaAtualizada); // Retorna a Tarefa atualizada
        }
        // Retorna 404 Not Found com uma mensagem no corpo
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa com ID " + id + " não encontrada para atualização.");
    }
    // Atualizar tarefa
    //@PutMapping("/{id}")
    //public ResponseEntity<String> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaDetalhes) {
        //Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);

        //if (tarefaOptional.isPresent()) {
            //Tarefa tarefa = tarefaOptional.get();
           // tarefa.setNome(tarefaDetalhes.getNome());
           // tarefa.setDataEntrega(tarefaDetalhes.getDataEntrega());
            //.setResponsavel(tarefaDetalhes.getResponsavel());

            //Tarefa tarefaAtualizada = tarefaRepository.save(tarefa);
           // return ResponseEntity.ok(tarefaAtualizada);
       // }
        //return ResponseEntity.notFound().build();
        
        //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada para atualização.");
   // }

    // Remover tarefa   
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarTarefa(@PathVariable Long id) { // Alterado para ResponseEntity<String>
        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);
        if (tarefaOptional.isPresent()) {
            tarefaRepository.delete(tarefaOptional.get());
            // Retorna 200 OK com a mensagem de sucesso
            return ResponseEntity.ok("Tarefa com ID " + id + " deletada com sucesso!");
        }
        // Retorna 404 Not Found se a tarefa não for encontrada
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa com ID " + id + " não encontrada.");
    }
   //@DeleteMapping("/{id}")
    //public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        //Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);
        //if (tarefaOptional.isPresent()) {
           // tarefaRepository.delete(tarefaOptional.get());
           // return ResponseEntity.noContent().build();
       // }
        //return ResponseEntity.notFound().build();
   //}
}