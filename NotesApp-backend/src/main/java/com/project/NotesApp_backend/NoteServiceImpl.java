package com.project.NotesApp_backend;

import com.project.NotesApp_backend.DTO.NoteDTO;
import com.project.NotesApp_backend.Entity.Note;
import com.project.NotesApp_backend.Entity.Tag;
import com.project.NotesApp_backend.Repo.NoteRepository;
import com.project.NotesApp_backend.Repo.TagRepository;
import com.project.NotesApp_backend.Service.NoteService;
import com.project.NotesApp_backend.NoteSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final TagRepository tagRepository;
    private final EntityManager em;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository, TagRepository tagRepository, EntityManager em) {
        this.noteRepository = noteRepository;
        this.tagRepository = tagRepository;
        this.em = em;
    }

    @Override
    public Page<NoteDTO> searchNotes(String q, List<String> tags, boolean matchAllTags, Pageable pageable) {
        if (tags == null) tags = List.of();
        // If matchAllTags true -> we use custom query with grouping & HAVING count = n
        if (matchAllTags && !tags.isEmpty()) {
            // Custom CriteriaQuery to enforce "contains all tags"
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Note> cq = cb.createQuery(Note.class);
            Root<Note> root = cq.from(Note.class);
            Join<Note, Tag> joinTags = root.join("tags", JoinType.INNER);

            cq.select(root).distinct(true);
            Predicate tagsPredicate = joinTags.get("name").in(tags.stream().map(String::toLowerCase).collect(Collectors.toList()));
            cq.where(cb.equal(cb.lower(joinTags.get("name")), joinTags.get("name"))); // dummy to use cb

            // We actually need grouping by note id and having count(distinct tag.name) = tags.size()
            cq.where(joinTags.get("name").in(tags.stream().map(String::toLowerCase).collect(Collectors.toList())));
            cq.groupBy(root.get("id"));
            cq.having(cb.equal(cb.countDistinct(joinTags.get("id")), tags.size()));

            // apply text search if present
            if (q != null && !q.isBlank()) {
                String pattern = "%" + q.trim().toLowerCase() + "%";
                Predicate titleLike = cb.like(cb.lower(root.get("title")), pattern);
                Predicate contentLike = cb.like(cb.lower(root.get("content")), pattern);
                cq.where(cb.and(cb.or(titleLike, contentLike),
                        joinTags.get("name").in(tags.stream().map(String::toLowerCase).collect(Collectors.toList()))
                ));
            }

            // NOTE: pagination with CriteriaQuery is a bit manual
            var query = em.createQuery(cq);
            int total = query.getResultList().size();
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
            List<Note> list = query.getResultList();
            List<NoteDTO> dtoList = list.stream().map(NoteMapper::toDTO).collect(Collectors.toList());
            return new PageImpl<>(dtoList, pageable, total);
        } else {
            // match any tag OR no tags: use Specification
            Specification<Note> spec = Specification
                    .where(NoteSpecification.containsTextInTitleOrContent(q))
                    .and(NoteSpecification.hasAnyTag(tags));
            Page<Note> page = noteRepository.findAll(spec, pageable);
            List<NoteDTO> dtoList = page.getContent().stream().map(NoteMapper::toDTO).collect(Collectors.toList());
            return new PageImpl<>(dtoList, pageable, page.getTotalElements());
        }
    }

    @Override
    public NoteDTO getById(Long id) {
        Note n = noteRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Note not found"));
        return NoteMapper.toDTO(n);
    }

    @Override
    public NoteDTO createNote(NoteDTO dto) {
        Note note = new Note();
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        // persist tags (create if not exists)
        Set<Tag> tagEntities = mapTagNamesToEntities(dto.getTags());
        note.setTags(tagEntities);
        Note saved = noteRepository.save(note);
        return NoteMapper.toDTO(saved);
    }

    @Override
    public NoteDTO updateNote(Long id, NoteDTO dto) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Note not found"));
        NoteMapper.updateEntityFromDto(note, dto);
        if (dto.getTags() != null) {
            // replace tags (simple approach)
            Set<Tag> newTags = mapTagNamesToEntities(dto.getTags());
            note.setTags(newTags);
        }
        Note saved = noteRepository.save(note);
        return NoteMapper.toDTO(saved);
    }

    @Override
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public List<NoteDTO> exportAll() {
        List<Note> all = noteRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        return all.stream().map(NoteMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<NoteDTO> importNotes(List<NoteDTO> dtos) {
        List<NoteDTO> created = new ArrayList<>();
        for (NoteDTO dto : dtos) {
            Note note = new Note();
            note.setTitle(dto.getTitle());
            note.setContent(dto.getContent());
            Set<Tag> tagEntities = mapTagNamesToEntities(dto.getTags());
            note.setTags(tagEntities);
            // Option: you may honor dto.id to upsert. Here we always create new entries.
            Note saved = noteRepository.save(note);
            created.add(NoteMapper.toDTO(saved));
        }
        return created;
    }

    // Helper to map list of tag names to Tag entities (create if not exists)
    private Set<Tag> mapTagNamesToEntities(List<String> tagNames) {
        Set<Tag> set = new HashSet<>();
        if (tagNames == null) return set;
        for (String raw : tagNames) {
            if (raw == null) continue;
            String name = raw.trim();
            if (name.isEmpty()) continue;
            // normalize name (lowercase or preserve case? we store original)
            Optional<Tag> existing = tagRepository.findByNameIgnoreCase(name);
            Tag tag = existing.orElseGet(() -> new Tag(name));
            set.add(tag);
        }
        return set;
    }
}
