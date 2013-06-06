var map = function (){
	// set up objects
	// array to hold this.DateCost.time
	var times = new Array();
	// array to hold this.DateCost.cost
	var moneySpent = new Array();
	// array to get the values of days apart!
	var daysApart = new Array();
	// object needed for 
	var reduceVal = new Object();
	
	// iterate through all subdocuements of main doc. This case it is DateCost ;-) 
	for(var index = 0; index < this.dateCost.length; index++){
		// at each indexed time of DateCost.time grab the milliseconds out!
		// and store in times array!
		times.push(this.dateCost[index].date);
		// at each indexed cost, grab the cost and store in moneySpent array! 
		moneySpent.push(this.dateCost[index].cost);
	}
	// sort the times by day,  so we can find time
	// between each purchase
	times.sort(function(a,b){return a-b});
	for(var index = 0; index < times.length - 1; index++){
		// 1000 * 60 *60 * 24 is gothe amount of milliseconds in a day
		// taking the two numbers and finding the rounded value returns total days
		// in an integer form!!
		daysApart.push(Math.round(Math.abs(times[index] - times[index + 1]) / (1000 * 60 * 60 * 24)));
	}
	// sort the arrays before storing them into the object
	daysApart.sort(function(a,b){return a-b});
	moneySpent.sort(function(a,b){return a-b});
	// add arrays to object
	reduceVal.days = daysApart;
	reduceVal.cost = moneySpent;
	// store last purchase into object
	reduceVal.dateOfLastPurchase = this.dateCost[this.dateCost.length - 1].date;
		// multiple references are made to the length.
	// easier to store as a variable.
	var amountOfDays = reduceVal.days.length;
	var amountOfCost = reduceVal.cost.length;
	// sums of days and cost!
	var sumOfDays = 0;
	var sumOfCost = 0;
	// check if the number is odd or even
	reduceVal.medianDay = (amountOfDays % 2 == 0) ? 
	// if even, take the middle number  and the next lowest.
	// divide by 2, this returns the Median!
		(reduceVal.days[amountOfDays / 2] + reduceVal.days[(amountOfDays / 2) - 1]) / 2 : 
	// if odd, take the middle, and FLOOR it (as indexes are zero based in javascript)
		reduceVal.days[Math.floor(amountOfDays / 2)];
	// add the total days
	for(var index = 0; index < amountOfDays; index++){
		sumOfDays += reduceVal.days[index];
	}
	// divide sum of days / amountOfDays to get the mean!
	reduceVal.meanDay = sumOfDays / amountOfDays;
	// check if the number of costs is odd or even
	reduceVal.medianCost = (amountOfCost % 2 == 0) ? 
	// if even, take the middle number  and the next lowest.
	// divide by 2, this returns the Median!
		(reduceVal.cost[amountOfCost / 2] + reduceVal.cost[(amountOfCost / 2) - 1]) / 2 : 
	// if odd, take the middle, and FLOOR it (as indexes are zero based in javascript)
		reduceVal.cost[Math.floor(amountOfCost / 2)];
	// get the sum of costs
	for(var index = 0; index < amountOfCost; index++){
			sumOfCost += reduceVal.cost[index];
		}
	// divide sum of cost / total amount of costs to get the mean!
	reduceVal.meanCost = sumOfCost / amountOfCost;
	
	reduceVal.datesSpendingAboveMean = new Array();
	reduceVal.datesSpendingAboveMedian = new Array();
	for(var index = 0; index < reduceVal.days; index++){
		if(reduceVal.cost[index] > reduceVal.meanDay){
			reduceVal.datesSpendingAboveMean.push(reduceVal.days[index]);
		}		
		if(reduceVal.cost[index] > reduceVal.medianDay){
			reduceVal.datesSpendingAboveMedian.push(reduceVal.days[index]);
		}
	}
	// remove objects not needed for the docuement model
	delete reduceVal.days;
	delete reduceVal.cost;
	// return the docuement back
		// calls the emit function, which will transfer to the reduce!
	emit(this.customerId, reduceVal);
}

var reduce = function(customerId, reduceVal){}

