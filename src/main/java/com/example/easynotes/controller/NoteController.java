package com.example.easynotes.controller;

import com.example.easynotes.model.Note;
import com.example.easynotes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {

    @Autowired
    NoteRepository noteRepository;

    @GetMapping("/notes")
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @PostMapping("/notes")
    public Note createNote(@Valid @RequestBody Note note) {
        return noteRepository.save(note);
    }

    @GetMapping("/notes/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable(value = "id") Long noteId) {
        Note note = noteRepository.findOne(noteId);

        if (note == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(note);
    }

    @PutMapping("/notes/{id}")
    public ResponseEntity<String> updateNote(@PathVariable(value = "id") Long noteId, @Valid @RequestBody Note noteDetails) {
        Note note = noteRepository.findOne(noteId);

        if (note == null) {
            return ResponseEntity.notFound().build();
        }

        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());

        Note upgradedNote = noteRepository.save(note);
        ResponseEntity.ok(upgradedNote);

        return ResponseEntity.ok().body("Registro atualizado com sucesso!");
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<Note> deleteNote(@PathVariable(value = "id") Long noteId) {
        Note note = noteRepository.findOne(noteId);

        if (note == null) {
            return ResponseEntity.notFound().build();
        }

        noteRepository.delete(note);

        return ResponseEntity.ok().build();
    }
}
