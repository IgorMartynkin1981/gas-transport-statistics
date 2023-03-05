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
        if (this.deleteButtons) {
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
            .then(resolve => { console.log(resolve) });
    }


    saveSubdv = (event) => {
        const subdv = this.form.getFormData();

        if ( subdv.subdivisionName && subdv.subdivisionFullName ) {  
            this.saveData(subdv)
                .then(resolve => { console.log(resolve) });
        }
    }

    async saveData( sub ) { 
        const urlSaveSubdv = new URL (URL_SUBDIVISION, URL_BACKEND);
        
        /*$.ajax({
            type: sub.id ? "PATCH" : "POST",
            url: urlSaveSubdv,
            data: JSON.stringify(sub),
            success: function(dataResult) {                
                let ts = JSON.parse(dataResult);            
                console.log(ts)
            },
            error: function(er) {
                console.log(er);
            }
        });*/

        try {
            const result = await fetchJson( urlSaveSubdv, {
                method: sub.id ? 'PATCH' : 'POST',
                items: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(sub)
            });
            console.log(result);
            //this.dispatchEventAfterSave(result.id);
        } catch (error) {
            console.error('Ошибка сохранения информации о подразделении:', error);
        }
    }

    async deleteData (subId) {
        console.log('Удаление подразделения id ', subId);
        const urlDeleteSubdv = new URL (URL_SUBDIVISION + '/' + subId, URL_BACKEND);

        /*$.ajax({
            type: "DELETE",
            url: urlDeleteSubdv,
            //data: JSON.stringify( {"id": subId} ),
            success: function(dataResult) {                
                let ts = JSON.parse(dataResult);            
                console.log(ts)
            },
            error: function(er) {
                console.log(er);
            }
        });*/

        try {
            const result = await fetchJson( urlDeleteSubdv, {
                method: 'DELETE',
                items: {
                    'Content-Type': 'application/json'
                }
            });
            console.log(result);
            //this.dispatchEventAfterSave(result.id);
        } catch (error) {
            console.error('Ошибка удаления подразделения:', error);
        }
    }

    async loadData() {
        const urlSubdivisions = new URL(URL_SUBDIVISION, URL_BACKEND);

        try {
            const promiseSubdivision = fetchJson(urlSubdivisions);

            const [subdivisions] = await Promise.all( [promiseSubdivision] );
            
            return [subdivisions];

        } catch (error) {
            console.error (error);
            return [ [] ];
        }
    }
    
    //TODO: delete, test data
    createTempData() {
        this.subdivisions = [
            {
                "id": 1,
                "subdivisionName": "АГОК",
                "subdivisionFullName": "Айхальский Горнообогатительный Комбинат"
            },
            {
                "id": 2,
                "subdivisionName": "УГОК",
                "subdivisionFullName": "Удачнинский Горнообогатительный Комбинат"
            }
        ];
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
                type: 'submit',
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
        return new ModalWindow (this.nameModalWindow);
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

        //this.createTempData();//TODO: delete, test data

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

        this.subdvTable.element.addEventListener('saveExistSubdv', this.saveSubdv);

        this.subdvTable.element.addEventListener('saveNewSubdv',  this.saveSubdv);

        this.subdvTable.element.addEventListener('deleteSubdv',  this.deleteSubdv);

    }

    destroy() {
        this.remove();
    }
}