import React, { useState, useEffect } from "react";
import { fetchNotes, createNote, deleteNote, importNotes } from "../api.js";
import SearchBar from "../components/SearchBar.jsx";
import NoteForm from "../components/NoteForm.jsx";
import NoteList from "../components/NoteList.jsx";
import ImportExportButtons from "../components/ImportExportButtons.jsx";

const HomePage = () => {
  const [notes, setNotes] = useState([]);
  const [search, setSearch] = useState("");

  const loadNotes = async () => {
    const params = { search, page: 0, size: 20 };
    const res = await fetchNotes(params);
    setNotes(res.data.content || res.data);
  };

  useEffect(() => {
    loadNotes();
  }, [search]);

  const handleAddNote = async (note) => {
    await createNote(note);
    loadNotes();
  };

  const handleDeleteNote = async (id) => {
    await deleteNote(id);
    loadNotes();
  };

  const handleImport = async (data) => {
    await importNotes(data);
    loadNotes();
  };

  return (
    <div className="page">
      <SearchBar value={search} onChange={setSearch} />
      <NoteForm onAdd={handleAddNote} />
      <ImportExportButtons onImport={handleImport} />
      <NoteList notes={notes} onDelete={handleDeleteNote} />
    </div>
  );
};

export default HomePage;
