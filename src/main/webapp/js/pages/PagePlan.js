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
        if (this.deleteButtons && !button ) {
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
            .then(resolve => { /*console.log(resolve)*/ });
    }
    
    formattedDataforSave (item) {
        item.subdivisionId = item.subdivision;
        item.creationTime = moment().format('YYYY-MM-DD');

        if ( !item.periodStart || !item.periodEnd) {
            const dates = getStrDateFromYearKvartal(item.year, item.kvartal);
            item.periodStart = dates.start;
            item.periodEnd = dates.end;
        }
        
        item.userId = localStorage.getItem("authid");

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
                    const newItem =  this.formattedDataforSave(updateItem.item);
                    newItem.subdivision = newItem.subdivisionId;

                    this.plans.push(newItem);
                    this.sortingPlansArr();

                    this.planTable.addRow(newItem);
                break;
                case "update":
                    let indexForEdit = null;
                    this.plans.map( (pln, index) => {
                        if (pln.id == updateItem.item.id) { // === не ставить, неявное сравнение
                            indexForEdit = index;
                        }
                    });

                    const updItem = this.formattedDataforSave(updateItem.item);
                    updItem.subdivision = updItem.subdivisionId;

                    this.plans.splice(indexForEdit, 1, updItem);
                    //this.sortingPlansArr ();

                    this.planTable.updateRow(updItem);
                break;
                case "delete":                    
                    let indexForDelete = null;
                    this.plans.map( (pln, index) => {
                        if (pln.id == updateItem.id) { // === не ставить, неявное сравнение
                            indexForDelete = index;                           
                        }
                    });

                    this.plans.splice(indexForDelete, 1);
                    this.planTable.deleteRow(updateItem.id);
                break;
            }
        }
    }

    async saveData( pln ) { 

        const idForEdit = pln.id ? '/'+pln.id : '';

        const urlsavePlan = new URL (URL_PLANS + idForEdit, URL_BACKEND);
        
        try {
            const result = await fetchJson( urlsavePlan, {
                method: pln.id ? 'PATCH' : 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },                
                body: JSON.stringify(pln)
            });

            this.dispatchEventAfterEdit (
                "Успешно сохранено",
                "info",
                "icon-bell-55",
                true,
                {   action: pln.id ? 'update' : 'save', 
                    item: result
                }
            );

        } catch (error) {
            console.error('Ошибка сохранения информации об элементе плана:', error);
            this.dispatchEventAfterEdit ("Ошибка сохранения элемента плана: " + error, "danger", "icon-bell-55");
        }
    }

    async deleteData (planId) {
        const urldeletePlan = new URL (URL_PLANS + '/' + planId, URL_BACKEND);

        try {
            const result = await fetchJson( urldeletePlan, {
                method: 'DELETE',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }                
            });
            this.dispatchEventAfterEdit ("Успешно удалено", "info", "icon-bell-55", true, {action: "delete", id: planId});

        } catch (error) {
            console.error('Ошибка удаления элемента плана:', error);
            this.dispatchEventAfterEdit ("Ошибка удаления элемента плана: " + error, "danger", "icon-bell-55", true);
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

        this.sortingPlansArr();

        return new CustomTable(this.headers, this.plans);
    }

    sortingPlansArr () {
        this.plans.sort( (a, b) => {            
            if (a.subdivision.subdivisionName > b.subdivision.subdivisionName )  return 1;
            if (a.subdivision.subdivisionName < b.subdivision.subdivisionName )  return -1;

            const {month: monthA, year: yearA} = getYearKvartal(a.periodStart);
            const {month: monthB, year: yearB} = getYearKvartal(b.periodStart);
            if (yearA > yearB) return -1;
            if (yearA < yearB) return 1;
            if (yearA === yearB)  return monthA - monthB;
        });
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
                //type: 'submit', // закомментировать, чтобы увидеть ошибку отправки
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
        return new ModalWindow (this.nameModalWindow, "Плановые показатели");
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

        this.modalWindow.element.addEventListener('afterSave', this.afterSave);

        this.planTable.element.addEventListener('saveExistPlan', this.savePlan);

        this.planTable.element.addEventListener('saveNewPlan',  this.savePlan);

        this.planTable.element.addEventListener('deletePlan',  this.deletePlan);

    }

    destroy() {
        this.remove();
    }
}