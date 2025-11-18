<!-- README.md for NoteSpace (HTML-style content) -->

<h1 align="center">🗂️ NoteSpace</h1>

<p align="center">
  <strong>NoteSpace</strong> — a clean, responsive Notes app with multi-tag support, search, import/export, and dark/light mode.
  Built with <em>React.js (frontend)</em> and <em>Spring Boot + MySQL (backend)</em>.
</p>

<hr/>

<h2>🔎 Project Overview</h2>
<p>
  NoteSpace helps you quickly create, tag, search, and manage notes. Notes can have multiple tags, are searchable by title/content/tags,
  and can be filtered on an <strong>All Notes</strong> page. The app supports dark/light themes and JSON import/export for portability.
</p>

<hr/>

<h2>✨ Key Features</h2>
<ul>
  <li>Create, edit and delete notes</li>
  <li>Assign multiple tags to a note</li>
  <li>Search notes by title, content or tag</li>
  <li>Filter notes by tag on the All Notes page</li>
  <li>Export and import notes as JSON</li>
  <li>Dark / Light mode (theme)</li>
  <li>Responsive layout (desktop & mobile)</li>
</ul>

<hr/>

<h2>🛠️ Tech Stack</h2>
<ul>
  <li><strong>Frontend:</strong> React.js, Axios, plain CSS (no Tailwind)</li>
  <li><strong>Backend:</strong> Spring Boot, Spring Data JPA (Hibernate)</li>
  <li><strong>Database:</strong> MySQL</li>
  <li><strong>Tools:</strong> Maven, Node / npm, Vite or Create React App</li>
</ul>

<hr/>

<h2>🚀 Quick Start — Development Setup</h2>

<h3>1. Backend (Spring Boot)</h3>
<p>Assumes Java 17+, Maven and MySQL are installed.</p>

<pre><code>
# create database (in MySQL)
CREATE DATABASE notesdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
</code></pre>

<p>Configure <code>src/main/resources/application.properties</code>:</p>
<pre><code>
spring.datasource.url=jdbc:mysql://localhost:3306/notesdb?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=YOUR_DB_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8080
</code></pre>

<p>Run backend:</p>
<pre><code>
mvn clean spring-boot:run
# or build: mvn clean package && java -jar target/your-app.jar
</code></pre>

<h3>2. Frontend (React)</h3>

<pre><code>
# from project root (or frontend folder)
npx create-react-app notes-frontend    # or use vite
cd notes-frontend
npm install axios react-router-dom
# copy the src/ components & styles from the project into this folder
npm start
</code></pre>

<p>Open: <code>http://localhost:3000</code> (CRA) or Vite dev URL.</p>

<hr/>

<h2>📡 Backend API Endpoints</h2>
<ul>
  <li><code>GET /api/notes</code> — list/search notes (query params: <code>search</code>, <code>tags</code> comma-separated, <code>matchAllTags</code>, <code>page</code>, <code>size</code>)</li>
  <li><code>GET /api/notes/{id}</code> — get single note</li>
  <li><code>POST /api/notes</code> — create note (body: JSON with <code>title</code>, <code>content</code>, <code>tags</code> array)</li>
  <li><code>PUT /api/notes/{id}</code> — update note</li>
  <li><code>DELETE /api/notes/{id}</code> — delete note</li>
  <li><code>GET /api/notes/export</code> — export all notes as JSON</li>
  <li><code>POST /api/notes/import</code> — import notes (body: JSON array)</li>
  <li><code>GET /api/tags</code> — list all tags (strings)</li>
</ul>

<hr/>

<h2>📥 Example Requests</h2>

<p>Create a note (curl):</p>
<pre><code>
curl -X POST http://localhost:8080/api/notes \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Project Presentation Prep",
    "content": "Finalize slides and practice the group presentation for tomorrow.",
    "tags": ["work","presentation","urgent"]
  }'
</code></pre>

<p>Search notes by tag:</p>
<pre><code>
GET http://localhost:8080/api/notes?tags=work
</code></pre>

<p>Export notes:</p>
<pre><code>
GET http://localhost:8080/api/notes/export
</code></pre>

<hr/>

<h2>🧪 Dummy Data (for demo)</h2>
<pre><code>
1) Title: Project Presentation Prep
   Content: Finalize slides and practice the group presentation for tomorrow.
   Tags: work, presentation, urgent

2) Title: Weekend To-Do List
   Content: - Buy groceries
            - Clean the room
            - Watch "The Social Network"
            - Plan gym routine
   Tags: personal, weekend, myday

3) Title: Java Microservices Learning Plan
   Content: 1) Understand Spring Boot and REST APIs
            2) Learn Docker + Docker Compose basics
            3) Implement service registry using Eureka
   Tags: learning, java, microservices
</code></pre>

<hr/>

<h2>🎨 Customization</h2>
<ul>
  <li>Change theme colors: edit CSS variables in <code>src/styles.css</code>.</li>
  <li>Switch DB from MySQL to H2 for quick local testing (change <code>application.properties</code>).</li>
  <li>Add authentication (JWT) if you want per-user notes.</li>
</ul>

<hr/>

<h2>📁 Project Structure (recommended)</h2>
<pre><code>
backend/
  src/main/java/... (Spring Boot app)
  src/main/resources/application.properties

frontend/
  src/
    api/api.js
    components/*.jsx
    pages/*.jsx
    utils/*.js
    styles.css
</code></pre>

<hr/>

<h2>📬 Contact & License</h2>
<p>
  Built by <strong>Krishna Awasthi</strong>.  
  Want improvements or help integrating auth, deployment (Heroku/Render/Vercel), or tests? Reach out!
</p>

<hr/>

<p align="center">
  — Happy coding! ✨
</p>
