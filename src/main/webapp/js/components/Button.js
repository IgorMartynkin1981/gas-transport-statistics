/*
{ 
title, наименование кнопки
ico, иконка кнопки
nameModalWindow, наименование модального окна, с которым должно взаимодействовать
nameId, имя кнопки в теге
clas, стили кнопки
type button или submit 
}
*/
export default class Button {

    constructor ({
        title, 
        ico,
        nameModalWindow,
        nameId,            
        clas,
        type
    } = {}
    ){
        this.title = title; 
        this.ico = ico;
        this.nameModalWindow = nameModalWindow;
        this.nameId = nameId;            
        this.clas = clas;
		this.type = type ? type : 'button'
        this.render();
    }

    getTemplate() {
        return `
        <button 
            data-element="${this.nameId}" 
            type="${this.type}" 
            rel="tooltip" 
            class="btn ${ this.clas ? this.clas : 'btn-success btn-round float-right mr-5' }"
            ${ this.nameModalWindow ? `data-toggle="modal" data-target=".${this.nameModalWindow}"` : `` }
        >
            ${this.title}
            ${ this.ico ? `<i class="tim-icons ${this.ico}"></i>` : ``}
        </button>`;
    }

    render () {
		const element = document.createElement("div");
        element.innerHTML = this.getTemplate(); 
        this.element = element.firstElementChild;
    }

    remove() {
		this.element.remove();		
    }

    destroy() {
        this.remove();
        this.element = null;
    }
}