import React from 'react'
import { useParams } from 'react-router-dom'
import Recipe from '../../components/Recipe';

const SingleRecipePage = () => {
    let params = useParams();

    return (
        <Recipe recipeId={params.recipeId} />
    )
}

export default SingleRecipePage