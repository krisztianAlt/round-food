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
            
            // delete previous images and indicators from carousel
            $("#car-indicators").empty();
            $("#car-inner").empty();
            
            // put new images and indicators into carousel  
            var carouselIndicatorsList = document.getElementsByClassName("carousel-indicators")[0];
            var carouselImageHolder = document.getElementsByClassName("carousel-inner")[0];
            
            var index;
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
            
            var descriptionParagraph = document.getElementById('food-description');
            descriptionParagraph.innerText = "Description: " + description;
            
            // delete previous price row:
            $("#food-price").empty();
            
        	// new price row:
            var priceParagraph = document.getElementById('food-price');
            var rowDiv = document.createElement("div");
            rowDiv.setAttribute("class", "row");
            var priceTitle = document.createElement("div");
            priceTitle.setAttribute("class", "col-7");
            priceTitle.innerText = "Price:";
            var priceValue = document.createElement("div");
            priceValue.setAttribute("class", "col-4 price-col");
            priceValue.innerText = price;
            var currency = document.createElement("div");
            currency.setAttribute("class", "col-1");
            currency.innerText = "Ft";
            rowDiv.appendChild(priceTitle);
            rowDiv.appendChild(priceValue);
            rowDiv.appendChild(currency);
            priceParagraph.appendChild(rowDiv);
            
            // delete previous extra toppings:
            $("#extra-toppings-container").empty();
            
            // put new extra toppings into their div:
            if (toppings.length > 0){
            	var extraToppingsContainer = document.getElementById("extra-toppings-container");
            	
            	var extrasTitle = document.createElement('p');
            	extrasTitle.innerText = "Extra toppings, garnishment etc.:";
            	extraToppingsContainer.appendChild(extrasTitle);
            	
            	var toppingsIndex;
            	for (toppingsIndex = 0; toppingsIndex < toppings.length; toppingsIndex++){
            		var toppingRowDiv = document.createElement("div");
            		toppingRowDiv.setAttribute("class", "row");
            		if (toppingsIndex == toppings.length-1){
            			toppingRowDiv.setAttribute("id", "last-topping-row");
            		}
            		
            		var inputFieldDiv = document.createElement('div');
            		inputFieldDiv.setAttribute("class", "col-1");
            		var inputField = document.createElement('input');
            		inputField.setAttribute("class", "form-check-input");
            		inputField.setAttribute("type", "checkbox");
            		inputField.setAttribute("value", toppings[toppingsIndex].id);
            		inputField.setAttribute("data-price", toppings[toppingsIndex].price);
            		inputField.setAttribute("id", "flexCheck" + toppingsIndex);
            		inputFieldDiv.appendChild(inputField);
            		
            		var toppingName = document.createElement('div');
            		toppingName.setAttribute("class", "col-6");
            		toppingName.innerText = toppings[toppingsIndex].name;
            		
            		var toppingPriceValue = document.createElement("div");
            		toppingPriceValue.setAttribute("class", "col-4 price-col");
            		toppingPriceValue.innerText = toppings[toppingsIndex].price;
                    
            		var toppingCurrency = document.createElement("div");
            		toppingCurrency.setAttribute("class", "col-1");
            		toppingCurrency.innerText = "Ft";
            		
            		toppingRowDiv.appendChild(inputFieldDiv);
            		toppingRowDiv.appendChild(toppingName);
            		toppingRowDiv.appendChild(toppingPriceValue);
            		toppingRowDiv.appendChild(toppingCurrency);
           		
            		extraToppingsContainer.appendChild(toppingRowDiv);
            	}
            }
            
            // delete previous total price row:
            $("#sum-total").empty();
            
            // new sum-total row:
            var sumTotalParagraph = document.getElementById('sum-total');
            var sumRowDiv = document.createElement("div");
            sumRowDiv.setAttribute("class", "row");
            var sumPriceTitle = document.createElement("div");
            sumPriceTitle.setAttribute("class", "col-7");
            sumPriceTitle.innerText = "Total price:";
            var sumPriceValue = document.createElement("div");
            sumPriceValue.setAttribute("id", "sum-total-value");
            sumPriceValue.setAttribute("class", "col-4 price-col");
            sumPriceValue.innerText = price;
            sumPriceValue.setAttribute("data-totalprice", price);
            var sumCurrency = document.createElement("div");
            sumCurrency.setAttribute("class", "col-1");
            sumCurrency.innerText = "Ft";
            sumRowDiv.appendChild(sumPriceTitle);
            sumRowDiv.appendChild(sumPriceValue);
            sumRowDiv.appendChild(sumCurrency);
            sumTotalParagraph.appendChild(sumRowDiv);           
            
            // watch checkboxes' changing:
            app.foodEntitiesHandler.listenToppingSelection();
            
            $("#foodModal").modal("toggle");
		},
		
		listenToppingSelection: function () {
	        $('input[class=form-check-input]').change(function(){
	            var totalPrice = $('#sum-total-value').data("totalprice");
	            var newTotalPrice;
	            var price = $(this).data("price");
	            if($(this).is(':checked')) {
	                newTotalPrice = (parseFloat(totalPrice) + parseFloat(price));
	            } else {
	                newTotalPrice = (parseFloat(totalPrice) - parseFloat(price));
	            }
	            $("#sum-total-value").text(String(newTotalPrice));
	            $("#sum-total-value").data("totalprice", String(newTotalPrice));
	        });
	    }
		
}

$(document).ready(app.init());