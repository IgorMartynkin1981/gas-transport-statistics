import fetchJson from '../utils/fetch-json.js';

import CustomTable from '../components/CustomTable.js';
import Card from '../components/Card.js';
import Button from '../components/Button.js';
import Toolbar from '../components/Toolbar.js';
import ModalWindow from '../components/ModalWindow.js';
import ValidationForm from '../components/ValidationForm.js';
import {URL_BACKEND, URL_SUBDIVISION, URL_USERS} from '../globalVariables.js';

export default class PageUsers {

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

    showWindowEditUser = (idEdit) => {
        const user = Object.values( this.users ).find( item => { return item.id == idEdit } );
        user.subdivision = user.subdivision ? user.subdivision.id : null;     

        if (this.form) { this.form.remove(); }
        this.form = this.createForm( user );   
        this.modalWindow.update(this.form.element);
    }

    showWindowDeleteUser = (idEdit) => {
        const user = Object.values( this.users ).find( item => { return item.id == idEdit } );

        this.deleteButtons = [{
            title: 'Удалить',
            clas: 'btn-success btn-round float-right mr-5',
            nameId: 'deleteUser',
            //nameModalWindow: this.nameModalWindow,
            action: () => {
                const nameEvent  = 'deleteUser';
                const event = new CustomEvent(nameEvent, {
                    detail: {id: idEdit}
                });
                this.userTable.element.dispatchEvent(event);
            }
        }]
        const message = new Card(
            'Подтверждение удаления',
            `<p> Удалить пользователя ${user.lastName} ${user.firstName}?</p>`, 
            this.deleteButtons.map( button => new Button(button).element.outerHTML ).join('')
        );
        this.modalWindow.update(message.element);
        //TODO: защита от удаления подразделений, которые используются в пользователях и в др таблицах
    }

    deleteUser = (event) => {
        this.deleteData(event.detail.id)
            .then(resolve => { /*console.log(resolve)*/ });
    }

    saveUser = (event) => {
        const user = this.form.getFormData(); 
        
        if ( user.subdivision === -1 || !user.subdivision ) {
            delete user.subdivision;
        }
        if ( user.password === '' || !user.password ) {
            delete user.password; 
        }

        if ( user.lastName && user.firstName && user.login && user.email.indexOf('@') > 0) {  
            this.saveData(user)
                .then(resolve => { /*console.log(resolve) */});
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

    dispatchEventAfterEdit (text, styleMessage, icon, closeModalWindow = false, updateItem = {} ) { console.log(updateItem);
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
                    this.users.push(updateItem.item);
                    this.userTable.addRow(updateItem.item);
                break;
                case "update":
                    let indexForEdit = null;
                    this.users.map( (usr, index) => {
                        if (usr.id == updateItem.item.id) { // === не ставить, неявное сравнение
                            indexForEdit = index;
                        }
                    });

                    this.users.splice(indexForEdit, 1, updateItem.item);
                    this.userTable.updateRow(updateItem.item);
                break;
                case "delete":                    
                    let indexForDelete = null;
                    this.users.map( (usr, index) => {
                        if (usr.id == updateItem.id) { // === не ставить, неявное сравнение
                            indexForDelete = index;                           
                        }
                    });

                    this.users.splice(indexForDelete, 1);
                    this.userTable.deleteRow(updateItem.id);
                break;
            }
        }
    }

    async saveData( usr ) {

        const idForEdit = usr.id ? '/'+usr.id : '';

        usr.subdivisionId = usr.subdivision;

        const urlSaveUser = new URL (URL_USERS + idForEdit, URL_BACKEND);
        try {
            const result = await fetchJson( urlSaveUser, {
                method: usr.id ? 'PATCH' : 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },

                body: JSON.stringify(usr)
            });

            this.dispatchEventAfterEdit (
                "Успешно сохранено",
                "info",
                "icon-bell-55",
                true,
                {   action: usr.id ? 'update' : 'save', 
                    item: result
                }
            );

        } catch (error) {
            console.error('Ошибка сохранения информации о пользователе:', error);
            this.dispatchEventAfterEdit ("Ошибка сохранения пользователя: " + error, "danger", "icon-bell-55");
        }
    }

    async deleteData (usrId) {        
        const urlDeleteUser = new URL (URL_USERS + '/' + usrId, URL_BACKEND);

        try {
            const result = await fetchJson( urlDeleteUser, {
                method: 'DELETE',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            });
            this.dispatchEventAfterEdit ("Успешно удалено", "info", "icon-bell-55", true, {action: "delete", id: usrId});
            
        } catch (error) {
            console.error('Ошибка удаления пользователя:', error);
            this.dispatchEventAfterEdit ("Ошибка удаления пользователя: " + error, "danger", "icon-bell-55", true);
        }
    }

    async loadData() {
        const urlUsers = new URL(URL_USERS, URL_BACKEND);
        const urlSubdivisions = new URL(URL_SUBDIVISION, URL_BACKEND);

        try {
            const promiseUser = fetchJson(urlUsers);
            const promiseSubdivision = fetchJson(urlSubdivisions);

            const [users, subdivisions] = await Promise.all( [promiseUser, promiseSubdivision] );
            
            return [users, subdivisions];

        } catch (error) {
            console.error (error);
            return [[], []];
        }
    }
    
    createUserList() {
        this.headers = [
            {
                title: 'ФИО',
                formatedfield: (usr) => { return `${usr.lastName} ${usr.firstName}` },
            },
            {
                title: 'Логин',
                formatedfield: (usr) => { return `${usr.login}` },
            },
            {
                title: 'Подразделение',
                formatedfield: (usr) => { return `${ usr.subdivision ? usr.subdivision.subdivisionName : ''}` },
            },
            {
                title: 'E-mail',
                formatedfield: (usr) => { return `${usr.email}` },
            },
            {
                title: 'Действия',
                classHeadCell: 'sorting_desc_disabled sorting_asc_disabled text-right',
                classRowCell: 'text-right',
                formatedfield: (usr) => { 
                    return `<a href="javascript:void(0)" class="btn btn-link btn-warning btn-icon btn-sm edit" data-toggle="modal" data-target=".${this.nameModalWindow}"><i data-idedit=${usr.id} data-element="editButton" class="tim-icons icon-pencil"></i></a>
                            <a href="javascript:void(0)" class="btn btn-link btn-danger btn-icon btn-sm remove" data-toggle="modal" data-target=".${this.nameModalWindow}"><i data-iddelete=${usr.id} data-element="deleteButton" class="tim-icons icon-simple-remove"></i></a>`
                },
            },
        ]
        return new CustomTable(this.headers, this.users);
    }

    createForm( userToEdit ) {
        const optSubdiv = this.subdivisions.reduce( (concatArr, item) => {
            return concatArr.concat([{
                id: item.id,
                title: item.subdivisionName
            }])
        }, []);
        this.formsItems = [            
            {
                title: 'Фамилия',
                name: 'lastName',                
                required: true,
                type: 'text',
            },
            {
                title: 'Имя',
                name: 'firstName',
                required: true,
                type: 'text',
            },
            {
                title: 'Е-мейл',
                name: 'email',
                type: 'email',
            },
            {
                title: 'Логин',
                name: 'username',
                required: true,
                type: 'text',
            },
            {
                title: 'Пароль',
                name: 'password',
                type: 'password',
            },
            {
                title: 'Подразделение',
                name: 'subdivision',
                type: 'selector',
                selectorItems: optSubdiv,
            },
        ]
        this.formButtons = [
            {
                title: 'Сохранить',
                clas: 'btn-success btn-round float-right mr-5',
                //nameModalWindow: this.nameModalWindow,
                nameId: 'saveUser',
                //type: 'submit', // закомментировать, чтобы увидеть ошибку отправки
                action: () => {
                    const nameEvent  = userToEdit ? 'saveExistUser' : 'saveNewUser';
                    const event = new CustomEvent(nameEvent);
                    this.userTable.element.dispatchEvent(event); 
                }
            }
        ]

        return new ValidationForm({items: this.formsItems, showObj: userToEdit, formButtons: this.formButtons});
    }

    createModalWindow() {
        return new ModalWindow (this.nameModalWindow, "Пользователи");
    }

    createToolbarButtons() {
        this.buttons = [
            {
                title: 'Добавить пользователя', 
                ico: 'icon-minimal-right',
                clas: 'btn-success btn-round float-right mr-5',
                nameModalWindow: this.nameModalWindow,
                nameId: 'addUser',
                action: () => {
                    const event = new CustomEvent('show-form-add');
                    this.userTable.element.dispatchEvent(event);                    
                }
            }
        ]
        return new Toolbar(this.buttons);
    }

    async render() {
        const [users, subdivisions] = await this.loadData();
        this.users = users;
        this.subdivisions = subdivisions;
        this.nameModalWindow = 'modal-window-users';

        this.modalWindow = this.createModalWindow();

        this.userTable = this.createUserList();        

        this.toolbar = this.createToolbarButtons();        

        this.initEventListeners();
    }

    initEventListeners() {
        this.userTable.element.addEventListener('show-form-add', event => {            
            if (this.form) { this.form.remove(); }
            this.form = this.createForm();      
            this.modalWindow.update(this.form.element);
        });

        this.userTable.element.addEventListener('click', event => {          
            switch (event.target.dataset.element) {
                case 'editButton': this.showWindowEditUser(event.target.dataset.idedit);
                    break;
                case 'deleteButton': this.showWindowDeleteUser(event.target.dataset.iddelete);
                    break;
            }
        });

        this.modalWindow.element.addEventListener('click', this.onModalWindowClick);

        this.modalWindow.element.addEventListener('afterSave', this.afterSave);

        this.userTable.element.addEventListener('saveExistUser', this.saveUser);

        this.userTable.element.addEventListener('saveNewUser',  this.saveUser);

        this.userTable.element.addEventListener('deleteUser',  this.deleteUser);

    }

    destroy() {
        this.remove();
    }
}