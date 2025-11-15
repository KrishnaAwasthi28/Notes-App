import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar.jsx";
import HomePage from "./pages/HomePage.jsx";
import AllNotesPage from "./pages/AllNotesPage.jsx";
import "./App.css";

const App = () => {
  const [darkMode, setDarkMode] = useState(false);

  return (
    <Router>
      <div className={darkMode ? "app dark" : "app"}>
        <Navbar
          darkMode={darkMode}
          toggleDarkMode={() => setDarkMode((d) => !d)}
        />

        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/all-notes" element={<AllNotesPage />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;
