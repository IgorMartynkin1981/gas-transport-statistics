import Sidebar from './components/Sidebar.js';
import Navbar from './components/Navbar.js';
import getUrlParams from './utils/get-url-params.js';

const mainColor = 'green';   //blue | green | orange | red 

const sidebar = new Sidebar ( { 
    title: 'УЭТиОП',
    mainIco: 'icon-delivery-fast',
    color: mainColor,
    items: [
        {
            titleI: 'Графики',
            nameI: 'dashboardMenu', 
            icoI: 'icon-chart-pie-36',
            hrefI: '/pages/dashboard.html'            
        },
        {
            titleI: 'Пользователи',
            nameI: 'usersMenu', 
            icoI: 'icon-single-02', 
            subItems: [
                {   
                    titleS: 'Пользователи',
                    icoS: 'icon-minimal-right',
                    hrefS: '/pages/users.html'
                },
                {   
                    titleS: 'Подразделения',
                    icoS: 'icon-minimal-right',
                    hrefS: '/pages/subdivision.html'
                },
            ]
        },
        {
            titleI: 'План и факт',
            name: 'planFactMenu', 
            icoI: 'icon-notes', 
            subItems: [
                {   
                    titleS: 'Указание плана',
                    icoS: 'icon-minimal-right',
                    hrefS: '/pages/plan.html'
                },
                {   
                    titleS: 'Указание факта',
                    icoS: 'icon-minimal-right',
                    hrefS: '/pages/fact.html'
                }
            ]
        },
    ]
});

const navbar = new Navbar({
    title: 'ПЛАН И ФАКТ ИСПОЛЬЗОВАНИЯ ТРАНСПОРТА', 
    userName: 'IvanskayaYuV'
});

const mainPanel = document.querySelector('.main-panel');
const wrapper = document.querySelector('.wrapper');

wrapper.prepend(sidebar.button, sidebar.menu);
mainPanel.setAttribute('data', mainColor);
mainPanel.prepend(navbar.element);

/*const page = new URL(window.location).pathname;
console.log(page);
//import(`/js/pages/${getUrlParams()[page]}.js`);*/

moment.updateLocale('ru', {
    week: {
      dow: 1, // Monday is the first day of the week.
    }
});
