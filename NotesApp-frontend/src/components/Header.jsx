import React from "react";

const Header = ({ darkMode, toggleDarkMode }) => {
  return (
    <header className="header">
      <h1>📝 Notes App</h1>
      <button onClick={toggleDarkMode}>
        {darkMode ? "☀️ Light Mode" : "🌙 Dark Mode"}
      </button>
    </header>
  );
};

export default Header;
