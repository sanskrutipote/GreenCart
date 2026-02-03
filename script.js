// Check login status on page load
document.addEventListener('DOMContentLoaded', function () {
    checkLoginStatus();
});

function checkLoginStatus() {
    const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
    const loginBtn = document.getElementById('login');
    const mobileLoginBtn = document.getElementById('mobile_login');

    if (isLoggedIn) {
        if (loginBtn) {
            loginBtn.innerHTML = '<i class="fa fa-fw fa-sign-out"></i>Logout';
            loginBtn.parentElement.setAttribute('href', 'javascript:void(0)');
            loginBtn.onclick = logout;
        }
        if (mobileLoginBtn) {
            mobileLoginBtn.innerHTML = '<i class="fa fa-fw fa-sign-out"></i>Logout';
            mobileLoginBtn.parentElement.setAttribute('href', 'javascript:void(0)');
            mobileLoginBtn.onclick = logout;
        }
    } else {
        if (loginBtn) {
            loginBtn.innerHTML = '<i class="fa fa-fw fa-user"></i>Login';
            loginBtn.parentElement.setAttribute('href', 'login.html');
            loginBtn.onclick = null;
        }
        if (mobileLoginBtn) {
            mobileLoginBtn.innerHTML = '<i class="fa fa-fw fa-user"></i>Login';
            mobileLoginBtn.parentElement.setAttribute('href', 'login.html');
            mobileLoginBtn.onclick = null;
        }
    }
}

function logout() {
    localStorage.removeItem('isLoggedIn');
    localStorage.removeItem('username');
    window.location.href = 'login.html';
}

// Mobile navigation toggle
const bar = document.querySelector(".bar");
const mobile_nav = document.querySelector(".mobile_nav");
const right_bar = document.querySelector(".right-bar");

if (bar) {
    bar.onclick = () => {
        mobile_nav.classList.toggle("mobile_nav_active");
        bar.classList.toggle("bar_active");
    };
}
