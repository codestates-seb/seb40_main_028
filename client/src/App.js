import { BrowserRouter } from 'react-router-dom';
import React from 'react';
import Router from './routes/Routes';

function App() {
  return (
    <div className="w-full max-w-lg mx-auto bg-d-lighter">
      <BrowserRouter>
        <Router />
      </BrowserRouter>
    </div>
  );
}

export default App;
