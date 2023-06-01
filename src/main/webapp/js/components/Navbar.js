export default class Navbar {
    constructor( { 
        title = 'Заголовок', 
        userName = 'none'       
    } = {} ) {

        this.title = title;
        this.userName = userName;

        this.render();
    }

    getTemplate() {
        return `
        <nav class="navbar navbar-expand-lg navbar-absolute navbar-transparent">
            <div class="container-fluid">

                <div class="navbar-wrapper">                
                    <div class="navbar-toggle d-inline">
                    <button type="button" class="navbar-toggler">
                        <span class="navbar-toggler-bar bar1"></span>
                        <span class="navbar-toggler-bar bar2"></span>
                        <span class="navbar-toggler-bar bar3"></span>
                    </button>
                    </div>
                    <a class="navbar-brand" href="javascript:void(0)">${this.title}</a>
                </div>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navigation" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-bar navbar-kebab"></span>
                    <span class="navbar-toggler-bar navbar-kebab"></span>
                    <span class="navbar-toggler-bar navbar-kebab"></span>
                </button>

            <div class="collapse navbar-collapse" id="navigation">
                <ul class="navbar-nav ml-auto">

                <li class="dropdown nav-item">
                    <a href="#" class="dropdown-toggle nav-link" data-toggle="dropdown">
                    <div class="photo">
                        <img src="/img/users/${this.userName}.jpg" alt="Profile Photo">
                    </div>
                    <b class="caret d-none d-lg-block d-xl-block"></b>                  
                    </a>
                    <ul class="dropdown-menu dropdown-navbar">
                    <li class="nav-link">
                        <a href="javascript:void(0)" class="nav-item dropdown-item">Сменить пароль</a>
                    </li>
                    <li class="nav-link">
                        <a href="javascript:void(0)" class="nav-item dropdown-item logout">Выход</a>
                    </li>
                    </ul>
                </li>
                <li class="separator d-lg-none"></li>
                </ul>
            </div>
            </div>
        </nav>   
        `;
    }

    render() {
        const element = document.createElement("div"); 
        element.innerHTML = this.getTemplate();
        this.element = element.firstElementChild;
    }

    remove() {
        this.element.remove();
    }

    destroy() {
        this.remove();
    }


}