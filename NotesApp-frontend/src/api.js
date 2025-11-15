import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080/api", // backend URL
});

// Notes endpoints
export const fetchNotes = (params) => api.get("/notes", { params });
export const createNote = (note) => api.post("/notes", note);
export const updateNote = (id, note) => api.put(`/notes/${id}`, note);
export const deleteNote = (id) => api.delete(`/notes/${id}`);
export const fetchTags = () => api.get("/tags");
export const exportNotes = () => api.get("/notes/export");
export const importNotes = (notes) => api.post("/notes/import", notes);

export default api;
