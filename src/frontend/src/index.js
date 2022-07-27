import React from 'react'
import ReactDOM from "react-dom/client";

import { BrowserRouter, Routes, Route } from "react-router-dom";


import App from './App.js';

import RecipesPage from './pages/recipe/RecipesPage';

const root = ReactDOM.createRoot(
    document.getElementById("root")
);

root.render(
    <BrowserRouter>
        <Routes>
            <Route path="/" element={<App />} />
            <Route path="recipe" element={<RecipesPage />} />
        </Routes>
    </BrowserRouter>
);
