//== Class definition

var DatatableDataLocalDemo = function () {
	//== Private functions

	// demo initializer
	var demo = function () {

		var dataJSONArray = JSON.parse('[{"NUM":1,"nom":"YAHIAOUI" , "prenom":"Issam" , "specialite":"Système d information","etablissement":"ESI ex INI"},{"NUM":2,"nom":"AMARA" , "prenom":"Ziyad" , "specialite":"Electronique","etablissement":"INILEC"},{"NUM":3,"nom":"ZEMOURI" , "prenom":"mohamed amine" , "specialite":"Mecanique","etablissement":"Polytech"},{"NUM":4,"nom":"SETRERRAHMANE" , "prenom":"Ahmed Djallel Eddine" , "specialite":"Système d information","etablissement":"ESI ex INI"}]');

		var datatable = $('.m_datatable').mDatatable({
			// datasource definition
			data: {
				type: 'local',
				source: dataJSONArray,
				pageSize: 10
			},

			// layout definition
			layout: {
				theme: 'default', // datatable theme
				class: '', // custom wrapper class
				scroll: false, // enable/disable datatable scroll both horizontal and vertical when needed.
				// height: 450, // datatable's body's fixed height
				footer: false // display/hide footer
			},

			// column sorting
			sortable: true,

			pagination: true,

			search: {
				input: $('#generalSearch')
			},

			// inline and bactch editing(cooming soon)
			// editable: false,

			// columns definition
			columns: [{
				field: "NUM",
				title: "#",
				width: 50,
				sortable: false,
				selector: false,
				textAlign: 'center'
			}, {
				field: "nom",
				title: "Nom",
				width : 130
			}, {
				field: "prenom",
				title: "Prénom",
				width : 130
			}, {
				field: "specialite",
				title: "Spécialité",
				width: 150
			}, {
				field: "etablissement",
				title: "Etablissement"
			}, {
				field: "Actions",
				width: 100,
				title: "Actions",
				sortable: false,
				locked: {right: 'xl'},
				template: function (row) {
					return '\
					<a   name="/edit-op/' + row.num + '"  class="editOP btn m-btn--pill btn-outline-success btn-sm ">\
					<span>\
						<i  class="fa flaticon-dashboard"></i><span id="edit" >Evaluer</span>\
					</span>\
					</a>\
				';
				}
			}]
		});

		var query = datatable.getDataSourceQuery();

		$('#m_form_status').on('change', function () {
			datatable.search($(this).val(), 'Status');
		}).val(typeof query.Status !== 'undefined' ? query.Status : '');

		$('#m_form_type').on('change', function () {
			datatable.search($(this).val(), 'Type');
		}).val(typeof query.Type !== 'undefined' ? query.Type : '');

		$('#m_form_status, #m_form_type').selectpicker();

	};

	return {
		//== Public functions
		init: function () {
			// init dmeo
			demo();
		}
	};
}();

jQuery(document).ready(function () {
	DatatableDataLocalDemo.init();
});