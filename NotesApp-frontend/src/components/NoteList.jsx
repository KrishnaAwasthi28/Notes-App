import React from "react";
import NoteCard from "./NoteCard.jsx";

const NoteList = ({ notes, onDelete }) => {
  return (
    <div className="note-grid">
      {notes.length === 0 ? (
        <p className="empty">No notes found.</p>
      ) : (
        notes.map((note) => (
          <NoteCard key={note.id} note={note} onDelete={() => onDelete(note.id)} />
        ))
      )}
    </div>
  );
};

export default NoteList;
