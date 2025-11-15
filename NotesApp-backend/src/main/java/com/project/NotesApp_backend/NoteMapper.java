package com.project.NotesApp_backend;

import com.project.NotesApp_backend.DTO.NoteDTO;
import com.project.NotesApp_backend.Entity.Note;
import com.project.NotesApp_backend.Entity.Tag;

import java.util.List;
import java.util.stream.Collectors;

public class NoteMapper {

    public static NoteDTO toDTO(Note note) {
        if (note == null) return null;
        NoteDTO dto = new NoteDTO();
        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setContent(note.getContent());
        dto.setCreatedAt(note.getCreatedAt());
        dto.setUpdatedAt(note.getUpdatedAt());
        dto.setTags(note.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.toList()));
        return dto;
    }

    public static void updateEntityFromDto(Note note, NoteDTO dto) {
        if (dto.getTitle() != null) note.setTitle(dto.getTitle());
        if (dto.getContent() != null) note.setContent(dto.getContent());
        // tags handled in service (need Tag entities)
    }
}
