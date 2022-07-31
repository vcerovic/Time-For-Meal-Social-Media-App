import React, { useEffect } from 'react'
import { Link } from 'react-router-dom'
import { removeChar } from '../utils/StringUtils'

const Comment = ({ comment }) => {

    useEffect(() => {
        
    }, [])
    return (
        <div className='comment'>
            <div>
                <h3><Link to={`/users/${comment.appUser.id}`}>{comment.appUser.username}</Link></h3>
                <p>{comment.comment}</p>
            </div>
            <p>{removeChar(comment.createdAt, "T")}</p>
        </div>
    )
}

export default Comment