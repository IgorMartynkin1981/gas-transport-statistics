import fetchJson from '../utils/fetch-json.js';

import CustomTable from '../components/CustomTable.js';
import Card from '../components/Card.js';
import Button from '../components/Button.js';
import Toolbar from '../components/Toolbar.js';
import ModalWindow from '../components/ModalWindow.js';
import ValidationForm from '../components/ValidationForm.js';
import {URL_BACKEND, URL_SUBDIVISION} from '../globalVariables.js';

export default class PageSubdivision {

    onModalWindowClick = (event) => {
        let button;

        if (this.formButtons) {
            button = this.formButtons.find( item => { return item.nameId === event.target.dataset.element});
        }
        if (this.deleteButtons && !button ) {
            button = this.deleteButtons.find( item => { return item.nameId === event.target.dataset.element});
        }

        if ( button ) { 
            const fnAction = button.action.bind(this);
            fnAction();
        };
    }

    showWindowEditSubdv = (idEdit) => {
        const subdv = Object.values( this.subdivisions ).find( item => { return item.id == idEdit } );
        subdv.subdivision = subdv.subdivision ? subdv.subdivision.id : null;     

        if (this.form) { this.form.remove(); }
        this.form = this.createForm( subdv );   
        this.modalWindow.update(this.form.element);
    }

    showWindowDeleteSubdv = (idEdit) => {
        const subdv = Object.values( this.subdivisions ).find( item => { return item.id == idEdit } );

        this.deleteButtons = [{
            title: 'Удалить',
            clas: 'btn-success btn-round float-right mr-5',
            nameId: 'deleteSubdv',
            //nameModalWindow: this.nameModalWindow,
            action: () => {
                const nameEvent  = 'deleteSubdv';
                const event = new CustomEvent(nameEvent, {
                    detail: {id: idEdit}
                });
                this.subdvTable.element.dispatchEvent(event);
            }
        }]
        const message = new Card(
            'Подтверждение удаления',
            `<p> Удалить подразделение ${subdv.subdivisionName}?</p>`, 
            this.deleteButtons.map( button => new Button(button).element.outerHTML ).join('')
        );
        this.modalWindow.update(message.element);
        //TODO: защита от удаления подразделений, которые используются в пользователях и в др таблицах
    }

    deleteSubdv = (event) => {
        this.deleteData(event.detail.id)
            .then(resolve => { /*console.log(resolve)*/ });
    }

    saveSubdv = (event) => {
        const subdv = this.form.getFormData();

        if ( subdv.subdivisionName && subdv.subdivisionFullName ) {  
            this.saveData(subdv)
                .then(resolve => { /*console.log(resolve)*/ });
        }
    }

    afterSave = (event) => {

        $.notify(
        {
            icon: "tim-icons " + event.detail.icon,
            message: event.detail.text
        },{
            type: event.detail.style,
            timer: 8000,
            placement: {
                from: "bottom",
                align: "center"
            }
        });
    }

    dispatchEventAfterEdit (text, styleMessage, icon, closeModalWindow = false, updateItem = {} ) {
        this.modalWindow.element.dispatchEvent(new CustomEvent("afterSave", {
            detail: { 
                "text": text,
                "style": styleMessage,
                "icon": icon
            }
        }));

        if ( closeModalWindow ) {
            this.modalWindow.close();
        }

        if ( updateItem ) {
            switch (updateItem.action) {
                case "save":
                    this.subdivisions.push(updateItem.item);
                    this.subdvTable.addRow(updateItem.item);
                break;
                case "update":
                    let indexForEdit = null;
                    this.subdivisions.map( (sub, index) => {
                        if (sub.id == updateItem.item.id) { // === не ставить, неявное сравнение
                            indexForEdit = index;
                        }
                    });

                    this.subdivisions.splice(indexForEdit, 1, updateItem.item);
                    this.subdvTable.updateRow(updateItem.item);
                break;
                case "delete":                    
                    let indexForDelete = null;
                    this.subdivisions.map( (sub, index) => {
                        if (sub.id == updateItem.id) { // === не ставить, неявное сравнение
                            indexForDelete = index;                           
                        }
                    });

                    this.subdivisions.splice(indexForDelete, 1);
                    this.subdvTable.deleteRow(updateItem.id);
                break;
            }
        }
    }

    async saveData( sub ) { 

        const idForEdit = sub.id ? '/'+sub.id : '';

        const urlSaveSubdv = new URL (URL_SUBDIVISION + idForEdit, URL_BACKEND);

        try {
            const result = await fetchJson( urlSaveSubdv, {
                method: sub.id ? 'PATCH' : 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },                
                body: JSON.stringify(sub)
            });
            
            this.dispatchEventAfterEdit (
                "Успешно сохранено",
                "info",
                "icon-bell-55",
                true,
                {   action: sub.id ? 'update' : 'save', 
                    item: result
                }
            );

        } catch (error) {
            console.error('Ошибка сохранения информации о подразделении:', error);
            this.dispatchEventAfterEdit ("Ошибка сохранения подразделения: " + error, "danger", "icon-bell-55");
        }
    }

    async deleteData (subId) {
        const urlDeleteSubdv = new URL (URL_SUBDIVISION + '/' + subId, URL_BACKEND);

        try {
            const result = await fetchJson( urlDeleteSubdv, {
                method: 'DELETE',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            });
            this.dispatchEventAfterEdit ("Успешно удалено", "info", "icon-bell-55", true, {action: "delete", id: subId});
            
        } catch (error) {
            console.error('Ошибка удаления подразделения:', error);
            this.dispatchEventAfterEdit ("Ошибка удаления подразделения: " + error, "danger", "icon-bell-55", true);
        }
    }

    async loadData() {
        const urlSubdivisions = new URL(URL_SUBDIVISION, URL_BACKEND);

        try {
            const promiseSubdivision = fetchJson(urlSubdivisions);

            const [subdivisions] = await Promise.all( [promiseSubdivision] );
            
            return [subdivisions]; // при расширении этого массива ниже в catch return  добавить пустой массив

        } catch (error) {
            console.error (error);
            return [ [] ];
        }
    }
    
    createSubdivisionList() {
        this.headers = [
            {
                title: 'Краткое наименование',
                formatedfield: (sub) => { return `${sub.subdivisionName}` },
            },
            {
                title: 'Полное наименование',
                formatedfield: (sub) => { return `${sub.subdivisionFullName}` },
            },
            {
                title: 'Действия',
                classHeadCell: 'sorting_desc_disabled sorting_asc_disabled text-right',
                classRowCell: 'text-right',
                formatedfield: (sub) => { 
                    return `<a href="javascript:void(0)" class="btn btn-link btn-warning btn-icon btn-sm edit" data-toggle="modal" data-target=".${this.nameModalWindow}"><i data-idedit=${sub.id} data-element="editButton" class="tim-icons icon-pencil"></i></a>
                            <a href="javascript:void(0)" class="btn btn-link btn-danger btn-icon btn-sm remove" data-toggle="modal" data-target=".${this.nameModalWindow}"><i data-iddelete=${sub.id} data-element="deleteButton" class="tim-icons icon-simple-remove"></i></a>`
                },
            },
        ]
        return new CustomTable(this.headers, this.subdivisions);
    }

    createForm( subdvToEdit ) {        
        this.formsItems = [
            {
                title: 'Краткое наименование',
                name: 'subdivisionName',                
                required: true,
                type: 'text',
            },
            {
                title: 'Полное наименование',
                name: 'subdivisionFullName',
                required: true,
                type: 'text',
            },
        ]
        this.formButtons = [
            {
                title: 'Сохранить',
                clas: 'btn-success btn-round float-right mr-5',
                //nameModalWindow: this.nameModalWindow,
                nameId: 'saveSubdv',
                //type: 'submit', // закомментировать, чтобы увидеть ошибку отправки
                action: () => {
                    const nameEvent  = subdvToEdit ? 'saveExistSubdv' : 'saveNewSubdv';
                    const event = new CustomEvent(nameEvent);
                    this.subdvTable.element.dispatchEvent(event); 
                }
            }
        ]

        return new ValidationForm({items: this.formsItems, showObj: subdvToEdit, formButtons: this.formButtons});
    }

    createModalWindow() {
        return new ModalWindow (this.nameModalWindow, 'Подразделения');
    }

    createToolbarButtons() {
        this.buttons = [
            {
                title: 'Добавить подразделение', 
                ico: 'icon-minimal-right',
                clas: 'btn-success btn-round float-right mr-5',
                nameModalWindow: this.nameModalWindow,
                nameId: 'addSubdv',
                action: () => {
                    const event = new CustomEvent('show-form-add');
                    this.subdvTable.element.dispatchEvent(event);                    
                }
            }
        ]
        return new Toolbar(this.buttons);
    }

    async render() {
        const [subdivisions] = await this.loadData();
        this.subdivisions = subdivisions;
        this.nameModalWindow = 'modal-window-subdivisions';

        this.modalWindow = this.createModalWindow();

        this.subdvTable = this.createSubdivisionList();        

        this.toolbar = this.createToolbarButtons();        

        this.initEventListeners();
    }

    initEventListeners() {
        this.subdvTable.element.addEventListener('show-form-add', event => {            
            if (this.form) { this.form.remove(); }
            this.form = this.createForm();      
            this.modalWindow.update(this.form.element);
        });

        this.subdvTable.element.addEventListener('click', event => {          
            switch (event.target.dataset.element) {
                case 'editButton': this.showWindowEditSubdv(event.target.dataset.idedit);
                    break;
                case 'deleteButton': this.showWindowDeleteSubdv(event.target.dataset.iddelete);
                    break;
            }
        });

        this.modalWindow.element.addEventListener('click', this.onModalWindowClick);

        this.modalWindow.element.addEventListener('afterSave', this.afterSave);

        this.subdvTable.element.addEventListener('saveExistSubdv', this.saveSubdv);

        this.subdvTable.element.addEventListener('saveNewSubdv',  this.saveSubdv);

        this.subdvTable.element.addEventListener('deleteSubdv',  this.deleteSubdv);

    }

    destroy() {
        this.remove();
    }
}