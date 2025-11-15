import React, { useEffect, useState } from "react";
import { fetchNotes, fetchTags, deleteNote } from "../api.js";
import NoteList from "../components/NoteList.jsx";

const AllNotesPage = () => {
  const [notes, setNotes] = useState([]);
  const [tags, setTags] = useState([]);
  const [selectedTag, setSelectedTag] = useState(null);

  // Fetch notes (optionally filtered by tag)
  const loadNotes = async (tag) => {
    const params = tag ? { tags: tag, page: 0, size: 100 } : { page: 0, size: 100 };
    const res = await fetchNotes(params);
    setNotes(res.data.content || res.data);
  };

  // Fetch all available tags
  const loadTags = async () => {
    const res = await fetchTags();
    setTags(res.data);
  };

  // Handle tag click
  const handleTagClick = (tag) => {
    if (selectedTag === tag) {
      // clicking same tag removes filter
      setSelectedTag(null);
      loadNotes();
    } else {
      setSelectedTag(tag);
      loadNotes(tag);
    }
  };

  // Delete note and refresh filtered notes
  const handleDelete = async (id) => {
    await deleteNote(id);
    loadNotes(selectedTag);
  };

  useEffect(() => {
    loadTags();
    loadNotes();
  }, []);

  return (
    <div className="page">
      <h2>📚 All Notes</h2>

      <div className="tag-filter-bar">
        <span className="filter-label">Filter by tag:</span>
        <div className="tag-list">
          {tags.map((tag) => (
            <button
              key={tag}
              className={`tag-filter-btn ${selectedTag === tag ? "active-tag" : ""}`}
              onClick={() => handleTagClick(tag)}
            >
              #{tag}
            </button>
          ))}
          {selectedTag && (
            <button className="clear-filter-btn" onClick={() => handleTagClick(selectedTag)}>
              ❌ Clear Filter
            </button>
          )}
        </div>
      </div>

      <NoteList notes={notes} onDelete={handleDelete} />
    </div>
  );
};

export default AllNotesPage;
