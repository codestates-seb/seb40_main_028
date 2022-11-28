import React from "react";
import { BrowserRouter } from "react-router-dom";
import Router from "./routes/Routes";

function App() {
  return (
    <div className="w-full h-screen max-w-lg mx-auto bg-d-lighter overflow-y-scroll">
      <BrowserRouter>
        <Router />
      </BrowserRouter>
    </div>
  );
}

export default App;
