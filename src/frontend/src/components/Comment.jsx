import React, { useEffect } from 'react'

const Comment = ({ comment }) => {

    useEffect(() => {
        console.log(comment)
    }, [])
    return (
        <div>Comment</div>
    )
}

export default Comment