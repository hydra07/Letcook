(function($) {
    "use strict";
	
	/* ..............................................
	   Loader 
	   ................................................. */
	$(window).on('load', function() {
		$('.preloader').fadeOut();
		$('#preloader').delay(550).fadeOut('slow');
		$('body').delay(450).css({
			'overflow': 'visible'
		});
	});

	/* ..............................................
	   Fixed Menu
	   ................................................. */

	$(window).on('scroll', function() {
		if ($(window).scrollTop() > 50) {
			$('.main-header').addClass('fixed-menu');
		} else {
			$('.main-header').removeClass('fixed-menu');
		}
	});

	/* ..............................................
	   Gallery
	   ................................................. */

	$('#slides-shop').superslides({
		inherit_width_from: '.cover-slides',
		inherit_height_from: '.cover-slides',
		play: 5000,
		animation: 'fade',
	});

	$(".cover-slides ul li").append("<div class='overlay-background'></div>");

	/* ..............................................
	   Map Full
	   ................................................. */

	$(document).ready(function() {
		$(window).on('scroll', function() {
			if ($(this).scrollTop() > 100) {
				$('#back-to-top').fadeIn();
			} else {
				$('#back-to-top').fadeOut();
			}
		});
		$('#back-to-top').click(function() {
			$("html, body").animate({
				scrollTop: 0
			}, 600);
			return false;
		});
	});

	/* ..............................................
	   Special Menu
	   ................................................. */

	var Container = $('.container');
	Container.imagesLoaded(function() {
		var portfolio = $('.special-menu');
		portfolio.on('click', 'button', function() {
			$(this).addClass('active').siblings().removeClass('active');
			var filterValue = $(this).attr('data-filter');
			$grid.isotope({
				filter: filterValue
			});
		});
		var $grid = $('.special-list').isotope({
			itemSelector: '.special-grid'
		});
	});

	/* ..............................................
	   BaguetteBox
	   ................................................. */

	baguetteBox.run('.tz-gallery', {
		animation: 'fadeIn',
		noScrollbars: true
	});

	/* ..............................................
	   Offer Box
	   ................................................. */

	$('.offer-box').inewsticker({
		speed: 3000,
		effect: 'fade',
		dir: 'ltr',
		font_size: 13,
		color: '#ffffff',
		font_family: 'Montserrat, sans-serif',
		delay_after: 1000
	});

	/* ..............................................
	   Tooltip
	   ................................................. */

	$(document).ready(function() {
		$('[data-toggle="tooltip"]').tooltip();
	});

	/* ..............................................
	   Owl Carousel Instagram Feed
	   ................................................. */

	$('.main-instagram').owlCarousel({
		loop: true,
		margin: 0,
		dots: false,
		autoplay: true,
		autoplayTimeout: 3000,
		autoplayHoverPause: true,
		navText: ["<i class='fas fa-arrow-left'></i>", "<i class='fas fa-arrow-right'></i>"],
		responsive: {
			0: {
				items: 2,
				nav: true
			},
			600: {
				items: 3,
				nav: true
			},
			1000: {
				items: 5,
				nav: true,
				loop: true
			}
		}
	});

	/* ..............................................
	   Featured Products
	   ................................................. */

	$('.featured-products-box').owlCarousel({
		loop: true,
		margin: 15,
		dots: false,
		autoplay: true,
		autoplayTimeout: 3000,
		autoplayHoverPause: true,
		navText: ["<i class='fas fa-arrow-left'></i>", "<i class='fas fa-arrow-right'></i>"],
		responsive: {
			0: {
				items: 1,
				nav: true
			},
			600: {
				items: 3,
				nav: true
			},
			1000: {
				items: 4,
				nav: true,
				loop: true
			}
		}
	});

	/* ..............................................
	   Scroll
	   ................................................. */

	$(document).ready(function() {
		$(window).on('scroll', function() {
			if ($(this).scrollTop() > 100) {
				$('#back-to-top').fadeIn();
			} else {
				$('#back-to-top').fadeOut();
			}
		});
		$('#back-to-top').click(function() {
			$("html, body").animate({
				scrollTop: 0
			}, 600);
			return false;
		});
	});


	/* ..............................................
	   Slider Range
	   ................................................. */

	$(function() {
		$("#slider-range").slider({
			range: true,
			min: 0,
			max: 4000,
			values: [1000, 3000],
			slide: function(event, ui) {
				$("#amount").val("$" + ui.values[0] + " - $" + ui.values[1]);
			}
		});
		$("#amount").val("$" + $("#slider-range").slider("values", 0) +
			" - $" + $("#slider-range").slider("values", 1));
	});

	/* ..............................................
	   NiceScroll
	   ................................................. */

	$(".brand-box").niceScroll({
		cursorcolor: "#9b9b9c",
	});
	
	
}(jQuery));

// function showSuggestions(){
// 	let query = $("#searchInput").val();
// 	$.get("/api/suggest-products?query=" + query, function (data) {
// 		var suggestionList = document.getElementById("suggestionList");
// 		suggestionList.innerHTML = "";
// 		data.forEach(function (suggestion) {
// 			var suggestionItem = document.createElement("div");
// 			suggestionItem.innerHTML = suggestion;
// 			suggestionList.appendChild(suggestionItem);
// 		});
// 	});
//
// }
// let previousQuery = null;
function displaySuggestions(suggestions) {
	let suggestionList = document.getElementById("searchSuggestions");

	suggestionList.innerHTML = "";

	if (Array.isArray(suggestions)) {
		suggestions.forEach((suggestion,index) => {
			let item = document.createElement("div");
			let divId = "suggestionItem" + index;

			item.textContent = suggestion;
			item.id = divId;
			item.className = "list-group-item";
			item.addEventListener("click", () => {
				document.getElementById("searchInput").value = suggestion;
				suggestionList.innerHTML = "";
			});

			suggestionList.appendChild(item);
		});
	}
}
// Hàm lấy gợi ý từ API


async function fetchSuggestions() {

	let query = document.getElementById("searchInput").value;
	let url = '/shop/api/suggest-products?query=' + query;

	let response = await fetch(url);
	let suggestions = await response.json();
	// console.log(suggestions);
	if (query.length > 2) {
		displaySuggestions(suggestions);
		console.log(suggestions);
	}

	if (query.length == 0){
		for (let i = 0; i < suggestions.length; i++) {
			console.log("suggestionItem" + i);
			document.getElementById("suggestionItem" + i).remove();
		}
	}

}
function displaySuggestionsIngredient(suggestions, ingredientId) {
	let suggestionRowId = 'suggest' + ingredientId; // Construct the ID for the suggestion row
	let suggestionRow = document.getElementById(suggestionRowId);

	suggestionRow.innerHTML = ""; // Clear the existing content

	if (Array.isArray(suggestions)) {
		suggestions.forEach((suggestion, index) => {
			let item = document.createElement("div");
			let divId = `suggestionItem${index}`;

			item.textContent = suggestion;
			item.id = divId;
			item.className = "list-group-item";
			item.addEventListener("click", () => {
				document.getElementById(`${ingredientId}`).value = suggestion;
				suggestionRow.innerHTML = ""; // Clear the suggestion list
			});

			suggestionRow.appendChild(item);
		});
	}
}


async function fetchSuggestionsIngredient(ingredientId) {
	let query = document.getElementById(`${ingredientId}`).value;
	let url = '/shop/api/suggest-products?query=' + query;

	let response = await fetch(url);
	let suggestions = await response.json();
	// console.log(suggestions);
	if (query.length > 2) {
		displaySuggestionsIngredient(suggestions, ingredientId);
		console.log(suggestions);
	}

	if (query.length == 0){
		for (let i = 0; i < suggestions.length; i++) {
			console.log("suggestionItem" + i);
			document.getElementById("suggestionItem" + i).remove();
		}
	}

}

// Hiển thị gợi ý

//
// // Lắng nghe sự kiện gõ vào ô input
// const input = document.getElementById("searchInput");
// input.addEventListener("input", () => {
//
// 	let query = input.value;
//
// 	if(query.length > 2) {
// 		fetchSuggestions(query);
// 	// }else if(query.length == 0) {
// 	// 	document.getElementById("searchSuggestions")?.remove();
// 	}else {
// 		document.getElementById("searchSuggestions").remove();
// 	}
//
// });

// suggest/ recipe

function displaySuggestionsRecipe(suggestions) {
	let suggestionList = document.getElementById("search-recipe-suggest");

	suggestionList.innerHTML = "";

	if (Array.isArray(suggestions)) {
		suggestions.forEach((suggestion,index) => {
			let item = document.createElement("div");
			let divId = "suggestionItem" + index;

			item.textContent = suggestion;
			item.id = divId;
			item.className = "list-group-item";
			item.addEventListener("click", () => {
				document.getElementById("search-recipe").value = suggestion;
				suggestionList.innerHTML = "";
			});

			suggestionList.appendChild(item);
		});
	}
}
// Hàm lấy gợi ý từ API


async function fetchSuggestionsRecipe() {

	let query = document.getElementById("search-recipe").value;
	let url = '/shop/api/suggest-recipes?query=' + query;

	let response = await fetch(url);
	let suggestions = await response.json();
	// console.log(suggestions);
	if (query.length > 2) {
		displaySuggestionsRecipe(suggestions);
		console.log(suggestions);
	}

	if (query.length == 0){
		for (let i = 0; i < suggestions.length; i++) {
			console.log("suggestionItem" + i);
			document.getElementById("suggestionItem" + i).remove();
		}
	}


}