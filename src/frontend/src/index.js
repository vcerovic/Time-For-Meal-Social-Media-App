import React from 'react'
import ReactDOM from "react-dom/client";

import { BrowserRouter, Routes, Route } from "react-router-dom";


import App from './App.js';
import RegistrationPage from './pages/auth/RegistrationPage.jsx';

import RecipesPage from './pages/recipe/RecipesPage';
import SingleRecipePage from './pages/recipe/SingleRecipePage.jsx';

const root = ReactDOM.createRoot(
    document.getElementById("root")
);

root.render(
    <BrowserRouter>
        <Routes>
            <Route path="/" element={<App />} />
            <Route path='register' element={<RegistrationPage />}/>
            <Route path="recipes" element={<RecipesPage />} />
            <Route path="recipes/:recipeId" element={<SingleRecipePage />} />
            <Route
                path="*"
                element={
                    <main style={{ padding: "1rem" }}>
                        <p>There's nothing here!</p>
                    </main>
                }
            />
        </Routes>
    </BrowserRouter>
);
