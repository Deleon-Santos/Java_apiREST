
package com.exemple.api_rest.controller;

import com.exemple.api_rest.model.Tarefa;
import com.exemple.api_rest.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api_rest/tarefa")
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;

    									// Criar uma requisicao
    @PostMapping
    public Tarefa criarTarefa(@RequestBody Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    									// Consultar todas as tarefas
    @GetMapping
    public List<Tarefa> listarTarefas() {
        return tarefaRepository.findAll();
    }

    									// Consultar tarefa id
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
    public ResponseEntity<Object> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaDetalhes) { 
        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);

        if (tarefaOptional.isPresent()) {
            Tarefa tarefa = tarefaOptional.get();
            tarefa.setNome(tarefaDetalhes.getNome());
            tarefa.setDataEntrega(tarefaDetalhes.getDataEntrega());
            tarefa.setResponsavel(tarefaDetalhes.getResponsavel());

            Tarefa tarefaAtualizada = tarefaRepository.save(tarefa);
            return ResponseEntity.ok(tarefaAtualizada); 
        }
        // Retorna uma mensagem no corpo informando a inexistencia do id
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa com ID " + id + " não encontrada.");
    }
          
    									// Remover tarefa   
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarTarefa(@PathVariable Long id) { 
        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);
        if (tarefaOptional.isPresent()) {
            tarefaRepository.delete(tarefaOptional.get());
            // Retorna mensagem de sucesso
            return ResponseEntity.ok("Tarefa com ID " + id + " deletada com sucesso!");
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa com ID " + id + " não encontrada.");
    }
   
}