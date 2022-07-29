import React from 'react'
import logo from './assets/images/time2meal_logo.png'
import { Link } from 'react-router-dom';

const App = () => {
  return (
    <div id='home'>
      <img className='logo' src={logo} alt="Time for meal logo" />
      <div>
        <p>Welcome to</p>
        <h1>Time for Meal</h1>
        <p>Social media app for food lovers. You can share your recipes and interact with other users.</p>
        <Link className='linkBtn'  to={'/recipes'}>View all recipes</Link>
      </div>
    </div>
  )
}

export default App