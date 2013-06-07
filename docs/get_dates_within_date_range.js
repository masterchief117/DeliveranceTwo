// how to query for a value in a document
// stored in a subdocument
// this is for a document that looks like.. 
//{
//        "_id" : ObjectId("51b1df32909be03cd5cdce71"),
//        "customerId" : 101,
//        "dateCost" : [
//                {
//                        "cost" : 349.2,
//                        "date" : NumberLong("1187300052000")
//                },
//                {
//                        "cost" : 355.7,
//                        "date" : NumberLong("1191327516000")
//                },
//                {
//                        "cost" : 351.9,
//                        "date" : NumberLong("1206819244000")
//                },
//                {
//                        "cost" : 204,
//                        "date" : NumberLong("1217170750000")
//                }
//        ]
//}
// call the aggregate function
db.sales.aggregate(
[
// unwind will split off all the subdocuments. Don't forget the $
{$unwind : '$dateCost'} , 
// match asks "Does this match the boolean", if so, returns!
{$match : 
	// for each element... in this case it is dateCost.date
	// we are measuring against
	{'dateCost.date' : 
	// $gte (greater-than-or-equal)
	// $lte (less-than-or-equal)
	// firstTime and lastTime will need to be a long value (millis)
	{$gte : firstTime, $lte : lastTime}
	}
}])