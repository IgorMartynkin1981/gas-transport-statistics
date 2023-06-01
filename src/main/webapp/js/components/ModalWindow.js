export default class ModalWindow {

    constructor (
            nameModalWindow = '',            
            headerModalWindow = 'Действие'
    ){
        this.nameModalWindow = nameModalWindow;
        this.headerModalWindow = headerModalWindow;
		this.contentElement = document.createElement("div");		
		this.render();
    }

    getTemplate(content = '') {
        return `
        <div class="modal fade ${this.nameModalWindow}" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" data-element="modalCloseButton" class="close" data-dismiss="modal" aria-hidden="true">
                        <i class="tim-icons icon-simple-remove"></i>
                        </button>
                        <h3 class="modal-title" id="myModalLabel"> ${this.headerModalWindow} </h3>
                    </div>
                    <div class="modal-body" data-element="modalBody" >
                        ${content}   
                    </div>                    
                </div>
            </div>
        </div>
        `;
    }

    render () {
		const element = document.createElement("div");
        element.innerHTML = this.getTemplate(this.contentElement.innerHTML); 
        this.element = element.firstElementChild;

        this.subElements = this.getSubElements(this.element);
    }

    update(newContent) {
        this.subElements.modalBody.innerHTML = newContent.outerHTML;
    }

    getSubElements(element) {
        const subElements = {};
        const elements = element.querySelectorAll('[data-element]');

        for (const item of elements) {
            subElements[item.dataset.element] = item;
        }

        return subElements;
    }

    close(){
        this.subElements.modalCloseButton.dispatchEvent(new Event('click', {bubbles: true}));
    }

    remove() {
		this.element.remove();
		this.subElements = null;
    }

    destroy() {
        this.remove();
        this.element = null;
        this.subElements = null;
    }
}