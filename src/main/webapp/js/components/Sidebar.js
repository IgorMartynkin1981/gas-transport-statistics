export default class Sidebar {
    constructor( { 
        title = 'Главная',
        mainIco = 'icon-delivery-fast',
        color = 'green',   //blue | green | orange | red 
        items = [...
            {
                titleI,
                nameI, 
                icoI,
                hrefI, 
                subItems: [...
                    {   
                        titleS,
                        icoS,
                        hrefS
                    }
                ]
            }
        ]
    } = {} ) {
        
        this.title = title;
        this.items = items;
        this.mainIco = mainIco;
        this.color = color;

        this.renderMenu();
        this.renderButton();
    }

    getMenuSubItem(item) {
        return `
            <li>
                <a href="${item.hrefS}">
                    <span class="sidebar-mini-icon"><i class="tim-icons ${item.icoS}"></i></span>
                    <span class="sidebar-normal"> ${item.titleS} </span>
                </a>
            </li>
        `;
    }
    
    getMenuItem(item) {
        const params = {
            href: '',          
            arrowDropDown: '',
            subItemsHTML: '',
            liClass: '',
            toggle: ''
        };

        if (item.subItems) {    
            params.href = '#'+item.nameI;            
            params.toggle = 'data-toggle="collapse"';
            params.arrowDropDown = '<b class="caret"></b>';
            params.subItemsHTML = 
                `<div class="collapse" id="${item.nameI}">
                <ul class="nav">`
                + item.subItems.map(subItem => this.getMenuSubItem(subItem)).join('') +                
                `</ul>
                </div>`;
        } else {
            params.href = item.hrefI;
            params.liClass = 'class="active"';
        }

        return `
        <li ${params.liClass}>
            <a ${params.toggle} href="${params.href}">
            <i class="tim-icons ${item.icoI}"></i>
            <p> ${item.titleI}
                ${params.arrowDropDown}
            </p>
            </a>
            ${params.subItemsHTML}
        </li>
        `;
    }

    getTemplate() {
        const itemsHTML = this.items.map(item => this.getMenuItem(item)).join('');

        return `
        <div class="sidebar" data="${this.color}">            
            <div class="sidebar-wrapper">
                <div class="logo">
                <a href="javascript:void(0)" class="simple-text logo-mini">
                    <i class="tim-icons ${this.mainIco}"></i>
                </a>
                    <a href="javascript:void(0)" class="simple-text logo-normal">
                        ${this.title}
                    </a>
                </div>
                <ul class="nav">
                    ${itemsHTML}                    
                </ul>
            </div>
        </div>    
        `;
    }

    renderMenu() {
        const menu = document.createElement("div"); 
        menu.innerHTML = this.getTemplate();
        this.menu = menu.firstElementChild;
    }

    renderButton() {
        const button = document.createElement("div"); 
        button.innerHTML = `
            <div class="navbar-minimize-fixed">
            <button class="minimize-sidebar btn btn-link btn-just-icon">
                <i class="tim-icons icon-align-center visible-on-sidebar-regular text-muted"></i>
                <i class="tim-icons icon-bullet-list-67 visible-on-sidebar-mini text-muted"></i>
            </button>
            </div>`;
        this.button = button.firstElementChild; 
    }

    remove() {
        this.element.remove();
    }

    destroy() {
        this.remove();
    }


}