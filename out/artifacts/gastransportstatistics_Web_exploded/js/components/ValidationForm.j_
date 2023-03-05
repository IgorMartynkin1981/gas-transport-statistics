
import escapeHtml from '/js/utils/escape-html.js';
import {URL_BACKEND, URL_SUBDIVISION, URL_USERS} from '/js/globalVariables.js';
import Card from './Card.js';
import Button from './Button.js';
/*
@this.items: {
	title, // подпись к полю
	name, // наименование поля, должны быть идентичны с поступащим на отображение элементом this.showObj
	required, // boolean обязательность ввода
	type: 'text' 'selector' 'email' 'password' 'number' 'datapicker'//тип элемента
	selectorItems: // перечень options раскрывающегося списка
	[
		{
		id, //id элемента (как в БД)
		title //подпись к элементу
		}
	]
}
*/

export default class ValidationForm {

    onSubmit = event => {
        event.preventDefault();
        this.save();
    };

    constructor ( {
		items = [],
		showObj = {},
		formButtons = [],
		name = 'validation-form'
	} = {}
	){
		

		this.name = name;
		this.formButtons = formButtons;
		this.showObj = showObj;		

		if ( Object.keys(showObj).length ) {
			this.title = 'Редактирование'
		} else {
			this.title = 'Добавление'
		}

		this.items = items;

		this.render();
    }

	getSelectElement(item, curVal=-1) {
		return `
		<div class="form-group">			
			<select class="form-control" data-element="${item.name}" name="${item.name}" >
				<option value="-1"></option>
				${ item.selectorItems.map( option => {
					return `<option value="${option.id}" ${ curVal === option.id ? `selected` : `` }>${option.title}</option>`
					}).join('')
				}
			</select>
		</div>
		`;
	}

	getInputElement(item, curVal='') {
		return  `
		<input class="form-control" 
				data-element="${item.name}" 
				type="${item.type}" 
				name="${item.name}" 
				${ item.required ? `required` : ``} 
				value="${curVal}">`;
	}

	getFormElement(itemForm) {
		let itemHtml = '';
		const curVal = this.showObj[itemForm.name]; 

		const inputTypes = ['text', 'email', 'password', 'number'];
		const selectorTypes = ['selector'];
		
		if ( inputTypes.includes(itemForm.type) ) {		
			itemHtml = this.getInputElement(itemForm, curVal);
		};

		if ( selectorTypes.includes(itemForm.type) ) {		
			itemHtml = this.getSelectElement(itemForm, curVal);
		}

		return `
		<div class="row">
			<label class="col-sm-2 col-form-label">${itemForm.title}</label>
			<div class="col-sm-7">
				<div class="form-group">
					${itemHtml}
				</div>
			</div>
		</div>
		`
	}

    getTemplate() {
		return `
		<form data-element="${this.name}" id="${this.name}" class="form-horizontal">` +
			new Card( 	this.title,
						this.items.map( itemForm => this.getFormElement(itemForm)).join(''),
						this.formButtons.map( button => new Button(button).element.outerHTML ).join('') 
			).element.outerHTML+ `
		</form>` ;
    }

    async render () {
		const element = document.createElement("div");
        element.innerHTML = this.getTemplate(); 
        this.element = element.firstElementChild;

		this.subElements = this.getSubElements(this.element);

        return this.element;
    }

	getSubElements(element) {
        const subElements = {};
        const elements = element.querySelectorAll('[data-element]');
    
        for (const item of elements) {
            subElements[item.dataset.element] = item;
        }

        return subElements;
    }

	getFormData() {
        const formValues = {};

        if ( this.showObj.id ) {formValues.id = Number(this.showObj.id); }

		const formItems = document.querySelector(`[data-element="${this.name}"]`);

		Object.values(formItems).map( item => {
			
			if ( !item.type ) { return; }
			if ( ['submit', 'button', 'form'].includes(item.type) ) { return; }

			let value;
			switch ( item.type ) {
				case 'number':
				case 'select-one' :
					value = !isNaN(Number(item.value)) ? Number(item.value) : item.value;
					break;
				case 'password':
					if (item.value !== '') {value = md5(item.value)};
					break;
				default:
					value = item.value;
			}
			formValues[item.name] = value;
		})

		return formValues;
	}

	/*
    async save() {      
        const urlSaveProduct = new URL ('api/rest/products', BACKEND_URL);
        const product = this.getFormData();

        try {
            const result = await fetchJson( urlSaveProduct, {
                method: this.productId ? 'PATCH' : 'PUT',
                items: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(product)
            });

            this.dispatchEventAfterSave(result.id);
        } catch (error) {
            console.error('Ошибка сохранения информации о продукте:', error);
        }
    }

    

    dispatchEventAfterSave (id) {
        const event = this.productId
            ? new CustomEvent('product-updated', { detail: id })
            : new CustomEvent('product-saved');
    
        this.element.dispatchEvent(event);
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
        const { productForm, uploadImage } = this.subElements;

        productForm.addEventListener('submit', this.onSubmit);
        uploadImage.addEventListener('click', this.onUploadImage);
    }
*/
    remove() {
		this.element.remove();
		this.subElements = null;
		this.showObj = null;
    }

    destroy() {
        this.remove();
        this.element = null;
        this.subElements = null;
    }
}