/*headers
{
    title: 'E-mail',
    formatedfield: (usr) => { return `${usr.email}` },
},
{
    title: 'Действия',
    classHeadCell: 'sorting_desc_disabled sorting_asc_disabled text-right',
    classRowCell: 'text-right',
    formatedfield: () => { 
        return `<a href="javascript:void(0)" class="btn btn-link btn-warning btn-icon btn-sm edit"><i class="tim-icons icon-pencil"></i></a>
                <a href="javascript:void(0)" class="btn btn-link btn-danger btn-icon btn-sm remove"><i class="tim-icons icon-simple-remove"></i></a>`
    },
},
*/

export default class CustomTable {
    constructor(
        headers = [...
            {
                title,
                classHeadCell,
                classRowCell,
                formatedfield
            }   
        ] = [],
        data = []
    ){
        this.headers = headers;
        this.data = data;

        this.render();
    }

    getHeaderCell(head) {
        return `<th ${ head.classHeadCell ? 'class="'+head.classHeadCell+'"' : ''}>${head.title}</th>`
    }

    getBodyRow(data) {
        return `<tr data-id=${data.id}>
                ${ this.headers.map( head => {
                    return `
                    <td  ${ head.classRowCell ? 'class="'+head.classRowCell+'"' : ''}>
                        ${ head.formatedfield(data) } 
                    </td>`;
                }).join('') }
                </tr>`;
    }

    getTemplate() {
        return `            
            <table id="datatable" class="table table-striped">
                <thead>
                    <tr>
                    ${ this.headers.map( curHead => this.getHeaderCell(curHead)).join('') }
                    </tr>
                </thead>
                <tbody data-element="tableBody">
                    ${ this.data.map( curData => this.getBodyRow(curData)).join('') }
                </tbody>
                <tfoot>
				</tfoot>
            </table>            
        `;
    }

    render() {
        const element = document.createElement("div"); 
        element.innerHTML = this.getTemplate();        
        this.element = element.firstElementChild;

        this.subElements = this.getSubElements(this.element);

        //this.initEventListeners();

        return this.element;
    }

    
    getSubElements(element) {
        const subElements = {};
        const elements = element.querySelectorAll('[data-id]');

        for (const item of elements) {
            subElements[item.dataset.id] = item;
        }

        return subElements;
    }

    remove() {
        this.element.remove();
        this.subElements = null;
    }

    destroy() {
        this.remove();
        this.element = null;
        this.subElements = null;
    }
}