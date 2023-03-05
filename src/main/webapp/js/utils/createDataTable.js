export default function createDataTable (header = [], data = []) {
	
	const element = new DataTable( {
		language: {
				"lengthMenu": "Записей на странице _MENU_",
				"zeroRecords": "Ничего не найдено, извините",
				"info": "Показано страниц _PAGE_ из _PAGES_",
				"infoEmpty": "Нет данных",
				"infoFiltered": "(фильтр по _MAX_ кол-ву записей)",
				"paginate": {
					"first": "Первая",
					"last": "Последняя",
					"next": "Следующая",
					"previous": "Предыдущая"
				},
				"search": "Поиск",
			},
		pagination: "bootstrap",
		filter:true,
		data: data,
		destroy: true,
		lengthMenu:[20,40,200],
		pageLength: 40, 
		order: [[ 3, "desc" ]],
		bAutoWidth: false,//отключаем автоширину столбцов			
		columns: header	// наименование столбцов
	} );

	return element;
}