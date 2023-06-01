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
        if (this.deleteButtons && !button ) {
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
            .then(resolve => { /*console.log(resolve)*/ });
    }
    
    formattedDataforSave (item) {
        //item.subdivisionId =  item.subdivision;
        item.creationTime = moment().format('YYYY-MM-DD');

        if ( !item.periodStart || !item.periodEnd) {
            const yearRegExp = /(?<=_).*/;
            const year = Number(item.week.match(yearRegExp));

            const weeks = getWeeksArray (year);
            const saveWeek = weeks.find( curWeek => {return curWeek.id === item.week} );
            item.periodStart = saveWeek.startDate.format('YYYY-MM-DD');
            item.periodEnd = saveWeek.stopDate.format('YYYY-MM-DD');
        }

        item.userId = localStorage.getItem("authid");

        delete item.week;
        //delete item.subdivision;

        return item;
    }

    saveFact = (event) => {
        const fact = this.formattedDataforSave( this.form.getFormData() );

        if (    fact.periodStart 
                && fact.periodEnd 
                && fact.userId  
                && fact.distanceDt > 0
                && fact.distanceGas > 0
                && fact.consumptionGas > 0
                && fact.consumptionDt > 0
            ) {  
            this.saveData(fact)
                .then(resolve => { /*console.log("succes", resolve)*/ });
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

                    this.facts.push(newItem);

                    this.sortingFactsArr();

                    this.factTable.addRow(newItem);
                break;
                case "update":
                    let indexForEdit = null;
                    this.facts.map( (fct, index) => {
                        if (fct.id == updateItem.item.id) { // === не ставить, неявное сравнение
                            indexForEdit = index;
                        }
                    });

                    const updItem = this.formattedDataforSave(updateItem.item);

                    this.facts.splice(indexForEdit, 1, updItem);
                    this.sortingFactsArr();

                    this.factTable.updateRow(updItem);
                break;
                case "delete":                    
                    let indexForDelete = null;
                    this.facts.map( (fct, index) => {
                        if (fct.id == updateItem.id) { // === не ставить, неявное сравнение
                            indexForDelete = index;                           
                        }
                    });

                    this.facts.splice(indexForDelete, 1);
                    this.factTable.deleteRow(updateItem.id);
                break;
            }
        }
    }

    async saveData( fct ) {

        const idForEdit = fct.id ? '/'+fct.id : '';

        const urlsaveFact = new URL (URL_FACTS + idForEdit, URL_BACKEND);

        try {
            const result = await fetchJson( urlsaveFact, {
                method: fct.id ? 'PATCH' : 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },                
                body: JSON.stringify(fct)
            });

            this.dispatchEventAfterEdit (
                "Успешно сохранено",
                "info",
                "icon-bell-55",
                true,
                {   action: fct.id ? 'update' : 'save', 
                    item: result
                }
            );
            
        } catch (error) {
            console.error('Ошибка сохранения информации об элементе плана:', error);
            this.dispatchEventAfterEdit ("Ошибка сохранения элемента плана: " + error, "danger", "icon-bell-55");
        }
    }

    async deleteData (factId) {
        const urldeleteFact = new URL (URL_FACTS + '/' + factId, URL_BACKEND);

        try {
            const result = await fetchJson( urldeleteFact, {
                method: 'DELETE',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }                
            });
            this.dispatchEventAfterEdit ("Успешно удалено", "info", "icon-bell-55", true, {action: "delete", id: factId});

        } catch (error) {
            console.error('Ошибка удаления элемента плана:', error);
            this.dispatchEventAfterEdit ("Ошибка удаления элемента плана: " + error, "danger", "icon-bell-55", true);
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

        this.sortingFactsArr();

        //console.log(this.facts);

        return new CustomTable(this.headers, this.facts, 'factListtest');
    }

    sortingFactsArr() {
        this.facts.sort( (a, b) => {
            if (a.subdivision.subdivisionName > b.subdivision.subdivisionName )  return 1;
            if (a.subdivision.subdivisionName < b.subdivision.subdivisionName )  return -1;

            if ( a.periodStart > b.periodStart ) return -1;
            if ( a.periodStart === b.periodStart ) return 0;
            if ( a.periodStart < b.periodStart ) return 1;            
        });
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
                //type: 'submit', // закомментировать, чтобы увидеть ошибку отправки
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
        return new ModalWindow (this.nameModalWindow, "Еженедельные показатели");
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

        this.modalWindow.element.addEventListener('afterSave', this.afterSave);

        this.factTable.element.addEventListener('saveExistFact', this.saveFact);

        this.factTable.element.addEventListener('saveNewFact',  this.saveFact);

        this.factTable.element.addEventListener('deleteFact',  this.deleteFact);

    }

    destroy() {
        this.remove();
    }
}