import React from "react";
import TagChip from "./TagChip.jsx";

const NoteCard = ({ note, onDelete }) => {
  return (
    <div className="note-card">
      <div className="note-header">
        <h3>{note.title}</h3>
        <button className="delete-btn" onClick={onDelete}>
          🗑️
        </button>
      </div>
      <p className="note-content">{note.content}</p>
      <div className="tags">
        {note.tags?.map((tag) => (
          <TagChip key={tag} name={tag} />
        ))}
      </div>
    </div>
  );
};

export default NoteCard;
