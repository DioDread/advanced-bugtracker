window.onload = function () {
    var loginForm = select('.login-form-cont'), regForm = select('.register-form-cont'), hide = 'hide',
            usernameInput = loginForm.select('input[name=username]'), passwordInput = loginForm.select('input[name=password]'),
            signInBtn = select('#sign-in'), signUpBtn = select('#sign-up'),
            regBtn = select('#registrate'), regCancelBtn = select('#reg-cancel');

    signInBtn.addEventListener('click', getLogin);
    signUpBtn.addEventListener('click', showRegistrationForm);
    regCancelBtn.addEventListener('click', showLoginForm);
    loginForm.addEventListener('keydown', function(evt) {
        if(evt.keyCode == '13') {
            getLogin();
        }
    });

    if (usernameInput.value.length > 0) {
        cleanUpAutofill();
    }

    function getLogin() {
        var getLoginUrl = document.location.href + '?username=' + usernameInput.value + '&password=' + passwordInput.value,
                ajax = new Ajax('get', getLoginUrl, {'Accept': 'application/json;'});
        
        ajax.success = function(response) {
            if (response.success) {
                document.location.replace(document.location.href + 'main');
            }
        };
        
        ajax.failure = function(err) {
            console.log(err);
        };
        
        ajax.call();
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
        usernameInput.value = '';
        passwordInput.value = '';
    }
};