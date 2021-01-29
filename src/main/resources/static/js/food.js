var app = app || {};

app.init = function() {
    app.foodEntitiesHandler.getFoodIdFromHTML();
};

app.foodEntitiesHandler = {
	
		getFoodIdFromHTML: function (){
			var images = document.getElementsByClassName('card-img-top');
			var cardTitles = document.getElementsByClassName('card-title');
			
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
			
			
		},
		
		getFoodDataFromServer: function (foodId){
			// var foodName = document.getElementById('food-name');
			// foodName.innerText = foodId;
			
			var dataPackage = {'foodId': foodId};
	        $.ajax({
	            url: '/food',
	            type: 'POST',
	            contentType: "application/json; charset=utf-8",
	            data: JSON.stringify(dataPackage),
	            dataType: 'json',
	            success: function(response) {
	            	app.foodEntitiesHandler.openFoodModal(response.foodData);
	            },
	            error: function() {
	                console.log('ERROR: endpoint calling failed.');
	                var foodDescription = document.getElementById('food-description');
	                foodDescription.innerText = "Sorry, problem occured, please, try later.";
	                $("#foodModal").modal("toggle");
	            }
	        });
		},
		
		openFoodModal: function(foodData){
        	var id = foodData.id;
            var name = foodData.name;
            var description = foodData.description;
            var price = foodData.price;
            var pictures = foodData.foodPictures;
            var toppings = foodData.extraToppings;
            
            var foodModalLabel = document.getElementById('foodModalLabel');
            foodModalLabel.innerText = name;
            var descriptionParagraph = document.getElementById('food-description');
            descriptionParagraph.innerText = "Description: " + description;
            var priceParagraph = document.getElementById('food-price');
            priceParagraph.innerText = "Price: " + price + " Ft";
            
            // delete previous images and indicators from carousel
            $("#car-indicators").empty();
            $("#car-inner").empty();
            
            // put new images and indicators into carousel  
            var carouselIndicatorsList = document.getElementsByClassName("carousel-indicators")[0];
            var carouselImageHolder = document.getElementsByClassName("carousel-inner")[0];
            for (index = 0; index < pictures.length; index++){
            	var newLiElement = document.createElement('li');
            	var newImageDiv = document.createElement('div');
            	
            	if (index == 0){
            		newLiElement.className = "active";
            		newImageDiv.className = "carousel-item active";
            	} else {
            	
            		newImageDiv.className = "carousel-item";
            	}
            	
            	newLiElement.setAttribute("data-bs-target", "#carouselFoodIndicators");
            	newLiElement.setAttribute("data-bs-slide-to", index);
            	carouselIndicatorsList.appendChild(newLiElement);
            	
            	var newImage = document.createElement('img');
            	newImage.setAttribute("src", "/picture_collection/" + pictures[index].fileName);
            	newImage.setAttribute("class", "d-block w-100");
            	newImage.setAttribute("alt", pictures[index].fileName);
            	var titleDiv = document.createElement('div');
            	titleDiv.setAttribute("class", "carousel-caption d-block");
            	var titleParagraph = document.createElement('p');
            	titleParagraph.innerText = pictures[index].title;
            	titleDiv.appendChild(titleParagraph);
            	newImageDiv.appendChild(newImage);
            	newImageDiv.appendChild(titleDiv);
            	carouselImageHolder.appendChild(newImageDiv);
            }
            
            $("#foodModal").modal("toggle");
		}
		
}

$(document).ready(app.init());