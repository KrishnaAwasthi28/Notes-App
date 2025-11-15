package com.project.NotesApp_backend.Controller;

import com.project.NotesApp_backend.DTO.NoteDTO;
import com.project.NotesApp_backend.Service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") // allow React dev server
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService service) {
        this.noteService = service;
    }

    @GetMapping("/notes")
    public ResponseEntity<Page<NoteDTO>> searchNotes(
            @RequestParam Optional<String> search,
            @RequestParam Optional<String> tags, // comma separated
            @RequestParam Optional<Boolean> matchAllTags,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size
    ) {
        String q = search.orElse("");
        List<String> tagList = tags.filter(s -> !s.isBlank())
                .map(s -> List.of(s.split(",")))
                .map(list -> list.stream().map(String::trim).filter(t -> !t.isEmpty()).toList())
                .orElse(List.of());
        boolean match = matchAllTags.orElse(false);
        int p = page.orElse(0);
        int s = size.orElse(20);
        Pageable pageable = PageRequest.of(p, s, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<NoteDTO> result = noteService.searchNotes(q, tagList, match, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/notes/{id}")
    public ResponseEntity<NoteDTO> getNote(@PathVariable Long id) {
        var dto = noteService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/notes")
    public ResponseEntity<NoteDTO> create(@RequestBody NoteDTO dto) {
        var created = noteService.createNote(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/notes/{id}")
    public ResponseEntity<NoteDTO> update(@PathVariable Long id, @RequestBody NoteDTO dto) {
        var updated = noteService.updateNote(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/notes/export")
    public ResponseEntity<List<NoteDTO>> exportAll() {
        var list = noteService.exportAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/notes/import")
    public ResponseEntity<List<NoteDTO>> importNotes(@RequestBody List<NoteDTO> dtos) {
        var created = noteService.importNotes(dtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
