import React, { useRef } from 'react'
import {useLocation} from "react-router-dom";
import { resetPassword } from '../../api/AuthApi';
import { validatePassword } from '../../utils/ValidationUtils';

const ResetPasswordPage = () => {
    const passwordRef = useRef();
    const search = useLocation().search;  
    const token = new URLSearchParams(search).get('token');
   
    
    const handleSubmit = event => {
        event.preventDefault();

        if(validatePassword(passwordRef.current)){
            resetPassword(token, passwordRef.current.value);
        }
       
    }

    
    if(!token) return <div></div>
    else return(
        <div id='formPage'>
        <div className='form-container'>
            <h1 className='title'>Reset password</h1>
            <form onSubmit={handleSubmit}>       
                <div className='field'>
                    <input
                        id="password"
                        type="password"
                        ref={passwordRef}
                        placeholder=" "
                    />
                    <label htmlFor="password">New Password</label>
                    <div className="error"></div>
                </div>
                <button className='linkBtn' type="submit">Submit</button>
            </form>
        </div>
    </div>
    )
}

export default ResetPasswordPage