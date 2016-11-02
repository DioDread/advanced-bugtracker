window.onload = function () {
    var loginForm = select('.login-form-cont'), regForm = select('.register-form-cont'), hide = 'hide',
            usernameEl = loginForm.select('input[name=username]'), passwordEl = loginForm.select('input[name=password]'),
            signInBtn = select('#sign-in'), signUpBtn = select('#sign-up'), 
            regBtn = select('#registrate'), regCancelBtn = select('#reg-cancel');
    
    signUpBtn.addEventListener('click', showRegistrationForm);
    regCancelBtn.addEventListener('click', showLoginForm);

    if (usernameEl.value.length > 0) {
        cleanUpAutofill();
    }

    function showRegistrationForm() {
        loginForm.classList.add(hide);
        regForm.classList.remove(hide);
    }

    function showLoginForm() {
        loginForm.classList.remove(hide);
        regForm.classList.add(hide);
    }

    function cleanUpAutofill() {
        usernameEl.value = '';
        passwordEl.value = '';
    }
};