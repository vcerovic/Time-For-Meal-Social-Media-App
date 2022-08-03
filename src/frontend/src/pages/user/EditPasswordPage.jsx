import React, { useState, useEffect, useRef } from 'react'
import { useCookies } from 'react-cookie';
import { useParams, useNavigate } from 'react-router-dom'
import { changePassword, registerUser, validateUser } from '../../api/AuthApi';
import { getUserById, getUserImage, updateUser, getUserImageFile } from '../../api/UserApi';
import { blobToFile } from '../../utils/FileUtils';
import { validateChangePassword } from '../../utils/ValidationUtils';

const EditPasswordPage = () => {
  const [user, setUser] = useState({});
  const [cookies, setCookie, removeCookie] = useCookies();
  const [hasLoaded, setHasLoaded] = useState(false);

  const oldPasswordRef = useRef();
  const emailRef = useRef();
  const newPasswordRef = useRef();

  let params = useParams();
  const navigate = useNavigate();


  const handleSubmit = e => {
    e.preventDefault();

    if(validateChangePassword({emailRef, oldPasswordRef, newPasswordRef})){
      changePassword(
        oldPasswordRef.current.value,
        newPasswordRef.current.value,
        emailRef.current.value,
        cookies
      )
        .then(success => {
          if (success) {
            removeCookie('JWT', { path: '/', sameSite: 'none', secure: 'None' });
            navigate('/login');
          }
        })
    }
  }


  useEffect(() => {
    validateUser(cookies)
      .then(isValid => {
        if (isValid) {
          getUserById(params.userId)
            .then(data => setUser(data))
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
    if (user) return (
      <div id='formPage'>
        <div className='form-container'>
          <h1 className='title'>Edit password</h1>
          <form onSubmit={handleSubmit}>
            <div className='field'>
              <input
                id="email"
                type="email"
                defaultValue={user.email}
                ref={emailRef}
              />
              <label htmlFor="email">Email</label>
              <div className="error"></div>
            </div>
            <div className='field'>
              <input
                id="oldPassword"
                type="password"
                ref={oldPasswordRef}
              />
              <label htmlFor="oldPassword">Old password</label>
              <div className="error"></div>
            </div>
            <div className='field'>
              <input
                id="newPassword"
                type="password"
                ref={newPasswordRef}
              />
              <label htmlFor="newPassword">New password</label>
              <div className="error"></div>
            </div>

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

export default EditPasswordPage