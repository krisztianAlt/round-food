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
					app.foodEntitiesHandler.openFoodModal(foodId);
				}); 
			}
			
			for (index = 0; index < cardTitles.length; index++){
				cardTitles[index].addEventListener("click", function() {
					var foodId = this.getAttribute("data-food-id");
					app.foodEntitiesHandler.openFoodModal(foodId);
				}); 
			}
			
			
		},
		
		openFoodModal: function (foodId){
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
	            	console.log(response);
	            	console.log('Response from API: ' + response.foodData.name);
	                // var success = response.succeeded;
	                var foodName = response.foodData.name;
	                console.log(foodName);
	                /*if (success && articles.length!=0) {
	                    $('#newsModal').modal('toggle');
	                    app.mapHandling.listNews(articles, countryName, selectedNewsAgency);
	                } else if (success && articles.length==0) {
	                    $('#messageModal').modal('toggle');
	                    var modalTitle = document.getElementById("messageModalLabel");
	                    modalTitle.textContent = "There aren't articles about " + countryName + ". Please, try to choose another country or news agency.";
	                } else {
	                    $('#messageModal').modal('toggle');
	                    var modalTitle = document.getElementById("messageModalLabel");
	                    modalTitle.textContent = "Sorry, problem occured, please, try later.";
	                };*/
	            },
	            error: function() {
	                console.log('ERROR: endpoint calling failed.');
	            }
	        });
			
			$("#foodModal").modal("toggle");
		}
		
}

$(document).ready(app.init());