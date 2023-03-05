import fetchJson from '../utils/fetch-json.js';
import getYearKvartal from '../utils/get-year-kvartal.js';
import getStrDateFromYearKvartal from '../utils/get-str-date-from-year-kvartal.js';

import CustomTable from '../components/CustomTable.js';
import Card from '../components/Card.js';
import Button from '../components/Button.js';
import Toolbar from '../components/Toolbar.js';
import ModalWindow from '../components/ModalWindow.js';
import ValidationForm from '../components/ValidationForm.js';
import {URL_BACKEND, URL_USERS, URL_SUBDIVISION, URL_PLANS} from '../globalVariables.js';

export default class PagePlan {

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

    showWindowEditPlan = (idEdit) => {
        const plan = Object.values( this.plans ).find( item => { return item.id == idEdit } );

        plan.subdivision = plan.subdivision ? plan.subdivision.id : null;
        const dataDateStart = getYearKvartal(plan.periodStart);
        plan.year = dataDateStart.year;
        plan.kvartal = dataDateStart.kvartal;

        if (this.form) { this.form.remove(); }
        this.form = this.createForm( plan );   
        this.modalWindow.update(this.form.element);
    }

    showWindowDeletePlan = (idEdit) => {
        const plan = Object.values( this.plans ).find( item => { return item.id == idEdit } );

        this.deleteButtons = [{
            title: 'Удалить',
            clas: 'btn-success btn-round float-right mr-5',
            nameId: 'deletePlan',
            //nameModalWindow: this.nameModalWindow,
            action: () => {
                const nameEvent  = 'deletePlan';
                const event = new CustomEvent(nameEvent, {
                    detail: {id: idEdit}
                });
                this.planTable.element.dispatchEvent(event);
            }
        }]
        const message = new Card(
            'Подтверждение удаления',
            `<p> Удалить запись?</p>`, 
            this.deleteButtons.map( button => new Button(button).element.outerHTML ).join('')
        );
        this.modalWindow.update(message.element);
    }

    deletePlan = (event) => {
        this.deleteData(event.detail.id)
            .then(resolve => { console.log(resolve) });
    }
    
    formattedDataforSave (item) {
        item.subdivisionId = item.subdivision;
        item.creationTime = "{{start}}";

        const dates = getStrDateFromYearKvartal(item.year, item.kvartal);
        item.periodStart = dates.start;
        item.periodEnd = dates.end;
        
        item.userId = 1; //TODO: дописать сохранение записи под авторизованным пользователем

        delete item.kvartal;
        delete item.year;
        delete item.subdivision;

        return item;
    }

    savePlan = (event) => {
        const plan = this.formattedDataforSave( this.form.getFormData() );

        if (    plan.periodStart 
                && plan.periodEnd 
                && plan.userId  
                && plan.planDistanceDt > 0
                && plan.planDistanceGas > 0
                && plan.planConsumptionGas > 0
                && plan.planConsumptionDt > 0
            ) {  
            this.saveData(plan)
                .then(resolve => { console.log(resolve) });
        }
    }

    async saveData( pln ) { 
        const urlsavePlan = new URL (URL_PLANS, URL_BACKEND);
        
        /*$.ajax({
            type: "POST",
            url: urlsavePlan,
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
            const result = await fetchJson( urlsavePlan, {
                method: pln.id ? 'PATCH' : 'POST',
                items: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(pln)
            });
            console.log(result);
            //this.dispatchEventAfterSave(result.id);
        } catch (error) {
            console.error('Ошибка сохранения информации об элементе плана:', error);
        }
    }

    async deleteData (planId) {
        console.log('Удаление элемента плана id ', planId);
        const urldeletePlan = new URL (URL_PLANS + '/' + planId, URL_BACKEND);

        /*$.ajax({
            type: "DELETE",
            url: urldeletePlan,
            //data: JSON.stringify( {"id": planId} ),
            success: function(dataResult) {                
                let ts = JSON.parse(dataResult);            
                console.log(ts)
            },
            error: function(er) {
                console.log(er);
            }
        });*/

        try {
            const result = await fetchJson( urldeletePlan, {
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
        const urlUsers = new URL(URL_PLANS, URL_BACKEND);
        const urlSubdivisions = new URL(URL_SUBDIVISION, URL_BACKEND);
        const urlPlans = new URL(URL_PLANS, URL_BACKEND);

        try {            
            const promisePlans = fetchJson(urlPlans);
            const promiseSubdivision = fetchJson(urlSubdivisions);

            const [plans, subdivisions] = await Promise.all( [promisePlans, promiseSubdivision] );

            return [plans, subdivisions]; // при расширении этого массива ниже в catch return  добавить пустой массив

        } catch (error) {
            console.error (error);
            return [ [] ];
        }
    }
    
    //TODO: delete, test data
    createTempData() {        
        this.plans = [
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
                "creationTime": "2022-11-16",
                "periodStart": "2022-01-01",
                "periodEnd": "2022-03-31",
                "planConsumptionGas": 155.0,
                "planDistanceGas": 23.0,
                "planConsumptionDt": 55.0,
                "planDistanceDt": 0.0
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
                "periodStart": "2022-04-01",
                "periodEnd": "2022-06-30",
                "planConsumptionGas": 255.0,
                "planDistanceGas": 223.0,
                "planConsumptionDt": 255.0,
                "planDistanceDt": 225.0
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
                "periodStart": "2022-07-01",
                "periodEnd": "2022-09-30",
                "planConsumptionGas": 355.0,
                "planDistanceGas": 323.0,
                "planConsumptionDt": 355.0,
                "planDistanceDt": 325.0
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
                "periodStart": "2022-10-01",
                "periodEnd": "2022-12-31",
                "planConsumptionGas": 455.0,
                "planDistanceGas": 423.0,
                "planConsumptionDt": 455.0,
                "planDistanceDt": 425.0
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
                "creationTime": "2023-01-01",
                "periodStart": "2023-04-01",
                "periodEnd": "2023-06-30",
                "planConsumptionGas": 255.0,
                "planDistanceGas": 223.0,
                "planConsumptionDt": 255.0,
                "planDistanceDt": 225.0
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
                "periodStart": "2023-07-01",
                "periodEnd": "2023-09-30",
                "planConsumptionGas": 355.0,
                "planDistanceGas": 323.0,
                "planConsumptionDt": 355.0,
                "planDistanceDt": 325.0
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
                "periodStart": "2023-10-01",
                "periodEnd": "2023-12-31",
                "planConsumptionGas": 455.0,
                "planDistanceGas": 423.0,
                "planConsumptionDt": 455.0,
                "planDistanceDt": 425.0
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
                "periodStart": "2023-01-01",
                "periodEnd": "2023-03-31",
                "planConsumptionGas": 155.0,
                "planDistanceGas": 23.0,
                "planConsumptionDt": 55.0,
                "planDistanceDt": 0.0
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

    createPlanList() {
        this.headers = [
            {
                title: 'Подразделение',
                formatedfield: (pln) => { return `${pln.subdivision.subdivisionName}` },
            },
            {
                title: 'Год',
                formatedfield: (pln) => {
                    const dataDateStart = getYearKvartal(pln.periodStart);
                    return `${ dataDateStart.year }` 
                },
            },
            {
                title: 'Квартал',
                formatedfield: (pln) => {
                    const dataDateStart = getYearKvartal(pln.periodStart);                
                    return `${ dataDateStart.kvartal }`;
                },
            },
            {
                title: 'Потребление, газ',
                formatedfield: (pln) => { return `${pln.planConsumptionGas}` },
            },
            {
                title: 'Пробег, газ',
                formatedfield: (pln) => { return `${pln.planDistanceGas}` },
            },
            {
                title: 'Потребление, ДТ',
                formatedfield: (pln) => { return `${pln.planConsumptionDt}` },
            },
            {
                title: 'Пробег, ДТ',
                formatedfield: (pln) => { return `${pln.planDistanceDt}` },
            },
            {
                title: 'Сотрудник',
                formatedfield: (pln) => { return `${pln.user.lastName} ${pln.user.firstName}` },
            },
            {
                title: 'Действия',
                classHeadCell: 'sorting_desc_disabled sorting_asc_disabled text-right',
                classRowCell: 'text-right',
                formatedfield: (pln) => { 
                    return `<a href="javascript:void(0)" class="btn btn-link btn-warning btn-icon btn-sm edit" data-toggle="modal" data-target=".${this.nameModalWindow}"><i data-idedit=${pln.id} data-element="editButton" class="tim-icons icon-pencil"></i></a>
                            <a href="javascript:void(0)" class="btn btn-link btn-danger btn-icon btn-sm remove" data-toggle="modal" data-target=".${this.nameModalWindow}"><i data-iddelete=${pln.id} data-element="deleteButton" class="tim-icons icon-simple-remove"></i></a>`
                },
            },
        ]

        this.plans.sort( (a, b) => {
            const {month: monthA, year: yearA} = getYearKvartal(a.periodStart);
            const {month: monthB, year: yearB} = getYearKvartal(b.periodStart);
            if (yearA > yearB) return -1;
            if (yearA < yearB) return 1;
            if (yearA === yearB)  return monthA - monthB;
        });

        return new CustomTable(this.headers, this.plans);
    }

    createForm( planToEdit ) {
        const optSubdiv = this.subdivisions.reduce( (concatArr, item) => {
            return concatArr.concat([{
                id: item.id,
                title: item.subdivisionName
            }])
        }, []);
        const optKvartal = [
            {
                id: 1,
                title: "1 квартал"
            },
            {
                id: 2,
                title: "2 квартал"
            },
            {
                id: 3,
                title: "3 квартал"
            },
            {
                id: 4,
                title: "4 квартал"
            },
        ]
        this.formsItems = [
            {
                title: 'Подразделение',
                name: 'subdivision',
                type: 'selector',
                required: true,
                selectorItems: optSubdiv,
            },           
            {
                title: 'Год',
                name: 'year',                
                type: 'number',
                required: true,
            },
            {
                title: 'Квартал',
                name: 'kvartal',                
                type: 'selector',
                required: true,
                selectorItems: optKvartal,
            },
            {
                title: 'Потребление, газ',
                name: 'planConsumptionGas',
                required: true,
                type: 'number',
            },
            {
                title: 'Пробег, газ',
                name: 'planDistanceGas',
                required: true,
                type: 'number',
            },
            {
                title: 'Потребление, ДТ',
                name: 'planConsumptionDt',
                required: true,
                type: 'number',
            },
            {
                title: 'Пробег, ДТ',
                name: 'planDistanceDt',
                required: true,
                type: 'number',
            },
            
        ]
        this.formButtons = [
            {
                title: 'Сохранить',
                clas: 'btn-success btn-round float-right mr-5',
                //nameModalWindow: this.nameModalWindow,
                nameId: 'savePlan',
                type: 'submit', // закомментировать, чтобы увидеть ошибку отправки
                action: () => {
                    const nameEvent  = planToEdit ? 'saveExistPlan' : 'saveNewPlan';
                    const event = new CustomEvent(nameEvent);
                    this.planTable.element.dispatchEvent(event); 
                }
            }
        ]

        return new ValidationForm({items: this.formsItems, showObj: planToEdit, formButtons: this.formButtons});
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
                    this.planTable.element.dispatchEvent(event);                    
                }
            }
        ]
        return new Toolbar(this.buttons);
    }

    async render() {
        const [plans, subdivisions] = await this.loadData();
        this.plans = plans;
        this.subdivisions = subdivisions;
        this.nameModalWindow = 'modal-window-plans';

        //this.createTempData();//TODO: delete, test data

        this.modalWindow = this.createModalWindow();

        this.planTable = this.createPlanList();        

        this.toolbar = this.createToolbarButtons();        

        this.initEventListeners();
    }

    initEventListeners() {
        this.planTable.element.addEventListener('show-form-add', event => {            
            if (this.form) { this.form.remove(); }
            this.form = this.createForm();      
            this.modalWindow.update(this.form.element);
        });

        this.planTable.element.addEventListener('click', event => {          
            switch (event.target.dataset.element) {
                case 'editButton': this.showWindowEditPlan(event.target.dataset.idedit);
                    break;
                case 'deleteButton': this.showWindowDeletePlan(event.target.dataset.iddelete);
                    break;
            }
        });

        this.modalWindow.element.addEventListener('click', this.onModalWindowClick);

        this.planTable.element.addEventListener('saveExistPlan', this.savePlan);

        this.planTable.element.addEventListener('saveNewPlan',  this.savePlan);

        this.planTable.element.addEventListener('deletePlan',  this.deletePlan);

    }

    destroy() {
        this.remove();
    }
}