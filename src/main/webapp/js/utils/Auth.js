export default class Auth {
     // setup the class and hide the body by default
    constructor() {
        document.querySelector("body").style.display = "none";
        const auth = localStorage.getItem("authid");
        this.validateAuth(auth);
    }
    // check to see if the localStorage item passed to the function is valid and set
    validateAuth(auth) {
        if (auth != 1) {
            window.location.replace("/");
        } else {
            if (window.location.pathname === '/pages/login.html') {
                window.location.replace("/pages/dashboard.html");
            }
            document.querySelector("body").style.display = "block";
        }
    }
    // will remove the localStorage item and redirect to login  screen
    logOut() {
        localStorage.removeItem("authid");
        window.location.replace("/");
    }
}