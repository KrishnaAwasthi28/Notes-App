import React from "react";
import { exportNotes } from "../api.js";

const ImportExportButtons = ({ onImport }) => {
  const handleExport = async () => {
    const res = await exportNotes();
    const data = JSON.stringify(res.data, null, 2);
    const blob = new Blob([data], { type: "application/json" });
    const url = URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = "notes-export.json";
    a.click();
  };

  const handleImport = (e) => {
    const file = e.target.files[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = (ev) => {
      const json = JSON.parse(ev.target.result);
      onImport(json);
    };
    reader.readAsText(file);
  };

  return (
    <div className="import-export">
      <button onClick={handleExport}>⬇️ Export Notes</button>
      <label className="import-label">
        ⬆️ Import Notes
        <input
          type="file"
          accept="application/json"
          onChange={handleImport}
          hidden
        />
      </label>
    </div>
  );
};

export default ImportExportButtons;
