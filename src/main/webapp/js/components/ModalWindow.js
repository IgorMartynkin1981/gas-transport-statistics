export default class ModalWindow {

    constructor ( 
        nameModalWindow = '', 
        contentElement = document.createElement("div") 
    ){
        this.nameModalWindow = nameModalWindow;
		this.contentElement = contentElement;		
		this.render();
    }

    getTemplate(content = '') {
        return `
        <div class="modal fade ${this.nameModalWindow}" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div data-element="modalContent" class="modal-content">
                ${content}
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
        this.subElements.modalContent.innerHTML = newContent.outerHTML;
    }

    getSubElements(element) {
        const subElements = {};
        const elements = element.querySelectorAll('[data-element]');

        for (const item of elements) {
            subElements[item.dataset.element] = item;
        }

        return subElements;
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