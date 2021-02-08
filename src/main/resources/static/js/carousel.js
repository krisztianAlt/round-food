var app = app || {};

app.init = function() {
	app.carouselHandler.getFoodIdFromCarousel();
};

app.carouselHandler = {
		
		getFoodIdFromCarousel: function(){
			var carouselItems = document.getElementsByClassName('carousel-inner')[0];
			var carouselImages = carouselItems.getElementsByTagName('img');
			
			for (var index = 0; index < carouselImages.length; index++){
				carouselImages[index].addEventListener("click", function() {			
					var foodId = this.getAttribute("data-food-id");
					console.log(foodId);
					app.foodEntitiesHandler.getFoodDataFromServer(foodId);
				}); 
			}
		}

}

$(document).ready(app.init());