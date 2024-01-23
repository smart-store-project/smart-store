function togglePasswordVisibility() {
    let openEyeIcon = document.querySelector('.login-container-content-item-2-form-body-item-1-inputPassword-openEyeIcon');
    let passwordInput = document.getElementById('passwordInput');
    let passwordType = passwordInput.type;
    if (passwordType === "password") {
        passwordInput.type = "text";
        openEyeIcon.style.display = "block";
    } else {
        passwordInput.type = "password";
        openEyeIcon.style.display = "none";
    }
}

