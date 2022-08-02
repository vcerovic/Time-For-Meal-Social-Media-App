import React, { useState, useEffect, useRef } from 'react'
import { useCookies } from 'react-cookie';
import { useParams, useNavigate } from 'react-router-dom'
import { registerUser, validateUser } from '../../api/AuthApi';
import { getUserById, getUserImage, updateUser, getUserImageFile } from '../../api/UserApi';
import { blobToFile } from '../../utils/FileUtils';

const RegistrationPage = () => {
    const [user, setUser] = useState({});
    const [userImage, setUserImage] = useState();
    const [cookies, setCookie, removeCookie] = useCookies();
    const [isLogged, setIsLogged] = useState(false);
    const [hasLoaded, setHasLoaded] = useState(false);

    const usernameRef = useRef();
    const emailRef = useRef();
    const imageRef = useRef();
    const passwordRef = useRef();

    let params = useParams();
    const navigate = useNavigate();


    const handleSubmit = e => {
        e.preventDefault();
        registerUser(
            usernameRef.current.value,
            emailRef.current.value,
            passwordRef.current.value
        );
    }

    const handleUpdateUser = e => {
        e.preventDefault();

        const formData = new FormData();
        formData.append('username', usernameRef.current.value);
        formData.append('email', emailRef.current.value);

        getUserImageFile(user.id)
            .then(image => {
                imageRef.current.files[0]
                    ? formData.append('image', imageRef.current.files[0])
                    : formData.append('image', blobToFile(image, user.image));

                updateUser(user.id, formData, cookies)
                    .then(success => {
                        if (success) {
                            removeCookie('JWT', { path: '/', sameSite: 'none', secure: 'None' });
                            navigate('/login');
                        }
                    })
            })


    }

    useEffect(() => {
        validateUser(cookies)
            .then(isValid => {
                setIsLogged(isValid)
                if (isValid) {
                    getUserById(params.userId)
                        .then(data => {
                            setUser(data)
                            getUserImage(params.userId)
                                .then(image => setUserImage(image))
                        })
                }
            })
            .finally(setHasLoaded(true));
    }, []);

    if (!hasLoaded) return (
        <div id="preloader">
            <div id="loader"></div>
        </div>
    )
    else {
        if (isLogged) return (
            <div id='formPage'>
                <div className='form-container'>
                    <h1 className='title'>Edit {user.username}</h1>
                    <form onSubmit={handleUpdateUser}>
                        <div className='field'>
                            <input
                                id="username"
                                type="text"
                                defaultValue={user.username}
                                ref={usernameRef}
                            />
                            <label htmlFor="username">Username</label>
                        </div>
                        <div className='field'>
                            <input
                                id="email"
                                type="email"
                                defaultValue={user.email}
                                ref={emailRef}
                            />
                            <label htmlFor="email">Email</label>
                        </div>

                        <div className='field image-field'>
                            <input type="file" ref={imageRef} id="image-file" placeholder=' ' accept="image/png, image/jpeg" className="input_file" />
                            <label htmlFor="image-file">Change {user.image} image</label>
                        </div>
                        <button className='linkBtn' type="submit">Submit</button>
                    </form>
                </div>
            </div>
        )
        else
            return (
                <div id='formPage'>
                    <div className='form-container'>
                        <h1 className='title'>Register</h1>
                        <form onSubmit={handleSubmit}>
                            <div className='field'>
                                <input
                                    id="username"
                                    type="text"
                                    placeholder=' '
                                    ref={usernameRef}
                                />
                                <label htmlFor="username">Username</label>
                            </div>
                            <div className='field'>
                                <input
                                    id="email"
                                    type="email"
                                    placeholder=' '
                                    ref={emailRef}
                                />
                                <label htmlFor="email">Email</label>
                            </div>
                            <div className='field'>
                                <input
                                    id="password"
                                    type="password"
                                    placeholder=' '
                                    ref={passwordRef}
                                />
                                <label htmlFor="password">Password</label>
                            </div>
                            <br></br>
                            <button className='linkBtn' type="submit">Submit</button>
                        </form>
                    </div>
                </div>

            )
    }
}

export default RegistrationPage