import fetchJson from '../utils/fetch-json.js';
import getYearKvartal from '../utils/get-year-kvartal.js';
import getWeek from '../utils/get-week.js';
import getWeeksArray from '../utils/get-weeks-array.js';

import CustomTable from '../components/CustomTable.js';
import Card from '../components/Card.js';
import Button from '../components/Button.js';
import Toolbar from '../components/Toolbar.js';
import ModalWindow from '../components/ModalWindow.js';
import ValidationForm from '../components/ValidationForm.js';
import {URL_BACKEND, URL_USERS, URL_SUBDIVISION, URL_FACTS} from '../globalVariables.js';

export default class PageFact {

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

    showWindowEditFact = (idEdit) => {
        const fact = Object.values( this.facts ).find( item => { return item.id == idEdit } ); // "==" на "===" не изменять (строка с числом в сравнении)
        fact.subdivisionId = fact.subdivision.id;

        const dataWeek = getWeek(fact.periodStart);
        fact.week = dataWeek.numWeek;

        if (this.form) { this.form.remove(); }
        this.form = this.createForm( fact );
        this.modalWindow.update(this.form.element);
    }

    showWindowDeleteFact = (idEdit) => {
        const fact = Object.values( this.facts ).find( item => { return item.id == idEdit } ); // "==" на "===" не изменять (строка с числом в сравнении)

        this.deleteButtons = [{
            title: 'Удалить',
            clas: 'btn-success btn-round float-right mr-5',
            nameId: 'deleteFact',
            //nameModalWindow: this.nameModalWindow,
            action: () => {
                const nameEvent  = 'deleteFact';
                const event = new CustomEvent(nameEvent, {
                    detail: {id: idEdit}
                });
                this.factTable.element.dispatchEvent(event);
            }
        }]
        const message = new Card(
            'Подтверждение удаления',
            `<p> Удалить запись?</p>`, 
            this.deleteButtons.map( button => new Button(button).element.outerHTML ).join('')
        );
        this.modalWindow.update(message.element);
    }

    deleteFact = (event) => {
        this.deleteData(event.detail.id)
            .then(resolve => { console.log(resolve) });
    }
    
    formattedDataforSave (item) {
        item.subdivisionId = item.subdivisionId;
        item.creationTime = "{{start}}";

        const yearRegExp = /(?<=_).*/;
        const year = Number(item.week.match(yearRegExp));

        const weeks = getWeeksArray (year);
        const saveWeek = weeks.find( curWeek => {return curWeek.id === item.week} );
        item.periodStart = saveWeek.startDate.format('YYYY-MM-DD');
        item.periodEnd = saveWeek.stopDate.format('YYYY-MM-DD');

        item.userId = 1; //TODO: дописать сохранение записи под авторизованным пользователем

        delete item.week;
        delete item.subdivision;

        return item;
    }

    saveFact = (event) => {
        const fact = this.formattedDataforSave( this.form.getFormData() );

        console.log(fact);

        if (    fact.periodStart 
                && fact.periodEnd 
                && fact.userId  
                && fact.distanceDt > 0
                && fact.distanceGas > 0
                && fact.consumptionGas > 0
                && fact.consumptionDt > 0
            ) {  
            this.saveData(fact)
                .then(resolve => { console.log("succes", resolve) });
        }
    }

    async saveData( fct ) { 
        const urlsaveFact = new URL (URL_FACTS, URL_BACKEND);
        
        /*$.ajax({
            type: "POST",
            url: urlsaveFact,
            data: JSON.stringify(pln),
            success: function(dataResult) {                
                let ts = JSON.parse(dataResult);            
                console.log(ts)
            },
            error: function(er) {
                console.log(er);
            }
        });*/

        try {
            const result = await fetchJson( urlsaveFact, {
                method: fct.id ? 'PATCH' : 'POST',
                items: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(fct)
            });
            console.log("succes",result);
            //this.dispatchEventAfterSave(result.id);
        } catch (error) {
            console.error('Ошибка сохранения информации об элементе плана:', error);
        }
    }

    async deleteData (factId) {
        console.log('Удаление элемента плана id ', factId);
        const urldeleteFact = new URL (URL_FACTS + '/' + factId, URL_BACKEND);

        /*$.ajax({
            type: "DELETE",
            url: urldeleteFact,
            //data: JSON.stringify( {"id": factId} ),
            success: function(dataResult) {                
                let ts = JSON.parse(dataResult);            
                console.log(ts)
            },
            error: function(er) {
                console.log(er);
            }
        });*/

        try {
            const result = await fetchJson( urldeleteFact, {
                method: 'DELETE',
                items: {
                    'Content-Type': 'application/json'
                }
            });
            console.log(result);
            //this.dispatchEventAfterSave(result.id);
        } catch (error) {
            console.error('Ошибка удаления элемента плана:', error);
        }
    }

    async loadData() {
        const urlUsers = new URL(URL_FACTS, URL_BACKEND);
        const urlSubdivisions = new URL(URL_SUBDIVISION, URL_BACKEND);
        const urlFacts = new URL(URL_FACTS, URL_BACKEND);

        try {            
            const promiseFacts = fetchJson(urlFacts);
            const promiseSubdivision = fetchJson(urlSubdivisions);

            const [facts, subdivisions] = await Promise.all( [promiseFacts, promiseSubdivision] );

            return [facts, subdivisions]; // при расширении этого массива ниже в catch return  добавить пустой массив

        } catch (error) {
            console.error (error);
            return [ [],[] ];
        }
    }
    
    //TODO: delete, test data
    createTempData() {        
        this.facts = [           
            {
                "id": 9,
                "user": {
                    "id": 1,
                    "firstName": "Igor",
                    "lastName": "Martynkin",
                    "email": "MartynkinIA@alrosa.ru",
                    "login": "MartynkinIA",
                    "subdivision": null,
                    "userPassword": null,
                    "apiToken": null
                },
                "subdivision": {
                    "id": 1,
                    "subdivisionName": "АДТ",
                    "subdivisionFullName": "Алмаздортранс"
                },
                "creationTime": "2022-01-01",
                "periodStart": "2022-09-26",
                "periodEnd": "2022-10-02",
                "consumptionGas": 255.0,
                "distanceGas": 223.0,
                "consumptionDt": 255.0,
                "distanceDt": 225.0
            }, 
            {
                "id": 2,
                "user": {
                    "id": 1,
                    "firstName": "Igor",
                    "lastName": "Martynkin",
                    "email": "MartynkinIA@alrosa.ru",
                    "login": "MartynkinIA",
                    "subdivision": null,
                    "userPassword": null,
                    "apiToken": null
                },
                "subdivision": {
                    "id": 1,
                    "subdivisionName": "АДТ",
                    "subdivisionFullName": "Алмаздортранс"
                },
                "creationTime": "2022-01-01",
                "periodStart": "2022-11-07",
                "periodEnd": "2022-11-13",
                "consumptionGas": 255.0,
                "distanceGas": 223.0,
                "consumptionDt": 255.0,
                "distanceDt": 225.0
            },
            {
                "id": 3,
                "user": {
                    "id": 2,
                    "firstName": "Igor1",
                    "lastName": "Martynkin1",
                    "email": "MartynkinIA1@alrosa.ru",
                    "login": "MartynkinIA1",
                    "subdivision": {
                        "id": 1,
                        "subdivisionName": "АДТ",
                        "subdivisionFullName": "Алмаздортранс"
                    },
                    "userPassword": null,
                    "apiToken": null
                },
                "subdivision": {
                    "id": 1,
                    "subdivisionName": "АДТ",
                    "subdivisionFullName": "Алмаздортранс"
                },
                "creationTime": "2022-01-01",
                "periodStart": "2022-11-14",
                "periodEnd": "2022-11-20",
                "consumptionGas": 355.0,
                "distanceGas": 323.0,
                "consumptionDt": 355.0,
                "distanceDt": 325.0
            },
            {
                "id": 1,
                "user": {
                    "id": 2,
                    "firstName": "Igor1",
                    "lastName": "Martynkin1",
                    "email": "MartynkinIA1@alrosa.ru",
                    "login": "MartynkinIA1",
                    "subdivision": {
                        "id": 1,
                        "subdivisionName": "АДТ",
                        "subdivisionFullName": "Алмаздортранс"
                    },
                    "userPassword": null,
                    "apiToken": null
                },
                "subdivision": {
                    "id": 1,
                    "subdivisionName": "АДТ",
                    "subdivisionFullName": "Алмаздортранс"
                },
                "creationTime": "2022-10-31",
                "periodStart": "2022-10-31",
                "periodEnd": "2022-11-06",
                "consumptionGas": 155.0,
                "distanceGas": 23.0,
                "consumptionDt": 55.0,
                "distanceDt": 0.0
            },
            {
                "id": 4,
                "user": {
                    "id": 1,
                    "firstName": "Igor",
                    "lastName": "Martynkin",
                    "email": "MartynkinIA@alrosa.ru",
                    "login": "MartynkinIA",
                    "subdivision": null,
                    "userPassword": null,
                    "apiToken": null
                },
                "subdivision": {
                    "id": 1,
                    "subdivisionName": "АДТ",
                    "subdivisionFullName": "Алмаздортранс"
                },
                "creationTime": "2022-01-01",
                "periodStart": "2022-11-21",
                "periodEnd": "2022-11-27",
                "consumptionGas": 455.0,
                "distanceGas": 423.0,
                "consumptionDt": 455.0,
                "distanceDt": 425.0
            },            
            {
                "id": 6,
                "user": {
                    "id": 1,
                    "firstName": "Igor",
                    "lastName": "Martynkin",
                    "email": "MartynkinIA@alrosa.ru",
                    "login": "MartynkinIA",
                    "subdivision": null,
                    "userPassword": null,
                    "apiToken": null
                },
                "subdivision": {
                    "id": 1,
                    "subdivisionName": "АДТ",
                    "subdivisionFullName": "Алмаздортранс"
                },
                "creationTime": "2022-01-01",
                "periodStart": "2022-11-28",
                "periodEnd": "2022-12-04",
                "consumptionGas": 255.0,
                "distanceGas": 223.0,
                "consumptionDt": 255.0,
                "distanceDt": 225.0
            },
            {
                "id": 7,
                "user": {
                    "id": 2,
                    "firstName": "Igor1",
                    "lastName": "Martynkin1",
                    "email": "MartynkinIA1@alrosa.ru",
                    "login": "MartynkinIA1",
                    "subdivision": {
                        "id": 1,
                        "subdivisionName": "АДТ",
                        "subdivisionFullName": "Алмаздортранс"
                    },
                    "userPassword": null,
                    "apiToken": null
                },
                "subdivision": {
                    "id": 1,
                    "subdivisionName": "АДТ",
                    "subdivisionFullName": "Алмаздортранс"
                },
                "creationTime": "2022-01-01",
                "periodStart": "2022-12-05",
                "periodEnd": "2022-12-11",
                "consumptionGas": 355.0,
                "distanceGas": 323.0,
                "consumptionDt": 355.0,
                "distanceDt": 325.0
            },
            {
                "id": 8,
                "user": {
                    "id": 1,
                    "firstName": "Igor",
                    "lastName": "Martynkin",
                    "email": "MartynkinIA@alrosa.ru",
                    "login": "MartynkinIA",
                    "subdivision": null,
                    "userPassword": null,
                    "apiToken": null
                },
                "subdivision": {
                    "id": 1,
                    "subdivisionName": "АДТ",
                    "subdivisionFullName": "Алмаздортранс"
                },
                "creationTime": "2022-01-01",
                "periodStart": "2022-12-12",
                "periodEnd": "2022-12-18",
                "consumptionGas": 455.0,
                "distanceGas": 423.0,
                "consumptionDt": 455.0,
                "distanceDt": 425.0
            },
            {
                "id": 5,
                "user": {
                    "id": 2,
                    "firstName": "Igor1",
                    "lastName": "Martynkin1",
                    "email": "MartynkinIA1@alrosa.ru",
                    "login": "MartynkinIA1",
                    "subdivision": {
                        "id": 1,
                        "subdivisionName": "АДТ",
                        "subdivisionFullName": "Алмаздортранс"
                    },
                    "userPassword": null,
                    "apiToken": null
                },
                "subdivision": {
                    "id": 1,
                    "subdivisionName": "АДТ",
                    "subdivisionFullName": "Алмаздортранс"
                },
                "creationTime": "2022-11-16",
                "periodStart": "2022-12-19",
                "periodEnd": "2022-12-25",
                "consumptionGas": 155.0,
                "distanceGas": 23.0,
                "consumptionDt": 55.0,
                "distanceDt": 0.0
            },
        ];
        this.subdivisions = [
            {
                "id": 3,
                "subdivisionName": "АГОК",
                "subdivisionFullName": "Айхальский Горнообогатительный Комбинат"
            },
            {
                "id": 2,
                "subdivisionName": "УГОК",
                "subdivisionFullName": "Удачнинский Горнообогатительный Комбинат"
            },
            {   "id": 1,
                "subdivisionName": "АДТ",
                "subdivisionFullName": "Алмаздортранс"
            },
        ];
    }

    createFactList() {
        this.headers = [
            {
                title: 'Подразделение',
                formatedfield: (fct) => { return `${fct.subdivision.subdivisionName}` },
            },
            {
                title: 'Год',
                formatedfield: (fct) => {
                    const dataDateStart = getYearKvartal(fct.periodStart);
                    return `${ dataDateStart.year }`;
                },
            },
            {
                title: 'Неделя',
                formatedfield: (fct) => {
                    const dataWeek = getWeek(fct.periodStart);
                    return  `${ dataWeek.start.format('DD MMMM') + ' - ' + dataWeek.stop.format('DD MMMM') }`;
                },
            },
            {
                title: 'Потребление, газ',
                formatedfield: (fct) => { return `${fct.consumptionGas}` },
            },
            {
                title: 'Пробег, газ',
                formatedfield: (fct) => { return `${fct.distanceGas}` },
            },
            {
                title: 'Потребление, ДТ',
                formatedfield: (fct) => { return `${fct.consumptionDt}` },
            },
            {
                title: 'Пробег, ДТ',
                formatedfield: (fct) => { return `${fct.distanceDt}` },
            },
            {
                title: 'Сотрудник',
                formatedfield: (fct) => { return `${fct.user.lastName} ${fct.user.firstName}` },
            },
            {
                title: 'Действия',
                classHeadCell: 'sorting_desc_disabled sorting_asc_disabled text-right',
                classRowCell: 'text-right',
                formatedfield: (fct) => { 
                    return `<a href="javascript:void(0)" class="btn btn-link btn-warning btn-icon btn-sm edit" data-toggle="modal" data-target=".${this.nameModalWindow}"><i data-idedit=${fct.id} data-element="editButton" class="tim-icons icon-pencil"></i></a>
                            <a href="javascript:void(0)" class="btn btn-link btn-danger btn-icon btn-sm remove" data-toggle="modal" data-target=".${this.nameModalWindow}"><i data-iddelete=${fct.id} data-element="deleteButton" class="tim-icons icon-simple-remove"></i></a>`
                },
            },
        ]

        this.facts.sort( (a, b) => {
            if ( a.periodStart > b.periodStart ) return -1;
            if ( a.periodStart === b.periodStart ) return 0;
            if ( a.periodStart < b.periodStart ) return 1;            
        });

        return new CustomTable(this.headers, this.facts);
    }

    createForm( factToEdit ) {
        const optSubdiv = this.subdivisions.reduce( (concatArr, item) => {
            return concatArr.concat([{
                id: item.id,
                title: item.subdivisionName
            }])
        }, []);

        const yearRegExp = /(?<=_).*/;
        const year = factToEdit ? Number(factToEdit.week.match(yearRegExp)) : moment().year() ;

        const optWeek = getWeeksArray(year);
        this.formsItems = [
            {
                title: 'Подразделение',
                name: 'subdivisionId',
                type: 'selector',
                required: true,
                selectorItems: optSubdiv,
            },           
            {
                title: 'Неделя',
                name: 'week',                
                type: 'selector',
                required: true,
                selectorItems: optWeek,
            },
            {
                title: 'Потребление, газ',
                name: 'consumptionGas',
                required: true,
                type: 'number',
            },
            {
                title: 'Пробег, газ',
                name: 'distanceGas',
                required: true,
                type: 'number',
            },
            {
                title: 'Потребление, ДТ',
                name: 'consumptionDt',
                required: true,
                type: 'number',
            },
            {
                title: 'Пробег, ДТ',
                name: 'distanceDt',
                required: true,
                type: 'number',
            },

        ]
        this.formButtons = [
            {
                title: 'Сохранить',
                clas: 'btn-success btn-round float-right mr-5',
                //nameModalWindow: this.nameModalWindow,
                nameId: 'saveFact',
                type: 'submit', // закомментировать, чтобы увидеть ошибку отправки
                action: () => {
                    const nameEvent  = factToEdit ? 'saveExistFact' : 'saveNewFact';
                    const event = new CustomEvent(nameEvent);
                    this.factTable.element.dispatchEvent(event);
                }
            }
        ]

        return new ValidationForm({items: this.formsItems, showObj: factToEdit, formButtons: this.formButtons});
    }

    createModalWindow() {
        return new ModalWindow (this.nameModalWindow);
    }

    createToolbarButtons() {
        this.buttons = [
            {
                title: 'Добавить элемент плана', 
                ico: 'icon-minimal-right',
                clas: 'btn-success btn-round float-right mr-5',
                nameModalWindow: this.nameModalWindow,
                nameId: 'addUser',
                action: () => {
                    const event = new CustomEvent('show-form-add');
                    this.factTable.element.dispatchEvent(event);                    
                }
            }
        ]
        return new Toolbar(this.buttons);
    }

    async render() {
        const [facts, subdivisions] = await this.loadData();
        this.facts = facts;
        this.subdivisions = subdivisions;
        this.nameModalWindow = 'modal-window-facts';

        //this.createTempData();//TODO: delete, test data

        this.modalWindow = this.createModalWindow();

        this.factTable = this.createFactList();        

        this.toolbar = this.createToolbarButtons();        

        this.initEventListeners();
    }

    initEventListeners() {
        this.factTable.element.addEventListener('show-form-add', event => {            
            if (this.form) { this.form.remove(); }
            this.form = this.createForm();
            this.modalWindow.update(this.form.element);
        });

        this.factTable.element.addEventListener('click', event => {          
            switch (event.target.dataset.element) {
                case 'editButton': this.showWindowEditFact(event.target.dataset.idedit);
                    break;
                case 'deleteButton': this.showWindowDeleteFact(event.target.dataset.iddelete);
                    break;
            }
        });

        this.modalWindow.element.addEventListener('click', this.onModalWindowClick);

        this.factTable.element.addEventListener('saveExistFact', this.saveFact);

        this.factTable.element.addEventListener('saveNewFact',  this.saveFact);

        this.factTable.element.addEventListener('deleteFact',  this.deleteFact);

    }

    destroy() {
        this.remove();
    }
}