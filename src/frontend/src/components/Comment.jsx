import React, { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { useCookies } from 'react-cookie';
import { removeChar } from '../utils/StringUtils'
import { deleteComment } from '../api/RecipeApi'
import { validateUser } from '../api/AuthApi';
import { getUserByUsername } from '../api/UserApi';
import jwt_decode from "jwt-decode";
import Swal from "sweetalert2";

const Comment = ({updateRecipe, comment }) => {
    const [cookies, setCookie, removeCookie] = useCookies();
    const [hasLoaded, setHasLoaded] = useState(false);
    const [user, setUser] = useState();
    let params = useParams();

    const handleDeleteComment = () => {
        Swal.fire({
            title: 'Are you sure you want to delete this comment?',
            showCancelButton: true,
            confirmButtonText: 'Delete',
            confirmButtonColor: '#d61717',
        }).then((result) => {
            if (result.isConfirmed) {
                deleteComment(params.recipeId, comment.id, cookies.JWT)
                .then(() => updateRecipe())
            } else {
                Swal.fire('Okay!', '', 'info')
            }
        })
    }

    useEffect(() => {
        validateUser(cookies)
            .then(isValid => {
                if (isValid) {
                    let decoded = jwt_decode(cookies.JWT);
                    getUserByUsername(decoded.sub)
                        .then(data => {
                            setUser(data)
                        })
                        .finally(setHasLoaded(true));
                } else {
                    setHasLoaded(true);
                }
            })

    }, [])

    if (!hasLoaded) {
        return (
            <div id="preloader">
                <div id="loader"></div>
            </div>
        )
    } else {
        return (
            <div className='comment'>
                <div>
                    <h3><Link to={`/users/${comment.appUser.id}`}>{comment.appUser.username}</Link></h3>
                    <p>{comment.comment}</p>
                </div>
                <div className='actions'>
                    <p>{removeChar(comment.createdAt, "T")}</p>
                    {user != null ? user.id === comment.appUser.id ? <button className='deletBtn'
                        onClick={handleDeleteComment}><i className="fa-solid fa-trash"></i></button> : null : null}

                </div>

            </div>
        )
    }
}

export default Comment