export default class Card {

    constructor ( 
        head = '', 
        content = '',
        footer = ''
    ){
        this.head = head;
		this.content = content;		
		this.footer = footer;		
		this.render();
    }

    getTemplate() {
        return `
        <div class="card">
            <div class="card-header">
                <h4 class="card-title">${this.head}</h4>
            </div>
            <div class="card-body">           
                ${this.content}          
            </div>
            <div class="card-footer">           
                ${this.footer}
            </div>
        </div>; 
        `;
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