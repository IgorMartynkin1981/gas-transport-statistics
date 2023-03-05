import Button from '../components/Button.js';
/*
 buttons = [...
            {
            Для компонента Button (описание лучше там посмотреть)
            title, наименование кнопки
            ico, иконка кнопки
            nameModalWindow, наименование nameCLassId созданного модального окна
            nameId, имя кнопки в теге
            clas, стили кнопки
            
            Для назначения обработчика
            action функция-действие при клике
            }
        ]
group = boolean являются ли кнопки группой переключателей
*/
export default class Navbar {
    constructor( 
        buttons = [...
            { 
            title, 
            ico,
            nameModalWindow,
            nameId,            
            clas,
            action
            }
        ] = [],
        group = false
    ){
        this.buttons = buttons;
        this.group = group;
        this.render();
    }

    getTemplate() {
        const group = {};
        group.open = this.group ?  `<div class="btn-group btn-group-toggle float-right" >` : ``;
        group.close = this.group ?  `</div>` : ``;

        return `
        <div class="toolbar">
            ${ group.open }
		    ${ this.buttons.map( button => {return new Button(button).element.outerHTML} ).join('') }
            ${ group.close }
		</div>`;
    }

    render() {
        const element = document.createElement("div"); 
        element.innerHTML = this.getTemplate();
        this.element = element.firstElementChild;

        this.subElements = this.getSubElements(this.element);
        this.initEventListeners();
    }

    getSubElements(element) {
        const subElements = {};
        const elements = element.querySelectorAll('[data-element]');

        for (const item of elements) {
            subElements[item.dataset.element] = item;
        }

        return subElements;
    }

    initEventListeners() {
        this.buttons.map( button => {
            const {nameId} = button;
            this.subElements[nameId].addEventListener('click', button.action);
        });
    }

    remove() {
        this.element.remove();
    }

    destroy() {
        this.remove();
    }


}