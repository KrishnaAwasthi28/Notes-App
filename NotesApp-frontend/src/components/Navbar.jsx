import React from "react";
import { Link, useLocation } from "react-router-dom";

const Navbar = ({ darkMode, toggleDarkMode }) => {
  const location = useLocation();

  return (
    <nav className="navbar">
      <div className="nav-left">
        <h2 className="logo">📝 NoteSpace</h2>
        <Link
          to="/"
          className={location.pathname === "/" ? "active-link" : ""}
          id="td-none"
        >
          Home
        </Link>
        <Link
          to="/all-notes"
          className={location.pathname === "/all-notes" ? "active-link" : ""}
          id="td-none"
        >
          All Notes
        </Link>
      </div>

      <div className="nav-right">
        <button onClick={toggleDarkMode}>
          {darkMode ? "☀️ Light" : "🌙 Dark"}
        </button>
      </div>
    </nav>
  );
};

export default Navbar;
