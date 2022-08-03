import { setError, setSuccess} from './ValidationFeedback.js'

const isValidEmail = email => {
    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}


export const validateUserRegistration = fields => {
    let isValid = true;
    let nameFld = fields.usernameRef.current;
    let emailFld = fields.emailRef.current;
    let passwordFld = fields.passwordRef.current;

    if (nameFld.value === '') {
        setError(nameFld, 'Username is required.');
        isValid = false;
    } else if (nameFld.value.length > 15 || nameFld.value.length < 4) {
        setError(nameFld, 'Username must be between 4 and 15 characters');
        isValid = false;
    } else {
        setSuccess(nameFld);
    }

    if (emailFld.value === '') {
        setError(emailFld, 'Email is required');
        isValid = false;
    } else if (emailFld.value.length > 60 ) {
        setError(emailFld, 'Email can only be 60 characters long.');
        isValid = false;
    } else if (!isValidEmail(emailFld.value)) {
        setError(emailFld, 'Provide a valid email address');
        isValid = false;
    } else {
        setSuccess(emailFld);
    }

    if (passwordFld.value === '') {
        setError(passwordFld, 'Password is required.');
        isValid = false;
    } else if (passwordFld.value.length > 25 || passwordFld.value.length < 6) {
        setError(passwordFld, 'Pasword must be between 6 and 25 characters');
        isValid = false;
    } else {
        setSuccess(passwordFld);
    }

    return isValid;
}

export const validateUserEdit = fields => {
    let isValid = true;
    let nameFld = fields.usernameRef.current;
    let emailFld = fields.emailRef.current;

    if (nameFld.value === '') {
        setError(nameFld, 'Username is required.');
        isValid = false;
    } else if (nameFld.value.length > 15 || nameFld.value.length < 4) {
        setError(nameFld, 'Username must be between 4 and 15 characters');
        isValid = false;
    } else {
        setSuccess(nameFld);
    }

    if (emailFld.value === '') {
        setError(emailFld, 'Email is required');
        isValid = false;
    } else if (emailFld.value.length > 60 ) {
        setError(emailFld, 'Email can only be 60 characters long.');
        isValid = false;
    } else if (!isValidEmail(emailFld.value)) {
        setError(emailFld, 'Provide a valid email address');
        isValid = false;
    } else {
        setSuccess(emailFld);
    }

    return isValid;
}

export const validateUserLogin = fields => {
    let isValid = true;
    let nameFld = fields.usernameRef.current;
    let passwordFld = fields.passwordRef.current;

    if (nameFld.value === '') {
        setError(nameFld, 'Username is required.');
        isValid = false;
    } else if (nameFld.value.length > 15 || nameFld.value.length < 4) {
        setError(nameFld, 'Username must be between 4 and 15 characters');
        isValid = false;
    } else {
        setSuccess(nameFld);
    }

    if (passwordFld.value === '') {
        setError(passwordFld, 'Password is required.');
        isValid = false;
    } else if (passwordFld.value.length > 25 || passwordFld.value.length < 6) {
        setError(passwordFld, 'Pasword must be between 6 and 25 characters');
        isValid = false;
    } else {
        setSuccess(passwordFld);
    }

    return isValid;
}

export const validateChangePassword = fields => {
    let isValid = true;
    let emailFld = fields.emailRef.current;
    let oldPasswordFld = fields.oldPasswordRef.current;
    let newPasswordFld = fields.newPasswordRef.current;


    if (emailFld.value === '') {
        setError(emailFld, 'Email is required');
        isValid = false;
    } else if (emailFld.value.length > 60 ) {
        setError(emailFld, 'Email can only be 60 characters long.');
        isValid = false;
    } else if (!isValidEmail(emailFld.value)) {
        setError(emailFld, 'Provide a valid email address');
        isValid = false;
    } else {
        setSuccess(emailFld);
    }
    

    if (oldPasswordFld.value === '') {
        setError(oldPasswordFld, 'Password is required.');
        isValid = false;
    } else if (oldPasswordFld.value.length > 25 || oldPasswordFld.value.length < 6) {
        setError(oldPasswordFld, 'Pasword must be between 6 and 25 characters');
        isValid = false;
    } else {
        setSuccess(oldPasswordFld);
    }

    if (newPasswordFld.value === '') {
        setError(newPasswordFld, 'Password is required.');
        isValid = false;
    } else if (newPasswordFld.value.length > 25 || newPasswordFld.value.length < 6) {
        setError(newPasswordFld, 'Pasword must be between 6 and 25 characters');
        isValid = false;
    } else {
        setSuccess(newPasswordFld);
    }

    return isValid;
}

export const validateRecipe = fields => {
    let isValid = true;
    let nameFld = fields.nameRef.current;
    let prepTimeFld = fields.prepTimeRef.current;
    let cookTimeFld = fields.cookTimeRef.current;
    let servingFld = fields.servingRef.current;
    let image = fields.imageRef.current;
    let instructionFld = fields.instructionRef.current;
    let ingredientsFld = fields.ingredientsRef.current;
    let selectedIngredients = fields.selectedIngredients;

    
    if (nameFld.value === '') {
        setError(nameFld, 'Name is required.');
        isValid = false;
    } else if (nameFld.value.length > 50 || nameFld.value.length < 4) {
        setError(nameFld, 'Name must be between 4 and 50 characters');
        isValid = false;
    } else {
        setSuccess(nameFld);
    }

    if (prepTimeFld.value === '') {
        setError(prepTimeFld, 'Preparation time is required');
        isValid = false;
    } else if(isNaN(prepTimeFld.value)){
        setError(prepTimeFld, 'Please enter a number');
        isValid = false;
    } else if (prepTimeFld.value < 1) {
        setError(prepTimeFld, 'Prep time should be at least 1 minute');
        isValid = false;
    } else {
        setSuccess(prepTimeFld);
    }

    if (cookTimeFld.value === '') {
        setError(cookTimeFld, 'Cook time is required');
        isValid = false;
    } else if(isNaN(cookTimeFld.value)){
        setError(cookTimeFld, 'Please enter a number');
        isValid = false;
    } else if (cookTimeFld.value < 1) {
        setError(cookTimeFld, 'Cook time should be at least 1 minute');
        isValid = false;
    } else {
        setSuccess(cookTimeFld);
    }

    
    if (servingFld.value === '') {
        setError(servingFld, 'Serving is required');
        isValid = false;
    } else if(isNaN(servingFld.value)){
        setError(servingFld, 'Please enter a number');
        isValid = false;
    } else if (servingFld.value < 1) {
        setError(servingFld, 'Serving should be at least for 1 person');
        isValid = false;
    } else {
        setSuccess(servingFld);
    }

    if (!image.files[0] && !fields.isEditForm) {
        setError(image, 'Image is required');
        isValid = false;
    } else {
        setSuccess(image);
    }


    if (instructionFld.value === '') {
        setError(instructionFld, 'Instructions are required.');
        isValid = false;
    } else if (instructionFld.value.length > 20000 || instructionFld.value.length < 50) {
        setError(instructionFld, 'Instructions must be between 20 and 20000 characters');
        isValid = false;
    } else {
        setSuccess(instructionFld);
    }


    if(selectedIngredients.length < 1){
        setError(ingredientsFld, 'You must select at least 1 ingredient.');
        isValid = false;
    } else {
        setSuccess(ingredientsFld);
    }


    return isValid;
}