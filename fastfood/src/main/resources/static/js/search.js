
JQuery(document).ready(function($){
	
	var searchShow = function() {
		// alert();
		var searchWrap = $('.search-wrap');
		$('.js-search-open').on('click', function(e){
			e.preventDefault();
			searchWrap.addClass('active');
			setTimeout(function(){
				searchWrap.find('.form-control').focus();
			}, 300);
		});
		
		$('.js-search-close').on('click', function(e){
			e.preventDefault();
			searchWrap.removeClass('active');
		})
	};
	
	searchShow();
	
	
	var sitePlusMinus = function() {
		$('.js-btn-minus').on('click', function(e){
			e.preventDefault();
			if ( $(this).closest('.input-group').find('.form-control').val() != 0  ) {
				$(this).closest('.input-group').find('.form-control').val(parseInt($(this).closest('.input-group').find('.form-control').val()) - 1);
			} else {
				$(this).closest('.input-group').find('.form-control').val(parseInt(0));
			}
		});
		$('.js-btn-plus').on('click', function(e){
			e.preventDefault();
			$(this).closest('.input-group').find('.form-control').val(parseInt($(this).closest('.input-group').find('.form-control').val()) + 1);
		});
	};
	sitePlusMinus();
	
});