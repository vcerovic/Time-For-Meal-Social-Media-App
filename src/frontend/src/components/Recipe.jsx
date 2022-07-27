import React from 'react'

const Recipe = ({recipe}) => {
  return (
    <div>
        <h1>{recipe.name}</h1>
        <p>{recipe.serving}</p>
    </div>
  )
}

export default Recipe