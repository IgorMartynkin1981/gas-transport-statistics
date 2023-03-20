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
        if (this.deleteButtons) {
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
            .then(resolve => { console.log(resolve) });
    }

    saveUser = (event) => {
        const user = this.form.getFormData();

        console.log(user);
        
        if ( user.subdivision === -1 || !user.subdivision ) {
            delete user.subdivision ;
        }
        if ( user.password === '' || !user.password ) {
            delete user.password; 
        }

        if ( user.userName && user.password && user.lastName && user.firstName && user.login && user.email.indexOf('@') > 0) {
            this.saveData(user)
                .then(resolve => { console.log(resolve) });
        }
    }

    async saveData( usr ) {
        const urlSaveUser = new URL ("/signup", "http://localhost:42727/user_console");
        
        /*$.ajax({
            type: "POST",
            url: urlSaveUser,
            data: JSON.stringify(usr),
            success: function(dataResult) {                
                let ts = JSON.parse(dataResult);            
                console.log(ts)
            },
            error: function(er) {
                console.log(er);
            }
        });*/

        try {
            const result = await fetchJson( urlSaveUser, {
                // method: usr.id ? 'PATCH' : 'POST',
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                // items: {
                //     'Content-Type': 'application/json'
                // },
                body: JSON.stringify(usr)
            });
            console.log(result);
            //this.dispatchEventAfterSave(result.id);
        } catch (error) {
            console.error('Ошибка сохранения информации о пользователе:', error);
        }
    }

    async deleteData (usrId) {
        console.log('Удаление пользователя id ', usrId);
        const urlDeleteUser = new URL (URL_USERS + '/' + usrId, URL_BACKEND);

        /*$.ajax({
            type: "DELETE",
            url: urlDeleteUser,
            //data: JSON.stringify( {"id": usrId} ),
            success: function(dataResult) {                
                let ts = JSON.parse(dataResult);            
                console.log(ts)
            },
            error: function(er) {
                console.log(er);
            }
        });*/

        try {
            const result = await fetchJson( urlDeleteUser, {
                method: 'DELETE',
                items: {
                    'Content-Type': 'application/json'
                }
            });
            console.log(result);
            //this.dispatchEventAfterSave(result.id);
        } catch (error) {
            console.error('Ошибка удаления пользователя:', error);
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
    
    //TODO: delete, test data
    createTempData() {        
        this.users = [
            {
                "id": 1,
                "firstName": "Igor",
                "lastName": "Martynkin",
                "email": "MartynkinIA@alrosa.ru",
                "login": "MartynkinIA",
                "subdivision": null			//NOT INTEGER, if class SubDivision is Null, return null
            },
            {
                "id": 2,
                "firstName": "Igor1",
                "lastName": "Martynkin1",
                "email": "MartynkinIA1@alrosa.ru",
                "login": "MartynkinIA1",
                "subdivision": {			//NOT INTEGER, if class SubDivision not Null, return SubDivision class
                    "id": 1,
                    "subdivisionName": "АГОК",
                    "subdivisionFullName": "Айхальский Горнообогатительный Комбинат"
                }
            }
        ];
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
                name: 'login',
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
        return new ModalWindow (this.nameModalWindow);
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

        //this.createTempData();//TODO: delete, test data

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

        this.userTable.element.addEventListener('saveExistUser', this.saveUser);

        this.userTable.element.addEventListener('saveNewUser',  this.saveUser);

        this.userTable.element.addEventListener('deleteUser',  this.deleteUser);

    }

    destroy() {
        this.remove();
    }
}