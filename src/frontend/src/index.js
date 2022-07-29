import React from 'react'
import ReactDOM from "react-dom/client";

import { BrowserRouter, Routes, Route } from "react-router-dom";
import { CookiesProvider } from "react-cookie";
import styles from './assets/styles/style.css'


import App from './App.js';
import LoginPage from './pages/auth/LoginPage.jsx';
import RegistrationPage from './pages/auth/RegistrationPage.jsx';

import RecipesPage from './pages/recipe/RecipesPage';
import SingleRecipePage from './pages/recipe/SingleRecipePage.jsx';
import UserPage from './pages/user/UserPage.jsx';
import NewRecipePage from './pages/recipe/NewRecipePage.jsx';
import Navbar from './components/Navbar';

const root = ReactDOM.createRoot(
    document.getElementById("root")
);

root.render(
    <CookiesProvider>
        <BrowserRouter>
            <Navbar />
            <Routes>
                <Route path="/" element={<App />} />
                <Route path='register' element={<RegistrationPage />} />
                <Route path='login' element={<LoginPage />} />
                <Route path="recipes" element={<RecipesPage />} />
                <Route path="recipes/new" element={<NewRecipePage />} />
                <Route path="recipes/:recipeId" element={<SingleRecipePage />} />
                <Route path="users/:userId" element={<UserPage />} />
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
    </CookiesProvider>

);
