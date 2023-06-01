import fetchJson from '../utils/fetch-json.js';
import getYearKvartal from '../utils/get-year-kvartal.js';

import PlanFactDashboardDark from '../components/PlanFactDashboardDark.js';
import FactDashboardDark from '../components/FactDashboardDark.js';
import CustomTable from '../components/CustomTable.js';
import Toolbar from '../components/Toolbar.js';
import {URL_BACKEND, URL_SUBDIVISION, URL_FACTS, URL_PLANS} from '../globalVariables.js';

export default class PageDashboard {

    constructor(
        consumptionGasDash = 'consumptionGasDash',
        millageGasDash = 'millageGasDash',
        consumptionDtDash = 'consumptionDtDash',
        millageDTDash = 'millageDTDash',
        factDash = 'factDash'
    ){
        this.namesDOM = {
            'consumptionGas': consumptionGasDash,
            'millageGas': millageGasDash,
            'consumptionDt': consumptionDtDash,
            'millageDT': millageDTDash
        };
        this.namesSQL = {
            'consumptionGas': 'ConsumptionGas',
            'millageGas': 'DistanceGas',
            'consumptionDt': 'ConsumptionDt',
            'millageDT': 'DistanceDt'
        }
        this.factDashName = factDash;
    }

    async loadSubdivisions() {        
        const urlSubdivisions = new URL(URL_SUBDIVISION, URL_BACKEND);
        try {            
            const promiseSubdivision = fetchJson(urlSubdivisions);
            const subdivisions = await promiseSubdivision;
            return subdivisions; // при расширении этого массива ниже в catch return  добавить пустой массив
        } catch (error) {
            console.error (error);
            return [];
        }
    }

    async loadData( subID, periodStart, periodEnd ) {
        const urlFacts = new URL(URL_FACTS + `?subdivisionId=${subID}&periodStart=${periodStart}&periodEnd=${periodEnd}`, URL_BACKEND);
        const urlPlans = new URL(URL_PLANS + `?subdivisionId=${subID}&periodStart=${periodStart}&periodEnd=${periodEnd}`, URL_BACKEND);

        try {            
            const promiseFacts = fetchJson(urlFacts);
            const promisePlans = fetchJson(urlPlans);

            const [facts, plans] = await Promise.all( [promiseFacts, promisePlans] );

            return [facts, plans]; // при расширении этого массива ниже в catch return  добавить пустой массив

        } catch (error) {
            console.error (error);
            return [ [],[] ];
        }
    }

    calculationDataForPlanFactDash (data, type) {
        data.forEach( (record ) => {            
            const indexKvartal = getYearKvartal(record.periodStart).kvartal - 1 ;
            Object.values(this.namesSQL).forEach( sqlName => {
                if ( !this.data[type + sqlName] ) {
                    this.data[type + sqlName] = [];
                }       
                if ( !this.data[type + sqlName][indexKvartal]  ) {
                    this.data[type + sqlName][indexKvartal]  = 0;
                } 
                //в базе наименований полей плана и факта начинаются с раного регситри префикса, поэтому подсчет значений разными case
                switch (type) {                    
                    case 'plan':    this.data[type + sqlName][indexKvartal] = record[type + sqlName];
                                    break;
                    case 'fact':    const lowSQLName = sqlName[0].toLowerCase() + sqlName.slice(1); 
                                    this.data[type + sqlName][indexKvartal] += record[lowSQLName];
                                    break;
                }
            });
            this.dataDashUndefinedTo0(type);
        });

        if ( data.length === 0) {
            Object.values(this.namesSQL).forEach( sqlName => {
                this.data[type + sqlName] = [0, 0, 0, 0];
            });
        }
    }

    calculationDataForFactDash(dataFact) {
        this.dataForDashFact = { 
            'consumptionGas': [], 
            'millageGas': [],
            'consumptionDt': [],
            'millageDT':[],
            'label': []
        };
        dataFact.map( fct => {
            this.dataForDashFact.consumptionGas.push(fct.consumptionGas);
            this.dataForDashFact.millageGas.push(fct.distanceGas);
            this.dataForDashFact.consumptionDt.push(fct.consumptionDt);
            this.dataForDashFact.millageDT.push(fct.distanceDt);
            this.dataForDashFact.label.push(fct.periodStart);
        })
    }

    dataDashUndefinedTo0(prefix) {
        Object.values(this.namesSQL).forEach( sqlName => {
            for (let i =0; i < 4; i++ ){
                if ( !this.data[prefix + sqlName][i] ) {
                    this.data[prefix + sqlName][i] = 0;
                }
            }
        });
    }

    fillingData( [dataFact, dataPlan] ) {
        this.data = {}; 
        this.calculationDataForPlanFactDash(dataPlan, 'plan');
        this.calculationDataForPlanFactDash(dataFact, 'fact');

        this.calculationDataForFactDash(dataFact);
    }

    createDashes() {

        for (const [key, value] of Object.entries(this.namesDOM)) {
            this[value] = new PlanFactDashboardDark({
                canvasName: value,
                dataPlan: [0, 0, 0, 0],
                dataFact: [0, 0, 0, 0]
            });
        };

        this[this.factDashName] = new FactDashboardDark({
            canvasName: this.factDashName,
            consumptionGas: [0, 0, 0, 0],
            millageGas: [0, 0, 0, 0],
            consumptionDt: [0, 0, 0, 0],
            millageDT: [0, 0, 0, 0]
        });
    }

    createToolbarButtonsSubdiv() {
        this.buttons = [];

        this.subdivisions.forEach(subdiv => {
            const curSub = {
                title: subdiv.subdivisionName,
                clas: 'btn btn-sm btn-info btn-simple',
                nameId: subdiv.id
            }
            this.buttons.push(curSub);
        });

        const grouppedButton = true;

        return new Toolbar(this.buttons, grouppedButton);
    }

    createToolbarButtonsYear() {
        this.buttons = [];

        const curYear = moment().year();

        [curYear-2, curYear-1, curYear, curYear+1].forEach(year => {
            const buttonYear = {
                title: year,
                clas: 'btn btn-sm btn-info btn-simple',
                nameId: year
            }
            this.buttons.push(buttonYear);
        });

        const grouppedButton = true;

        return new Toolbar(this.buttons, grouppedButton);
    }

    createSummaryTable() {
        this.summaryData = [
            {
                title: 'Потребление, газ',
                plan: this.data['plan'+this.namesSQL['consumptionGas']].reduce((sum, current) => sum + current, 0),
                fact: this.data['fact'+this.namesSQL['consumptionGas']].reduce((sum, current) => sum + current, 0)
            },
            {
                title: 'Пробег, газ',
                plan: this.data['plan'+this.namesSQL['millageGas']].reduce((sum, current) => sum + current, 0),
                fact: this.data['fact'+this.namesSQL['millageGas']].reduce((sum, current) => sum + current, 0)
            },
            {
                title: 'Потребление, ДТ',
                plan: this.data['plan'+this.namesSQL['consumptionDt']].reduce((sum, current) => sum + current, 0),
                fact: this.data['fact'+this.namesSQL['consumptionDt']].reduce((sum, current) => sum + current, 0)
            },
            {
                title: 'Пробег, ДТ',
                plan: this.data['plan'+this.namesSQL['millageDT']].reduce((sum, current) => sum + current, 0),
                fact: this.data['fact'+this.namesSQL['millageDT']].reduce((sum, current) => sum + current, 0)
            },
        ];

        const headers = [
            {
                title: 'Год',
                formatedfield: (item) => { return `${item.title}` },
            },
            {
                title: 'План',
                formatedfield: (item) => { return `${item.plan}` },
            },
            {
                title: 'Факт',
                formatedfield: (item) => { return `${item.fact}` },
            },
            {
                title: 'Исполнение',
                formatedfield: (item) => { 

                    let procent = Math.round(item.fact / item.plan * 100);
                    if ( isNaN(procent) ) procent = 0;

                    return `
                    <div class="progress-container progress-sm progress-success">
                        <div class="progress">
                            <span class="progress-value">${ procent }%</span>
                            <div class="progress-bar" role="progressbar" aria-valuenow="${procent}" aria-valuemin="0" aria-valuemax="100" style="width: ${procent}%;"></div>
                        </div>
                    </div>` 
                },
            },
        ];

        return new CustomTable(headers, this.summaryData);
    }

    updateDashes() {
        for (const [key, value] of Object.entries(this.namesDOM)) {
            const dashDataFactPlan = this[value].dash.config.data;
            dashDataFactPlan.datasets[0].data = this.data['plan' + this.namesSQL[key]];
            dashDataFactPlan.datasets[1].data = this.data['fact' + this.namesSQL[key]];
            this[value].dash.update();
        };

        const dashDataFact = this[this.factDashName].dash.config.data;
        dashDataFact.datasets[0].data = this.dataForDashFact.consumptionGas;
        dashDataFact.datasets[1].data = this.dataForDashFact.millageGas;
        dashDataFact.datasets[2].data = this.dataForDashFact.consumptionDt;
        dashDataFact.datasets[3].data = this.dataForDashFact.millageDT;
        dashDataFact.labels = this.dataForDashFact.label;
        this[this.factDashName].dash.update();
    }

    insertSummaryTable() {
        if (this.summaryTable) this.summaryTable.remove();
        this.summaryTable = this.createSummaryTable();
        const appendInto = document.querySelector('[data-element="summaryTable"]');
        appendInto.append(this.summaryTable.element);
    }

    async update( subID, year ) {

        this.currentSubID = subID;
        this.currentYear = year;

        const data = await this.loadData( subID, year+'-01-01', year+'-12-31');

        this.fillingData(data);

        this.updateDashes();

        this.insertSummaryTable();

    }

    async render() {

        this.subdivisions = await this.loadSubdivisions();

        this.currentSubID = this.subdivisions[0].id;
        this.currentYear = moment().year();

        this.toolbarSubdiv = this.createToolbarButtonsSubdiv();
        this.toolbarYear = this.createToolbarButtonsYear();
        this.toolbarSubdiv.subElements[this.currentSubID].classList.add('active');
        this.toolbarYear.subElements[this.currentYear].classList.add('active');

        this.createDashes();

        this.update(this.currentSubID, this.currentYear);

        this.initEventListeners();
    }

    initEventListeners() {

        this.toolbarSubdiv.element.addEventListener('click', event => {
            Object.values( this.toolbarSubdiv.subElements ).forEach( button => {
                button.classList.remove('active');
            });
            event.target.classList.add('active');

            const newSubdivision = Number(event.target.dataset.element);
            this.update( newSubdivision, this.currentYear);            
        });

        this.toolbarYear.element.addEventListener('click', event => {

            Object.values( this.toolbarYear.subElements ).forEach( button => {
                button.classList.remove('active');
            });
            event.target.classList.add('active');


            const newYear = Number(event.target.dataset.element);
            this.update( this.currentSubID, newYear);            
        });
    }

    destroy() {
        this.remove();
    }
}