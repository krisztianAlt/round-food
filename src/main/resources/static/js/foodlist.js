var app = app || {};

app.init = function() {
	app.foodListHandler.getFoodIdFromFoodSelector();
};

app.foodListHandler = {
		
		getFoodIdFromFoodSelector: function(){
			var foodHolder = document.getElementById('food-holder');
			var images = foodHolder.getElementsByClassName('card-img-top');
			var cardTitles = foodHolder.getElementsByClassName('card-title');
			
			for (index = 0; index < images.length; index++){
				images[index].addEventListener("click", function() {			
					var foodId = this.getAttribute("data-food-id");
					app.foodEntitiesHandler.getFoodDataFromServer(foodId);
				}); 
			}
			
			for (index = 0; index < cardTitles.length; index++){
				cardTitles[index].addEventListener("click", function() {
					var foodId = this.getAttribute("data-food-id");
					app.foodEntitiesHandler.getFoodDataFromServer(foodId);
				}); 
			}
		}
		
}

$(document).ready(app.init());