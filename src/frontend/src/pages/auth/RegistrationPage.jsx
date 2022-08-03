import React, { useState, useEffect, useRef } from 'react'
import { useCookies } from 'react-cookie';
import { useParams, useNavigate, useLocation } from 'react-router-dom'
import { registerUser, validateUser } from '../../api/AuthApi';
import { getUserById, getUserImage, updateUser, getUserImageFile } from '../../api/UserApi';
import { blobToFile } from '../../utils/FileUtils';
import { validateUserEdit, validateUserRegistration } from '../../utils/ValidationUtils';

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
    const location = useLocation();

    let params = useParams();
    const navigate = useNavigate();


    const handleSubmit = e => {
        e.preventDefault();

        if (validateUserRegistration({ usernameRef, emailRef, passwordRef })) {
            registerUser(
                usernameRef.current.value,
                emailRef.current.value,
                passwordRef.current.value
            );
        }
    }

    const handleUpdateUser = e => {
        e.preventDefault();

        if (validateUserEdit({ usernameRef, emailRef })) {
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
    }

    useEffect(() => {
        console.log(location);
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
        if (location.pathname.endsWith("edit") && isLogged && user) return (
            <div id='formPage'>
                <div className='form-container'>
                    <h1 className='title'>Edit {user.username}</h1>
                    <form onSubmit={handleUpdateUser}>
                        <div className='field'>
                            <input
                                id="username"
                                type="text"
                                defaultValue={user.username}
                                placeholder=' '
                                ref={usernameRef}
                            />
                            <label htmlFor="username">Username</label>
                            <div className="error"></div>
                        </div>
                        <div className='field'>
                            <input
                                id="email"
                                type="text"
                                defaultValue={user.email}
                                placeholder=' '
                                ref={emailRef}
                            />
                            <label htmlFor="email">Email</label>
                            <div className="error"></div>
                        </div>

                        <div className='field image-field'>
                            <input type="file" ref={imageRef} id="image-file" placeholder=' ' accept="image/png, image/jpeg" className="input_file" />
                            <label htmlFor="image-file">Change  image</label>
                            <div className="error"></div>
                        </div>
                        <button className='linkBtn' type="submit">Submit</button>
                    </form>
                </div>
            </div>
        )
        else if (location.pathname.endsWith("/register") && !isLogged)
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
                                <div className="error"></div>
                            </div>
                            <div className='field'>
                                <input
                                    id="email"
                                    type="text"
                                    placeholder=' '
                                    ref={emailRef}
                                />
                                <label htmlFor="email">Email</label>
                                <div className="error"></div>
                            </div>
                            <div className='field'>
                                <input
                                    id="password"
                                    type="password"
                                    placeholder=' '
                                    ref={passwordRef}
                                />
                                <label htmlFor="password">Password</label>
                                <div className="error"></div>
                            </div>
                            <br></br>
                            <button className='linkBtn' type="submit">Submit</button>
                        </form>
                    </div>
                </div>

            )
        else {
            return <div></div>
        }
    }
}

export default RegistrationPage