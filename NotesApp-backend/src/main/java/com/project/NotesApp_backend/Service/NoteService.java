package com.project.NotesApp_backend.Service;
import com.project.NotesApp_backend.DTO.NoteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoteService {
    Page<NoteDTO> searchNotes(String q, List<String> tags, boolean matchAllTags, Pageable pageable);
    NoteDTO getById(Long id);
    NoteDTO createNote(NoteDTO dto);
    NoteDTO updateNote(Long id, NoteDTO dto);
    void deleteNote(Long id);
    List<NoteDTO> exportAll();
    List<NoteDTO> importNotes(List<NoteDTO> dtos);
}